package com.rye.catcher.project.socket.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
public static void main(String[] args) {
	Socket socket=new Socket();
	//超时时间
	try {
		socket.setSoTimeout(3000);
		socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);
	    System.out.println("已发起服务器连接，并进入后续流程~");
	    System.out.println("客户端信息："+socket.getLocalAddress()
	    +",P:"+socket.getLocalPort());
	    System.out.println("服务器信息："+socket.getInetAddress()
	    +",P:"+socket.getPort());
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		todo(socket);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	try {
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
 
private static void todo(Socket client)throws Exception{
	//得到键盘流
	InputStream in = System.in;
	BufferedReader reader=new BufferedReader(new InputStreamReader(in));
	//得到输出流
	OutputStream outputStream=client.getOutputStream();
	//通过打印流将数据发出去
	PrintStream printStream=new PrintStream(outputStream);
	String readLine = reader.readLine();
	printStream.println(readLine);
	//得到输入流，循环读
	InputStream inputStream = client.getInputStream();
	BufferedReader rBufferedReader=new BufferedReader(
			new InputStreamReader(inputStream));
	String inputContent;
	boolean flag=true;
	//循环读取
	while(flag) {
		inputContent=rBufferedReader.readLine();
		System.out.println(inputContent);
		if (inputContent.equalsIgnoreCase("bye")) {
			flag=false;
		}
	}
	reader.close();
	printStream.close();
	rBufferedReader.close();
	
	
	
}

}
