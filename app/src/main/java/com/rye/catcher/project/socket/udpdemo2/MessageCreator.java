package com.rye.catcher.project.socket.udpdemo2;

/**
 * socket口令，搜索者和设备间交互的统一口令
 */
public class MessageCreator {
    //B.C.D等统一口令,（接收到消息时回送的）
    private static final String SN_HEADER="收到暗号，我是(SN):";
    private static final String PORT_HEADER="这是暗号，请回电端口(Port):";
    public static String buildWithPort(int port){
        return PORT_HEADER+port;
    }
    public static int parsePort(String data){
        if (data.startsWith(PORT_HEADER)){
            return Integer.parseInt(data.substring(PORT_HEADER.length()));
        }
        return -1;
    }
    public static String buildWithSn(String sn){
        return SN_HEADER+sn;
    }
    public static String parseSn(String data){
        if (data.startsWith(SN_HEADER)){
            return  data.substring(SN_HEADER.length());
        }
        return null;
    }
}
