package com.rye.catcher.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZZG on 2018/1/10.
 */

public class LivePreservationActivity extends BaseActivity {
    private static final String TAG = "LivePreservationActivit";
    @BindView(R.id.container)
    LinearLayout container;
    private static final String DATE_FORMAT="yyyy年MM月dd日 HH:mm:ss";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_conflict);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        Log.i(TAG, "onCreate:---------------------->");
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
        String time=sdf.format(date);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG, "onSaveInstanceState: ---------->");
    }
}
