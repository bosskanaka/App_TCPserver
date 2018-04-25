package com.example.a58160121.app_tcpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class Server {
    static final int serverPort = 2222;
    ServerSocket serverSocket;
    MainActivity activity;

    public Server(MainActivity activity) {
        this.activity = activity;
        ServerWorker serverWorker = new ServerWorker();
        serverWorker.start();
    }

    public int getPort(){
        return  serverPort;
    }

    private class ServerWorker extends  Thread{
        private int count=0;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(serverPort);
                while (true){
                    final Socket clientSocket = serverSocket.accept();
                    count++;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.txtMonitor.append("#" + count
                            + " from "
                            + clientSocket.getInetAddress()
                            + ":"
                            + clientSocket.getPort() + "\n"
                            );
                        }
                    });

                    ServerCommunication comm = new ServerCommunication(clientSocket);
                    comm.start();

                }
            } catch (IOException e){
                //
            }

        }
    }

    private class ServerCommunication extends  Thread {

        private  Socket clientSocket;

        ServerCommunication(Socket clientSocket){
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                final String msg = input.readUTF();
                output.writeUTF("ACK 250");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s_msg[] = msg.split(" ");
                        if(s_msg[0].equals("MSG")){
                            if (s_msg[1].equals("1")){
                                activity.ibt.setImageResource(R.drawable.icon_1);
                            }else{
                                activity.ibt.setImageResource(R.drawable.icon_2);
                            }
                            activity.txtMonitor.append("Client say : " + msg + "\n");
                        }
                    }
                });
            } catch (IOException e) {
                //
            }
            }
        }

        public  String getIpAddress() {
            String ip ="";
            try {
                Enumeration<NetworkInterface> netInt = NetworkInterface.getNetworkInterfaces();
                while (netInt.hasMoreElements()) {
                    NetworkInterface networkInterface = netInt.nextElement();
                    Enumeration<InetAddress>enumInetAddress = networkInterface.getInetAddresses();
                    while (enumInetAddress.hasMoreElements()) {
                        InetAddress inetAddress = enumInetAddress.nextElement();
                        if (inetAddress.isSiteLocalAddress()) {
                            ip += "SERVER running at ip => " + inetAddress.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e){
                //
            }
            return ip;
        }

    }


