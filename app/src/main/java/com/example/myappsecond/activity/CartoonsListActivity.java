package com.example.myappsecond.activity;

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

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.EChatApp;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.GreenDaos.Base.CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.DaoSession;

import com.example.myappsecond.R;
import com.example.myappsecond.adapter.BaseArrayAdapter;
import com.example.myappsecond.adapter.GreenAdapter;
import com.example.myappsecond.project.catcher.eventbus.MessageEvent;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private  PullToRefreshListView listView;
    private  EditText query;
    private DaoSession daoSession;
    private CartoonsDao cartoonsDao;
    private List datalist=new ArrayList<Cartoons>();
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
        init();
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
                Cartoons cartoons= (Cartoons) adapter.getItem(position);

            }
        };

        listView.setOnItemClickListener(vsl);
        //下拉监听回调
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //获取更新数据
               new dataAsyncTask().execute();

                if (listView.isRefreshing()){
                    listView.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            listView.onRefreshComplete();

                        }
                    }, 500);
                }
            }
        });
    }

    private void init() {
        setBarTitle("漫");
        listView=findViewById(R.id.listView);
        query=findViewById(R.id.search_bar);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        daoSession= EChatApp.getInstance().getDaoSession();
        cartoonsDao=daoSession.getCartoonsDao();
        //查询数据进行适配
        datalist=cartoonsDao.queryBuilder()
                .limit(CartoonsListActivity.ITEM_COUNT)
                .list();

        if (adapter==null){
            adapter=new GreenAdapter(this,datalist);
            adapter.setService(new BaseArrayAdapter.IService<Cartoons>() {
                @Override
                public boolean add(Cartoons item, String query) {
                    return item.getNAME().contains(query)
                            ||item.getHERO().contains(query)
                            ||item.getHEROINE().contains(query);
                }
            });
            listView.setAdapter(adapter);
        }else{
          adapter.notifyDataSetChanged();
          if (query!=null){
            adapter.getFilter().filter(query.getText());
          }
        }

        listView.setAdapter(adapter);
    }
    public  class  dataAsyncTask extends AsyncTask<Void,Integer,List<Cartoons>>{

         @Override
         protected List<Cartoons> doInBackground(Void... voids) {
          final List<Cartoons> newdata =  cartoonsDao.queryBuilder()
                     .offset(batch*CartoonsListActivity.ITEM_COUNT)
                     .limit(20)
                     .list();
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  if (newdata!=null){
                      batch++;
                      datalist.addAll(newdata);
                      adapter.notifyDataSetChanged();
                  }else{

                  }
              }
          });
          return newdata;
         }

         @Override
         protected void onPostExecute(List<Cartoons> cartoons) {
             //提交之前进行一些操作吧，加个bottomView,不需要runonuithread，因为这个方法和onprogressupdated一样运行在主线程中
                   if (cartoons==null||cartoons.size()==0){
                               TextView textView=new TextView(CartoonsListActivity.this);
                               textView.setText("没有更多数据了");
                               listView.getRefreshableView().addFooterView(textView);//要getRefreshableView先。。
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
