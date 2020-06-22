package com.rye.catcher.activity;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.rye.base.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recycler.diffcall.ZDiffCallback;
import com.rye.catcher.activity.adapter.recycler.diffdata.DemoAdapterEx;
import com.rye.catcher.activity.adapter.recycler.diffdata.TitleAdapter;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModel;
import com.rye.catcher.activity.adapter.recycler.samedata.DemoAdapter;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModelEx;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModelOne;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModelThree;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModelTwo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class RecycleDemoActivity extends BaseActivity implements TitleAdapter.OnItemClick {
    public static final String TAG = "RecycleDemoActivity";
    private DemoAdapter mContentAdapter;
    private DemoAdapterEx mContentAdapterEx;

    private TitleAdapter mTitleAdapter;

    private List<DataModelEx> mDataListEx;

    private List<DataModel> mDataList;
    int colors[] = {R.color.soft1, R.color.soft2, R.color.soft3};

    @BindView(R.id.recycler)
    RecyclerView mContent;
    @BindView(R.id.titles)
    RecyclerView mTitles;

    @Override
    public int getLayoutId() {
        return R.layout.province_list;
    }

    @Override
    public void initEvent() {
        //recycleView+gridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //最为重要！！！！
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {//一个Item横跨几列
            @Override
            public int getSpanSize(int position) {//上面GridLayoutManager中的数字，相当于分母，底下相当于分子
                //举例来说，如果这里是1，那么Item就占一行的二分之一，如果是2，就占一整行，如果是3，那你自己试试吧！
                int type = mContent.getAdapter().getItemViewType(position);
                if (type == DataModelEx.TYPE_THREE) {
                    return gridLayoutManager.getSpanCount();//就是上面的2
                } else {
                    return 1;
                }
            }
        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL,false));
        mContent.addItemDecoration(new RecyclerView.ItemDecoration() {//item之间设置间距
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int pos = parent.getChildAdapterPosition(view);//当前pos
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
        mContent.setLayoutManager(gridLayoutManager);

        initData();//数据格式相同
        //    initDataEx();//数据格式不相同
        //标题
        initTitle();
    }

    private void initTitle() {
        mTitleAdapter = new TitleAdapter();
        List<String> datas = new ArrayList<>();
        datas.add("全部更新");
        datas.add("部分更新");
        datas.add("一个更新");
        datas.add("部分插入");
        datas.add("部分删除");
        datas.add("差量更新");
        mTitleAdapter.setDatas(datas);
        mTitleAdapter.setOnItemClickListener(this::onClick);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        mTitles.setLayoutManager(manager);
        mTitles.setAdapter(mTitleAdapter);
        mTitles.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5, 5, 5, 5);
            }
        });
    }

    /**
     * 数据格式不相同
     */
    private void initDataEx() {
        mContentAdapterEx = new DemoAdapterEx(this);
        mContent.setAdapter(mContentAdapterEx);
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
        mContentAdapterEx.addList(list1, list2, list3);
        mContentAdapterEx.notifyDataSetChanged();//加了数据之后要提示更新
    }

    /**
     * 数据格式相同
     */
    private void initData() {
        mContentAdapter = new DemoAdapter(this);
        mContent.setAdapter(mContentAdapter);
        mDataList = new ArrayList<>();
        Log.i(TAG, "init: " + (int) 2.7);
        for (int i = 0; i < 20; i++) {
            DataModel model = new DataModel();
            int type = (int) ((Math.random() * 3) + 1);
            if (i < 5 || i > 15 && i < 20) {
                type = 1;
                model.mBgUrl = R.drawable.bg_one;
                model.mContent = "（づ￣3￣）づ╭❤～";
            }
            if (i >= 5 && i < 10) {
                type = 2;
                model.mBgUrl = R.drawable.bg_two;
                model.mContent = "(๑•̀ㅂ•́)و✧";
            }
            if (i >= 10 && i <= 15) {
                type = 3;
                model.mBgUrl = R.drawable.bg_three;
                model.mContent = "Σ( ° △ °|||)︴";
            }

            model.mType = type;
            mDataList.add(model);
        }
        mContentAdapter.addList(mDataList);
        mContentAdapter.notifyDataSetChanged();//加了数据之后要提示更新
    }


    public List<DataModel> getNewData() { //只变了0~4，16~19之间的
        List<DataModel> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DataModel model = new DataModel();
            int type = (int) ((Math.random() * 3) + 1);
            if (i < 5 || i > 15 && i < 20) {
                type = 2;
                model.mBgUrl = R.drawable.bg_one;
                model.mContent = "（づ￣3￣）づ╭❤～";
            }
            if (i >= 5 && i < 10) {
                type = 2;
                model.mBgUrl = R.drawable.bg_two;
                model.mContent = "(๑•̀ㅂ•́)و✧";
            }
            if (i >= 10 && i <= 15) {
                type = 3;
                model.mBgUrl = R.drawable.bg_three;
                model.mContent = "Σ( ° △ °|||)︴";
            }

            model.mType = type;
            datas.add(model);
        }
        return datas;
    }


    public List<DataModel> getNewData2(int extraSize) {
        List<DataModel> datas = new ArrayList<>();
        for (int i = 0; i < mContentAdapter.getItemCount() + extraSize; i++) {
            DataModel model = new DataModel();
            int type = (int) ((Math.random() * 3) + 1);
            if (i < 5) {
                model.mBgUrl = R.drawable.bg_one;
                model.mContent = "（づ￣3￣）づ╭❤～";
            }
            if (i >= 5) {
                model.mBgUrl = R.drawable.bg_two;
                model.mContent = "(๑•̀ㅂ•́)و✧";
            }

            model.mType = type;
            datas.add(model);
        }
        mDataList = datas;
        return datas;
    }

    /**
     * 更新部分数据
     *
     * @return
     */
    public List<DataModel> updatePartData() {

        for (int i = 0; i < 3; i++) {
            DataModel model = new DataModel();
            //  int type = (int) ((Math.random() * 3) + 1);
            model.mBgUrl = R.drawable.bg_three;
            model.mContent = "(๑•̀ㅂ•́)و✧";
            //不改变Type
            model.mType = mDataList.get(i).mType;
            mDataList.remove(i);
            mDataList.add(i, model);
        }
        return mDataList;
    }

    public List<DataModel> updateSingleData() {
        DataModel model = new DataModel();
        //  int type = (int) ((Math.random() * 3) + 1);

        model.mBgUrl = R.drawable.bg_two;
        model.mContent = "(๑•̀ㅂ•́)و✧";
        //不改变Type
        model.mType = mDataList.get(0).mType;
        mDataList.remove(0);
        mDataList.add(0, model);
        return mDataList;
    }

    @Override
    public void onClick(@NotNull View v, @NotNull int pos) {

        switch (pos) {
            case 0://全量更新
                mContentAdapter.addList(getNewData2(0));
                mContentAdapter.notifyItemChanged();
                break;
            case 1://部分更新 ----成功
                mContentAdapter.addList(updatePartData());
                mContentAdapter.notifyItemRanged(0, 3);
                break;
            case 2://单个更新  --成功
                mContentAdapter.addList(updateSingleData());
                mContentAdapter.notifyItemChanged(0);
                break;
            case 3:
                mContentAdapter.addList(getNewData2(5));
                mContentAdapter.notifyItemInserted(0);
                break;
            case 4:
                break;
            case 5:
                diffUpdate();
                break;
        }
    }

    private List<DataModel> diffData() {
        List<DataModel> newList = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {
            DataModel dataModel = new DataModel();
            if (i < 5) {
                dataModel.mBgUrl = R.drawable.bg_three;
                dataModel.mContent = "I Giao~";
            } else {
                dataModel.mBgUrl = mDataList.get(i).mBgUrl;
                dataModel.mContent = mDataList.get(i).mContent;
            }
            dataModel.mType = mDataList.get(i).mType;
            newList.add(dataModel);
        }
//        DataModel dataModel = new DataModel();
//        dataModel.mBgUrl = R.drawable.bg_three;
//        dataModel.mContent = "Biu~~";
//        dataModel.mType = 3 ;
//        newList.add(0,dataModel);
        return newList;
    }

    /**
     * DiffUtil差量更新
     */
    private void diffUpdate() {
        List<DataModel> newList= diffData();
        ZDiffCallback diffCallback = new ZDiffCallback(newList,mDataList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
        result.dispatchUpdatesTo(mContentAdapter);
        mContentAdapter.addList(newList);
    }
}
