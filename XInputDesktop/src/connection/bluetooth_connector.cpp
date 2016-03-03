#include <lib/header.hpp>
#include <stdio.h>
#include <connection/bluetooth_connector.h>

#pragma comment(lib, "Ws2_32.lib")

union {
   CHAR buf[5000];
   SOCKADDR_BTH   _Unused_; 
} butuh;

namespace slyv { 
namespace bluetooth { 

// Constructor for the initialization of Windows sockets.
BluetoothConnector::BluetoothConnector(void) : windows_version(MAKEWORD(2, 2)),
                                               pwsa_query_results((LPWSAQUERYSET)butuh.buf),
                                               dw_size(sizeof(butuh.buf)) { 
   if(WSAStartup(windows_version, &wsa_data) != 0) {
      printf("WSAStartup did not start. Error code: %ld'n", WSAGetLastError());
   } else {
      printf("WSAStartup was successfully started.\n");
   }

   // WSAQUERYDATA initialization.
   ZeroMemory(&wsa_query, sizeof(WSAQUERYSET));
   wsa_query.dwSize = sizeof(WSAQUERYSET);
   wsa_query.dwNameSpace = NS_BTH;
   wsa_query.lpcsaBuffer = NULL;

   // WSAQUERYSET initialization.
   ZeroMemory(pwsa_query_results, sizeof(WSAQUERYSET));
   pwsa_query_results->dwSize = sizeof(WSAQUERYSET);
   pwsa_query_results->dwNameSpace = NS_BTH;
   pwsa_query_results->lpBlob = NULL;
}

// Terminate any connections and the Windows socket.
BluetoothConnector::~BluetoothConnector(void) { 
   if (WSACleanup() != CONNECTION_SUCCESS) { 
      printf("ERROR: WSACleanup was not a success!!");   
   } else {
      printf("Successful cleanup");
   }
}

// Start a query for searching bluetooth devices...
__BOOL__ BluetoothConnector::find_bluetooth_devices(void) { 
   BOOL have_name;
   DWORD dw_namespace;

   // Begin service look up.
   if (WSALookupServiceBegin(&wsa_query, LUP_CONTAINERS, &handle_lookup) == SOCKET_ERROR) { 
      printf("\nLookup Service has failed!\n");
      // Figure out why service has failed.
      BOOL error = WSAGetLastError();
      if (error == WSANOTINITIALISED) { 
         printf("Look up was not initialized!\n");
      } else if (error == WSASERVICE_NOT_FOUND) { 
         printf("WSA service not found!!\n");
      } else if ( error == WSAEINVAL) { 
         printf("One or more parameters were missing or invalid!!\n");
      }
      return -1;
   } else {
      printf("Initiating Lookup Service...\n\n"); 
   }

   // Seach through windows bluetooth service query.
   while (WSALookupServiceNext(handle_lookup, 
                               LUP_RETURN_NAME | LUP_RETURN_ADDR, 
                               &dw_size, pwsa_query_results) == CONNECTION_SUCCESS) {
      bt_addr = ((SOCKADDR_BTH*)pwsa_query_results->lpcsaBuffer->RemoteAddr.lpSockaddr)->btAddr;
      have_name = (pwsa_query_results->lpszServiceInstanceName) && *(pwsa_query_results->lpszServiceInstanceName);
      dw_namespace = pwsa_query_results->dwNameSpace;

      // Print the name, address, and other things from what was found.
      wprintf(L"Name:\tNAP Address:\tSAP Address:\tName Space:\n");
      wprintf(L"%s\t0X%04X\t\t0X%08X\t0X%0X\n", pwsa_query_results->lpszServiceInstanceName, 
                                                GET_NAP(bt_addr), GET_SAP(bt_addr),
                                                dw_namespace);
   }

   // End the query service.
   if (WSALookupServiceEnd(handle_lookup) == CONNECTION_SUCCESS) { 
      printf("Service end has ended successfully!");
   } else {
      printf("Failed to end the service!!");
   }

   return 0;
}

__BOOL__ BluetoothConnector::close_connection(void) { 
   return 0;
}

__BOOL__ BluetoothConnector::stop_broadcast_connection(void) { 
   return 0;
}
} // bluetooth namespace 
} // slyv namespace 