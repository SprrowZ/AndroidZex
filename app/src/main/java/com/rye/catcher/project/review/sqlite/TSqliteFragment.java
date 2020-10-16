package com.rye.catcher.project.review.sqlite;

import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rye.base.BaseFragment;
import com.rye.base.interfaces.OnItemClickListener;
import com.rye.base.utils.ToastHelper;
import com.rye.catcher.R;
import com.rye.catcher.agocode.beans.PgcBean;
import com.rye.catcher.project.cprovider.SQLiteFunction;
import com.rye.catcher.project.review.sqlite.adapters.SqliteActionAdapter;
import com.rye.catcher.project.review.sqlite.adapters.SqliteResultAdapter;
import com.rye.catcher.project.review.sqlite.helpers.SqliteDemoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020-09-28
 *
 * @description: 测试SQLite
 */
public class TSqliteFragment extends BaseFragment implements OnItemClickListener {
    private static final String TAG = "TSqliteFragment";
    //需要监听的URI
    private static final String OBSERVABLE_URI = "content://com.rye.catcher/ugc";
    //ugcContentObserver msgId
    public static final int UGC_EVENT_ID = 11;

    private RecyclerView mActionRecycler;
    private RecyclerView mResultRecycler;
    private EditText mSelection;

    private SqliteResultAdapter sqliteResultAdapter;
    private SqliteActionAdapter sqliteActionAdapter;


    private SQLiteFunction function;
    //ContentObserver
    private ContentObserver mUgcContentObserver;
    private Handler ugcHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UGC_EVENT_ID:
                    dealUgcChangeEvent((String) msg.obj);
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sqlite;
    }

    @Override
    public void initEvent() {
        super.initEvent();

        mActionRecycler = mRoot.findViewById(R.id.recycler_action);
        mResultRecycler = mRoot.findViewById(R.id.recycler_result);
        mSelection = mRoot.findViewById(R.id.selection);

        handleObserver();
        initRecyclers();
        function = new SQLiteFunction(getContext());
    }

    private void initRecyclers() {
        mActionRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        sqliteActionAdapter = new SqliteActionAdapter();
        sqliteActionAdapter.setOnItemClickListener(this);
        sqliteActionAdapter.setData(SqliteDemoHelper.getBaseActions());
        mActionRecycler.setAdapter(sqliteActionAdapter);
        mActionRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 20;
                outRect.right = 20;
            }
        });

        mResultRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        sqliteResultAdapter = new SqliteResultAdapter();
        //sqliteResultAdapter.setData();
        mResultRecycler.setAdapter(sqliteResultAdapter);

    }

    private void handleObserver() {
        mUgcContentObserver = new UgcContentObserver(ugcHandler);
        getContext().getContentResolver().registerContentObserver(Uri.parse(OBSERVABLE_URI),
                false, mUgcContentObserver);
    }


    private void dealUgcChangeEvent(String result) {
        ToastHelper.showToastShort(getContext(), result);
    }


    private void insertFakeData() {
           function.insertFakeUgcData();  //通过SQLite的insert方法
       // function.insertFakeUgcDataByExecSQL(); //通过sql语句
    }


    private void queryAllDatas() { //数据量较少，可以不开子线程
        List<PgcBean> datas = function.queryAllUgcData();
        Log.e("Rye", "query finish && build ok...");
        sqliteResultAdapter.setData(datas);
    }

    private void updatePartSize() {
        boolean success = function.updateData("《虫师》", 10);
        if (success) {
            ToastHelper.showToastShort(getContext(), "数据更新成功！更新范围 id 0~10");
        }
    }

    private void deleteAllData() {
        boolean success = function.deleteAllData();
        if (success) {
            ToastHelper.showToastShort(getContext(), "删除 id < 100 的数据！");
        }
    }

    private void selectByLike(){ //根据输入框的文字 进行模糊搜索
        mSelection.setVisibility(View.VISIBLE);
        List<PgcBean> pgcBeans = function.selectByLike(String.valueOf(mSelection.getText()));

        sqliteResultAdapter.setData(pgcBeans);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().getContentResolver().unregisterContentObserver(mUgcContentObserver);
    }

    @Override
    public void onItemClick(int pos, View item) {
        switch (pos) {
            case 0: //插入假数据,通过sql
                insertFakeData();
                break;
            case 1: //查询全部数据
                queryAllDatas();
                break;
            case 2: //更新数据
                updatePartSize();
                break;
            case 3: //删除所有数据
                deleteAllData();
                break;
            case 4: //模糊搜索
                selectByLike();

        }
    }
}
