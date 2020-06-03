package com.studioapp.mobileapp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class MonitorizareFragment extends Fragment {

    private static View rootView;

    TextView umiditate;
    TextView temperatura;
    TextView puls;
    TextView EKG;

    Button btnBluetooth;

    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter adapter;
    private final String DEVICE_ADDRESS = "98:D3:51:FD:D2:59";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    boolean deviceConnected = false;

    private LineGraphSeries<DataPoint> series;
    private static final Random RANDOM = new Random();
    private int lastX = 0;

    Handler bluetoothIn;
    final int handlerState = 0;
    private StringBuilder recDataString = new StringBuilder();
    String ekg;



    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_monitorizare,container,false);

        umiditate = rootView.findViewById(R.id.text_umiditate);
        temperatura = rootView.findViewById(R.id.text_temperatura);
        puls = rootView.findViewById(R.id.text_puls);
        EKG = rootView.findViewById(R.id.text_EKG);

        GraphView graph = rootView.findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        //series.setColor(Color.GREEN);

        graph.addSeries(series);
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMaxX(0);
        viewport.setMaxY(1000);
        viewport.setScrollable(true);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    recDataString.append(readMessage);
                    int endOfLineIndex = recDataString.indexOf("~");
                    if (endOfLineIndex > 0) {
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);
                        //umiditate.setText("Data Received = " + dataInPrint);
                        String []val=dataInPrint.split(",");
                        umiditate.setText(val[0]);
                        temperatura.setText(val[1]);
                        puls.setText(val[2]);
                        EKG.setText(val[3]);
                        recDataString.delete(0, recDataString.length());
                        dataInPrint = " ";
                    }
                }
            }
        };


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BluetoothInitializare()) {
            if (Bluetoothconnectat()) {
                deviceConnected = true;
                Toast.makeText(getActivity().getApplicationContext(), "S-a conectat!!", Toast.LENGTH_LONG).show();
                ConnectedThread thread=new ConnectedThread(inputStream,outputStream);
                thread.start();
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void addEntry() {
        series.appendData(new DataPoint(lastX++, RANDOM.nextInt(1000)), true, 40);
    }

    public boolean BluetoothInitializare() {
        boolean found = false;
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (myBluetooth == null) {
            Toast.makeText(getActivity().getApplicationContext(), "Bluetooth Device nu este disponibil", Toast.LENGTH_LONG).show();
            getActivity().finish();
        } else if (!myBluetooth.isEnabled()) {
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Nu au fost gasite dispozitive pentru imperechere", Toast.LENGTH_LONG).show();
        } else {
            for (BluetoothDevice bt : pairedDevices) {
                if (bt.getAddress().equals(DEVICE_ADDRESS)) {
                    device = bt;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean Bluetoothconnectat() {
        boolean connected = true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
        if (connected) {
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connected;
    }

    private class ConnectedThread extends Thread{
        private final InputStream InStream;
        private final OutputStream OutStream;
        public ConnectedThread(InputStream inputStream,OutputStream outputStream)
        {
            InStream=inputStream;
            OutStream=outputStream;
        }
        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = InStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }


    }

    private void uploadMonitorDB() throws SQLException {
        String SQL = "UPDATE [dbo].[UserMonitor]" +
                " SET umiditate=?, temperatura=?, puls=?, EKG=?, mesajAlarma=?" +
                " WHERE id=?";

        Log.e("uploadMonitorDB","uploadMonitorDB");

        PreparedStatement statement = Profil.getInstance().conectionclass().prepareStatement(SQL);
        statement.setFloat(0, Float.parseFloat(umiditate.getText().toString()));
        statement.setFloat(1, Float.parseFloat(temperatura.getText().toString()));
        statement.setFloat(2, Float.parseFloat(puls.getText().toString()));
        statement.setFloat(3, Float.parseFloat(EKG.getText().toString()));
        statement.setString(4, "mesajul de alarma");

        int rowUpdated = statement.executeUpdate();
        if(rowUpdated>0)
        {
            Log.e("UPDATE","UPDATE EFECTUAT CU SUCCES");
        }
        Profil.getInstance().conectionclass().close();

    }

}


