package com.rye.catcher.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.beans.vm.TestViewModel;

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

    private TestViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);
        ButterKnife.bind(this);

       viewModel= ViewModelProviders.of(this).get(TestViewModel.class);
       //监听数据改变
       viewModel.getCurrentName().observe(this, new Observer<String>() {
           @Override
           public void onChanged(@Nullable String s) {

           }
       });
    }
    @OnClick({R.id.add1,R.id.add2})
    public void onViewClicked(View view){
         switch (view.getId()){
             case R.id.add1:
                 viewModel.setNum1(viewModel.getNum1()+1);
                 scores.get(0).setText(String.valueOf(viewModel.getNum1()));
                 break;
             case R.id.add2:
                 viewModel.setNum1(viewModel.getNum2()+1);
                 scores.get(1).setText(String.valueOf(viewModel.getNum2()));
                 break;
         }
    }
}
