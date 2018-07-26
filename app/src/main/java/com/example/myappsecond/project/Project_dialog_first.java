package com.example.myappsecond.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/11/14.
 */

public class Project_dialog_first extends BaseActivity {
    private LinearLayout parent;
    private EditText son;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_dialog_first);
//        parent=findViewById(R.id.parent);
         //son=findViewById(R.id.son);
//        MeasureUtil.setLeftScale(this,parent,son,R.drawable.icon_title_bar_edit_search);
    }
}
