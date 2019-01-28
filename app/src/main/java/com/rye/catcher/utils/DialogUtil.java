package com.rye.catcher.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.rye.catcher.R;
import com.rye.catcher.project.dialog.ctdialog.ExDialog;
import com.rye.catcher.project.dialog.ctdialog.IDialog;

import java.util.HashMap;

/**
 * Created at 2018/9/30.
 *
 * @author Zzg
 */
public class DialogUtil {
    private static final  String TAG="DialogUtil";
    /**
     * 创建默认dialog
     * @param context
     * @param content
     * @param btn1Str 确认按钮的name，取消是默认的
     * @param positiveClickListener
     */
    public static void createDefaultDialog(Context context,String title, String content, String btn1Str,
                                           IDialog.OnClickListener positiveClickListener){
     createDefaultDialog(context,title,content,btn1Str,positiveClickListener,"",null);
    }

    /**
     * 创建默认dialog
     * @param context
     * @param title
     * @param content
     * @param btn1Str
     * @param positiveClickListener
     * @param btn2Str
     * @param negativeClickListener
     */
    public static void createDefaultDialog(Context context,String title,String content,String btn1Str,
                                           IDialog.OnClickListener positiveClickListener,String btn2Str,
                                           IDialog.OnClickListener negativeClickListener){
        ExDialog.Builder builder=new ExDialog.Builder(context);
        if (!TextUtils.isEmpty(title)){
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)){
            builder.setContent(content);
        }
        if (positiveClickListener!=null){
            if (TextUtils.isEmpty(btn1Str)) {
                builder.setPositiveButton(positiveClickListener);
            }else{
                builder.setPositiveButton(btn1Str,positiveClickListener);
            }
        }
        if (negativeClickListener!=null){
            if(TextUtils.isEmpty(btn2Str)){
                builder.setNegativeButton(negativeClickListener);
            }else{
                builder.setNegativeButton(btn2Str,negativeClickListener);
            }
        }
        builder.show();
    }

    private static HashMap<String,ExDialog> hashMap=new HashMap<>();

    /**
     * 创建loading布局，两个属性值是为了让loading，youknow
     * @param context
     */
    public static void createLoadingDialog(Context context){
        closeLoadingDialog(context);
        ExDialog dialog= new ExDialog.Builder(context)
                .setDialogView(R.layout.loading_dialog_second)
                .setWindowBackgroundP(0.5f)
                .setCancelable(false)
                .setCancelableOutSide(false)
                .show();
        hashMap.put(context.getClass().getSimpleName(),dialog);
    }

    /**
     * 关闭loadingDialog
     * @param context
     */
    public  static void closeLoadingDialog(Context context){
        String dialogKey=context.getClass().getSimpleName();
        Log.i(TAG, "closeLoadingDialog: "+dialogKey);
        ExDialog dialog=hashMap.get(dialogKey);
       if (dialog!=null){
           hashMap.remove(dialogKey);
           dialog.dismiss();
       }
    }

}















