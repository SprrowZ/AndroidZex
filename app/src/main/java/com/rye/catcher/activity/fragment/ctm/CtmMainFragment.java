package com.rye.catcher.activity.fragment.ctm;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawn.zgstep.ctm.fragments.CtmDropDownFragment;
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

//    @OnClick({R.id.alpha, R.id.scale, R.id.translate,
//            R.id.rotate, R.id.animation_set_one, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10,
//            R.id.btn11, R.id.btn12})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.alpha:
////                //通过广播控制Activity点击事件
////                View view1 = View.inflate(getActivity(), R.layout.bcustom_setshadowlayer, null);
////                Intent intent = new Intent();
////                intent.setAction("com.custom.fragment.activity");
////                Bundle bundle = new Bundle();
////                bundle.putString("shadow", "true");
////                intent.putExtras(bundle);
////                getActivity().sendBroadcast(intent);
//                break;
//            case R.id.scale:
//                Intent intent2 = new Intent(getActivity(), CtmFragmentActivity.class);
//                Bundle bundle2 = new Bundle();
//                bundle2.putString(CtmMainFragment.FRAGMENT_NAME, CtmMainFragment.TELESCOPE);
//                intent2.putExtras(bundle2);
//                startActivity(intent2);
//                break;
//            case R.id.translate:
//                Intent intent1 = new Intent(getActivity(), CtmFragmentActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putString(CtmMainFragment.FRAGMENT_NAME, CtmMainFragment.DISTORTIONVIEW);
//                intent1.putExtras(bundle1);
//                startActivity(intent1);
//                break;
//            case R.id.rotate:
//                startActivity(new Intent(getActivity(), CtmNinethActivity.class));
//                break;
//            case R.id.animation_set_one:
//
//                break;
//            case R.id.btn6:
//                startActivity(new Intent(getActivity(), CtmElevenActivity.class));
//                break;
//            case R.id.btn7:
//                startActivity(new Intent(getActivity(), CtmWaterFallActivity.class));
//                break;
//            case R.id.btn8:
//                Intent intent8 = new Intent();
//                intent8.setAction("com.custom.fragment.activity");
//                Bundle bundle3 = new Bundle();
//                bundle3.putString("ROTATEFIRST", "true");
//                intent8.putExtras(bundle3);
//                getActivity().sendBroadcast(intent8);
//
//                break;
//
//            case R.id.btn9:
//                //加载一个fragment,发个广播吧。。额，自己坑自己玩
    //            EventBus.getDefault().postSticky(new MessageEvent("insertProgress"));
//                break;
//            case R.id.btn10:
//                Intent intent3 = new Intent(getActivity(), CtmDDActivity.class);
//                startActivity(intent3);
//                break;
//            case R.id.btn11:
//                startActivity(new Intent(getActivity(), CtmPTRActivity.class));
//                break;
//        }
//    }

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
        dataList.add(new CtmData("遮罩..........................15"));
        dataList.add(new CtmData("望远镜效果"));
        dataList.add(new CtmData("圆形头像"));
        dataList.add(new CtmData("ViewGroup"));
        dataList.add(new CtmData("VG_EX"));
        dataList.add(new CtmData("渐变"));
        dataList.add(new CtmData("瀑布流"));
        dataList.add(new CtmData("太极"));
        dataList.add(new CtmData("进度条"));
        dataList.add(new CtmData("下拉刷新"));
        dataList.add(new CtmData("DropDown"));
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
            case 5:
                addView(R.layout.bcustom_gradientex);
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

        }
    }
}
