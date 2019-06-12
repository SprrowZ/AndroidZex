package com.rye.catcher.base.socket.server;

import com.rye.catcher.base.socket.constants.TCPConstants;
import com.rye.catcher.base.socket.constants.UDPConstants;

import java.io.IOException;

/**
 * 服务器
 */
public class Server {
    public static void main(String[] args){
        TCPServer tcpServer=new TCPServer(TCPConstants.PORT_SERVER);
        boolean isSuccessed=tcpServer.start();
        if (!isSuccessed){
            System.out.println("Start TCP server failed!");
            return;
        }
        ServerProvider.start(UDPConstants.PORT_SERVER);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerProvider.stop();
        tcpServer.stop();
    }
}
