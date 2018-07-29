package com.example.myappsecond.project.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/10/30.
 */

public class Topbar extends RelativeLayout {
private Button leftButton,rightButton;
    private TextView tvTitle;

    private int leftTextColor;
    private Drawable leftBackground;
    private  String leftText;

    private int rightTextColor;
    private Drawable rightBackground;
    private  String rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String title;
//布局属性
    private  LayoutParams leftParams,rightParams,titleParams;
    //控件事件监听器,接口回调机制
    private topbarClickListener listener;
    public interface topbarClickListener{
        void leftClick();
         void rightClick();
    }
    public void setOnTopbarClickListener(topbarClickListener listener){
        this.listener=listener;
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //连接自定义的属性
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.Topbar);
        //获取左边的属性
        leftTextColor=ta.getColor(R.styleable.Topbar_leftTextColor,0);
                leftBackground=ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftText=ta.getString(R.styleable.Topbar_leftText);
      //获取右边的属性
        rightTextColor=ta.getColor(R.styleable.Topbar_rightTextColor,0);
           rightBackground=ta.getDrawable(R.styleable.Topbar_rightBackground);
        rightText=ta.getString(R.styleable.Topbar_rightText);
//标题属性
        titleTextSize=ta.getDimension(R.styleable.Topbar_titleTextsize,0);
        titleTextColor=ta.getColor(R.styleable.Topbar_titleTextColor,0);
        title=ta.getString(R.styleable.Topbar_title);
        //使用完回收
        ta.recycle();
        //处理所需要的控件
        leftButton=new Button(context);
        rightButton=new Button(context);
        tvTitle=new TextView(context);
        //给控件设置属性
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);
   //右边
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);
        //标题
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);
        //设置一个总的背景色
        setBackgroundColor(0xFFF59563);
        //将这些控件添加到布局上(Viewgroup)去
        leftParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(leftButton,leftParams);

        rightParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(rightButton,rightParams);
        titleParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(tvTitle,titleParams);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
 rightButton.setOnClickListener(new OnClickListener() {
     @Override
     public void onClick(View v) {
               listener.rightClick();
     }
 });
    }

























}
