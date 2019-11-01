package com.dawn.zgstep.test_activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dawn.zgstep.R;
import com.dawn.zgstep.demos.annotations.BindView;
import com.dawn.zgstep.demos.annotations.zButterKnife;

public class ZStepMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zButterKnife.bind(this);
    }


}
