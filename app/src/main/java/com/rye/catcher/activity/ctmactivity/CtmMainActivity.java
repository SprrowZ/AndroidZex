package com.rye.catcher.activity.ctmactivity;

import android.content.Context;
import android.content.Intent;

import com.dawn.zgstep.ui.ctm.fragments.CtmColorMatrixFragment;
import com.dawn.zgstep.ui.ctm.fragments.CtmFontViewFragment;
import com.dawn.zgstep.ui.activity.DemoActivity;
import com.rye.base.BaseRecyclerActivity;
import com.rye.base.beans.JsonBean;
import com.rye.base.widget.BaseRecyclerAdapter;
import com.rye.catcher.project.ctmviews.dellist.DelMainActivity;
import com.rye.catcher.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by ZZG on 2017/11/9.
 *
 * ---- TODO 这一块，要全部都移动到step模块中去
 */

public class CtmMainActivity extends BaseRecyclerActivity<JsonBean> {

    @NotNull
    @Override
    public DataInfo<JsonBean> getDataInfo() {
        return new DataInfo<>("ctm", JsonBean[].class);
    }

    @NotNull
    @Override
    public BaseRecyclerAdapter<JsonBean> initAdapter() {
        return new CtmAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ctm_main;
    }

    @Override
    public void initEvent() {

    }


    class CtmAdapter extends BaseRecyclerAdapter<JsonBean> {
        @Override
        public void bindHolder(@NotNull BaseRecyclerHolder holder, int position) {
            holder.content.setText(mDataList.get(position).name);
            String action =  mDataList.get(position).action;
            holder.content.setOnClickListener(v -> {
                onClick(action,v.getContext());
            });
        }

        private void onClick(String action,Context context) {
            switch (action) {
                case "testColorMatrix":
                    CtmFragmentActivity.start(context, CtmColorMatrixFragment.class.getName());
                    break;
                case "testFonts":
                    CtmFragmentActivity.start(context, CtmFontViewFragment.class.getName());
                    break;
                case "testDelete-Ex":
                    startActivity(new Intent(context, DelMainActivity.class));
                    break;
                case "testXferMode":
                    DemoActivity.start(context);
                    break;
                case "testNext":
                    startActivity(new Intent(context, CtmNextActivity.class));
                    break;
            }
        }


    }


}
