package com.rye.catcher.activity.adapter.recyadapter.samedata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.beans.recybean.DataModel;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class TypeOneViewHolder extends TypeAbstractViewHolder {
    public ImageView  avatar;
    public TextView  name;

    public TypeOneViewHolder(View itemView) {
        super(itemView);
        avatar=itemView.findViewById(R.id.avatar);
        name=itemView.findViewById(R.id.name);
        itemView.setBackgroundResource(R.color.soft8);
    }
    @Override
    public void bindHolder(DataModel dataModel){
        avatar.setBackgroundColor(dataModel.avatarColor);
        name.setText(dataModel.name);
    }
}
