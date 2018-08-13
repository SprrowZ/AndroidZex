package com.example.myappsecond.project.ctmviews;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.fragment.CtmViewFragment;
import com.example.myappsecond.fragment.DistortionFragment;
import com.example.myappsecond.fragment.TelescopeFragment;

/**
 * Created by ZZG on 2018/3/8.
 */

public class CtmEighthActivity extends BaseActivity {
    private String origin;
    private TelescopeFragment telescopeFragment;
    private DistortionFragment distortionFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctm_eighth);
        origin = getIntent().getStringExtra(CtmViewFragment.ORIGIN);
        init();
    }

    private void init() {
        telescopeFragment = new TelescopeFragment();
        distortionFragment = new DistortionFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, telescopeFragment)
                .add(R.id.container, distortionFragment)
                .hide(distortionFragment)
                .show(telescopeFragment).commit();
        //判斷
         if (CtmViewFragment.TELESCOPE.equals(origin)) {
            getSupportFragmentManager().beginTransaction()
                    .hide(distortionFragment)
                    .show(telescopeFragment).commit();

        } else if (CtmViewFragment.DISTORTIONVIEW.equals(origin)) {
            getSupportFragmentManager().beginTransaction()
                    .hide(telescopeFragment)
                    .show(distortionFragment).commit();
        }
    }
}