package com.ownappsgm.grubermartin.bluetoothcontroller;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ControlPanel extends AppCompatActivity {

    TextView tvUserHintControlPanel;
    Button btnLedOnControlPanel, btnLedOffControlPanel;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        tvUserHintControlPanel = (TextView) findViewById(R.id.tvUserHintControlPanel);
        btnLedOnControlPanel = (Button) findViewById(R.id.btnLedOnControlPanel);
        btnLedOffControlPanel = (Button) findViewById(R.id.btnLedOffControlPanel);
        Intent recieveDevice = getIntent();
        mmDevice =  recieveDevice.getExtras().getParcelable("device");
        try {
            openBT();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        //Optional

        Toast.makeText(getApplicationContext(), "Verbindung wurde hergestellt", Toast.LENGTH_SHORT).show();

    }

    public void onBtnLedOnClicked(View v)
    {
        try {
            sendData('e');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBtnLedOffClicked(View v)
    {
        try {
            sendData('a');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(char msg) throws IOException{
         mmOutputStream.write(msg);
        System.out.println("Data Sent");
    }
}
