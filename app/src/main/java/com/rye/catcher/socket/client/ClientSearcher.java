package com.rye.catcher.socket.client;

import com.rye.catcher.socket.client.bean.ServerInfo;
import com.rye.catcher.socket.constants.UDPConstants;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 18041at 2019/4/6
 * Function:
 */
public class ClientSearcher {
    private static final int LISTEN_PORT=UDPConstants.PORT_CLIENT_RESPONSE;
    public static ServerInfo searchServer(int timeout){
        System.out.println("UDPSearcher Started.");
        //成功收到回送的栅栏
        CountDownLatch receiveLatch=new CountDownLatch(1);
       return null;
    }
}
