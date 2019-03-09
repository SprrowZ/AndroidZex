package com.rye.catcher.project.socket.udpdemo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created at 2019/3/6.
 *
 * @author Zzg
 * @function:udp内容提供者（单播）
 */
public class UDPProvider {
    public static void main(String[] args) throws IOException {
        System.out.println("UDPProvider started!");
        //实际上就是声明一个提供者（接收者）
        DatagramSocket receiver=new DatagramSocket(20000);
        //缓冲接收到的数据
        final byte[] buf=new byte[512];
        DatagramPacket receivePack=new DatagramPacket(buf,buf.length);
        receiver.receive(receivePack);
       //发送者的ip、端口
       String senderIP= receivePack.getAddress().getHostAddress();
       int senderPort=receivePack.getPort();
       int packLength=receivePack.getLength();
       String data=new String(receivePack.getData(),0,packLength);
       //输出发送者的相关信息
       System.out.println("接收到来自："+senderIP+":"+senderPort+",的数据为："
       +data);
       //现在我们回送数据，回送数据和接收数据都是通过DatagramPacket来的
        String responseData="Receive data length is:"+data.length();
       byte[] responseDataBytes=responseData.getBytes();
       DatagramPacket providerPacket=new DatagramPacket(responseDataBytes,
               responseDataBytes.length,
               receivePack.getAddress(),
               receivePack.getPort());
       receiver.send(providerPacket);//回送数据
       receiver.close();
       System.out.println("UDPProvider Finish!");
    }
}
