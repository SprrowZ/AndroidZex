package com.rye.catcher.activity.adapter.recyadapter.diffdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recyadapter.samedata.TypeAbstractViewHolder;
import com.rye.catcher.activity.testdata.DataModel;
import com.rye.catcher.activity.testdata.DataModelTwo;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class TypeTwoViewHolderEx extends TypeAbstractViewHolderEx<DataModelTwo> {
    public ImageView avatar;
    public TextView name;
    public TextView  content;
    public TypeTwoViewHolderEx(View itemView) {
        super(itemView);
        avatar=itemView.findViewById(R.id.avatar);
        name=itemView.findViewById(R.id.name);
        content=itemView.findViewById(R.id.content);
        itemView.setBackgroundResource(R.color.soft6);
    }
    @Override
    public void bindHolder(DataModelTwo dataModel){
        avatar.setBackgroundResource(dataModel.avatarColor);
        name.setText(dataModel.name);
        content.setText(dataModel.content);
    }
}
