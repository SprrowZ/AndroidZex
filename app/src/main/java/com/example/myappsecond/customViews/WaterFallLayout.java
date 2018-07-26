package com.example.myappsecond.customViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZZG on 2018/4/26.
 */

public class WaterFallLayout extends ViewGroup {
    private int childWidth;//子控件宽度
    private int vSpace=20;//垂直间距，底下水平间距
    private int hSpace=20;
    private int  top[];//每列高度
    private int columns=3;//列数


    public WaterFallLayout(Context context) {
        super(context);
                  top = new int[columns]; 
    }

    public WaterFallLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
                  top = new int[columns];
    }

    public WaterFallLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
             top = new int[columns];
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
               //开始摆放子控件
        int childCount=getChildCount();
        clearTop();
        for (int i=0;i<childCount;i++){
           View child=this.getChildAt(i);
           int childHeight=(int)child.getMeasuredHeight()*childWidth/child.getMeasuredWidth();
           int minColum=getMinHeightColum();  //得到最小的列，准备摆放到这里
           int tleft=minColum*(childWidth+hSpace);
           int ttop=top[minColum];
           int tright=tleft+childWidth;
           int tbottom=ttop+childHeight;
           top[minColum]+=vSpace+childHeight;//摆放完，要更新一下这个列的高度
           child.layout(tleft,ttop,tright,tbottom);
           
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子控件宽度
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);//必须测量，否则底下getMeasuredHeight和getMeasureWidth拿不到
        childWidth=(widthSize-(columns-1)*hSpace)/columns;
        //测得总宽度
        int wrapWidth;
        int childCount=getChildCount();
        if (childCount>columns){
            wrapWidth=widthSize;
        }else {
            wrapWidth=columns*childWidth+(columns-1)*hSpace;
        }
      //测得控件总长度
        clearTop();      //清空数据，以防上次数据影响这次计算，也就是每加一个就得重新计算，费时
        for (int i=0;i<getChildCount();i++)  {
            View child=getChildAt(i);
            int childHeight=Integer.valueOf(child.getMeasuredHeight()*childWidth/child.getMeasuredWidth());
            int minColum=getMinHeightColum(); //拿到高度最短的列,一个一个排嘛
            top[minColum]+=vSpace+childHeight;

        }
        //控件总长度
        int wrapHeight=getMaxHeight();
       setMeasuredDimension(widthMode==MeasureSpec.AT_MOST?wrapWidth:widthSize,wrapHeight);
    }

    private int getMaxHeight() {
        int maxcolum=0;
        for (int i=0;i<columns;i++){
            if (top[i]>top[maxcolum]){
                  top[maxcolum]=top[i];
            }

        }
        return top[maxcolum];
    }

    private int getMinHeightColum() {
        int mincolum=0;
        for (int i=0;i<columns;i++){
            if (top[i]<top[mincolum]){
                mincolum=i;
            }
        }
        return mincolum;
    }

    private void clearTop() {
       for (int i=0;i<columns;i++){
           top[i]=0;
       }
    }

    //添加item点击事件
        //监听接口
    public interface  OnItemClickListener{
        void onItemClick();
    }
    public void setOnItemClickListener(final OnItemClickListener listener){
        for (int i=0;i<getChildCount();i++){
            final  int index=i;
            View view=getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick();
                }
            });
        }
    }
                      //仿照layoutParams
    public static  class WaterfallLayoutParams extends ViewGroup.LayoutParams{

        public WaterfallLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public WaterfallLayoutParams(int width, int height) {
            super(width, height);
        }

        public WaterfallLayoutParams(LayoutParams source) {
            super(source);
        }

    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
    return new WaterfallLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
       return new WaterfallLayoutParams(WaterfallLayoutParams.WRAP_CONTENT, WaterfallLayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new WaterfallLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
         return p instanceof WaterfallLayoutParams;
    }
}
