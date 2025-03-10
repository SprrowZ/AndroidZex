package com.dawn.zgstep.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dawn.zgstep.R;
import com.dawn.zgstep.ui.ctm.fragments.MVVMDialogFragment;

public class MVVMActivity extends AppCompatActivity {
    private TextView mShowDialog;

    public static void jump(Context context) {
        Intent intent = new Intent(context, MVVMActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        mShowDialog = findViewById(R.id.showDialog);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    private void show() {
        MVVMDialogFragment dialogFragment = new MVVMDialogFragment();
        dialogFragment.show(this.getSupportFragmentManager(),"TEST");
    }
}