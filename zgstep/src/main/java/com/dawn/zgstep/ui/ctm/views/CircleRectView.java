package com.dawn.zgstep.ui.ctm.views;

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

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.dawn.zgstep.R;


/**
 * Created by ZZG on 2018/3/9.
 */

@SuppressLint("AppCompatCustomView")
public class CircleRectView extends ImageView {

    private static final String TAG = "DistortionViews";
    private Bitmap mBitmap;
    private int type;
    private float radius;
    private int bitmapID;
    //边框
    private int bDistance;
    private int bWidth;
    private int bColor;
    private boolean needBorder;
    private Paint mPaint;
    private Paint zPaint;
    private BitmapShader bitmapShader;

    public CircleRectView(Context context) {
        this(context, null);
    }

    public CircleRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        postInvalidate();
    }

    public void setDrawableId(int sourceId) {
        this.mBitmap = BitmapFactory.decodeResource(getResources(), sourceId);
        bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        postInvalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DisView);
        type = typedArray.getInt(R.styleable.DisView_shape_type, 0);

        bitmapID = typedArray.getResourceId(R.styleable.DisView_shape_src, -1);
        Log.i(TAG, "init: " + bitmapID);
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), bitmapID);
        }
        if (type == 1) {//如果为圆角方形
            radius = typedArray.getDimension(R.styleable.DisView_shape_radius, 5);
        }
//       if (BitmapID==-1){
//           throw new Exception("res不能为空");
//       }
        //边框属性
        bDistance = (int) typedArray.getDimension(R.styleable.DisView_shape_border_distance, 5);
        if (bDistance > 20) {//限制最大距离
            bDistance = 20;
        }
        bWidth = (int) typedArray.getDimension(R.styleable.DisView_shape_border_width, 3);
        if (bWidth > 10) {//限制最大宽度
            bWidth = 10;
        }
        bColor = typedArray.getColor(R.styleable.DisView_shape_border_color, getResources().getColor(R.color.alpha_red));

        needBorder = typedArray.getBoolean(R.styleable.DisView_shape_need_border, false);
        //画边框
        zPaint = new Paint();
        zPaint.setStrokeWidth(bWidth);
        zPaint.setColor(bColor);
        zPaint.setStyle(Paint.Style.STROKE);
        zPaint.setAntiAlias(true);


        typedArray.recycle();
        //Shader别忘了实现
        if (mBitmap!=null){
            bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        }
    }

    /**
     * 测量，有个神器是resolveSize方法，还是先不用这个的好
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec) + 2 * (bWidth + bDistance);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec) + 2 * (bWidth + bDistance);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取照片的宽高，要加上边距
        int width = mBitmap.getWidth() + 2 * (bWidth + bDistance);
        int height = mBitmap.getHeight() + 2 * (bWidth + bDistance);
        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth : width,
                (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight : height);//最关键的一步
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapShader == null) return;
        Matrix matrix = new Matrix();
        if (mBitmap.getHeight() == mBitmap.getWidth()) {
            float scale = (float) getWidth() / mBitmap.getWidth();
            matrix.setScale(scale, scale);
        } else {
            float xScale = (float) getWidth() / mBitmap.getWidth();
            float yScale = (float) getHeight() / mBitmap.getHeight();
            matrix.setScale(xScale, yScale);
        }
        //缩放印章上的图片
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        float half = getWidth() / 2;
        if (type == 0) {//圆形
            canvas.drawCircle(half, half, getWidth() / 2 - bDistance - bWidth, mPaint);
            //需要边框再绘制
            if (needBorder) {
                canvas.drawCircle(half, half, getWidth() / 2 - bWidth, zPaint);
            }
        } else if (type == 1) {//方形
            canvas.drawRoundRect(new RectF(bDistance + bWidth, bDistance + bWidth,
                            getWidth() - bDistance - bWidth, getHeight() - bDistance - bWidth),
                    radius, radius, mPaint);
            if (needBorder) {
                canvas.drawRoundRect(new RectF(0, 0, getWidth() - bWidth,
                        getHeight() - bWidth), radius, radius, zPaint);
            }
        }
    }


}
