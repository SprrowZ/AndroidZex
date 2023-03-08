package com.ndk.nativelib.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ndk.nativelib.DynamicRegisterLib;
import com.ndk.nativelib.NativeLib;
import com.ndk.nativelib.R;

public class NDKMainActivity extends AppCompatActivity {
    private TextView mNdkFirst;
    private TextView mNdkSecond;
    //native调用测试用
    public String text = "text";
    public int num = 0;

    public static void jump(Context context) {
        Intent intent = new Intent(context, NDKMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndkmain);
        mNdkFirst = findViewById(R.id.ndk_first);
        mNdkSecond = findViewById(R.id.ndk_second);
        test2();
    }

    //静态注册方式
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
                nativeLib.stringFromJNI3();//调用native方法修改对象相关属性以及调用方法
                NativeLib.stringFromJNI4();//调用static方法
                String changedText = "text:" + nativeLib.getText() + ",num:" + nativeLib.getNum() + ",name:"
                        + nativeLib.getName() + ",静态num1:" + NativeLib.num1;
                Toast.makeText(v.getContext(), changedText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //动态注册方式
    private void test2() {
        mNdkFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "add:" + DynamicRegisterLib.add(10, 10) +
                        ",sub:" + DynamicRegisterLib.sub(20, 5) +
                        ",mul:" + DynamicRegisterLib.mul(4, 7) +
                        ",div:" + DynamicRegisterLib.div(30, 5);
                Toast.makeText(v.getContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}