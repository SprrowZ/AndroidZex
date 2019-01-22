package com.rye.catcher.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recyadapter.diffdata.DemoAdapterEx;
import com.rye.catcher.activity.adapter.recyadapter.samedata.DemoAdapter;
import com.rye.catcher.beans.recybean.DataModel;
import com.rye.catcher.beans.recybean.DataModelOne;
import com.rye.catcher.beans.recybean.DataModelThree;
import com.rye.catcher.beans.recybean.DataModelTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class RecycleDemoActivity extends BaseActivity {
    public static final String TAG = "RecycleDemoActivity";
    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private DemoAdapterEx adapterEx;
    private List<DataModel> dataList;
    int colors[] = {R.color.soft1, R.color.soft2, R.color.soft3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_list);
        recyclerView = findViewById(R.id.recyclerView);
        //recycleView+gridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //最为重要！！！！
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {//一个Item横跨几列
            @Override
            public int getSpanSize(int position) {//上面GridLayoutManager中的数字，相当于分母，底下相当于分子
                //举例来说，如果这里是1，那么Item就占一行的二分之一，如果是2，就占一整行，如果是3，那你自己试试吧！
                int type = recyclerView.getAdapter().getItemViewType(position);
                if (type == DataModel.TYPE_THREE) {
                    return gridLayoutManager.getSpanCount();//就是上面的2
                } else {
                    return 1;
                }
            }
        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {//item之间设置间距
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                int spanSize = layoutParams.getSpanSize();//根据spanSize进行间隔的处理，是1还是2
                int spanIndex = layoutParams.getSpanIndex();
                // 所有的距离顶部的距离
                outRect.top = 20;
                if (spanSize != gridLayoutManager.getSpanCount()) {//一行不为1个的
                    if (spanIndex == 1) {
                        outRect.left = 10;
                    } else {//为2，也就是
                        outRect.right = 10;
                    }
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

      //  initData();//数据格式相同
        initDataEx();//数据格式不相同
    }

    private void initDataEx() {
        adapterEx=new DemoAdapterEx(this);
        recyclerView.setAdapter(adapterEx);
        //遍历的时候进行操作并不好，最好的是遍历的时候把值赋给另一个列表，然后addAll
        List<DataModelOne> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataModelOne data = new DataModelOne();
            data.avatarColor = colors[0];
            data.name = "name1:";
            list1.add(data);
        }

        List<DataModelTwo> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataModelTwo data = new DataModelTwo();
            data.avatarColor = colors[0];
            data.name = "name2:";
            data.content = "content2:";
            list2.add(data);
        }

        List<DataModelThree> list3 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataModelThree data = new DataModelThree();
            data.avatarColor = colors[0];
            data.name = "name3:";
            data.content = "content3:";
            data.contentColor = colors[2];
            list3.add(data);
        }
        adapterEx.addList(list1,list2,list3);
        adapterEx.notifyDataSetChanged();//加了数据之后要提示更新
    }

    private void initData() {


        adapter = new DemoAdapter(this);
        recyclerView.setAdapter(adapter);
        dataList = new ArrayList<>();

        Log.i(TAG, "init: " + (int) 2.7);
        for (int i = 0; i < 20; i++) {
            int type = (int) ((Math.random() * 3) + 1);
            if (i < 5 || i > 15 && i < 20) {
                type = 1;
            }
            if (i >= 5 && i < 10) {
                type = 2;
            }
            if (i >= 10 && i <= 15) {
                type = 3;
            }
            DataModel data = new DataModel();
            data.avatarColor = colors[type - 1];
            data.type = type;
            data.name = "name:" + i;
            data.content = "content:" + i;
            data.contentColor = colors[(type + 1) % 3];
            dataList.add(data);
        }
        adapter.addList(dataList);
        adapter.notifyDataSetChanged();//加了数据之后要提示更新
    }
}
