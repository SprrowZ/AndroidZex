package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rye.catcher.R;
import com.rye.base.utils.MeasureUtil;

/**
 * Created by Zzg on 2017/11/9.
 */

public class Colors extends View {
    private Paint mPaint;
    private Context mContext;
    private Bitmap bitmap;
    //图坐标
    int x,y;
    //
    private int flag;
    public Colors(Context context) {
        super(context);
    }

    public Colors(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;


        //初始化画笔
        initPaint();
        //初始化资源
        initRes(context);
        //
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flag){
                    case 0:
                        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
                        invalidate();
                        flag+=1;
                        break;
                    case 1:
                        ColorMatrix colorMatrix=new ColorMatrix(new float[]{
                                -1, 0, 0, 1, 1,
                                0, -1, 0, 1, 1,
                                0, 0, -1, 1, 1,
                                0, 0, 0, 1, 0,
                        });
                        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                        invalidate();
                        flag+=1;
                        break;
                    case 2:
                        ColorMatrix colorMatrix2=new ColorMatrix(new float[]{
                                1.5F, 1.5F, 1.5F, 0, -1,
                                1.5F, 1.5F, 1.5F, 0, -1,
                                1.5F, 1.5F, 1.5F, 0, -1,
                                0, 0, 0, 1, 0,
                        });
                        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
                        invalidate();
                        flag+=1;
                        break;
                    case 3:
                        ColorMatrix colorMatrix3=new ColorMatrix(new float[]{
                                0.393F, 0.769F, 0.189F, 0, 0,
                                0.349F, 0.686F, 0.168F, 0, 0,
                                0.272F, 0.534F, 0.131F, 0, 0,
                                0, 0, 0, 1, 0,
                        });
                        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix3));
                        invalidate();
                        flag+=1;
                        break;
                    case 4:
                        ColorMatrix colorMatrix4=new ColorMatrix(new float[]{
                                0.393F, 0.769F, 0.189F, 0, 0,
                                0.349F, 0.686F, 0.168F, 0, 0,
                                0.272F, 0.534F, 0.131F, 0, 0,
                                0, 0, 0, 1, 0,
                        });
                        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix4));
                        invalidate();
                        flag+=1;
                        break;
                    case 5:
                        ColorMatrix colorMatrix5=new ColorMatrix(new float[]{
                                0, 0, 1, 0, 0,
                                0, 1, 0, 0, 0,
                                1, 0, 0, 0, 0,
                                0, 0, 0, 1, 0,
                        });
                        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix5));
                        invalidate();
                        flag+=1;
                        break;
                    case 6:
                        mPaint.setColorFilter(new PorterDuffColorFilter(
                                Color.GREEN, PorterDuff.Mode.DARKEN));
                        invalidate();
                        flag=0;
                        break;
                }
            }
        });

    }
    private void initPaint() {
        //颜色矩阵
        ColorMatrix colorMatrix=new ColorMatrix(new float[]{
//                0.5F, 0, 0, 0, 0,
//                0, 0.5F, 0, 0, 0,
//                0, 0, 0.5F, 0, 0,
//                0, 0, 0, 1, 0,
//                1.438F, -0.122F, -0.016F, 0, -0.03F,
//                -0.062F, 1.378F, -0.016F, 0, 0.05F,
//                -0.062F, -0.122F, 1.483F, 0, -0.02F,
//                0, 0, 0, 1, 0,
                -1, 0, 0, 1, 1,
                0, -1, 0, 1, 1,
                0, 0, -1, 1, 1,
                0, 0, 0, 1, 0,
        });

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置颜色过滤器
       // mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
      // mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));

    }

    private void initRes(Context context) {
    bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.bk);
         /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         * 屏幕坐标x轴向左偏移位图一半的宽度
         * 屏幕坐标y轴向上偏移位图一半的高度
         */
         //简单的说就是让图片的中心居中，因为这个x，y代表的是左上角的坐标
         x=MeasureUtil.getScreenWidth(context)/2-bitmap.getWidth()/2;
         y=MeasureUtil.getScreenHeight(context)/2-bitmap.getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制位图
        canvas.drawBitmap(bitmap,x,y,mPaint);
    }
}
