package com.rye.catcher.project.dialog.ctdialog;

import android.view.View;

/**
 * Created at 2018/9/28.
 * dialog必须实现的一些方法
 * @author Zzg
 */
public interface IDialog {
    void dismiss();
    interface  OnBuilderListener{
        void onBuilderChildView(IDialog dialog, View view , int layoutRes);
    }
    interface  OnClickListener{
        void onClick(IDialog dialog);
    }
}
