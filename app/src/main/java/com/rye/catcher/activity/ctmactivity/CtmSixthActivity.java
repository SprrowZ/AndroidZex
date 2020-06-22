package com.rye.catcher.activity.ctmactivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

/**
 * Created by Zzg on 2017/11/27.
 */

public class CtmSixthActivity extends BaseActivity {
    private LinearLayout itemRoot;//父布局
    private int mlastX = 0;//上次位置
    private final int MAX_WIDTH = 100;//100是删除按钮的宽度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_delete);
        itemRoot = findViewById(R.id.lin_root);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int scrollX = itemRoot.getScrollX();
        int x = (int) event.getX();
        int maxLength = dipToPx(this, MAX_WIDTH);//dp转化为px，用来限制右滑长度
        int newScrollX = scrollX + mlastX - x;//移动后父布局位置
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //这个判断就是优化，不能左滑，右滑动有距离限制
            if (newScrollX < 0) {
                newScrollX = 0;
            } else if (newScrollX > maxLength) {
                newScrollX = maxLength;
            }
            itemRoot.scrollTo(newScrollX, 0);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {//下面开始优化滑动，超过二分之一就全部显示，否则，直接弹回
            if (scrollX > maxLength / 2) {
                newScrollX = maxLength;
            } else {
                newScrollX = 0;
            }
            itemRoot.scrollTo(newScrollX, 0);
        }
        mlastX = x;//重写到这功能就已经完成了，现在进行优化


        return super.onTouchEvent(event);
    }

    private int dipToPx(Context context, int dip) {//dp转化为px
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }


}
