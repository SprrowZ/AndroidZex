package com.rye.catcher.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.zApplication;
import com.rye.catcher.GreenDaos.Base.TB_Cartoons;
import com.rye.catcher.GreenDaos.Base.DaoSession;

import com.rye.catcher.GreenDaos.Base.TB_CartoonsDao;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.BaseArrayAdapter;
import com.rye.catcher.activity.adapter.GreenAdapter;
import com.rye.catcher.project.catcher.eventbus.MessageEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/7/5.
 */
public class CartoonsListActivity extends BaseActivity {

    /**
     * from CartoonsDoActivity
     */
    public static int  FROM_CARTOON=666;
//    private  PullToRefreshListView listView;
    private  EditText query;
    private DaoSession daoSession;
    private TB_CartoonsDao cartoonsDao;
    private List datalist=new ArrayList<TB_Cartoons>();
    private GreenAdapter adapter;
    private LayoutInflater inflater;
    public static String CARTOON_NAME;
    public static String [] HEROS;
    public static Long ID;
    public static int ITEM_COUNT=20;
    public int batch=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_exone);
      //  init();
        initEvent();
    }

    private void initEvent() {
        //注册EventBus
        EventBus.getDefault().register(this);
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AdapterView.OnItemClickListener vsl=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TB_Cartoons cartoons= (TB_Cartoons) adapter.getItem(position);
                Intent intent=new Intent(CartoonsListActivity.this,CartoonsDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(CartoonsDetailActivity.CARTOON_NAME,cartoons.getNAME());
                bundle.putString(CartoonsDetailActivity.CARTOON_HEROS,
                        cartoons.getHERO()+"、"+cartoons.getHEROINE());
                bundle.putString(CartoonsDetailActivity.CARTOON_PLOT,cartoons.getPLOT());
                bundle.putString(CartoonsDetailActivity.CARTOON_DIRECTOR,cartoons.getDIRECTOR());
                bundle.putString(CartoonsDetailActivity.CARTOON_ACTORS,cartoons.getACTORS());
                if (cartoons.getISSUE_TIME()!=null){
                    bundle.putString(CartoonsDetailActivity.CARTOON_START_TIME,cartoons.getISSUE_TIME().toString());
                }
                bundle.putSerializable(CartoonsDetailActivity.CARTOON_LIST_ACTOR, (Serializable) cartoons.getCHARACTERS());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

//        listView.setOnItemClickListener(vsl);
//        //下拉监听回调
//        listView.setOnRefreshListener(refreshView -> {
//            //获取更新数据
//           new dataAsyncTask(CartoonsListActivity.this).execute();
//           if (listView.isRefreshing()){
//                listView.postDelayed(()->{
//                        listView.onRefreshComplete();
//                }, 500);
//            }
//        });
    }

//    private void init() {
//        setBarTitle("漫");
//        listView=findViewById(R.id.listView);
//        query=findViewById(R.id.search_bar);
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        daoSession= zApplication.getInstance().getDaoSession();
//        cartoonsDao=daoSession.getTB_CartoonsDao();
//        //查询数据进行适配
//        datalist=cartoonsDao.queryBuilder()
//                .limit(CartoonsListActivity.ITEM_COUNT)
//                .list();
//
//        if (adapter==null){
//            adapter=new GreenAdapter(this,datalist);
//            adapter.setService((BaseArrayAdapter.IService<TB_Cartoons>) (item, query) ->
//                    item.getNAME().contains(query)
//                    ||item.getHERO().contains(query)
//                    ||item.getHEROINE().contains(query));
//            listView.setAdapter(adapter);
//        }else{
//          adapter.notifyDataSetChanged();
//          if (query!=null){
//            adapter.getFilter().filter(query.getText());
//          }
//        }
//
//        listView.setAdapter(adapter);
//    }
    //AsyncTask 静态内部类
    private static class  dataAsyncTask extends AsyncTask<Void,Integer,List<TB_Cartoons>>{
          WeakReference<CartoonsListActivity> mActivity;
          public dataAsyncTask(CartoonsListActivity activity){
              mActivity=new WeakReference<>(activity);
          }
          CartoonsListActivity zActivity=mActivity.get();
         @Override
         protected List<TB_Cartoons> doInBackground(Void... voids) {

          final List<TB_Cartoons> newdata =  zActivity.cartoonsDao.queryBuilder()
                     .offset(zActivity.batch*CartoonsListActivity.ITEM_COUNT)
                     .limit(20)
                     .list();
          zActivity.runOnUiThread(() -> {
              if (newdata!=null){
                  zActivity.batch++;
                  zActivity.datalist.addAll(newdata);
                  zActivity.adapter.notifyDataSetChanged();
              }else{

              }
          });
          return newdata;
         }

         @Override
         protected void onPostExecute(List<TB_Cartoons> cartoons) {
             //提交之前进行一些操作吧，加个bottomView,不需要runonuithread，因为这个方法和onprogressupdated一样运行在主线程中
                   if (cartoons==null||cartoons.size()==0){
                               TextView textView=new TextView(zActivity);
                               textView.setText("没有更多数据了");
                 //      zActivity.listView.getRefreshableView().addFooterView(textView);//要getRefreshableView先。。
                   }
             super.onPostExecute(cartoons);
         }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //destroy的时候注销掉EventBus
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread(MessageEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMessage();
        Log.i("EventBus", msg);
        setBarTitle("z漫");
    }

}
