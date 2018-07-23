package com.example.myappsecond.CustomViews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Zzg on 2017/10/31.
 */

public class MyButtonGroup extends RelativeLayout {
    private TextView textView;
    private Button firstButton,secondButton,thirdButton,fourthButton;

    private String title;
    private int titleTextColor;
    private float titleTextSize;

    private String firstButtonText;
    private Drawable firstButtonBack;
    private int firstButtonForth;

    private String secondButtonText;
    private Drawable secondButtonBack;
    private int secondButtonForth;

    private String thirdButtonText;
    private Drawable thirdButtonBack;
    private int thirdButtonForth;

    private String fourthButtonText;
    private Drawable fourthButtonBack;
    private int fourthButtonForth;


    public MyButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.myButtonGroup);
//        title=ta.getString(R.styleable.myButtonGroup_title);
//        titleTextColor=ta.getColor(R.styleable.myButtonGroup_titleTextColor,0);
//        titleTextSize=ta.getDimension(R.styleable.myButtonGroup_titleTextSize,0);
//
//        firstButtonText=ta.getString(R.styleable.myButtonGroup_firstButtonText);
//        firstButtonBack=ta.getDrawable(R.styleable.myButtonGroup_firstButtonBack);
//        firstButtonForth=ta.getColor(R.styleable.myButtonGroup_firstButtonForth,0);
//
//        secondButtonText=ta.getString(R.styleable.myButtonGroup_secondButtonText);
//        secondButtonBack=ta.getDrawable(R.styleable.myButtonGroup_secondButtonBack);
//        secondButtonForth=ta.getColor(R.styleable.myButtonGroup_secondButtonForth,0);
//
//        thirdButtonText=ta.getString(R.styleable.myButtonGroup_thirdButtonText);
//        thirdButtonBack=ta.getDrawable(R.styleable.myButtonGroup_thirdButtonBack);
//        thirdButtonForth=ta.getColor(R.styleable.myButtonGroup_thirdButtonForth,0);
//
//        fourthButtonText=ta.getString(R.styleable.myButtonGroup_fourthButtonText);
//        fourthButtonBack=ta.getDrawable(R.styleable.myButtonGroup_fourthButtonBack);
//        fourthButtonForth=ta.getColor(R.styleable.myButtonGroup_fourthButtonForth,0);
    }
}

