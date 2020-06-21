package com.rye.catcher.activity.ctmactivity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.rye.catcher.BaseOldActivity;
import com.rye.catcher.project.ctmviews.CircleImageView;

/**
 * Created by Zzg on 2017/11/21.
 */

public class CtmFivthActivity extends BaseOldActivity {
    private CircleImageView circleImageView=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circleImageView=new CircleImageView(this);
        setContentView(circleImageView);

    }
}
