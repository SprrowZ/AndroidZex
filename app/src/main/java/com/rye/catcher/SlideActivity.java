package com.rye.catcher;


import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideActivity extends BaseActivity {
   @BindView(R.id.viewDragHelper)
    Button viewDragHelper;
   @BindView(R.id.srcoller)
   Button scroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        ButterKnife.bind(this);
        init();
    }
    private void init(){

    }
}
