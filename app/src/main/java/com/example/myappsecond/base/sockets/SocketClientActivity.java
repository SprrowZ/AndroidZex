package com.example.myappsecond.base.sockets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by ZZG on 2018/8/3.
 */
public class SocketClientActivity extends BaseActivity {
    private EditText editText;
    private TextView received;
    private Button   send;
    //服务器地址，因为是虚拟地址，所以切一次网络换一次，dont forget
    public static final  String SERVER_IP="112.65.48.244";
    //跟服务器端口号保持一致
    public static final  int PORT=10086;
    private Socket socket;
    private BufferedReader inputReader=null;
    private BufferedWriter writer=null;
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
                doSocket();
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
//                    OutputStream os=socket.getOutputStream();
                    writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(editText.getText().toString());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        writer.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        });

    }
}
