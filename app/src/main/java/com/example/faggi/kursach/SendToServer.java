package com.example.faggi.kursach;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

class SendToServer {

    private static Socket socket;

    static void send(String number) {
        InetAddress address = null;
        try {
            String host = "81.162.0.237"; // address
            int port = 25000;
            try {
                address = InetAddress.getByName(host);
            } catch (java.net.UnknownHostException e) {
                e.printStackTrace();
            }
            socket = new Socket(address, port);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String sendMessage = number + "\n";
            bw.write(sendMessage);
            bw.flush();

        } catch (java.io.IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}