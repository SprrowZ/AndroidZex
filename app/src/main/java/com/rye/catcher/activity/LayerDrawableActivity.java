package com.rye.catcher.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Button;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

/**
 * Created by ZZG on 2017/11/8.
 */

public class LayerDrawableActivity extends BaseActivity {
    private Button btn1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_layerdrawable);
        init();
    }

    private void init() {

    }
}
