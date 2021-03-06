package com.rye.catcher.activity;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZG on 2017/11/8.
 */

public class DrawableMainActivity extends BaseActivity{
    @BindView(R.id.thistitle)
    TextView thistitle;
    @BindView(R.id.orr)
    Button btn1;
    @BindView(R.id.javaMore)
    Button btn2;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.someDemo)
    Button btn3;
    @BindView(R.id.retrofit)
    Button btn4;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.linear4)
    LinearLayout linear4;

    private View.OnClickListener listener;

    public void setMyOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.orr, R.id.javaMore, R.id.someDemo, R.id.retrofit, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.orr:
                Intent intent1 = new Intent(this, LayerDrawableActivity.class);
                startActivity(intent1);
                break;
            case R.id.javaMore:
                TransitionDrawable transition = (TransitionDrawable) btn2.getBackground();
                transition.startTransition(1000);
                break;
            case R.id.someDemo:
                break;
            case R.id.retrofit:
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                break;
            case R.id.btn7:
                break;
            case R.id.btn8:
                break;
            case R.id.btn9:
                break;
        }
    }
}
