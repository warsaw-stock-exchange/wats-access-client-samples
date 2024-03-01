#ifndef BTP_HPP
#define BTP_HPP

#include <functional>
#include <exception>
#include <thread>

#include <boost/asio/io_context.hpp>
#include <boost/asio/ip/tcp.hpp>
#include <boost/asio/write.hpp>
#include <boost/asio/read.hpp>
#include <boost/asio/buffer.hpp>
#include <boost/type_index.hpp>
#include <boost/algorithm/string.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

namespace wats::trading_port::messages {
#include "wats/generated/trading_port.hpp"
}

#include "common.hpp"

using namespace std;
using namespace std::placeholders;
using namespace boost::system;
using namespace boost::asio;
using namespace boost::asio::ip;

namespace btp = wats::trading_port;

namespace wats::trading_port {

template <typename Function>
struct function_traits :
    public function_traits<decltype(&std::remove_reference<Function>::type::operator())> { };

template <typename ClassType, typename ReturnType, typename... Arguments>
struct function_traits<ReturnType(ClassType::*)(Arguments...) const> :
    function_traits<ReturnType(*)(Arguments...)> { };

template <typename ClassType, typename ReturnType, typename... Arguments>
struct function_traits<ReturnType(ClassType::*)(Arguments...)> :
    function_traits<ReturnType(*)(Arguments...)> { };

template <typename ReturnType, typename... Arguments>
struct function_traits<ReturnType(*)(Arguments...)> {
    typedef ReturnType result_type;

    template <std::size_t Index>
    using arg = typename std::tuple_element<
        Index,
        std::tuple<Arguments...>
    >::type;

    static constexpr std::size_t arity = sizeof...(Arguments);
};

struct CallbackWrapper {
    template <typename T>
    CallbackWrapper(T&& obj) :
        wrappedCallback(std::make_unique<Wrapper<typename std::remove_reference<T>::type>> (std::forward<T>(obj))) { }

    struct CallbackBase {
        virtual bool operator()(const std::type_info& type) const = 0;
        virtual void operator()(const std::any& data) = 0;
        virtual ~CallbackBase() {}
    };

    std::unique_ptr<CallbackBase> wrappedCallback;

    template<typename T>
    struct Wrapper: public CallbackBase {

        typedef typename function_traits<T>::template arg<0> arg_type;

        Wrapper(const T& t) :
            wrappedObject(t) {}

        bool operator()(const std::type_info& type) const override {
            return (typeid(arg_type) == type);
        }

        void operator()(const std::any& data) override {
            wrappedObject(std::any_cast<arg_type>(data));
        }

        T wrappedObject;
    };

    bool operator()(const std::type_info& type) {
        return (*wrappedCallback)(type);
    }

    void operator()(const std::any& data) {
        return (*wrappedCallback)(data);
    }
};

template <typename T>
const btp::messages::Message& to_message(const T& t) {
    return reinterpret_cast<const btp::messages::Message&>(&t);
};

template <typename T>
const T& from_message(const btp::messages::Message& m) {
    return reinterpret_cast<const T&>(m);
};

class trading_port_exception: exception {
private:
	std::string m_error{};
public:
	trading_port_exception(std::string_view error): m_error{error} {}

	const char* what() const noexcept override { return m_error.c_str(); }
};

class TradingPort {
private:
    io_context& io_context_;
    tcp::socket socket_;
    uint32_t outSeqNum_;
    uint32_t inSeqNum_;
    std::string token_;
    uint16_t connectionId_;
    uint32_t sessionId_;
    SessionState state_;
    btp::messages::Message message_;
    std::map<btp::messages::MsgType,
        std::function<std::any(const btp::messages::Message&)>> dispatch_table_;
    std::vector<CallbackWrapper> callback_wrappers_;

    void read();

    void dispatch();

    template <typename T>
    void send(const T& t) {
        write(socket_, buffer((char*) &t, sizeof(T)));

        spdlog::debug("{} sent {{ SeqNum: {}, MsgType: {}, length: {} }}",
            btp::messages::MsgType2Name.find(t.header.msgType)->second,
            (uint32_t)t.header.seqNum, (uint16_t)t.header.msgType, t.header.length);
    }

    inline btp::messages::OrderId createOrderId(uint16_t seqNum,
        uint16_t sessionId, uint16_t connectionId) {
        return ((btp::messages::OrderId)seqNum << 0) |
               ((btp::messages::OrderId)sessionId << 36) |
               ((btp::messages::OrderId)connectionId << 50);
    }

public:
    TradingPort(io_context& io_context);
    ~TradingPort();

    /// @brief Method to assign a handler for particular type of MD message.
    /// @tparam Callback 
    /// @param cb Calback method as lambda or functor.
    template <typename Callback>
    void handle(Callback cb) {
        static_assert(function_traits<Callback>::arity == 1, "Two arguments");
        typedef typename function_traits<Callback>::template arg<0> arg_type_cv_ref;
        typedef typename std::remove_reference<arg_type_cv_ref>::type arg_type_cv;
        typedef typename std::remove_cv<arg_type_cv>::type arg_type;

        // Get the last element of fully qualified type name
        std::string fqname = boost::typeindex::type_id<arg_type>().pretty_name();
        vector<string> parts;
        boost::split(parts, fqname, boost::is_any_of("::"));

        // Assign callback using the name to message type matching helper
        btp::messages::MsgType mt = btp::messages::Name2MsgType.find(parts.back())->second;
        dispatch_table_[mt] = from_message<arg_type>;
        callback_wrappers_.emplace_back(cb);
    }

    /// @brief Initialize Trading Port connection.
    /// @param config Node of YAML configuration file.
    void start(const YAML::Node &config);

    /// @brief Initialize Trading Port connection.
    /// @param host IP address (numeric or symbolic) of the Trading Port link.
    /// @param port Port number of the Trading Port link.
    /// @param token Unique token received during the registration proces.
    /// @param connectionId Connection ID received during the registration proces.
    void start(const std::string &address, const unsigned short port,
        const std::string &token, const uint16_t connectionId);

    /// @brief Finalize Trading Port connection.
    void stop();

    /// @brief Login to the system
    /// @param token Token used to authenticate to the system
    /// @param connectionId Connection ID assigned by the service provider
    void login(std::string token, uint16_t connectionId);

    /// @brief Log out of the system
    void logout();

    /// @brief Add order to the system.
    /// @param message OrderAdd message structure. Has to be prepared by the user, header fields wil get updated by the library.
    /// @param OrderId Reference ID for the order submitted.
    void orderAdd(btp::messages::OrderAdd message, btp::messages::OrderId& OrderId);

    /// @brief Modify order earlier submitted to the system.
    /// @param message OrderModify structure. Has to be prepared by the user, header fields wil get updated by the library.
    void orderModify(btp::messages::OrderModify message);

    /// @brief Cancel order earlier submitted to the system.
    /// @param message OrderCancel message structure. Has to be prepared by the user, header fields wil get updated by the library.
    void orderCancel(btp::messages::OrderCancel message);
};

}

#endif // BTP_HPP
