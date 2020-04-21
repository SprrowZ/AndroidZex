package com.rye.catcher.activity.adapter.recy.samedata;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawn.zgstep.demos.annotations.BindView;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recy.recybean.DataModel;
import com.rye.catcher.activity.adapter.recy.recybean.DataModelEx;

import butterknife.ButterKnife;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class TypeTwoViewHolder extends TypeAbstractViewHolder<DataModel> {
    ImageView mAvater;
    TextView mContent;
    FrameLayout mParent;
    public TypeTwoViewHolder(View itemView) {
        super(itemView);
        mAvater = itemView.findViewById(R.id.avatar);
        mContent = itemView.findViewById(R.id.content);
        mParent = itemView.findViewById(R.id.parent);
    }
    @Override
    public void bindHolder(DataModel dataModel){
        mParent.setBackgroundResource(dataModel.mBgUrl);
        mContent.setText(dataModel.mContent);
    }
}
