package com.example.myappsecond.base.sockets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ZZG on 2018/8/3.
 */
public class SocketClientActivity extends BaseActivity {
    private EditText editText;
    private TextView received;
    private Button   send;
    //服务器地址，因为是虚拟地址，所以切一次网络换一次，dont forget
    public static   String SERVER_IP="192.168.43.231";
    //跟服务器端口号保持一致
    public static   int PORT=20133;
    private Socket socket;
    private BufferedReader reader=null;
    private BufferedWriter writer=null;
    private BufferedReader readerText=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sockets_demo1);
        initView();
        initEvent();
    }

    private void initEvent() {

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             act();
            }
        });
    }

    private void initView() {
        editText=findViewById(R.id.ed);
        received=findViewById(R.id.received);
        send=findViewById(R.id.send);
    }
    private void doSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket=new Socket(SERVER_IP,PORT);//new 实例
                    socket.setSoTimeout(15000);
                    //读取服务器返回的数据
                    reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //给服务器发消息
                    writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    //读取输入数据流
                    readerText=new BufferedReader(new InputStreamReader
                            (getStringStreamex(editText.getText().toString())));
                    String line = null;
                    String res = null;
                    while (!(line=readerText.readLine()).equalsIgnoreCase("bye")){
                        writer.write(line+"\n");
                        writer.flush();
                        res=reader.readLine();
                        Log.i("zzg", "run: "+res);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        reader.close();
                        writer.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();

    }
    public void act(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //1.创建监听指定服务器地址以及指定服务器监听的端口号
                    Socket socket = new Socket(SERVER_IP, PORT);
                    //2.拿到客户端的socket对象的输出流发送给服务器数据
                    OutputStream os = socket.getOutputStream();
                    //写入要发送给服务器的数据
                    os.write(editText.getText().toString().getBytes());
                    os.flush();
                    socket.shutdownOutput();
                    //拿到socket的输入流，这里存储的是服务器返回的数据
                    InputStream is = socket.getInputStream();
                    //解析服务器返回的数据
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader bufReader = new BufferedReader(reader);
                    String s = null;
                    final StringBuffer sb = new StringBuffer();
                    while((s = bufReader.readLine()) != null){
                        sb.append(s);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            received.setText(sb.toString());
                        }
                    });
                    //3、关闭IO资源（注：实际开发中需要放到finally中）
                    bufReader.close();
                    reader.close();
                    is.close();
                    os.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public static InputStream getStringStreamex(String str){
        if (str!=null&&!str.trim().equals("")){
            ByteArrayInputStream strEx=new ByteArrayInputStream(str.getBytes());
            return strEx;
        }
        return null;
    }
}
