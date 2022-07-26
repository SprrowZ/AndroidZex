package com.ndk.nativelib.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ndk.nativelib.NativeLib;
import com.ndk.nativelib.R;

public class NDKMainActivity extends AppCompatActivity {
    private TextView mNdkFirst;
    private TextView mNdkSecond;
    //native调用测试用
    public String text = "text";
    public int num = 0;
    public static  void jump(Context context){
        Intent intent  = new Intent(context,NDKMainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndkmain);
        mNdkFirst = findViewById(R.id.ndk_first);
        mNdkSecond = findViewById(R.id.ndk_second);
        test();
    }

    private void test() {
        NativeLib nativeLib = new NativeLib();
        mNdkFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), nativeLib.stringFromJNI2(), Toast.LENGTH_SHORT).show();
            }
        });
        mNdkSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nativeLib.stringFromJNI3();//调用native方法修改text属性
                Toast.makeText(v.getContext(),text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}