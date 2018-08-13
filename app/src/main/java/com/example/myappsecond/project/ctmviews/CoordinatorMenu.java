package com.example.myappsecond.project.ctmviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * Created by ZZG on 2018/8/11.
 */
public class CoordinatorMenu extends FrameLayout {
    public CoordinatorMenu(@NonNull Context context) {
        super(context);
    }
//    private Context mContext;
//    private int mScreenWidth;
//    private int mScreenHeight;
//    private View mMenuView;
//    private View mMainView;
//    private ViewDragHelper mViewDragHelper;
//    private float TOUCH_SLOP_SENSITIVITY=1.0f;
//    private int mMenuWidth=500;
//    //偏移？
//    private int mMenuOffset=300;
//    //判定方向
//    private boolean mDragOrientation;
//    private boolean LEFT_TO_RIGHT=true;
//    private boolean RIGHT_TO_LEFT=false;
//    private int mSpringBackDistance=200;
//    //透明度
//    private  String mShadowOpacity="00";
//    //优化用，获取菜单状态
//    private static  final  int MENU_CLOSED=1;
//    private static  final  int MENU_OPENED=0;
//    private static  int mMenuState;
//    public CoordinatorMenu(@NonNull Context context) {
//        super(context);
//        this.mContext=context;
//        init();
//    }
//
//    public CoordinatorMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        this.mContext=context;
//        init();
//    }
//
//    public CoordinatorMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.mContext=context;
//        init();
//    }
//    private void init(){
//        mViewDragHelper=ViewDragHelper.create(
//                this,
//                TOUCH_SLOP_SENSITIVITY,
//                new CoordinatorCallback()//
//        );
//        mScreenHeight=MeasureUtil.getScreenHeight(mContext);
//        mScreenWidth=MeasureUtil.getScreenWidth(mContext);
//    }
//
//    //绘制完调用
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        mMenuView=getChildAt(0);//左
//        mMainView=getChildAt(1);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return mViewDragHelper.shouldInterceptTouchEvent(ev);//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //讲点击事件传给mViewDragHelper，必不可少，意义何在？
//        mViewDragHelper.processTouchEvent(event);
//        return true;
//    }
//
//    @Override
//    public void computeScroll() {
//       if (mViewDragHelper.continueSettling(true)){
//           ViewCompat.postInvalidateOnAnimation(this);//处理刷新，实现平滑移动
//       }
//       if (mMainView.getLeft()==0){
//           mMenuState=MENU_CLOSED;
//       }else if (mMainView.getLeft()==mMenuWidth){
//           mMenuState=MENU_OPENED;
//       }
//    }
//    @Override
//    protected Parcelable onSaveInstanceState() {
//        final Parcelable superState = super.onSaveInstanceState();
//        final CoordinatorMenu.SavedState ss = new CoordinatorMenu.SavedState(superState);
//        ss.menuState = mMenuState;//保存状态
//        return ss;
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        if (!(state instanceof CoordinatorMenu.SavedState)) {
//            super.onRestoreInstanceState(state);
//            return;
//        }
//
//        final CoordinatorMenu.SavedState ss = (CoordinatorMenu.SavedState) state;
//        super.onRestoreInstanceState(ss.getSuperState());
//
//        if (ss.menuState == MENU_OPENED) {//读取到的状态是打开的话
//            openMenu();//打开菜单
//        }
//    }
//    public void openMenu(){
//        mViewDragHelper.smoothSlideViewTo(mMainView,mMenuWidth,0);
//        ViewCompat.postInvalidateOnAnimation(CoordinatorMenu.this);
//    }
//    public void closeMenu(){
//        mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
//        ViewCompat.postInvalidateOnAnimation(CoordinatorMenu.this);
//    }
//    class CoordinatorCallback extends ViewDragHelper.Callback{//貌似是核心处理逻辑
//
//        @Override
//        public boolean tryCaptureView(@NonNull View child, int pointerId) {
//            return mMainView==child ||mMenuView==child;//加后面这一句是为了让menu能接受触摸事件，交给main处理
//        }
//
//        @Override
//        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
//            if (capturedChild==mMainView){//当触摸的View是menu
//                mViewDragHelper.captureChildView(mMainView,activePointerId);
//            }
//        }
//
//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//           //需要限制滑动距离
//            if (left<0){
//                left=0;//初始位置是屏幕的左边缘
//            }else if (left>mMenuWidth){
//                left=mMenuWidth;
//            }
//            return  left;
//        }
//        //增加回弹效果，从左向右滑动main的时候小于一定距离松手直接回弹左边，同理，从右向左回弹右边
//        @Override
//        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
//            if (dx>0){
//                mDragOrientation=LEFT_TO_RIGHT;//从左向右
//            }else if (dx<0){
//                mDragOrientation=RIGHT_TO_LEFT;//从右向左
//            }
//
//            float scale=(float)(mMenuWidth-mMenuOffset)/(float)mMenuWidth;
//            int menuLeft=left-((int)(scale*left)+mMenuOffset);
//            mMenuView.layout(menuLeft,mMenuView.getTop(),menuLeft+mMenuWidth,mMenuView.getBottom());
//
//            float showing=(float)(mScreenWidth-left)/(float)mScreenWidth;
//            int hex=255-Math.round(showing*255);
//            if (hex<16){
//                mShadowOpacity="0"+Integer.toHexString(hex);
//            }else{
//                mShadowOpacity=Integer.toHexString(hex);
//            }
//        }
//
//        //View释放后调用
//        @Override
//        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
//            super.onViewReleased(releasedChild, xvel, yvel);
//            if (mDragOrientation==LEFT_TO_RIGHT){//从左向右滑动
//                if (mMainView.getLeft()<mSpringBackDistance){//小于设定的距离
//                    closeMenu();//打开菜单
//                }else{
//                    openMenu();
//                }
//            }else if (mDragOrientation=RIGHT_TO_LEFT){
//                if (mMainView.getLeft()<mMenuWidth-mSpringBackDistance) {//小于设定的距离
//                 closeMenu();
//                }else{
//                    openMenu();
//                }
//            }
//        }
//
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        MarginLayoutParams menuParams= (MarginLayoutParams) mMenuView.getLayoutParams();
//        menuParams.width=mMenuWidth;
//        mMenuView.setLayoutParams(menuParams);
//        //优化，重新亮屏后layout方法会重新调用，子View会重新布局，应避免这种情况
//        if (mMenuState==MENU_OPENED){
//            //保持打开的位置
//            mMenuView.layout(0,0,mMenuWidth,bottom);
//            mMainView.layout(mMenuWidth,0,mMenuWidth+mScreenWidth,bottom);
//            return;
//        }
//        mMenuView.layout(-mMenuOffset,top,mMenuWidth-mMenuOffset,bottom);
//    }
//
//    @Override
//    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//        boolean result=super.drawChild(canvas,child,drawingTime);//完成原有的子View：menu和main的绘制
//        int shadowLeft=mMainView.getLeft();//阴影左边缘位置
//        final Paint shadowPaint=new Paint();
//        shadowPaint.setColor(Color.parseColor("#"+mShadowOpacity+"777777"));//给画笔设置透明度变化的颜色
//        shadowPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(shadowLeft,0, mScreenWidth,mScreenHeight,shadowPaint);
//        //避免过度绘制
//        final int restoreCount = canvas.save();//保存画布当前的剪裁信息
//
//        final int height = getHeight();
//        final int clipLeft = 0;
//        int clipRight = mMainView.getLeft();
//        if (child == mMenuView) {
//            canvas.clipRect(clipLeft, 0, clipRight, height);//剪裁显示的区域
//        }
//
//        //恢复画布之前保存的剪裁信息
//        //以正常绘制之后的view
//        canvas.restoreToCount(restoreCount);
//
//       return  result;
//    }
//        //屏幕切换时保存状态值
//    protected  static  class SavedState extends AbsSavedState{
//   int menuState;//记录菜单状态的值
//            protected SavedState(Parcelable superState) {
//                super(superState);
//            }
//            @TargetApi(Build.VERSION_CODES.N)
//            SavedState(Parcel in, ClassLoader loader){
//                super(in,loader);
//                menuState=in.readInt();
//            }
//
//            @Override
//            public void writeToParcel(Parcel dest, int flags) {
//                super.writeToParcel(dest, flags);
//                dest.writeInt(menuState);
//            }
//        }
//
//


}