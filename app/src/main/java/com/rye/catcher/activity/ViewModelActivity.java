package com.rye.catcher.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rye.catcher.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewModelActivity extends AppCompatActivity {
    @BindViews({R.id.score1,R.id.score2})
    List<TextView> scores;
    @BindViews({R.id.add1,R.id.add2})
    List<Button> btns;

    private int num1=0;
    private int num2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.add1,R.id.add2})
    public void onViewClicked(View view){
         switch (view.getId()){
             case R.id.add1:
                 ++num1;
                 scores.get(0).setText(String.valueOf(num1));
                 break;
             case R.id.add2:
                 ++num2;
                 scores.get(1).setText(String.valueOf(num2));
                 break;
         }
    }
}
