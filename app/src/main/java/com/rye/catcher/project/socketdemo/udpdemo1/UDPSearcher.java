package com.rye.catcher.project.socketdemo.udpdemo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created at 2019/3/6.
 *
 * @author Zzg
 * @function:搜索者（单播）
 */
public class UDPSearcher {
    public static void main(String[] args) throws IOException {
        System.out.println("UDPSearcher started!");
        //实际上就是声明一个提供者（接收者），不指定端口，让系统自动分配
        DatagramSocket request=new DatagramSocket();


        //现在我们回送数据，回送数据和接收数据都是通过DatagramPacket来的
        String requestData="Hello-Kugou！";
        byte[] requestDataBytes=requestData.getBytes();
        DatagramPacket requestPacket=new DatagramPacket(requestDataBytes,
                requestDataBytes.length);
        //用本机测
        requestPacket.setAddress(InetAddress.getLocalHost());
        requestPacket.setPort(20000);//provider的端口，searcher不需要指定自己端口，系统随机分配
        request.send(requestPacket);//回送数据

        //接收回送的消息
        final byte[] buf=new byte[512];
        DatagramPacket receivePack=new DatagramPacket(buf,buf.length);
        request.receive(receivePack);
        //接收者的ip、端口
        String senderIP= receivePack.getAddress().getHostAddress();
        int senderPort=receivePack.getPort();
        int packLength=receivePack.getLength();
        String data=new String(receivePack.getData(),0,packLength);
        //输出发送者的相关信息
        System.out.println("UDPSearcher receive from ："+senderIP+":"+senderPort+",的数据为："
                +data);

        request.close();
        System.out.println("UDPSearcher Finish!");
    }
}
