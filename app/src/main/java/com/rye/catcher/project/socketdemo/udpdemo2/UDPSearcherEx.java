package com.rye.catcher.project.socketdemo.udpdemo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created at 2019/3/6.
 *
 * @author Zzg
 * @function:搜索者（广播）
 */
public class UDPSearcherEx {
    private static final int LISTEN_PORT=30000;//发送者需要监听一个端口
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("UDPSearcher Started!");
            //不要永久性监听，可以随时结束，通过线程来
           Listener listener= listen();
           sendBroadcast();
           //读取任意键盘信息后退出
        System.in.read();
        List<Device> devices=listener.getDevicesAndClose();
        for (Device device:devices){
            System.out.println("DeviceInfo--:"+device.toString());
        }
        System.out.println("UDPSearcher Finished!");
    }

    /**
     * 接收信息,CountDownLatch并发同步
     */
    private static Listener listen() throws InterruptedException {
         System.out.println("UDPSearcher start listen!");

         CountDownLatch countDownLatch=new CountDownLatch(1);
         Listener listener=new Listener(LISTEN_PORT,countDownLatch);
         listener.start();
         countDownLatch.await();
         return  listener;
    }

    /**
     * 发送消息
     */
    private static void sendBroadcast() throws IOException {
        System.out.println("UDPSearcher sendBroadcast started!");
        //实际上就是声明一个提供者（接收者），不指定端口，让系统自动分配
        DatagramSocket request=new DatagramSocket();


        //发送的数据格式要统一
        String requestData=MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] requestDataBytes=requestData.getBytes();
        DatagramPacket requestPacket=new DatagramPacket(requestDataBytes,
                requestDataBytes.length);
        //使用广播地址
        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        requestPacket.setPort(20000);//provider的端口，searcher不需要指定自己端口，系统随机分配
        request.send(requestPacket);//回送数据
        request.close();
        System.out.println("UDPSearcherEx sendBroadcast finished!");

    }

    /**
     * 设备信息
     */
    private static class Device{
        //final变量的使用方式，设置参数必传，通过构造函数传值赋值
       final int port;
       final String ip;
       final String sn;

        private Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + '\'' +
                    ", sn='" + sn + '\'' +
                    '}';
        }
    }

    private static class Listener extends Thread{
        //监听端口
        private final  int listenPort;
        //????，通知外部已启动
        private final CountDownLatch countDownLatch;
        //设备信息
        private final List<Device> devices=new ArrayList<>();
        //是否已完成的标志位
        private boolean done=false;

        private DatagramSocket ds=null;

        private Listener(int listenPort, CountDownLatch countDownLatch) {
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();
            //通知已启动
            countDownLatch.countDown();
            try{
                ds=new DatagramSocket(listenPort);
                //接收回送的消息
                while(!done){
                    final byte[] buf=new byte[512];
                    DatagramPacket receivePack=new DatagramPacket(buf,buf.length);
                    //接收消息
                    ds.receive(receivePack);
                    String ip= receivePack.getAddress().getHostAddress();
                    int port=receivePack.getPort();
                    int packLength=receivePack.getLength();
                    String data=new String(receivePack.getData(),0,packLength);
                    System.out.println("UDPSearcher receive from ："+ip+":"+port+",的数据为："
                            +data);
                    //devices增加
                   String sn=MessageCreator.parseSn(data);
                   if (sn!=null){
                       Device device=new Device(port,ip,sn);
                       devices.add(device);
                   }


                }


            }catch (Exception e){

            }finally {
                 close();
            }

            System.out.println("UDPSearcher listener finished!");
        }
        private void close(){
            if (ds!=null){
                ds.close();
                ds=null;
            }
        }

        List<Device> getDevicesAndClose(){
            done=true;
            close();
            return devices;
        }
    }
}
