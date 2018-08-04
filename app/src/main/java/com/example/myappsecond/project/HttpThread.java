package com.example.myappsecond.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zzg on 2017/10/16.
 */

public class HttpThread extends Thread {
    private String  url;
    private WebView webView;
    private Handler handler;
    private ImageView imageView;


    public  HttpThread(String url,WebView webView,Handler handler){
        this.url=url;
        this.webView=webView;
        this.handler=handler;
    }
    public  HttpThread(String url,ImageView imageView,Handler handler){
        this.url=url;
        this.imageView=imageView;
        this.handler=handler;
    }

    @Override
    public void run() {
        try {
           /*  URL Httpurl=new URL(url);
            HttpURLConnection conn= (HttpURLConnection) Httpurl.openConnection();
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            InputStream in=conn.getInputStream();
            FileOutputStream out = null;
            File downloadFile = null;
            Log.i("info", "run: fuck........................");
            String fileName=String.valueOf(System.currentTimeMillis());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File parent=Environment.getExternalStorageDirectory();
                 downloadFile=new File(parent,fileName);
                out=new FileOutputStream(downloadFile);

            }
            byte[] b=new byte[2*1024];
            int len;
            if(out!=null){
                while((len=in.read(b))!=-1){
                    out.write(b,0,len);
                }
            }

            final Bitmap bitmap=BitmapFactory.decodeStream(in);
           // final Bitmap bitmap= BitmapFactory.decodeFile(downloadFile.getAbsolutePath());
handler.post(new Runnable() {
    @Override
    public void run() {
imageView.setImageBitmap(bitmap);
    }
});

*/
            URL httpUrl=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)httpUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            InputStream in=connection.getInputStream();
            //通过获取网上的输入流，来将图片传给Bitmap
            final Bitmap bitmap= BitmapFactory.decodeStream(in);

            //同样通过post更新UI
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);

                }
            });




           /* final  StringBuilder sb=new StringBuilder();
            BufferedReader reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while((str=reader.readLine())!=null){
                sb.append(str);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webView.getSettings().setJavaScriptEnabled(true);

                 webView.loadDataWithBaseURL(url,sb.toString(),"text/html","utf-8",null);
                }
            });*/

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.run();
    }
}
