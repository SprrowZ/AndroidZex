package com.example.myappsecond;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myappsecond.activity.CartoonsDoActivity;
import com.example.myappsecond.activity.CharactersDoActivity;
import com.example.myappsecond.activity.CartoonsListActivity;
import com.example.myappsecond.activity.WebViewActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zzg on 2017/10/12.
 */

public class FirstActivity extends BaseActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    LinearLayout linear;
    private ImageView iv1;
    Timer timer;
    private Handler handler=new Handler();

    int i=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab04_first_activity);
        initView();
        myThread();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        btn6.setText("ding...");
                        break;
                }
                super.handleMessage(msg);
            }
        } ;
    }

    private void myThread() {

    }

    private void initView() {
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        findViewById(R.id.aa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    OperateIO();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.cc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          startActivity(new Intent(FirstActivity.this, WebViewActivity.class));
            }
        });

        findViewById(R.id.bb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, CartoonsDoActivity.class));
            }
        });
        findViewById(R.id.dd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this,CartoonsListActivity.class));
            }
        });
        findViewById(R.id.ff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, CharactersDoActivity.class));
            }
        });
    }

    private void OperateIO() throws IOException {
        String sdPath= Environment.getExternalStorageDirectory().toString();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//如果已经挂载
          String filepath=sdPath+File.separator+"First_Activity"+".png";
            FileInputStream in=null;
            FileOutputStream fos=null;
           File file=new File(filepath);
           if (!file.exists()){
               file.createNewFile();
           }else{
               Log.i("zzg", "OperateIO: file already exists..");
           }
           byte[] buf=new byte[1024];
           int len=0;
           //创建一个bitmap
            Bitmap bitmap = Bitmap.createBitmap(300, 300,
                    Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.parseColor("#FF0000"));//填充颜色
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setTextSize(80);
            paint.setColor(Color.GREEN);
            paint.setFlags(1);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText("CSDN", 100, 100, paint );
            try {
                in=new FileInputStream(file);
                fos=new FileOutputStream(file);
//                while ((len=in.read(buf))!=-1){
//                    fos.write(buf,0,len);
//                }
               bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                in.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
             catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
//                Uri uri = Uri.parse("http://www.baidu.com");
//                Intent it  = new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(it);
//                Toast.makeText(this,"dddd",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:"+"10010"));
                startActivity(intent);
                break;
            case R.id.btn2:

                linear=findViewById(R.id.linear);
                LayoutInflater inflater=LayoutInflater.from(this);
               View view1= inflater.inflate(R.layout.tab01,null);
               linear.addView(view1);

                break;
            case R.id.btn3:
                linear.removeAllViews();
                break;
            case R.id.btn4:
                //
               clickTest();
                break;
            case R.id.btn5:
                if (timer!=null){
                    timer.cancel();
                }
               else{
                    return ;
                }
              break;
            case R.id.btn6:
                new Thread(){
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=1;
                        // message.arg1=88;
                        handler.sendMessage(message);
                    }
                }.start();
            case R.id.btn7:

                break;
        }
    }

    private void clickTest() {
        iv1=findViewById(R.id.iv1);
        //新建一个数组用来存储图片
        final int [] imgs={R.drawable.test1,R.drawable.test2,R.drawable.test3};

       timer=new Timer();//全局声明，在这里实例化，如果全局实例化就会取消掉timer，导致不能循环
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        i=i%3;
                        iv1.setImageResource(imgs[i]);
                    }
                });
            }
        },1000,3000);
    }

}
