package com.rye.catcher.socket.client;

import com.rye.catcher.socket.client.bean.ServerInfo;

/**
 * Created by 18041at 2019/4/6
 * Function:
 */
public class Client {
    public static void main(String[] args){
        ServerInfo info=ClientSearcher.searchServer(10000);
        System.out.println("Server:"+info);
    }
}
