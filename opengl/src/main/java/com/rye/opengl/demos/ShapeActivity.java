package com.rye.opengl.demos;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;

import android.view.View;
import android.widget.FrameLayout;

import com.rye.base.BaseActivity;
import com.rye.opengl.R;
import com.rye.opengl.demos.other.IOnItemClickListener;
import com.rye.opengl.demos.other.ShapeAdapter;
import com.rye.opengl.demos.other.ShapeItemData;

import java.util.ArrayList;
import java.util.List;

public class ShapeActivity extends BaseActivity implements IOnItemClickListener {
    private GLSurfaceView mSurfaceView;
    private GLSurfaceView.Renderer mCurrentRenderer;

    private RecyclerView mRecycler;
    private FrameLayout mContainer;

    private ShapeAdapter mAdapter;

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
        resetSurfaceView();
    }

    @Override
    public void initEvent() {
        setDefaultRenderer();
        mAdapter = new ShapeAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setData(getDefaultData());
    }

    private void setDefaultRenderer() {
        if (mCurrentRenderer == null) {
            mCurrentRenderer = new TriangleRenderer(this);
            mSurfaceView.setRenderer(mCurrentRenderer);
        }
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

    private void resetSurfaceView(){
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mContainer.removeAllViews();
        mContainer.addView(mSurfaceView);
    }

    @Override
    public void onClick(View view) {
        ShapeItemData itemData = (ShapeItemData) view.getTag();
        GLSurfaceView.Renderer targetRenderer;
        switch (itemData.type) {
            case SHAPE_TRIANGLE:
                targetRenderer = new TriangleRenderer(this);
            break;
            case SHAPE_SQUARE:
                targetRenderer = new SquareRenderer(this);
                break;
            case SHAPE_CIRCLE:
                targetRenderer = new CircleRenderer(this);
                break;
            default:
                targetRenderer = new TriangleRenderer(this);
        }
        if (mCurrentRenderer == targetRenderer) return;
        mCurrentRenderer = targetRenderer;
        resetSurfaceView();
        mSurfaceView.setRenderer(targetRenderer);
    }

    private List<ShapeItemData> getDefaultData() {
        List<ShapeItemData> dataList = new ArrayList<>();

        ShapeItemData first = new ShapeItemData();
        first.type = ShapeItemData.ShapeType.SHAPE_TRIANGLE;
        first.content = "三角形";

        ShapeItemData second = new ShapeItemData();
        second.type = ShapeItemData.ShapeType.SHAPE_SQUARE;
        second.content = "四方形";

        ShapeItemData third = new ShapeItemData();
        third.type = ShapeItemData.ShapeType.SHAPE_CIRCLE;
        third.content = "圆形";

        dataList.add(first);
        dataList.add(second);
        dataList.add(third);
        return dataList;
    }
}