package com.rye.catcher.activity.ctmactivity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.CtmViewFragment;
import com.rye.catcher.activity.fragment.DistortionFragment;
import com.rye.catcher.activity.fragment.TelescopeFragment;

import java.util.HashMap;

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