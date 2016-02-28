package com.inasweaterpoorlyknit.androidxinput;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_ENABLE_BT = 2;

    private BluetoothAdapter bluetoothAdapter;
    private Button setBluetoothButton;
    private Button disableBluetoothButton;
    private Button findDevicesButton;
    private Button controlButton;
    //private ListView pairingListView;

    //private ArrayList<String> deviceInfo = new ArrayList<>();
    //private ArrayList<BluetoothDevice> pairedDevices = new ArrayList<>();


    /*
    private Boolean isConnected;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                isConnected = true;
            }
            else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
                isConnected = false;
            }
            // When discover finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                // get the bluetooth device object from the intent
                BluetoothDevice deviceX = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                pairedDevices.add(deviceX);
                // add the name and address to an array adapter to show in the list view
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                pairedDevices.clear();
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivity(discoverableIntent);
                Toast.makeText(context, "Searching and can be discovered...", Toast.LENGTH_LONG).show();
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                if(pairedDevices.size() > 0){
                    deviceInfo.clear();
                    for(BluetoothDevice device : pairedDevices) {
                        deviceInfo.add(device.getName() + "\n" + device.getAddress());
                    }
                    pairingListView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1, deviceInfo));
                }
                Toast.makeText(context, "Results found.", Toast.LENGTH_SHORT).show();
            }
        }
    };
    */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        disableBluetoothButton = (Button) findViewById(R.id.Set_Bluetooth_Button);
        disableBluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter != null) {
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    } else {
                        // Device does not support bluetooth
                        Toast.makeText(v.getContext(), "Bluetooth is already enabled.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Device does not support bluetooth
                    Toast.makeText(v.getContext(), "This Android device doesn't support bluetooth.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setBluetoothButton = (Button) findViewById(R.id.Disable_Bluetooth_Button);
        setBluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter != null) {
                    if (bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.disable();
                        Toast.makeText(v.getContext(), "Bluetooth was disabled.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Device does not support bluetooth
                        Toast.makeText(v.getContext(), "Bluetooth is already disabled.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Device does not support bluetooth
                    Toast.makeText(v.getContext(), "This Android device doesn't support bluetooth.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findDevicesButton = (Button) findViewById(R.id.Find_Devices_Button);
        findDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivity(discoverableIntent);
                    Toast.makeText(v.getContext(), "This device can now can be discovered...", Toast.LENGTH_LONG).show();
                    /*
                    pairedDevices = bluetoothAdapter.getBondedDevices();
                    if(pairedDevices.size() > 0){
                        deviceInfo.clear();
                        for(BluetoothDevice device : pairedDevices) {
                            deviceInfo.add(device.getName() + "\n" + device.getAddress());
                        }
                        pairingListView.setAdapter(new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, deviceInfo));
                    }
                    */
                } else {
                    // Device does not support bluetooth
                    Toast.makeText(v.getContext(), "Bluetooth is not enabled.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        controlButton = (Button) findViewById(R.id.Control_Button);
        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Controller.class);
                    startActivity(intent);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_ENABLE_BT){
                if(bluetoothAdapter.isEnabled()){
                    Toast.makeText(this, "Bluetooth was enabled.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if(requestCode == REQUEST_ENABLE_BT){
                Toast.makeText(this, "Bluetooth was not enabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    @Override
    public void onResume(){
        super.onResume();
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, foundFilter);
        IntentFilter startedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(broadcastReceiver, startedFilter);
        IntentFilter finishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, finishedFilter);

        IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(broadcastReceiver, filter1);
        this.registerReceiver(broadcastReceiver, filter2);
        this.registerReceiver(broadcastReceiver, filter3);
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
    */
}
