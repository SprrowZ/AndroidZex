package com.rye.catcher.project;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.rye.catcher.R;

/**
 * Created by zzg on 2017/10/11.
 */

public class MyDialog extends Dialog {
private Button btn_dialog;

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.dialog);
        btn_dialog=findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.this.dismiss();
            }
        });
    }
}
