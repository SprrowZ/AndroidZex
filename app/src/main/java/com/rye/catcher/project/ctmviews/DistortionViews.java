package com.rye.catcher.project.ctmviews;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.widget.ImageView;

import com.rye.catcher.R;

/**
 * Created by ZZG on 2018/3/9.
 */

@SuppressLint("AppCompatCustomView")
public class DistortionViews extends ImageView {

    private static  final  String TAG="DistortionViews";
    private Bitmap mBitmap;
    private int type;
    private int radius;
    private int bitmapID;
    //边框
    private int bDistance;
    private int bWidth;
    private int bColor;
    private boolean needBorder;
    private Paint mPaint;
    private Paint zPaint;
    private BitmapShader bitmapShader;
    public DistortionViews(Context context, @Nullable AttributeSet attrs) throws Exception {
        super(context, attrs);
        init(context,attrs);
    }

    public DistortionViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void setBitmap(Bitmap bitmap){
        this.mBitmap=bitmap;
        postInvalidate();
        Log.i(TAG, "setBitmapID: "+bitmap);
    }
    private void init(Context context, AttributeSet attrs) throws Exception {
       mPaint=new Paint();

       TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.DisView);
       //enum取值,getInt
       type= typedArray.getInt(R.styleable.DisView_Dtype,0);

       bitmapID =typedArray.getResourceId(R.styleable.DisView_Dsrc,-1);
        Log.i(TAG, "init: "+ bitmapID);
        if (mBitmap==null){
            mBitmap= BitmapFactory.decodeResource(getResources(), bitmapID);
        }
       if (type==1){//如果为圆角方形
           radius=typedArray.getInt(R.styleable.DisView_Dradius,5);
       }
//       if (BitmapID==-1){
//           throw new Exception("res不能为空");
//       }
       //边框属性
       bDistance = (int) typedArray.getDimension(R.styleable.DisView_Bdistance,5);
       if (bDistance >20){//限制最大距离
           bDistance =20;
       }
       bWidth= (int) typedArray.getDimension(R.styleable.DisView_Bwidth,3);
       if (bWidth>10){//限制最大宽度
           bWidth=10;
       }
       bColor =typedArray.getColor(R.styleable.DisView_Bcolor,getResources().getColor(R.color.alpha_red));

        needBorder=typedArray.getBoolean(R.styleable.DisView_NeedBorder,false);
        //画边框
        zPaint=new Paint();
        zPaint.setStrokeWidth(bWidth);
        zPaint.setColor(bColor);
        zPaint.setStyle(Paint.Style.STROKE);


        typedArray.recycle();
       //Shader别忘了实现
       bitmapShader=new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
    }

    /**
     * 测量，有个神器是resolveSize方法，还是先不用这个的好
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         //属性设置为wrap_content时候，需要走onmeasure，取到的值为精准值
        //获取控件宽高
        int Measurewidth=MeasureSpec.getSize(widthMeasureSpec)+2*(bWidth+ bDistance);
        int MeasurewidthMode=MeasureSpec.getMode(widthMeasureSpec);
        int Measureheight=MeasureSpec.getSize(heightMeasureSpec)+2*(bWidth+ bDistance);
        int MeasureheightMode=MeasureSpec.getMode(heightMeasureSpec);
        //获取照片的宽高，要加上边距
        int width=mBitmap.getWidth()+2*(bWidth+ bDistance);
        int height=mBitmap.getHeight()+2*(bWidth+ bDistance);
        setMeasuredDimension((MeasurewidthMode==MeasureSpec.EXACTLY)?Measurewidth:width,
                (MeasureheightMode==MeasureSpec.EXACTLY)?Measureheight:height);//最关键的一步
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Matrix matrix=new Matrix();
        if (mBitmap.getHeight()==mBitmap.getWidth()){
            float scale= (float)getWidth()/mBitmap.getWidth();
            matrix.setScale(scale,scale);
        }else{
            float Xscale=(float)getWidth()/mBitmap.getWidth();
            float Yscale=(float)getHeight()/mBitmap.getHeight();
            matrix.setScale(Xscale,Yscale);
        }
        //缩放印章上的图片
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        float half=getWidth()/2;
        if (type==0){//圆形
             canvas.drawCircle(half,half,getWidth()/2- bDistance -bWidth,mPaint);
             //需要边框再绘制
             if (needBorder){
                 canvas.drawCircle(half,half,getWidth()/2-bWidth,zPaint);
             }
        }else if (type==1){//方形
            canvas.drawRoundRect(new RectF(bDistance +bWidth, bDistance +bWidth,
                    getWidth()- bDistance -bWidth, getHeight()- bDistance -bWidth),
                    radius,radius,mPaint);
            if (needBorder){
                canvas.drawRoundRect(new RectF(0,0,getWidth()-bWidth,
                        getHeight()-bWidth),radius,radius,zPaint);
            }
        }
    }


}
