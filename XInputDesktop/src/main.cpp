#include "connection\bluetooth_connector.h"
#include <iostream>
#include <sstream>

using namespace std;
namespace BlueTooth = slyv::bluetooth;

// Mister main loop.
int main(int c, char* args[]) {
   // connector for the bluetooth mechanism.
   BlueTooth::BluetoothConnector connector;
   // Command for testing.
   string command;
   // Loop the test sequence.
   while (true) { 
      cout << "continue? (y/n): ";
      getline(cin, command);

      if (command.compare("y") == 0 || 
          command.compare("Y") == 0 || 
          command.compare("yes") == 0 ||
          command.compare("Yes") == 0 ||
          command.compare("YES") == 0) {  
         connector.find_bluetooth_devices();
      } else {
         break; 
      }
   }
   return 0;
}