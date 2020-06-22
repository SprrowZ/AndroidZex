package com.rye.catcher.activity.ctmactivity;

import android.content.Context;
import android.content.Intent;
import com.dawn.zgstep.ctm.CtmFirstActivity;
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
                case "testInvalidate":
                    Intent intent1 = new Intent(context, CtmFirstActivity.class);
                    startActivity(intent1);
                    break;
                case "testColorMatrix":
                    Intent intent2 = new Intent(context, CtmSecondActivity.class);
                    startActivity(intent2);
                    break;
                case "testFonts":
                    Intent intent3 = new Intent(context, CtmThirdActivity.class);
                    startActivity(intent3);
                    break;
                case "testCircle":
                    Intent intent4 = new Intent(context, CtmFivthActivity.class);
                    startActivity(intent4);
                    break;
                case "testDelete":
                    startActivity(new Intent(context, CtmSixthActivity.class));
                    break;
                case "testDelete-Ex":
                    startActivity(new Intent(context, DelMainActivity.class));
                    break;
                case "testDelete-My" :
                    startActivity(new Intent(context, CtmSeventhActivity.class));
                    break;
                case "testNext":
                    startActivity(new Intent(context, CtmMain2Activity.class));
                    break;
            }
        }


    }


}
