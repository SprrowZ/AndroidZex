package com.rye.catcher.project.socketdemo.udpdemo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

/**
 * Created at 2019/3/6.
 *
 * @author Zzg
 * @function:udp内容提供者（广播）
 */
public class UDPProviderEx {
    public static void main(String[] args) throws IOException {
              //生成一份唯一标识
        String sn= UUID.randomUUID().toString();
        Provider provider=new Provider(sn);
        provider.start();
        //输入任意字符就退出
        System.in.read();
        provider.exit();
    }

    /**
     * 子线程用于循环处理消息,主要为了可以随时结束任务
     */
    private static class Provider extends Thread {
        private final String sn;
        private boolean done = false;//是否已经处理完成
        private DatagramSocket ds = null;

        public Provider(String sn) {
            super();
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("UDPProvider started!");
            //实际上就是声明一个提供者（接收者）
            try {
                ds = new DatagramSocket(20000);
                while (!done) {
                    //构建接收实体
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
                    ds.receive(receivePack);
                    //发送者的ip、端口
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int packLength = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, packLength);
                    //输出发送者的相关信息
                    System.out.println("接收到来自：" + ip + ":" + port + ",的数据为："
                            + data);
                    //解析端口号，回送到A指定的端口号
                    int responsePort=MessageCreator.parsePort(data);
                     if (responsePort!=-1){
                         //现在我们回送数据，回送数据和接收数据都是通过DatagramPacket来的
                         String responseData = MessageCreator.buildWithSn(sn);
                         byte[] responseDataBytes = responseData.getBytes();
                         DatagramPacket providerPacket = new DatagramPacket(responseDataBytes,
                                 responseDataBytes.length,
                                 receivePack.getAddress(),
                                 responsePort);
                         ds.send(providerPacket);//回送数据
                     }
                }
            } catch (Exception ignored) {

            } finally {
                close();
            }
            System.out.println("UDPProvider Finish!");

        }


        /**
         * 关闭socket
         */
        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        /**
         * 停止循环
         */
        void exit() {
            done = true;
            close();
        }

    }
}
