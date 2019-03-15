package com.rye.catcher.items;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created at 2019/3/15.
 *
 * @author Zzg
 * @function:
 */
public class SimpleItemDecoration extends RecyclerView.ItemDecoration {

    //color的传入方式是resouce.getcolor
    protected Drawable mDivider;
    private int leftRight;
    private int topBottom;

    public SimpleItemDecoration(int leftRight,int topBottom,int mColor){
        this.leftRight=leftRight;
        this.topBottom=topBottom;
        if (mColor!=0){
            mDivider=new ColorDrawable(mColor);
        }
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final GridLayoutManager.SpanSizeLookup lookup = layoutManager.getSpanSizeLookup();

        if (mDivider == null || layoutManager.getChildCount() == 0) {
            return;
        }
        //判断总的数量是否可以整除
        int spanCount = layoutManager.getSpanCount();
        int left, right, top, bottom;
        final int childCount = parent.getChildCount();
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                //将带有颜色的分割线处于中间位置
                final float centerLeft = ((float) (layoutManager.getLeftDecorationWidth(child) + layoutManager.getRightDecorationWidth(child))
                        * spanCount / (spanCount + 1) + 1 - leftRight) / 2;
                final float centerTop = (layoutManager.getBottomDecorationHeight(child) + 1 - topBottom) / 2;
                //得到它在总数里面的位置
                final int position = parent.getChildAdapterPosition(child);
                //获取它所占有的比重
                final int spanSize = lookup.getSpanSize(position);
                //获取每排的位置
                final int spanIndex = lookup.getSpanIndex(position, layoutManager.getSpanCount());
                //判断是否为第一排
                boolean isFirst = layoutManager.getSpanSizeLookup().getSpanGroupIndex(position, spanCount) == 0;
                //画上边的，第一排不需要上边的,只需要在最左边的那项的时候画一次就好
                if (!isFirst && spanIndex == 0) {
                    left = layoutManager.getLeftDecorationWidth(child);
                    right = parent.getWidth() - layoutManager.getLeftDecorationWidth(child);
                    top = (int) (child.getTop() - centerTop) - topBottom;
                    bottom = top + topBottom;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
                //最右边的一排不需要右边的
                boolean isRight = spanIndex + spanSize == spanCount;
                if (!isRight) {
                    //计算右边的
                    left = (int) (child.getRight() + centerLeft);
                    right = left + leftRight;
                    top = child.getTop();
                    if (!isFirst) {
                        top -= centerTop;
                    }
                    bottom = (int) (child.getBottom() + centerTop);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                //将带有颜色的分割线处于中间位置
                final float centerLeft = (layoutManager.getRightDecorationWidth(child) + 1 - leftRight) / 2;
                final float centerTop = ((float) (layoutManager.getTopDecorationHeight(child) + layoutManager.getBottomDecorationHeight(child))
                        * spanCount / (spanCount + 1) - topBottom) / 2;
                //得到它在总数里面的位置
                final int position = parent.getChildAdapterPosition(child);
                //获取它所占有的比重
                final int spanSize = lookup.getSpanSize(position);
                //获取每排的位置
                final int spanIndex = lookup.getSpanIndex(position, layoutManager.getSpanCount());
                //判断是否为第一列
                boolean isFirst = layoutManager.getSpanSizeLookup().getSpanGroupIndex(position, spanCount) == 0;
                //画左边的，第一排不需要左边的,只需要在最上边的那项的时候画一次就好
                if (!isFirst && spanIndex == 0) {
                    left = (int) (child.getLeft() - centerLeft) - leftRight;
                    right = left + leftRight;
                    top = layoutManager.getRightDecorationWidth(child);
                    bottom = parent.getHeight() - layoutManager.getTopDecorationHeight(child);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
                //最下的一排不需要下边的
                boolean isRight = spanIndex + spanSize == spanCount;
                if (!isRight) {
                    //计算右边的
                    left = child.getLeft();
                    if (!isFirst) {
                        left -= centerLeft;
                    }
                    right = (int) (child.getRight() + centerTop);
                    top = (int) (child.getBottom() + centerLeft);
                    bottom = top + leftRight;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
    }
}
