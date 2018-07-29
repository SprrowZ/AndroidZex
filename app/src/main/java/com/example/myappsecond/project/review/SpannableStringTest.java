package com.example.myappsecond.project.review;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

/**
 * Created by ZZG on 2018/2/28.
 */

public class SpannableStringTest extends BaseActivity {
    private TextView tv1;private TextView tv2;
    private TextView tv3;private TextView tv4;
    private TextView tv5;private TextView tv6;
    private TextView tv7;private TextView tv8;
    private TextView tv9;private TextView tv10;
    private TextView[] textViews=new TextView[]{
            tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10
    };
    private int[] textView=new int[]{
            R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,
            R.id.tv6,R.id.tv7,R.id.tv8,R.id.tv9,R.id.tv10
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spannablestring_test);
        for (int i=0;i<textViews.length;i++){
            textViews[i]=findViewById(textView[i]);
        }
        initFont();
    }

    private void initFont() {
        String[] sources=new String[]{
        "白雪歌送武判官回京","北风卷地白草折，胡天八月即飞雪","忽如一夜春风来，千树万树梨花开",
                "将军角弓不得控，都护铁衣冷难着","瀚海阑干百丈冰，愁云惨淡万里凝",
                "中军置酒饮归客，胡琴琵琶与羌笛","纷纷暮雪下辕门，风掣红旗冻不翻",
                "轮台东门送君去，去时雪满天山路(表情)","山回路转不见君，雪上空留马行处"
        };
        //标题RelativeSizeSpan 设置字体大小
        SpannableString spannableString1=new SpannableString(sources[0]);
        RelativeSizeSpan span1=new RelativeSizeSpan(0.6f);
        RelativeSizeSpan span2=new RelativeSizeSpan(0.8f);
        RelativeSizeSpan span3=new RelativeSizeSpan(1f);
        RelativeSizeSpan span4=new RelativeSizeSpan(1.2f);
        RelativeSizeSpan span5=new RelativeSizeSpan(1.5f);
        RelativeSizeSpan span6=new RelativeSizeSpan(1.2f);
        RelativeSizeSpan span7=new RelativeSizeSpan(1f);
        RelativeSizeSpan span8=new RelativeSizeSpan(0.8f);
        RelativeSizeSpan span9=new RelativeSizeSpan(0.6f);

        spannableString1.setSpan(span1,0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span2,1,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span3,2,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span4,3,4,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span5,4,5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span6,5,6,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span7,6,7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span8,7,8,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(span9,8,9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textViews[0].setText(spannableString1);
        //第一句 ForegroundColorSpan 设置前景色
        SpannableString spannableString2=new SpannableString(sources[1]);
        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString2.setSpan(foregroundColorSpan,4,10,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textViews[1].setText(spannableString2);
        //第二句 BackgroundColorSpan 设置背景色????
        SpannableString spannableString3=new SpannableString(sources[2]);
        BackgroundColorSpan backgroundColorSpan=new BackgroundColorSpan(Color.parseColor("#AC0099EE"));
        spannableString2.setSpan(backgroundColorSpan,4,10,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textViews[2].setText(spannableString3);
        //第三句 StrikethroughSpan 设置删除线
        SpannableString spannableString4=new SpannableString(sources[3]);
        StrikethroughSpan strikethroughSpan=new StrikethroughSpan();
        spannableString4.setSpan(strikethroughSpan,4,10,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textViews[3].setText(spannableString4);
        //第四句 UnderlineSpan 设置下划线
        SpannableString spannableString5=new SpannableString(sources[4]);
        UnderlineSpan underlineSpan=new UnderlineSpan();
        spannableString5.setSpan(underlineSpan,4,10,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textViews[4].setText(spannableString5);
        //第五句 SuperscriptSpan,SubscriptSpan 设置上标和下标
        SpannableString spannableString6=new SpannableString(sources[5]);
        SuperscriptSpan superscriptSpan=new SuperscriptSpan();
        RelativeSizeSpan relativeSizeSpan=new RelativeSizeSpan(0.5f);
        SubscriptSpan  subscriptSpan1=new SubscriptSpan();
        RelativeSizeSpan relativeSizeSpan1=new RelativeSizeSpan(0.5f);
        spannableString6.setSpan(subscriptSpan1,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString6.setSpan(relativeSizeSpan1,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString6.setSpan(superscriptSpan,14,15,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString6.setSpan(relativeSizeSpan,14,15,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textViews[5].setText(spannableString6);
        //第六句 StyleSpan 设置风格
        SpannableString spannableString7=new SpannableString(sources[6]);
        StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
        spannableString7.setSpan(styleSpan,4,10,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textViews[6].setText(spannableString7);
        //第七句 ImageSpan 设置文本图片
       SpannableString spannableString8=new SpannableString(sources[7]);
        Drawable drawable=getResources().getDrawable(R.mipmap.smile);
        drawable.setBounds(0,-50,90,40);
        ImageSpan imageSpan=new ImageSpan(drawable);
        spannableString8.setSpan(imageSpan,15,19,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textViews[7].setText(spannableString8);
        //第八句 ClickableSpan 设置可点击的文本,这个可以替代，而且本身实现有些复杂，暂且不加
        //URLSpan，是ClickableSpan的子类


    }
}
