package com.example.myappsecond.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/10/15 0015.
 */
public class InputTools {

    //强制显示或者关闭系统键盘
    public static void KeyBoard(final EditText txtSearchKey, final boolean openFlag) {
        InputMethodManager m = (InputMethodManager)
                txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (openFlag) {
            m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
        } else {
            m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
        }
    }

    public static void showInput(Activity context) {
        View v = context.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void hideInput(Activity context) {
        View v = context.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
        }
    }
    /**
     * 隐藏键盘
     */
//    private void hideKeyboard() {//要继承View，才可以用getApplicationWindowToken(),
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {
//            imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
//        }
//    }

}
