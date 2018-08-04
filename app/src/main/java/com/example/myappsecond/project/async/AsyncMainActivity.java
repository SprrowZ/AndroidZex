package com.example.myappsecond.project.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.example.myappsecond.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZG on 2017/11/1.
 */

public class AsyncMainActivity extends Activity {
    private ListView mListView;
    private static String URL="http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asyncloading_main);
        mListView=findViewById(R.id.iv_main);
       new  myAsync().execute(URL);
    }
    private List<NewsBean> getJsonData(String url){
       List<NewsBean> newsBeanList=new ArrayList<NewsBean>();
        try {
            String jsonString=readStream(new URL(url).openStream());//去联网获取json数据
            Log.i("my", jsonString);
            //解析json数据
            JSONObject jsonObject;
            NewsBean newsBean;
            jsonObject=new JSONObject(jsonString);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                newsBean=new NewsBean();
                //将联网的数据传给bean，然后全都整合到newsBeanList中去
                newsBean.newsIconUrl=jsonObject.getString("picSmall");
                newsBean.newsTitle=jsonObject.getString("name");
                newsBean.newsContent=jsonObject.getString("description");
                newsBeanList.add(newsBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }
    //联网去读json数据
    private String readStream(InputStream is){
        InputStreamReader isr;
        String result=" ";
        String line=" ";
        try {
            isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            while ((line=br.readLine())!=null){
             result+=line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }
 class  myAsync extends AsyncTask<String,Void,List<NewsBean>>{

     @Override
     protected List<NewsBean> doInBackground(String... params) {
         return getJsonData(params[0]);//取得url
     }

     @Override
     protected void onPostExecute(List<NewsBean> newsBeanList) {
         super.onPostExecute(newsBeanList);
         //设置监听器
         AsyncAdapter myadapter=new AsyncAdapter(newsBeanList,AsyncMainActivity.this);
         mListView.setAdapter(myadapter);
     }
 }
}
