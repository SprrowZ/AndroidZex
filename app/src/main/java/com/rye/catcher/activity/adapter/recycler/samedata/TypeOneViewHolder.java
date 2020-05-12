package com.rye.catcher.activity.adapter.recycler.samedata;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModel;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class TypeOneViewHolder extends TypeAbstractViewHolder<DataModel> {
    ImageView  mAvater;
    FrameLayout mParent;
    TextView  mContent;
    public TypeOneViewHolder(View itemView) {
        super(itemView);
        mAvater = itemView.findViewById(R.id.avatar);
        mParent = itemView.findViewById(R.id.parent);
        mContent = itemView.findViewById(R.id.content);
    }
    @Override
    public void bindHolder(DataModel dataModel){
        mParent.setBackgroundResource(dataModel.mBgUrl);
        mContent.setText(dataModel.mContent);
    }
}
