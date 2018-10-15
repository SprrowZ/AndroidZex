package com.rye.catcher;

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
import android.widget.RelativeLayout;

import com.rye.catcher.activity.CartoonsDoActivity;
import com.rye.catcher.activity.CartoonsListActivity;
import com.rye.catcher.activity.CharactersDoActivity;
import com.rye.catcher.activity.WebViewActivity;
import com.rye.catcher.base.sockets.SocketClientActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zzg on 2017/10/12.
 */

public class FirstActivity extends BaseActivity  {
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.onelinear)
    LinearLayout onelinear;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.aa)
    Button aa;
    @BindView(R.id.bb)
    Button bb;
    @BindView(R.id.cc)
    Button cc;
    @BindView(R.id.dd)
    Button dd;
    @BindView(R.id.ee)
    Button ee;
    @BindView(R.id.ff)
    Button ff;
    @BindView(R.id.second)
    RelativeLayout second;
    Timer timer;
    private Handler handler = new Handler();

    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab04_first_activity);
        ButterKnife.bind(this);
        myThread();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        btn6.setText("ding...");
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    private void myThread() {

    }

    private void OperateIO() throws IOException {
        String sdPath = Environment.getExternalStorageDirectory().toString();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//如果已经挂载
            String filepath = sdPath + File.separator + "First_Activity" + ".png";
            FileInputStream in = null;
            FileOutputStream fos = null;
            File file = new File(filepath);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                Log.i("zzg", "OperateIO: file already exists..");
            }
            byte[] buf = new byte[1024];
            int len = 0;
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
            canvas.drawText("CSDN", 100, 100, paint);
            try {
                in = new FileInputStream(file);
                fos = new FileOutputStream(file);
//                while ((len=in.read(buf))!=-1){
//                    fos.write(buf,0,len);
//                }
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                in.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    private void clickTest() {
        iv1 = findViewById(R.id.iv1);
        //新建一个数组用来存储图片
        final int[] imgs = {R.drawable.test1, R.drawable.test2, R.drawable.test3};

        timer = new Timer();//全局声明，在这里实例化，如果全局实例化就会取消掉timer，导致不能循环
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    i++;
                    i = i % 3;
                    iv1.setImageResource(imgs[i]);
                });
            }
        }, 1000, 3000);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn6, R.id.btn4, R.id.btn5, R.id.aa, R.id.bb, R.id.cc, R.id.dd, R.id.ee, R.id.ff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:" + "10010"));
                startActivity(intent);
                break;
            case R.id.btn2:
                linear = findViewById(R.id.linear);
                LayoutInflater inflater = LayoutInflater.from(this);
                View view1 = inflater.inflate(R.layout.tab01, null);
                linear.addView(view1);
                break;
            case R.id.btn3:
                linear.removeAllViews();
                break;
            case R.id.btn6:
                new Thread() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        // message.arg1=88;
                        handler.sendMessage(message);
                    }
                }.start();
                break;
            case R.id.btn4:
                clickTest();
                break;
            case R.id.btn5:
                if (timer != null) {
                    timer.cancel();
                } else {
                    return;
                }
                break;
            case R.id.aa:
                try {
                    OperateIO();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bb:
                startActivity(new Intent(FirstActivity.this, CartoonsDoActivity.class));
                break;
            case R.id.cc:
                startActivity(new Intent(FirstActivity.this, WebViewActivity.class));
                break;
            case R.id.dd:
                startActivity(new Intent(FirstActivity.this, CartoonsListActivity.class));
                break;
            case R.id.ee:
                startActivity(new Intent(FirstActivity.this, SocketClientActivity.class));
                break;
            case R.id.ff:
                startActivity(new Intent(FirstActivity.this, CharactersDoActivity.class));
                break;
        }
    }
}
