package com.rye.catcher.activity.fragment.ctm;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawn.zgstep.ctm.fragments.CtmDropDownFragment;
import com.dawn.zgstep.ctm.fragments.CtmScanFragment;
import com.dawn.zgstep.ctm.fragments.CtmValueAnimatorFragment;
import com.dawn.zgstep.ctm.fragments.TestCanvasFragment;
import com.rye.base.BaseFragment;
import com.rye.base.interfaces.OnItemClickListener;
import com.rye.catcher.R;
import com.rye.catcher.activity.ctmactivity.CtmFragmentActivity;
import com.rye.catcher.activity.ctmactivity.CtmPTRActivity;
import com.rye.catcher.activity.ctmactivity.CtmWaterFallActivity;
import com.rye.catcher.activity.fragment.DistortionFragment;
import com.rye.catcher.activity.fragment.TelescopeFragment;
import com.rye.catcher.project.ctmviews.ProgressFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZZG on 2018/3/6.
 */

public class CtmMainFragment extends BaseFragment implements OnItemClickListener {
    @BindView(R.id.left_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.content_container)
    FrameLayout mContainer;

    private CtmFragmentAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ctm_main;
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mAdapter = new CtmFragmentAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(getDefaultDataList());
    }

    private View getLayoutXml(@LayoutRes int layId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View content = inflater.inflate(layId, mContainer, false);
        return content;
    }

    private void addView(@LayoutRes int layId) {
        mContainer.removeAllViews();
        mContainer.addView(getLayoutXml(layId));
    }


    private List<CtmData> getDefaultDataList() {
        List<CtmData> dataList = new ArrayList<>();
        dataList.add(new CtmData("遮罩"));
        dataList.add(new CtmData("望远镜效果"));
        dataList.add(new CtmData("圆形头像"));
        dataList.add(new CtmData("ViewGroup"));
        dataList.add(new CtmData("VG_EX"));
        dataList.add(new CtmData("ValueAnimator"));
        dataList.add(new CtmData("瀑布流"));
        dataList.add(new CtmData("太极"));
        dataList.add(new CtmData("进度条"));
        dataList.add(new CtmData("下拉刷新"));
        dataList.add(new CtmData("DropDown"));
        dataList.add(new CtmData("扫描"));
        dataList.add(new CtmData("测试Canvas"));
        return dataList;
    }

    @Override
    public void onItemClick(int pos, View item) {
        switch (pos) {
            case 0:
                break;
            case 1:
                CtmFragmentActivity.start(getContext(), TelescopeFragment.class.getName());
                break;
            case 2:
                CtmFragmentActivity.start(getContext(), DistortionFragment.class.getName());
                break;
            case 3://viewGroup
                addView(R.layout.bcustom_viewgroup);
                break;
            case 4:
                addView(R.layout.bcustom_viewgroupex);
                break;
            case 5://ValueAnimator 属性动画
                CtmFragmentActivity.start(getContext(), CtmValueAnimatorFragment.class.getName());
                break;
            case 6:
                CtmWaterFallActivity.start(getContext());
                break;
            case 7: //太极图

                break;
            case 8://进度条
                CtmFragmentActivity.start(getContext(), ProgressFragment.class.getName());
                break;
            case 9://下滑菜单
                CtmPTRActivity.start(getContext());
                break;
            case 10:
               CtmFragmentActivity.start(getContext(), CtmDropDownFragment.class.getName());
                break;
            case 11:
                CtmFragmentActivity.start(getContext(), CtmScanFragment.class.getName());
                break;
            case 12:
                CtmFragmentActivity.start(getContext(), TestCanvasFragment.class.getName());
                break;

        }
    }
}
