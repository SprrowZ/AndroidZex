package com.example.myappsecond;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myappsecond.Project.Http_test;
import com.example.myappsecond.Project.Http_test1;
import com.example.myappsecond.Project.ImageLoaderUntils;


/**
 * Created by ZZG on 2017/10/12.
 */

public class SecondActivity extends BaseActivity implements View.OnClickListener{
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private EditText editText;
    Handler handler;
    LinearLayout li_1;
  //  Fragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       setContentView(R.layout.tab04_second_activity);
        getFuck();

    }

 private void addView() {
     //底下这个逻辑是加载View不是加载fragment
//        LayoutInflater inflater=LayoutInflater.from(this);
//      View additem= inflater.inflate(R.layout.http_test1,null);
//     TextView text=additem.findViewById(R.id.text);
//     text.setText("fuck...");
//        li_1.addView(additem);
     Http_test1 fragment=new Http_test1();
     FragmentManager fm=getSupportFragmentManager();
     FragmentTransaction transaction=fm.beginTransaction();
     transaction.replace(R.id.framelayout,fragment);
     transaction.commit();


  }


    private void getFuck() {
  btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        li_1=findViewById(R.id.li_1);
        editText=findViewById(R.id.et);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }
public void HttpClient_login(){

}
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                addView();
                break;
            case R.id.btn2:
                Intent intent=new Intent(this,Http_test.class);
                startActivity(intent);
                break;
            case R.id.btn3:
                HttpClient_login();
                break;
            case R.id.btn4:
            Intent intent2=new Intent(this,ImageLoaderUntils.class);
                 startActivity(intent2);
//                File sdDir = null;
//                boolean sdCardExist = Environment.getExternalStorageState()
//                        .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
//                if(sdCardExist)
//                {
//                    sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//                }
//                Toast.makeText(this,sdDir.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn5:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("et",editText.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String ett=savedInstanceState.getString("et");
        editText.setText(ett);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
