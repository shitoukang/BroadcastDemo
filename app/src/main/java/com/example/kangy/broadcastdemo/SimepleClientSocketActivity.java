package com.example.kangy.broadcastdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class SimepleClientSocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simeple_client_socket);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Socket socket = new Socket("10.96.20.12", 3000);
                    //建立了连接之后，读取服务器发过来的消息
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = reader.readLine();
                    Toast.makeText(getApplicationContext(),line.toString(),Toast.LENGTH_LONG).show();
                    reader.close();
                    socket.close();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
