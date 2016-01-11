cmake_minimum_required(VERSION 3.1)
project(PosClient)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}")

if(UNIX)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Os -s -DQT_COMPILING_QSTRING_COMPAT_CPP")
endif()

include_directories("${CMAKE_CURRENT_SOURCE_DIR}/include")

if(UNIX)
include_directories("/usr/lib/jvm/java-7-oracle/include")
include_directories("/usr/lib/jvm/java-7-oracle/include/linux")
else()
include_directories("C:\\Program Files\\Java\\jdk1.7.0_79\\include")
include_directories("C:\\Program Files\\Java\\jdk1.7.0_79\\include\\win32")
endif()

if(${ARCH} MATCHES "X86")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -m32")
endif()

if(WIN32)
    if(${ARCH} MATCHES "X86")
		SET (CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} /SAFESEH:NO")
        set(CMAKE_PREFIX_PATH "C:\\Qt\\5.4\\msvc2013\\")
    else()
        set(CMAKE_PREFIX_PATH "C:\\Qt\\5.4\\msvc2013_64\\")
    endif()
else()
    if(${ARCH} MATCHES "X86")
        set(CMAKE_PREFIX_PATH "/opt/Qt/5.4/gcc")
    else()
        set(CMAKE_PREFIX_PATH "/opt/Qt/5.4/gcc_64")
	endif()
endif()

set(CMAKE_AUTO_MOC true)
set(CMAKE_INCLUDE_CURRENT_DIR ON)

find_package(Qt5Core REQUIRED)

file(GLOB PC_SRC ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp)

set(SOURCE_FILES ${PC_SRC})
add_library(PosClient SHARED ${SOURCE_FILES})

target_link_libraries(PosClient)
qt5_use_modules(PosClient Core)