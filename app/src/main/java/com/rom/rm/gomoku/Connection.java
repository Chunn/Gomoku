package com.rom.rm.gomoku;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Rơm on 4/8/2018.
 */

public class Connection extends AsyncTask<String,Void,Void> {

    private OutputStream outputStream;
    private Socket socketOfClient = null;
    private BufferedWriter os = null;
    private BufferedReader is = null;

    private static final String host="116.100.236.47";
    private static final int port=7777;

    @Override
    protected Void doInBackground(String... strings) {

        try {
            //Gửi yêu cầu kết nối tới server đang lắng nghe
            socketOfClient = new Socket(host,port);
            //Tạo luồng đầu ra(Gửi dữ liệu đến server)
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            //Tạo luồng đầu vào(nhận giữ liệu từ server)
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
        }catch (UnknownHostException e){
            System.err.println("Don't know host"+host);
        }catch (IOException e){
            System.err.println("Couldn't get I/O for the connection to"+host);
        }


        return null;
    }
}
