#ifndef _BLUETOOTH_CONNECTOR_H_
#define _BLUETOOTH_CONNECTOR_H_
#pragma once

#include <lib/architecture/arch.h>

namespace slyv { 
namespace bluetooth { 

typedef int __BOOL__;
//
// Bluetooth Connector class intended for connecting our mobile devices, or software connection.
//
class BluetoothConnector { 
private:
   WSADATA        wsa_data;
   HANDLE         handle_lookup;
   WSAQUERYSET    wsa_query;
   LPWSAQUERYSET  pwsa_query_results;
   DWORD          dw_size;
   BOOL           bool_have_name;
   BTH_ADDR       bt_addr;
   WORD          windows_version;
public:
   BluetoothConnector(void);

   ~BluetoothConnector(void);
   
   __BOOL__ find_bluetooth_devices(void);
   __BOOL__ stop_broadcast_connection(void);
   __BOOL__ connect_device(void) { return 0; }
   __BOOL__ close_connection(void);
}; /* BluetoothConnector */

} // bluetooth namespace 
} // slyv namespace 
#endif /* _BLUETOOTH_CONNECTOR_H_ */
