#ifndef COMMON_HPP
#define COMMON_HPP

#include <iostream>
#include <any>
#include <memory>
#include <type_traits>

#include <boost/asio/io_context.hpp>
#include <boost/asio/ip/tcp.hpp>

namespace wats {

enum class SessionState {
    disconnected,
    connecting,
    connected,
    disconnecting,
    shutdown
};

enum class EventSource {
    snapshot,
    stream,
    replay
};

}

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

inline boost::asio::ip::tcp::endpoint resolve(
    boost::asio::io_context& io_context,
    const std::string address, const unsigned short port,
    boost::system::error_code& ec) {
    boost::asio::ip::tcp::resolver resolver(io_context);
    boost::asio::ip::tcp::resolver::results_type results =
        resolver.resolve(boost::asio::ip::tcp::v4(),
        address, std::to_string(port), ec);
    return results->endpoint();
};

#endif // COMMON_HPP
