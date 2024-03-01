#ifndef OMD_HPP
#define OMD_HPP

#include <functional>
#include <exception>
#include <thread>
#include <map>
#include <tuple>

#include <boost/asio/io_context.hpp>
#include <boost/asio/ip/tcp.hpp>
#include <boost/asio/ip/udp.hpp>
#include <boost/asio/ip/multicast.hpp>
#include <boost/asio/write.hpp>
#include <boost/asio/read.hpp>
#include <boost/asio/buffer.hpp>
#include <boost/type_index.hpp>
#include <boost/algorithm/string.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include <cryptopp/cryptlib.h>
#include <cryptopp/chacha.h>
#include <cryptopp/secblock.h>
#include <cryptopp/filters.h>

namespace wats::online_market_data::messages {
#include "wats/generated/market_data.hpp"
}

namespace wats::online_market_data::replay {
#include "wats/generated/replay.hpp"
}

#include "common.hpp"
#include "decrypt.hpp"

using namespace std;
using namespace std::placeholders;
using namespace boost::system;
using namespace boost::asio;
using namespace boost::asio::ip;

namespace omd = wats::online_market_data;

namespace wats::online_market_data {

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
        virtual void operator()(const std::any& data, const EventSource source) = 0;
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

        void operator()(const std::any& data, const EventSource source) override {
            wrappedObject(std::any_cast<arg_type>(data), source);
        }

        T wrappedObject;
    };

    bool operator()(const std::type_info& type) {
        return (*wrappedCallback)(type);
    }

    void operator()(const std::any& data, const EventSource source) {
        return (*wrappedCallback)(data, source);
    }
};

template <typename T>
const omd::messages::Message& to_message(const T& t) {
    return reinterpret_cast<const omd::messages::Message&>(&t);
};

template <typename T>
const T& from_message(const omd::messages::Message& m) {
    return reinterpret_cast<const T&>(m);
};

class online_market_data_exception : exception {
private:
	std::string m_error{};
public:
	online_market_data_exception(std::string_view error): m_error{error} {}

	const char* what() const noexcept override { return m_error.c_str(); }
};

class MarketData {
private:
    io_context& io_context_;
    std::string token_;
    uint16_t connectionId_;
    tcp::socket socket_snapshot_;
    udp::socket socket_stream_;
    tcp::socket socket_replay_;
    SessionState state_;
    omd::messages::Message message_snapshot_;
    omd::messages::Message message_stream_;
    omd::messages::Message message_replay_;
    std::string replay_host_;
    uint16_t replay_port_;
    std::map<omd::messages::MsgType,
        std::function<std::any(const omd::messages::Message&)>> dispatch_table_;
    std::vector<CallbackWrapper> callback_wrappers_;
    Decryption decryption_;
    uint64_t expectedSeqNum_;
    udp::endpoint sender_;

    void dispatch(omd::messages::Message &message, EventSource source);

    void read_snapshot();
    void read_stream();
    void read_replay();

    template <typename T>
    void send(const T& t) {
        write(socket_snapshot_, buffer((char*) &t, sizeof(T)));

        spdlog::debug("{} sent {{ seqNum: {}, msgType: {}, length: {} }}",
            omd::messages::MsgType2Name.find(t.header.msgType)->second,
            (uint32_t)t.header.seqNum, (uint16_t)t.header.msgType, t.header.length);
    }

    void login(std::string token, uint16_t connectionId);
    void logout();
public:
    MarketData(io_context& io_context);
    ~MarketData();

    void replay_request(omd::messages::SeqNum from, omd::messages::SeqNum to);

    /// @brief Method to assign a handler for particular type of MD message.
    /// @tparam Callback
    /// @param cb Calback method as lambda or functor.
    template <typename Callback>
    void handle(Callback cb) {
        static_assert(function_traits<Callback>::arity == 2, "Two arguments");
        typedef typename function_traits<Callback>::template arg<0> arg_type_cv_ref;
        typedef typename std::remove_reference<arg_type_cv_ref>::type arg_type_cv;
        typedef typename std::remove_cv<arg_type_cv>::type arg_type;

        // Get the last element of fully qualified type name
        std::string fqname = boost::typeindex::type_id<arg_type>().pretty_name();
        vector<string> parts;
        boost::split(parts, fqname, boost::is_any_of("::"));

        // Assign callback using the name to message type matching helper
        omd::messages::MsgType mt = omd::messages::Name2MsgType.find(parts.back())->second;
        dispatch_table_[mt] = from_message<arg_type>;
        callback_wrappers_.emplace_back(cb);
    }

    /// @brief Initialize Market Data connection.
    /// @param config Node of YAML configuration file.
    void start(const YAML::Node &config);

    /// @brief Initialize Market Data connection.
    /// @param snapshotHost IP address (numeric or symbolic) of the Market Data snapshot link.
    /// @param snapshotPort Port number of the Market Data snapshot link.
    /// @param streamHost IP address (numeric or symbolic) of the Market Data stream (UDP) link.
    /// @param streamPort Port number of the Market Data stream (UDP) link.
    /// @param replayHost IP address (numeric or symbolic) of the Market Data replay link.
    /// @param replayPort Port number of the Market Data replay link.
    /// @param token Unique token received during the registration proces.
    /// @param connectionId Connection ID received during the registration proces.
    void start(const std::string snapshotHost, const uint16_t snapshotPort,
        const std::string streamHost, const uint16_t streamPort,
        const std::string streamInterface,
        const std::string replayHost, const uint16_t replayPort,
        const std::string &token, const uint16_t connectionId);

    /// @brief Finalize Market Data connection.
    void stop();
};

}

#endif // OMD_HPP
