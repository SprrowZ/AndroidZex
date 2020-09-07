package com.rye.catcher.activity;
import android.widget.Button;
import android.widget.Toast;
import com.dawn.zgstep.others.demos.annotations.BindViewEx;
import com.dawn.zgstep.others.demos.annotations.OnClick;
import com.rye.base.BaseActivity;
import com.rye.catcher.R;

/**
 * Created by ZZG on 2018/8/12.
 * 用于测试自定义注解--
 */
public class ReflectActivity extends BaseActivity {
    @BindViewEx(R.id.btn1)
    Button btn1;

    @OnClick({R.id.btn1})
    private void click(){
        Toast.makeText(this,"xxxxxx--xxxxxx",Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_reflect;
    }

    @Override
    public void initEvent() {
        btn1.setText("Hello MyWorld!");
    }
}
