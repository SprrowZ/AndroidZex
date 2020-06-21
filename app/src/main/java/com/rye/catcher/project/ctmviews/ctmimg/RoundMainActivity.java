package com.rye.catcher.project.ctmviews.ctmimg;

import android.os.Bundle;
import android.view.View;

import com.rye.catcher.BaseOldActivity;
import com.rye.catcher.R;

/**
 * Created by Zzg on 2017/12/1.
 */

public class RoundMainActivity extends BaseOldActivity {
    private RoundImageView mQiQiu;
    private RoundImageView mMeiNv ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_imageview);
        mQiQiu = (RoundImageView) findViewById(R.id.id_qiqiu);
        mMeiNv = (RoundImageView) findViewById(R.id.id_meinv);

        mQiQiu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mQiQiu.setType(RoundImageView.TYPE_ROUND);
            }
        });

        mMeiNv.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                mMeiNv.setBorderRadius(90);
            }
        });
     }
}
