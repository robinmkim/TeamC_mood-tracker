package com.teamc.moodtracker.websocket;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;



public class MyClient{

    private PrintWriter pw;
    private BufferedReader br;

    private Socket sk;

    public MyClient() throws HeadlessException {

        try {
            sk = new Socket("192.168.0.93",9080);
            pw = new PrintWriter(sk.getOutputStream(),true);
            br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }




    }

    public static void main(String[] args) {
        new MyClient();
    }

}