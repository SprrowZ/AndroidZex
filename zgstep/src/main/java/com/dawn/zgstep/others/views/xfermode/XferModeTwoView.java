package com.dawn.zgstep.others.views.xfermode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class XferModeTwoView extends View {
    private Paint mPaint;
    private Canvas mCanvas;
    private int canvasWidth;
    private int canvasHeight;
    int radius;
    private int yellow;
    private int blue;
    private int mType;
    private Paint txtPaint;
    private List<String>  descriptionList;

    public XferModeTwoView(Context context) {
        super(context, null);
    }

    public XferModeTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, -1);
    }

    public XferModeTwoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE,null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        radius = canvasWidth / 3;
        yellow = 0xFFFFCC44;
        blue = 0xFF66AAFF;
        mCanvas = canvas;
        dealRefresh();
        txtPaint = new Paint();
        txtPaint.setColor(0xFF000000);
        txtPaint.setTextSize(46);
        drawText();
    }
    private List<String> initData(){
        descriptionList = new ArrayList<>();
        descriptionList.add("Clear: 清除源图的所有alpha和颜色值");
        descriptionList.add("Src: 只绘制源图的alpha和颜色值");
        descriptionList.add("Src_In: 在源图和目标图相交的地方绘制源图像，目标图像其余部分不受影响");
        descriptionList.add("Src_Out: 在源图像和目标图像不想交的地方绘制源图像");
        descriptionList.add("Src_Atop: 在源图像和目标图像相交的地方绘制源图想，在不相交的地方绘制目标图像");
        descriptionList.add("Src_Over: 在目标图像上层绘制源图");
        descriptionList.add("Dst: 只绘制目标图");
        descriptionList.add("Dst_In: 在源图像和目标图相交的地方绘制目标图像");
        descriptionList.add("Dst_Out: 在源图像和目标图像不相交的地方绘制目标图像");
        descriptionList.add("Dst_Atop: 在源图像和目标图像相交的地方绘制目标图像");
        descriptionList.add("Dst_Over: 目标图像被绘制在源图像的上方");
        descriptionList.add("Xor: 在源图像和目标图形重叠之外的地方绘制他们，在重叠的地方什么都不绘制");
        descriptionList.add("Darken: 获得每个位置上两幅图像中最暗的像素并显示");
        descriptionList.add("Lighten: 获得每个位置上两幅图像中最亮的像素并显示");
        descriptionList.add("Multiply: 将每个位置的两个像素相乘，除以255，然后使用该值" +
                "创建一个新的像素进行显示。结果颜色=顶部颜色*底部颜色/255");
        descriptionList.add("Screen: 反转每个颜色，执行相同的操作（将他们相乘并除以255），" +
                "然后再次反转。结果颜色=255-(((255-顶部颜色)*(255-底部颜色))/255)");
        descriptionList.add("OverLay: 叠加");
        descriptionList.add("Add: 饱和度相加");
        return descriptionList;
    }


    private void dealRefresh(){
        switch (mType){
            case 0:
                clearXferByLayer();
                break;
            case 1:
                src();
                break;
            case 2:
                srcIn();
                break;
            case 3:
                srcOut();
                break;
            case 4:
                srcAtop();
                break;
            case 5:
                srcOver();
                break;
            case 6:
                dst();
                break;
            case 7:
                dstIn();
                break;
            case 8:
                dstOut();
                break;
            case 9:
                dstAtop();
                break;
            case 10:
                dstOver();
                break;
            case 11:
                xor();
                break;
            case 12:
                darken();
                break;
            case 13:
                lighten();
                break;
            case 14:
                multiply();
                break;
            case 15:
                screen();
                break;
            case 16:
                overlay();
                break;
            case 17:
                add();
                break;
        }
    }
    public void refresh(int type){
        mType = type;
        postInvalidate();
    }

    private void drawText(){

        String targetText = initData().get(mType);


        mCanvas.drawText(initData().get(mType ),0,3*radius,txtPaint);
    }


    private void normalDraw() {
        mCanvas.drawARGB(255, 139, 197, 186);
        //黄色
        mPaint.setColor(yellow);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        //蓝色
        mPaint.setColor(blue);
        mCanvas.drawRect(radius, radius, radius * 2.7f, radius * 2.7f, mPaint);
    }

    private void clearXfer() {
        mCanvas.drawARGB(255, 139, 197, 186);

        //黄色
        mPaint.setColor(0xFFFFCC44);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        //CLEAR --MODE  :会显示白色矩形 （Activity默认背景色是白色）
        // 画笔Paint已经设置Xfermode的值为PorterDuff.Mode.CLEAR，（规则就是清空区域内项目）
        //先画的叫目标图，后画的叫源图

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //蓝色
        mPaint.setColor(0xFF66AAFF);
        mCanvas.drawRect(radius, radius, radius * 2.7f, radius * 2.7f, mPaint);
        //去除XferMode
        mPaint.setXfermode(null);
    }

    @SuppressLint("NewApi")
    public void clearXferByLayer() {
        mCanvas.drawARGB(255, 139, 197, 186);
        //黄色
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, mPaint);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        //先画的叫目标图，后画的叫源图

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //蓝色
        mPaint.setColor(blue);
        mCanvas.drawRect(radius, radius, radius * 2.7f, radius * 2.7f, mPaint);
        //去除XferMode
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }

    @SuppressLint("NewApi")
    public void src() {
        mCanvas.drawARGB(255, 139, 197, 186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, mPaint);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        mPaint.setColor(blue);
        mCanvas.drawRect(radius, radius, 2.7f * radius, 2.7f * radius, mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }

    @SuppressLint("NewApi")
    public void srcIn() {
        mCanvas.drawARGB(255, 139, 197, 186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, mPaint);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawRect(radius, radius, 2.7f * radius, 2.7f * radius, mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void srcOut(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void srcAtop(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void srcOver(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);

    }

    @SuppressLint("NewApi")
    public void dst(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void dstIn(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void dstOut(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void dstAtop(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void dstOver(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void xor(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void darken(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void lighten(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(yellow);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void multiply(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void screen(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }
    @SuppressLint("NewApi")
    public void overlay(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
   }
    @SuppressLint("NewApi")
    public void add(){
       mCanvas.drawARGB(255,139,197,186);
       mPaint.setColor(yellow);
       int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
       mCanvas.drawCircle(radius,radius,radius,mPaint);
       mPaint.setColor(blue);
       mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
       mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
       mPaint.setXfermode(null);
       mCanvas.restoreToCount(layerId);
   }



}
