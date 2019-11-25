package com.rye.catcher.project.ctmviews;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rye.catcher.R;

/**
 * Created by Zzg on 2017/11/21.自定义ImageView的形状，圆形，椭圆，矩形，圆角矩形
 */


public class CircleImageView extends View {
private BitmapShader bitmapShader=null;
private Bitmap bitmap=null;
private ShapeDrawable shapeDrawable=null;
    private int BitmapWidth=0;
    private int BitmapHeight=0;
    public CircleImageView(Context context) {
        super(context);
        //得到图像
        bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.bk)).getBitmap();
        BitmapWidth=bitmap.getWidth();
        BitmapHeight=bitmap.getHeight();
        //构造渲染器BitmapShader
        bitmapShader=new BitmapShader(bitmap,Shader.TileMode.MIRROR,Shader.TileMode.REPEAT);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //讲图片裁剪为椭圆形
        //构建ShapeDrawable对象兵定义形状为椭圆，这里是定义形状的，可以改变的，就在这里改变
        shapeDrawable=new ShapeDrawable(new OvalShape());
        //得到画笔并设置渲染器
        Paint paint=shapeDrawable.getPaint();
        paint.setShader(bitmapShader);
        //设置显示区域
        shapeDrawable.setBounds(120,120,BitmapWidth+120,BitmapHeight+120);
        //绘制shapeDrawable
        shapeDrawable.draw(canvas);
    }

















}
