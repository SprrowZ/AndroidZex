package com.rye.catcher.base.socket.constants;

public class UDPConstants {
    //公用头部
    public static byte[] HEADER=new byte[]{ 7, 7, 7, 7, 7, 7, 7, 7};
    //服务器固化UDP接受端口
    public static int PORT_SERVER=30201;
    //客户端回送接口
    public static int PORT_CLIENT_RESPONSE=30202;

}
