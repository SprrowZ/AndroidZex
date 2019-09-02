package com.rye.base.widget.dialog.ctdialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.rye.base.widget.dialog.ctdialog.manager.ExDialogManager;

/**
 * Created at 2018/9/27.
 *
 * @author Zzg
 */
public abstract class ExBaseDialog extends DialogFragment {
    private View view=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      if (getLayoutRes()>0){
          //xml
          view=inflater.inflate(getLayoutRes(),null,false);
      }else{
          //view
          view=getDialogView();
      }
      return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Dialog dialog=getDialog();
        if (dialog!=null){
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //如果isCancelable()为false，则屏蔽物理返回键
            dialog.setCancelable(isCancelable());
            //如果isCancelableOutside()为false，点击屏幕外Dialog不会消失；
            dialog.setCanceledOnTouchOutside(isCancelableOutSide());
            //暂时不理解？？
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN
                            &&isCancelable();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window=getDialog().getWindow();
        if (window==null) return ;
        //s设置背景色透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog动画
        if (getAnimRes()>0){
            window.setWindowAnimations(getAnimRes());
        }
        //高宽
        WindowManager.LayoutParams params=window.getAttributes();
        if (getDialogWidth()>0){
            params.width=getDialogWidth();
        }else{
            params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        }

        if (getDialogHeight()>0){
            params.height=getDialogHeight();
        }else {
            params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        }
        //设置屏幕透明度 0.0f~1.0f之间？？？？？那上面的Background是不是可以去掉？
        params.dimAmount=getDimAmount();
        params.gravity=getGravity();
        window.setAttributes(params);
    }
    protected  View getBaseView(){
        return view;
    }
    protected  abstract  int getLayoutRes();
    protected  abstract  View getDialogView();
    protected  boolean isCancelableOutSide(){
        return true;
    }
    protected  int getDialogWidth(){
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
    protected  int getDialogHeight(){
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
    public float getDimAmount(){//这个是用来干嘛的
       return 0.2f;
    }
    protected  int getGravity(){
        return Gravity.CENTER;
    }
    protected  int getAnimRes(){
        return 0;
    }
    private static Point point=new Point();

    /**
     * 获取屏幕宽度，其实我们本地有这个方法，暂且用它的，耦合性方面好一些
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity){
        Display display=activity.getWindowManager().getDefaultDisplay();
        if (display!=null){
            display.getSize(point);
            return point.y;//高
        }
        return 0;
    }

    public static int getScreenWidth(Activity activity){
        Display display=activity.getWindowManager().getDefaultDisplay();
        if (display!=null){
            display.getSize(point);
            return point.x;//宽
        }
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();//这里千万不要忘了！
        ExDialogManager.getInstance().over();
    }


}
