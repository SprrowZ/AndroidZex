package com.rye.catcher.activity.adapter.recy.diffdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recy.recybean.DataModelThree;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class TypeThreeViewHolderEx extends TypeAbstractViewHolderEx<DataModelThree> {
    public ImageView avatar;
    public TextView name;
    public TextView  content;
    public ImageView contentImg;
    public TypeThreeViewHolderEx(View itemView) {
        super(itemView);
        avatar=itemView.findViewById(R.id.avatar);
        name=itemView.findViewById(R.id.name);
        content=itemView.findViewById(R.id.appName);
        contentImg=itemView.findViewById(R.id.contentImg);
        itemView.setBackgroundResource(R.color.soft5);
    }
    @Override
    public void bindHolder(DataModelThree dataModel){
        avatar.setBackgroundResource(dataModel.avatarColor);
        name.setText(dataModel.name);
        content.setText(dataModel.content);
        contentImg.setBackgroundResource(dataModel.contentColor);
    }
}
