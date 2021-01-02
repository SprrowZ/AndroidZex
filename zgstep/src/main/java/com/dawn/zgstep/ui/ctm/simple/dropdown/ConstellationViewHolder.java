package com.dawn.zgstep.ui.ctm.simple.dropdown;

import android.graphics.Typeface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dawn.zgstep.R;
import com.rye.base.utils.TypeFaceUtil;

/**
 * Created at 2018/12/19.
 *
 * @author Zzg
 */
public class ConstellationViewHolder extends DropParentViewHolder<DropBean> {
    FrameLayout container;
    TextView content;
    public ConstellationViewHolder(View itemView) {
        super(itemView);
        container=itemView.findViewById(R.id.base_container);
        content=itemView.findViewById(R.id.content);
    }

    @Override
    public void bindHolder(DropBean param) {
        Typeface tf= TypeFaceUtil.getInstance().tf(itemView.getContext());
        content.setText(param.getContent());
        content.setTypeface(tf);
        container.setOnClickListener(data->{

        });
    }
}
