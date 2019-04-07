package com.rye.catcher.project.socketdemo.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws Exception {
		// 因为是用本机测试，所以不用传入ip
      ServerSocket serverSocket=new ServerSocket(2000);
     System.out.println("服务器准备就绪！");
      System.out.println("服务器地址："+serverSocket.getLocalSocketAddress()
      +",P:"+serverSocket.getLocalPort());
     for(;;) {
    	Socket client= serverSocket.accept();
        ClientHandler clientHandler=new ClientHandler(client);
        clientHandler.start();
     }
      
	}
	//子线程处理
	private static class ClientHandler extends Thread{
		//拿到客户端
		private Socket socket;
		private boolean flag=true;
		public ClientHandler(Socket socket) {
			this.socket=socket;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			try {
				//输出流,通过PrintStream的println方法，，第一次见
				PrintStream socketOutput=new PrintStream(socket.getOutputStream());
			    //输入流
				BufferedReader socketInput=new BufferedReader(new 
						InputStreamReader(socket.getInputStream()));
				
			   //循环读取
				do {
					String inputString=socketInput.readLine(); 
					if (inputString.equalsIgnoreCase("bye")) {
						flag=false;
					}
					socketOutput.println("服务器接收到："+inputString
							+",长度："+inputString.length());
					System.out.println("服务端接受到的数据是："+inputString);	
			   }while(flag);
			
				socketInput.close();
				socketOutput.close();
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("连接异常关闭！");
			}finally {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("客户端已关闭！ip："+socket.getInetAddress()
			+",P:"+socket.getPort());
			
		}
		
	}

}
