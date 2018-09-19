package com.example.myappsecond.activity.adapter.recyadapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappsecond.R;
import com.example.myappsecond.activity.testdata.DataModel;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class TypeThreeViewHolder extends TypeAbstractViewHolder {
    public ImageView avatar;
    public TextView name;
    public TextView  content;
    public ImageView contentImg;
    public TypeThreeViewHolder(View itemView) {
        super(itemView);
        avatar=itemView.findViewById(R.id.avatar);
        name=itemView.findViewById(R.id.name);
        content=itemView.findViewById(R.id.content);
        contentImg=itemView.findViewById(R.id.contentImg);
        itemView.setBackgroundResource(R.color.soft5);
    }
    @Override
    public void bindHolder(DataModel dataModel){
        avatar.setBackgroundResource(dataModel.avatarColor);
        name.setText(dataModel.name);
        content.setText(dataModel.content);
        contentImg.setBackgroundResource(dataModel.contentColor);
    }
}
