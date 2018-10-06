package com.ownappsgm.grubermartin.bluetoothcontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvUserHint;
    Button btnShowPairedDevices;
    int REQUEST_ENABLE_BT = 2; // erhält Result Code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserHint = (TextView) findViewById(R.id.tvUserHint);
        btnShowPairedDevices = (Button) findViewById(R.id.btnShowPairedDevices);
    }

    public void onBtnListDevicesClicked(View v)
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Ihr Gerät unterstützt kein Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Überprüfen, ob Bluetooth eingeschaltet ist
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else
            {
                // Wenn Bluetooth eingeschaltet wurde, soll die PairedDevice Activity gestartet werden
                Intent goToPairedDeviceActivity = new Intent(this,PairedDevices.class);
                startActivity(goToPairedDeviceActivity);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Überprüfen welche Antwort die enableBluetooth Anfrage geliefert hat
        if(requestCode == REQUEST_ENABLE_BT)
        {
            if(resultCode == RESULT_OK)
            {
                // Wenn Bluetooth eingeschaltet wurde, soll die PairedDevice Activity gestartet werden
                Intent goToPairedDeviceActivity = new Intent(this,PairedDevices.class);
                startActivity(goToPairedDeviceActivity);
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Wenn du Bluetooth nicht einschaltest, kannst du nicht weiterfahren!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
