package com.example.myappsecond.CustomViews.ListView_Delete;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by ZZG on 2017/11/28.
 */
public class List_ListView extends ListView {
//    private LinearLayout itemRoot;//父布局
//    private int mlastX=0;//上次位置
//    private final  int MAX_WIDTH=100;//100是删除按钮的宽度
//    private Context mContext;
//    private Scroller mScroller;//Scroller,第一次使用
//
//    public CustomView_ListView(Context context) {
//        super(context);
//    }
//
//    public CustomView_ListView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext=context;
//        //实例化Scroller
//        mScroller=new Scroller(context,new OvershootInterpolator(context,null));
//    }
//    //重写onTouchEvent，用来滑动ListView
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        int maxLength = dipToPx(mContext, MAX_WIDTH);
//
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                //我们想知道当前点击了哪一行
//                int position = pointToPosition(x, y);
//                if (position != INVALID_POSITION) {
//                    List_MergeListAdapter.DataHolder data = (List_MergeListAdapter.DataHolder) getItemAtPosition(position);
//                    itemRoot = data.rootView;
//                }
//            }
//            break;
//            case MotionEvent.ACTION_MOVE: {
//                int scrollX = itemRoot.getScrollX();
//                int newScrollX = scrollX + mlastX - x;
//                if (newScrollX < 0) {
//                    newScrollX = 0;
//                } else if (newScrollX > maxLength) {
//                    newScrollX = maxLength;
//                }
//                itemRoot.scrollTo(newScrollX, 0);
//            }
//            break;
//            case MotionEvent.ACTION_UP: {
//                int scrollX = itemRoot.getScrollX();
//                int newScrollX =scrollX+mlastX-x;
//                if (scrollX > maxLength / 2) {
//                    newScrollX = maxLength;
//                } else {
//                    newScrollX = 0;
//                }
//                //替换掉下面这句
//                //itemRoot.scrollTo(newScrollX, 0);
//                mScroller.startScroll(scrollX,0,newScrollX-scrollX,0);
//                invalidate();//重绘一下，就会重新运行这个类
//            }
//            break;
//        }
//        mlastX = x;
//        return super.onTouchEvent(ev);
//    }
//    private int dipToPx(Context context,int dip){//dp转化为px
//        return (int)(dip*context.getResources().getDisplayMetrics().density+0.5f);
//    }
//    //实现computeScroll函数
//    public void computeScroll(){
//        if (mScroller.computeScrollOffset()){
//            itemRoot.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
//            invalidate();
//        }
//    }

    private List_LinearLayout mCurView;
    public List_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //我们想知道当前点击了哪一行
                int position = pointToPosition(x, y);
                if (position != INVALID_POSITION) {
                    List_MergeListAdapter.DataHolder data = (List_MergeListAdapter.DataHolder) getItemAtPosition(position);
                    mCurView = (List_LinearLayout) data.rootView;
                }
            }
            default:
                if (mCurView != null){
                    mCurView.disPatchTouchEvent(event);
                }
                return super.onTouchEvent(event);
        }

    }

}