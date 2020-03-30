package com.dawn.zgstep.others.job1;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobOneBean {

    public String title;
    public List<BangumiBean> datas;
    public static class BangumiBean{
        public String date;
        public int isToday;
        public List<Episode> episodes;
    }
    public static class Episode{
        public String cover;
        public long episodeId;
        public String date;
        public String title;
        public String index;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static JobOneBean getFakeDatas(){
        JobOneBean bean=new JobOneBean();
        bean.title="番剧";
        final int[] day = {3};
        List<BangumiBean> sunBeans=new ArrayList<>();
        for (int i=0;i<12;i++){
            BangumiBean outerItem=new BangumiBean();
            outerItem.date=String.format("3-%d", day[0]++);
            if (TextUtils.equals(sunBeans.get(i).date,"3-8")){
                outerItem.isToday=1;
            }else{
                outerItem.isToday=0;
            }
            int dataSize=new Random().nextInt(5)+3;
            for (int j=0;j<dataSize;j++){
                Episode episode=new Episode();
                episode.date=outerItem.date;
                episode.title=String.format("我是Child-%d",new Random(10));
                outerItem.episodes.add(episode);
            }
            sunBeans.add(outerItem);

        }
        bean.datas=sunBeans;
        return bean;

    }
}
