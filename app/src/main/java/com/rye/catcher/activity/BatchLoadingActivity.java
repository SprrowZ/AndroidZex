package com.rye.catcher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rye.catcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BatchLoadingActivity extends Activity {
    @BindView(R.id.list)
    ListView listView;
    private List<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    View footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_loading);
        ButterKnife.bind(this);
        footer = getLayoutInflater().inflate(R.layout.footer, null);

        listView.setOnScrollListener(new ScrollListener());

        // 模拟数据
        data.addAll(DataService.getData(0, 20));
        adapter = new ArrayAdapter<String>(this, R.layout.listview_item,
                R.id.textView, data);
        listView.addFooterView(footer);// 添加页脚（放在ListView最后）
        listView.setAdapter(adapter);
        listView.removeFooterView(footer);
    }
    private int number = 20; // 每次获取多少条数据
    private int maxpage = 5; // 总共有多少页
    private boolean loadfinish = true; // 指示数据是否加载完成

    private final class ScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            final int loadtotal = totalItemCount;
            int lastItemid = listView.getLastVisiblePosition(); // 获取当前屏幕最后Item的ID
            if ((lastItemid + 1) == totalItemCount) { // 达到数据的最后一条记录
                if (totalItemCount > 0) {
                    // 当前页
                    int currentpage = totalItemCount % number == 0 ? totalItemCount
                            / number
                            : totalItemCount / number + 1;
                    int nextpage = currentpage + 1; // 下一页
                    if (nextpage <= maxpage && loadfinish) {
                        loadfinish = false;
                        listView.addFooterView(footer);
                        // 开一个线程加载数据
                        new Thread(()->{
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // 发送消息
                            handler.sendMessage(handler.obtainMessage(100,
                                    data));
                        }).start();

                    }
                }
            }

        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            Log.i("MainActivity", "onScrollStateChanged(scrollState="
                    + scrollState + ")");
        }

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            data.addAll((List<String>) msg.obj);
            // 告诉ListView数据已经发生改变，要求ListView更新界面显示
            adapter.notifyDataSetChanged();
            if (listView.getFooterViewsCount() > 0) { // 如果有底部视图
                listView.removeFooterView(footer);
            }
            loadfinish = true; // 加载完成
        };
    };


}
  class DataService{
      public static List<String> getData(int offset, int maxResult) { // 分页limit
          // 0,20
          List<String> data = new ArrayList<String>();
          for (int i = 0; i < 20; i++) {
              data.add("ListView数据的分批加载" + i);
          }
          return data;

      }

}
