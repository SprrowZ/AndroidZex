package com.dawn.zgstep.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dawn.zgstep.R;

public class ShareElementAActivity extends AppCompatActivity {
    private ImageView mSharedItem;


    public static  void jumpTarget(Context context) {
        Intent intent = new Intent(context,ShareElementAActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element_aactivity);
        mSharedItem = findViewById(R.id.shared_item);
        mSharedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump();
            }
        });
    }

    private void jump() {
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(ShareElementAActivity.this,
                mSharedItem,"sharedElement").toBundle();
        Intent intent = new Intent(ShareElementAActivity.this,ShareElementBActivity.class);
        startActivity(intent,bundle);
    }
}