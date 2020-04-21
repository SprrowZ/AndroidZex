package com.rye.catcher.activity.adapter.recy.diffdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recy.recybean.DataModelOne;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class TypeOneViewHolderEx extends TypeAbstractViewHolderEx<DataModelOne> {
    public ImageView  avatar;
    public TextView  name;

    public TypeOneViewHolderEx(View itemView) {
        super(itemView);
        avatar=itemView.findViewById(R.id.avatar);
        name=itemView.findViewById(R.id.name);
        itemView.setBackgroundResource(R.color.soft8);
    }

    @Override
    public void bindHolder(DataModelOne dataModel){
        avatar.setBackgroundColor(dataModel.avatarColor);
        name.setText(dataModel.name);
    }
}
