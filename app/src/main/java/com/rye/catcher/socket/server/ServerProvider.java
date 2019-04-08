package com.rye.catcher.socket.server;


import com.rye.catcher.socket.constants.UDPConstants;
import com.rye.catcher.utils.ExtraUtil.ByteUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by 18041at 2019/4/5
 * Function: 服务器内容提供者
 */
public class ServerProvider {
     private static Provider PROVIDER_INSTANCE;

    /**
     *  开启监听
     * @param port
     */
     public static void start(int port){
         stop();
         String sn=UUID.randomUUID().toString();
         Provider provider=new Provider(sn,port);
         provider.start();
         PROVIDER_INSTANCE=provider;
     }

    /**
     * 结束监听
     */
    public static void stop(){
         if (PROVIDER_INSTANCE!=null){
             PROVIDER_INSTANCE.exit();
             PROVIDER_INSTANCE=null;
         }
     }

    /**
     * 服务器开启子线程监听消息
     */
    private static class Provider extends Thread{
         private final byte[] sn;
         private final  int port;
         private  boolean done=false;
         private DatagramSocket ds=null;
         //存储消息的Buffer
        final byte[] buffer=new byte[128];
         public Provider(String sn,int port){
            super();
            this.sn=sn.getBytes();
            this.port=port;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("UDPProvider Started.");
            try {
                //指定服务端端口，监听
                ds=new DatagramSocket(UDPConstants.PORT_SERVER);
                //接受消息的Packet
                DatagramPacket receivePack=new DatagramPacket(buffer,buffer.length);
                while(!done){
                    //开始接受客户端消息
                    ds.receive(receivePack);
                    //打印接收到的信息与发送者的信息
                    //发送者的ip地址
                    String clientIp=receivePack.getAddress().getHostAddress();
                    int clientPort=receivePack.getPort();
                    int clientDataLen=receivePack.getLength();
                    byte[] clientData=receivePack.getData();
                    //2 是指令，short存储，4是客户端回送端口，int，4个字节
                    boolean isValid=clientDataLen>=(UDPConstants.HEADER.length+2+4)
                            &&ByteUtils.startsWith(clientData,UDPConstants.HEADER);
                    System.out.println("ServerProvider receive from ip:"+clientIp+"\t port:"
                    +clientPort+"\t dataValid:"+isValid);
                    if (!isValid){
                        continue;
                    }
                    int index=UDPConstants.HEADER.length;
                    //解析命令
                    short cmd=(short)((clientData[index++]<<8|(clientData[index++]&0xff)));
                    //解析回送端口
                    int responsePort=(((clientData[index++])<<24)|
                            ((clientData[index++]&0xff)<<16)|
                            ((clientData[index++]&0xff)<<8)|
                            ((clientData[index++]&0xff)));
                    //判断合法性，基础命令为1
                    if (cmd==1&&responsePort>0){
                        //构建回送数据
                        ByteBuffer byteBuffer=ByteBuffer.wrap(buffer);
                        byteBuffer.put(UDPConstants.HEADER);
                        byteBuffer.putShort((short)2);
                        byteBuffer.putInt(port);
                        byteBuffer.put(sn);
                        int len=byteBuffer.position();
                        //直接根据发送者构建一份回送信息
                        DatagramPacket responsePacket=new DatagramPacket(buffer,
                                len,
                                receivePack.getAddress(),
                                responsePort);
                        ds.send(responsePacket);
                        System.out.println("ServerProvider response to:"+clientIp+",port:"+responsePort);
                    }else{
                        System.out.println("ServerProvider receive cmd nonsupport ;cmd:"+cmd+",port:"+responsePort);
                    }
                }
            }catch (Exception e){

            }finally {
                close();
            }
        }

        /**
         * 关闭DatagramSocket
         */
        private void close(){
             if (ds!=null){
                 ds.close();
                 ds=null;
             }
        }

        /**
         * 提供结束
         */
        void  exit(){
            done=true;
            close();
        }
    }
}
