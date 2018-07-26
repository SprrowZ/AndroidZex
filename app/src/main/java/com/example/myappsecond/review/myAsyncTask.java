package com.example.myappsecond.review;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myappsecond.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ZZG on 2017/10/31.
 */

public class myAsyncTask extends Activity {
    private  ImageView iv;
    private ProgressBar pg;
    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_asynctask);
        iv=findViewById(R.id.iv);
        pg=findViewById(R.id.pg);
        myAsync start=new myAsync();
        url="http://a.hiphotos.baidu.com/image/h%3D300/sign=92725f16b312c8fcabf3f0cdcc0292b4/8326cffc1e178a8259f85296fc03738da877e8c4.jpg";
        start.execute(url);

    }
    class myAsync extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
            String url=params[0];
            Bitmap bitmap=null;
            URLConnection connection;
            InputStream is;
            try {
                Thread.sleep(3000);
                connection=new URL(url).openConnection();
                is=connection.getInputStream();
                BufferedInputStream bis=new BufferedInputStream(is);
                bitmap= BitmapFactory.decodeStream(bis);
                is.close();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //运行前使进度条显示出来
            pg.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            pg.setVisibility(View.GONE);
            iv.setImageBitmap(bitmap);
        }
    }
}
