package com.example.myappsecond.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.fragments.MenuFragment;
import com.example.myappsecond.R;

/**
 * Created by angel on 2017/10/23.
 */

public class MenuActivity extends BaseActivity {
    Fragment menu;
    private LinearLayout menu_fragment;
    private Button option_menu;
    private static boolean Ok=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        addFragment();
        showMenu();
    }

    private void showMenu() {
        option_menu=findViewById(R.id.option_menu);
        option_menu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              Ok=true;
                return false;
            }
        });
    }

    private void addFragment() {
        //添加fragment
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        menu=new MenuFragment();
        transaction.add(R.id.menu_fragment,menu);
        transaction.commit();
        //设置容纳fragment的容器的大小
 menu_fragment=findViewById(R.id.menu_fragment);
        menu_fragment.getLayoutParams().height=500;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
   if (Ok==true){
       getMenuInflater().inflate(R.menu.option_menu,menu);
   }
        return true;
    }
}
