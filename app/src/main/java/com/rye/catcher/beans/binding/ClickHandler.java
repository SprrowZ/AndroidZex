package com.rye.catcher.beans.binding;

import android.content.Context;
import android.view.View;

/**
 * Created by 18041at 2019/4/14
 * Function: 第三屏点击事件
 */
public class ClickHandler {
    private Context mContext;
    public void clickEvent(View view){
        mContext=view.getContext();

    }


}
