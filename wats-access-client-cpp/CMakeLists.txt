cmake_minimum_required(VERSION 3.24)

project(trader)

set(gcc_like_cxx "$<COMPILE_LANG_AND_ID:CXX,Clang,GNU>")
set(msvc_cxx "$<COMPILE_LANG_AND_ID:CXX,MSVC>")

# set(CONAN_CMAKE_SILENT_OUTPUT 1)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(CMAKE_MODULE_PATH ${CMAKE_BINARY_DIR})
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY $<1:${CMAKE_SOURCE_DIR}/bin>)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_RUNTIME_OUTPUT_DIRECTORY})

set(BUILD_TESTS on)

# find packages
find_package(Boost 1.80.0 REQUIRED)
find_package(cryptopp 8.6.0 REQUIRED)
find_package(spdlog 1.10.0 REQUIRED)
find_package(yaml-cpp 0.7.0 REQUIRED)

set(WATS_SRC "${CMAKE_CURRENT_SOURCE_DIR}/src/wats")
set(WATS_INCLUDE "${CMAKE_CURRENT_SOURCE_DIR}/include/wats")

set(TESTS_SRC "${CMAKE_CURRENT_SOURCE_DIR}/src")
set(TESTS_INCLUDE "${CMAKE_CURRENT_SOURCE_DIR}/include")

add_compile_options(
    "$<${gcc_like_cxx}:$<BUILD_INTERFACE:>>"
    "$<${msvc_cxx}:$<BUILD_INTERFACE:-bigobj;-D_WIN32_WINNT=0x0601>>"
)

if(BUILD_TESTS)

    add_library(wats STATIC
        ${WATS_SRC}/decrypt.cpp
        ${WATS_SRC}/omd.cpp
        ${WATS_SRC}/bbo.cpp
        ${WATS_SRC}/btp.cpp
    )

    target_include_directories(wats
        PUBLIC ${TESTS_INCLUDE}
        PUBLIC ${TESTS_INCLUDE}/generated
    )

    target_precompile_headers(wats
        PRIVATE ${WATS_INCLUDE}/common.hpp
        PRIVATE ${WATS_INCLUDE}/decrypt.hpp
        PRIVATE ${WATS_INCLUDE}/wats.hpp
    )

    target_link_libraries(wats
        Boost::program_options
        Boost::filesystem
        cryptopp::cryptopp
        spdlog::spdlog
        yaml-cpp
        ${CMAKE_DL_LIBS}
    )

    enable_testing()

    file(GLOB TEST_SOURCES ${TESTS_SRC}/*.cpp)

    foreach(_SRC ${TEST_SOURCES})

        get_filename_component(testName ${_SRC} NAME_WE)

        add_executable(${testName} ${_SRC})

        target_precompile_headers(${testName} REUSE_FROM wats)

        target_include_directories(${testName} PRIVATE ${TESTS_INCLUDE})

        target_link_libraries(${testName} wats)

        set_target_properties(${testName} PROPERTIES
            RUNTIME_OUTPUT_DIRECTORY ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}
        )

        target_compile_features(${testName} INTERFACE cxx_std_17)

        add_test(
            NAME ${testName}
            COMMAND ${testName}
            WORKING_DIRECTORY ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}
        )

    endforeach(_SRC)

endif()