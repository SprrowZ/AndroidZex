package com.example.myappsecond.project.dialog.ctdialog.manager;

import com.example.myappsecond.project.dialog.ctdialog.ExDialog;

/**
 * Created at 2018/9/27.
 *管理多个dialog，按照dialog的优先级依次弹出
 * @author Zzg
 */
public class DialogWrapper {
    private ExDialog.Builder dialog;//统一管理dialog的弹出顺序
    public DialogWrapper(ExDialog.Builder dialog){
        this.dialog=dialog;
    }
    public ExDialog.Builder getDialog(){
        return dialog;
    }

    public void setDialog(ExDialog.Builder dialog){
        this.dialog=dialog;
    }
}
