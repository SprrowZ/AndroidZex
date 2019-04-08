package com.rye.catcher.socket.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 18041at 2019/4/7
 * Function:
 */
public class TCPServer {
   //socket连接端口
    public int port;
    private ClientListener mListener;

    public TCPServer(int port) {
        this.port = port;
    }



    //开启客户端接入线程
    public boolean start(){
        try{
            ClientListener listener=new ClientListener(port);
            mListener=listener;
            listener.start();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void  stop(){
        if (mListener != null) {
            mListener.exit();
        }
    }
    private static class ClientListener extends Thread{
        private ServerSocket server;
        private boolean done=false;
        private ClientListener(int port) throws IOException{
           server=new ServerSocket(port);
           System.out.println("服务器信息："+server.getLocalPort()+",Port:"+server.getLocalPort());
        }

        @Override
        public void run() {
            super.run();
            System.out.println("服务器准备就绪~~");
            //这里的循环是可以一直接收不同的客户端接入的
            do{
                //得到客户端
                Socket client;
                try{
                    client=server.accept();
                }catch (IOException e){
                    continue;
                }
                //客户端构建异步线程
                ClientHandler clientHandler=new ClientHandler(client);
                //启动
                clientHandler.start();
            }while (!done);
            System.out.println("服务器已关闭！");
        }

        void exit() {
            done = true;
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 处理收到的消息，回送消息的线程类
     */
    private static class ClientHandler extends Thread{
        //客户端socket
        private Socket socket;
        //标志位，用来结束接受消息的while循环
        private boolean flag = true;
        public ClientHandler(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("新客户端连接："+socket.getInetAddress()+
            ",Port:"+socket.getPort());
            try {
                //得到输入字符流
                BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //得到输出流
                PrintStream ps=new PrintStream(socket.getOutputStream());
                do{
                    String str=br.readLine();
                    if ("bye".equalsIgnoreCase(str)){
                        flag=false;
                        //回送
                        ps.println("bye~~");
                    }else{
                        System.out.println(str);
                        ps.println("回送数据："+str.length());
                    }
                }while (flag);
            }catch (Exception e){
               System.out.println("连接异常断开");
            }finally {
                //连接关闭
                try {
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            System.out.println("客户端已退出：" + socket.getInetAddress() +
                    " P:" + socket.getPort());
        }
    }
}
