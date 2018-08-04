package com.example.myappsecond.project.ctmviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2018/3/9.
 */

public class DistortionViews extends View {
    private Bitmap mBitmap;
    private int type;
    private int radius;
    private int BitmapID;
    private Paint mPaint;
    private BitmapShader bitmapShader;
    public DistortionViews(Context context, @Nullable AttributeSet attrs) throws Exception {
        super(context, attrs);
        init(context,attrs);
    }

    public DistortionViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) throws Exception {
       mPaint=new Paint();
//       mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//       mPaint.setColor(Color.RED);
//       mPaint.setStrokeWidth(25);
       TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.DisView);
       //enum取值,getInt
       type= typedArray.getInt(R.styleable.DisView_Dtype,0);
       BitmapID=typedArray.getResourceId(R.styleable.DisView_Dsrc,-1);
       mBitmap= BitmapFactory.decodeResource(getResources(),BitmapID);
       if (type==1){//如果为圆角方形
           radius=typedArray.getInt(R.styleable.DisView_Dradius,5);
       }
       if (BitmapID==-1){
           throw new Exception("res不能为空");
       }
       typedArray.recycle();
       //Shader别忘了实现
       bitmapShader=new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件宽高
        int Measurewidth=MeasureSpec.getSize(widthMeasureSpec);
        int MeasurewidthMode=MeasureSpec.getMode(widthMeasureSpec);
        int Measureheight=MeasureSpec.getSize(heightMeasureSpec);
        int MeasureheightMode=MeasureSpec.getMode(heightMeasureSpec);
        //获取照片
        int width=mBitmap.getWidth();
        int height=mBitmap.getHeight();
        setMeasuredDimension((MeasurewidthMode==MeasureSpec.EXACTLY)?Measurewidth:width,
                (MeasureheightMode==MeasureSpec.EXACTLY)?Measureheight:height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix=new Matrix();
        if (mBitmap.getHeight()==mBitmap.getWidth()){
            float scale=getWidth()/mBitmap.getWidth();
            matrix.setScale(scale,scale);
        }else{
            float Xscale=(float) getWidth()/mBitmap.getWidth();
            float Yscale=(float) getHeight()/mBitmap.getHeight();
            matrix.setScale(Xscale,Yscale);
        }

        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        float half=getWidth()/2;
        if (type==0){
           // canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),50,50,mPaint);
             canvas.drawCircle(half,half,getWidth()/2,mPaint);
        }else if (type==1){
            canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),radius,radius,mPaint);
        }
    }
}
