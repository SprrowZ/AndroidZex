package com.rye.opengl.demos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.rye.base.BaseActivity;
import com.rye.opengl.R;

public class ShapeActivity extends BaseActivity {
    private GLSurfaceView mSurfaceView;
    private TriangleRenderer mRenderer;

    private RecyclerView mRecycler;
    private FrameLayout mContainer;

    public static void start(Context context) {
        Intent intent = new Intent(context, ShapeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shape;
    }

    @Override
    public void initWidget() {
        mRecycler = findViewById(R.id.recycler);
        mContainer = findViewById(R.id.container_shape);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
    }

    @Override
    public void initEvent() {
        mRenderer = new TriangleRenderer(this);
        mSurfaceView.setRenderer(mRenderer);
        mContainer.removeAllViews();
        mContainer.addView(mSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }


}