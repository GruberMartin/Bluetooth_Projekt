package com.ownappsgm.grubermartin.bluetoothcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PairedDevices extends AppCompatActivity {

    TextView tvBluetoothIsOnPairedDevices;
    ListView lvFoundedDevicesPairedDevices;
    List<String> foundedDevicesList;
    List<String> deviceMACadresses;
    List<BluetoothDevice> bluetoothDevices;
    BluetoothDevice mmDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);
        tvBluetoothIsOnPairedDevices = (TextView) findViewById(R.id.tvBluetoothIsOnPairedDevices);
        lvFoundedDevicesPairedDevices = (ListView) findViewById(R.id.lvFoundedDevicesPairedDevices);
        checkForPairedDevices();

        // Falls noch keine Geräte gekoppelt sind, muss man sich für einen Broadcast Registrieren wenn ein Gerät gerfunden wurde.
        //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //registerReceiver(mReceiver, filter);

    }

    public void checkForPairedDevices()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            foundedDevicesList = new ArrayList<String>();
            deviceMACadresses = new ArrayList<String>();
            bluetoothDevices = new ArrayList<BluetoothDevice>();
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                foundedDevicesList.add(deviceName);
                deviceMACadresses.add(deviceHardwareAddress);
                bluetoothDevices.add(device);

            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,foundedDevicesList);
            lvFoundedDevicesPairedDevices.setAdapter(arrayAdapter);
            tvBluetoothIsOnPairedDevices.setText(R.string.listOfFoundedDevicesPairedDevices);
            // Element auswählen und die MAC Adresse des Augewählten elements erhalten
            lvFoundedDevicesPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String chlickedItemName = foundedDevicesList.get(position);
                    String selectedDevicesMacAddress = deviceMACadresses.get(position);
                    mmDevice = bluetoothDevices.get(position);
                    Toast.makeText(PairedDevices.this, chlickedItemName + " mit MAC Adresse: "+ selectedDevicesMacAddress, Toast.LENGTH_SHORT).show();

                    changeToControlActivity();


                }
            });
        }
        else
        {
            tvBluetoothIsOnPairedDevices.setText(R.string.noDevicesFoundedPairedDevices);
            //mBluetoothAdapter.startDiscovery();
        }
    }

    private void changeToControlActivity() {
        Intent deliveryOfDevice = new Intent(this,ControlPanel.class);
        deliveryOfDevice.putExtra("device",mmDevice);
        startActivity(deliveryOfDevice);
    }



    /*
    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }*/



}
