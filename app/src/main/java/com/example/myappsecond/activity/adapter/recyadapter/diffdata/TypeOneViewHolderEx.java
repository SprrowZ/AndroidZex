package com.example.myappsecond.activity.adapter.recyadapter.diffdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappsecond.R;
import com.example.myappsecond.activity.adapter.recyadapter.samedata.TypeAbstractViewHolder;
import com.example.myappsecond.activity.testdata.DataModel;
import com.example.myappsecond.activity.testdata.DataModelOne;

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
