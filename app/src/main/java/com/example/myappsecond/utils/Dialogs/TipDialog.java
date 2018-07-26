package com.example.myappsecond.utils.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myappsecond.R;

/**
 * Created by Zzg on 2018/7/4.
 */
public class TipDialog extends Dialog{
    Context mContext;
    LayoutInflater mLayoutInflater;
    public interface   OnCancelListener extends DialogInterface.OnCancelListener {
        void onCancel();

        @Override
        void onCancel(DialogInterface dialog);
    }
    public void  setOnCancelListener(OnCancelListener onCancelListener){
        onCancelListener.onCancel();
    }
    public TipDialog(@NonNull Context context) {
        super(context);
        mContext=context;
        init();
    }

    public TipDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
        init();
    }

    protected TipDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
        init();
    }

    private void init() {
        mLayoutInflater=LayoutInflater.from(mContext);
        View view=mLayoutInflater.inflate(R.layout.dialog_tipdialog,null);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    @Override
    public void show() {
        super.show();
    }

}
