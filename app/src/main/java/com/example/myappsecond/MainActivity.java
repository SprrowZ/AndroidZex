package com.example.myappsecond;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myappsecond.Animations.Memory;
import com.example.myappsecond.Fragments.Address_Fragment;
import com.example.myappsecond.Fragments.Frd_Fragment;
import com.example.myappsecond.Fragments.Settings_Fragment;
import com.example.myappsecond.Fragments.Weixin_Fragment;
import com.example.myappsecond.Project.Viewpager.helloActivity;

import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;
    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgAddress;
    private ImageButton mImgSettings;

   // private Fragment mTab01;
   // private Fragment mTab02;
   //  private Fragment mTab03;
   // private Fragment mTab04;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    private TextView textView;
    private EditText editText;
    private Button ensure;
    private Button cancel;
    private TextView wrong;
    private AlertDialog dialog;
    private View myview;

private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initEvents();
        setSelect(0);
       // jump();广告页
        findDream();

    }

    private void findDream() {
        textView=findViewById(R.id.textView);
        myview=getLayoutInflater().inflate(R.layout.dialog_password,null);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"dddd",Toast.LENGTH_LONG).show();
                dialog=new AlertDialog.Builder(MainActivity.this).create();
                cancel=myview.findViewById(R.id.cancel);
                dialog.setCancelable(false);
                dialog.setView(myview);
                dialog.show();

//                WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
//                params.height= (int) (MeasureUtil.getHeight(MainActivity.this)*0.3);
//                params.width= (int) (MeasureUtil.getWidth(MainActivity.this)*0.9);
//
//                dialog.getWindow().setAttributes(params);
//                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
        editText=myview.findViewById(R.id.editText);
        ensure=myview.findViewById(R.id.ensure);
        wrong=myview.findViewById(R.id.wrong);
        cancel=myview.findViewById(R.id.cancel);
        //监听editText
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!editText.getText().equals("")){
                   wrong.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(editText.getText().equals("")){
//                    wrong.setVisibility(View.GONE);
//                }
            }
        });




        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals(getString(R.string.password))){
                    Intent intent=new Intent(MainActivity.this, Memory.class);
                    startActivity(intent);
                    //一个view只能有一个父布局，所以dialog消失，必须去掉myview，下次再重新加载
                    if (myview.getParent() != null) {
                        ((ViewGroup) myview.getParent()).removeAllViews();
                    }
                    editText.setText("");
                    dialog.dismiss();
                }
                else{
                    wrong.setVisibility(View.VISIBLE);
                   // editText.setText("");
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //一个view只能有一个父布局，所以dialog消失，必须去掉myview，下次再重新加载
                if (myview.getParent() != null) {
                    ((ViewGroup) myview.getParent()).removeAllViews();
                }
                dialog.dismiss();
                editText.setText("");
                wrong.setVisibility(View.GONE);
            }
        });
    }


    private void jump() {//广告页
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,helloActivity.class);
                startActivity(intent);
            }
        },1000);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initEvents() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);

    }

    private void initView() {
        mViewPager=findViewById(R.id.id_viewpager);

        mTabWeixin=findViewById(R.id.li_1);
        mTabFrd=findViewById(R.id.li_2);
        mTabAddress=findViewById(R.id.li_3);
        mTabSettings=findViewById(R.id.li_4);
        mImgWeixin=findViewById(R.id.image1);
        mImgFrd=findViewById(R.id.image2);
        mImgAddress=findViewById(R.id.image3);
        mImgSettings=findViewById(R.id.image4);

        mFragments=new ArrayList<Fragment>();
        Fragment mTab01=new Weixin_Fragment();
        Fragment mTab02=new Frd_Fragment();
        Fragment mTab03=new Address_Fragment();
        Fragment mTab04=new Settings_Fragment();
        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);
        mFragments.add(mTab04);
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
     mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem=mViewPager.getCurrentItem();
                resetImg();
                switch (currentItem){

                    case 0:

                        mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed2);
                        break;
                    case 1:

                        mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed2);
                        break;
                    case 2:

                        mImgAddress.setImageResource(R.drawable.tab_address_pressed2);
                        break;
                    case 3:

                        mImgSettings.setImageResource(R.drawable.tab_settings_pressed2);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
private void setSelect(int i){
    //切换图片
    //切换内容区域
    //FragmentManager fm=getSupportFragmentManager();
    //FragmentTransaction transaction=fm.beginTransaction();
    switch (i){
        case 0:
           // mTab01=new Weixin_Fragment();
           // transaction.replace(R.id.id_content,mTab01);
            mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed2);
            break;
        case 1:
          //  mTab02=new Frd_Fragment();
            //transaction.replace(R.id.id_content,mTab02);
            mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed2);
            break;
        case 2:
            //mTab03=new Address_Fragment();
            //transaction.replace(R.id.id_content,mTab03);
            mImgAddress.setImageResource(R.drawable.tab_address_pressed2);
            break;
        case 3:
           // mTab04=new Settings_Fragment();
           // transaction.replace(R.id.id_content,mTab04);
            mImgSettings.setImageResource(R.drawable.tab_settings_pressed2);
            break;

    }


    mViewPager.setCurrentItem(i);

    //transaction.commit();
}

//    private void hideFragment(FragmentTransaction transaction) {
//         if (mTab01!=null){
//             transaction.hide(mTab01);
//         }
//         if (mTab02!=null){
//             transaction.hide(mTab02);
//        }
//        if (mTab03!=null){
//             transaction.hide(mTab03);
//         }
//         if (mTab04!=null){
//            transaction.hide(mTab04);
//         }
//     }


    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()){
            case R.id.li_1:
                setSelect(0);
                break;
            case R.id.li_2:
                setSelect(1);
                break;
            case R.id.li_3:
                setSelect(2);
                break;
            case R.id.li_4:
                setSelect(3);
                break;
        }
    }

    private void resetImg() {
        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal2);
        mImgFrd.setImageResource(R.drawable.tab_find_frd_normal2);
        mImgAddress.setImageResource(R.drawable.tab_address_normal2);
        mImgSettings.setImageResource(R.drawable.tab_settings_normal2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
