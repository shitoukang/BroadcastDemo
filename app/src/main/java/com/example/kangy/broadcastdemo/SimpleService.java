package com.example.kangy.broadcastdemo;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kangy on 2018/1/23.
 */

public class SimpleService {
    public static void main(String[] args)throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(3000);
        while (true)
        {
            Socket socket = serverSocket.accept();
            OutputStream os  = socket.getOutputStream();
            os.write("您收到的是服务器发来的消息！".getBytes("utf-8"));
            os.close();
            serverSocket.close();
        }

    }
}
