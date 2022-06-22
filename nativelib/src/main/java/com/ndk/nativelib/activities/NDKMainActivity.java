package com.ndk.nativelib.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ndk.nativelib.NativeLib;
import com.ndk.nativelib.R;

public class NDKMainActivity extends AppCompatActivity {
    private TextView mNdkFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndkmain);
        mNdkFirst = findViewById(R.id.ndk_first);
        test();
    }

    private void test() {
        NativeLib nativeLib = new NativeLib();
        mNdkFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), nativeLib.stringFromJNI2(), Toast.LENGTH_SHORT);
            }
        });
    }
}