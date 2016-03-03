#ifndef _ARCH_H_
#define _ARCH_H_
#pragma once

#if defined(_WIN32)
 //#include <Windows.h>
 #include <winsock2.h>
 #include <BluetoothAPIs.h>
 #include <ws2bth.h>

 #define CONNECTION_SUCCESS 0
#elif defined(__unix__)
 #if defined(__linux__)
  #error This software will not currently be compatible with linux yet.
 #else
  #error Please ensure this unix operating system is linux compatible.
  #error Windows is the only os that supports this software
 #endif /* __linux__ */
#elif defined(__APPLE__) && defined(__MACH__)
 #error This software is not currently compatible on OS X. 
#else
 #error Software isn't supported by your operating system.
#endif /* _WIN32 */
#endif /* _ARCH_H_ */
