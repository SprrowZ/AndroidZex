package com.example.myappsecond.Review;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myappsecond.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by angel on 2017/10/28.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    View header;
    //头部高度
    int headerHeight;
    //定义一个变量，为当前第一个可见的item的位置
    int firstVisibleItem;//当前第一个可见的item的位置,在OnSroll里调用，是第一个参数
    int scrollState;//listview当前滚动状态
    boolean isRemark;//标记，当前是在listView最顶端按下的
    int startY;//按下时的Y值
    int state;//listView当前的状态
    final int NONE=0;//正常状态
    final int PULL=1;//提示下拉状态
    final int RELESE=2;//提示释放状态
    final int REFLASHING=3;//刷新状态
    IReflashListener iReflashListener;//刷新数据的接口
    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**\
     * 初始化界面，将头部文件添加到listview
     * @param context
     */
    private void initView(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        header=inflater.inflate(R.layout.review_listview_refresh,null);
        //底下这句话非常重要，省了很多视频中复杂的步骤
        header.measure(0,0);
        headerHeight=header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
        //设置滑动事件
        this.setOnScrollListener(RefreshListView.this);
    }
    //设置上边距
    private void topPadding(int topPadding){
        header.setPadding(header.getPaddingLeft(),topPadding,
                header.getPaddingRight(),header.getPaddingBottom());
        header.invalidate();
    }
//继承的Scroll事件
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
this.scrollState=i;
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
 this.firstVisibleItem=i;

    }
//判断下滑的各个动作
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem==0){
              isRemark=true;
              startY= (int) ev.getY();
            }
            break;
            case MotionEvent.ACTION_MOVE:
            onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                //设置完底下的三个状态再设置这个
                if(state==RELESE){
                    state=REFLASHING;
                    //状态发生变化的时候改变界面
                    reflashViewByState();
                    //加载数据
                    iReflashListener.onReflash();
                }else if (state==PULL){
                    state=NONE;
                    isRemark=false;
                    //状态发生变化的时候改变界面
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    //判断移动过程中的状态变化
    private void onMove(MotionEvent ev){
         if (!isRemark){
             return ;
         }
         //取一下当前移动到哪个位置了
         int tempY= (int) ev.getY();
        //设置最大下拉距离
        int space = Math.min(tempY - startY, 500);
      //下拉状态，header一点一点移动，故要不断的设置高度
        int topPadding=space-headerHeight;
         switch(state){
             case NONE:
                 if (space>0)
                     state=PULL;
                 //状态发生变化的时候改变界面
                 reflashViewByState();
                 break;
             case PULL:
                 //header高度不断进行改变
                 topPadding(topPadding);
                 //下拉大于一定高度,并且正在滚动，在这一步加了一个全局变量scrollState
                 if (space>headerHeight+30&&scrollState==SCROLL_STATE_TOUCH_SCROLL){
                      state=RELESE;
                     //状态发生变化的时候改变界面
                     reflashViewByState();
                 }
                 break;
             case RELESE:
                 //header高度不断进行改变
                 topPadding(topPadding);
                 //小于一定高度，在放开状态
                 if (space<headerHeight+30){
                     state=PULL;
                     //状态发生变化的时候改变界面
                     reflashViewByState();
                 }else if(space<=0){
                     state=NONE;
                     isRemark=false;
                     //状态发生变化的时候改变界面
                     reflashViewByState();
                 }
                 break;
         }

    }
    //根据当前状态进行显示
    private void reflashViewByState(){
        TextView tip=header.findViewById(R.id.tip);
        ImageView arrow=header.findViewById(R.id.pull);
        ProgressBar progress=header.findViewById(R.id.progress);
        //给箭头添加一个动画,箭头先向下，在向上，再向下，所以需要两个动画
        RotateAnimation anim=new RotateAnimation(0,180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
              anim.setDuration(500);
              anim.setFillAfter(true);
        RotateAnimation anim1=new RotateAnimation(180,0,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
              anim1.setDuration(500);
              anim1.setFillAfter(true);

   switch (state){
       case NONE:
           //移除动画，从RELESE那里设置的动画
           arrow.clearAnimation();
           topPadding(-headerHeight);
           break;
       case PULL:
           arrow.setVisibility(View.VISIBLE);
           progress.setVisibility(View.GONE);
           tip.setText("下拉可以刷新");
           //添加箭头动画
           arrow.clearAnimation();
           arrow.setAnimation(anim1);
           break;
       case RELESE:
           arrow.setVisibility(View.INVISIBLE);
           progress.setVisibility(View.GONE);
           tip.setText("松开可以刷新");
           //添加动画
           arrow.clearAnimation();
           arrow.setAnimation(anim);
           break;
       case REFLASHING:
           topPadding(50);
           arrow.setVisibility(View.INVISIBLE);
           progress.setVisibility(View.VISIBLE);
           tip.setText("正在刷新...");
           //不清除动画是不能行的
           arrow.clearAnimation();
           break;
   }
    }
    //获取完数据
 public void reflashComplete(){
     state=NONE;
     isRemark=false;
     reflashViewByState();
     TextView date=header.findViewById(R.id.date);
     SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
     Date dateNow=new Date(System.currentTimeMillis());
     String time=format.format(dateNow);
     date.setText(time);
 }
 public void setInterface(IReflashListener iReflashListener){
 this.iReflashListener=iReflashListener;
 }
/**
 * 刷新数据接口
 */
public interface  IReflashListener{
    void onReflash();
}
}

















































