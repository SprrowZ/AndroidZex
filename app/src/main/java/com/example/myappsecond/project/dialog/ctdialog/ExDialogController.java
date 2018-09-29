package com.example.myappsecond.project.dialog.ctdialog;

import android.app.FragmentManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.myappsecond.R;

import java.lang.ref.WeakReference;

/**
 * Created at 2018/9/28.
 * ExDialog核心业务类
 * @author Zzg
 */
public class ExDialogController {
    /**
     * 很奇怪，下面的几个方法，我每敲一个都有提示，这个提示是从何而来的？？？
     */
    private int layoutRes;
    private int dialogWidth;
    private int dialogHeight;
    private float dimAmount=0.2f;
    private int gravity= Gravity.CENTER;
    private boolean isCancelableOutside=true;
    private boolean cancelable;
    private int animRes;
    private View dialogView;
    private IDialog.OnClickListener mPositiveButtonListener;
    private IDialog.OnClickListener mNegativeButtonListener;
    /**
     * 弱引用？？？
     */
    private WeakReference<IDialog>  mDialog;
    private String titleStr;//默认标题
    private String contentStr;//默认内容
    private String positiveStr;
    private String negativeStr;
    private boolean showBtnLeft,showBtnRight;


    private Button btn_ok,btn_cancal;
    ExDialogController(IDialog dialog){
        mDialog=new WeakReference<>(dialog);
    }
    int getAnimRes(){
        return animRes;
    }
    int getLayoutRes(){
        return layoutRes;
    }
    void setLayoutRes(int layoutRes){
        this.layoutRes=layoutRes;
    }
    int getDialogWidth(){
        return dialogWidth;
    }
    int getDialogHeight(){
        return dialogHeight;
    }
    float getDimAmount(){
        return dimAmount;
    }
    public int getGravity(){
        return gravity;
    }
    boolean isCancelableOutside(){
        return isCancelableOutside;
    }
    boolean isCancelable(){
       return cancelable;
    }
    public void setDialogView(View dialogView){
        this.dialogView=dialogView;
    }
    View getDialogView(){
        return dialogView;
    }
    public void setChildView(View view){
       setDialogView(view);

    }
    void dealDefaultDialog(IDialog.OnClickListener positiveBtnListener,IDialog.OnClickListener
                           negativeBtnListener,String titleStr,String contentStr,
                           boolean showBtnLeft,String negativeStr,boolean showBtnRight,
                           String positiveStr){
       if (dialogView==null) return;
       this.mNegativeButtonListener=negativeBtnListener;
       this.mPositiveButtonListener=positiveBtnListener;
       btn_ok=(Button)dialogView.findViewById(R.id.btn_ok);//管他有没有布局文件，先find再说
       btn_cancal=(Button)dialogView.findViewById(R.id.btn_cancel);
       if (btn_ok!=null&&!TextUtils.isEmpty(positiveStr)){
           btn_ok.setVisibility(showBtnRight? View.VISIBLE:View.GONE);
           btn_ok.setText(positiveStr);
           btn_ok.setOnClickListener(mButtonHandler);
       }
    }

    /**
     * 处理Dialog按钮的点击事件
     */
    private final View.OnClickListener mButtonHandler=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (v==btn_cancal){
                if (mDialog.get()==null) return;//mDialog.get()是什么意思？-->大概是获取Dialog
                if (mNegativeButtonListener!=null){
                    mNegativeButtonListener.onClick(mDialog.get());
                }
            }else if (v==btn_ok){
                if (mDialog.get()==null) return;;
                if (mPositiveButtonListener!=null){
                    mPositiveButtonListener.onClick(mDialog.get());
                }
            }
        }
    };

   public static  class SYParams{
       FragmentManager fragmentManager;
       int layoutRes;
       int dialogWidth;
       int dialogHeight;
       float dimAmount=0.2f;
       public int gravity=Gravity.CENTER;
       boolean isCancelableOutside=true;
       boolean cancelable=false;
       View dialogView;
       Context context;
       IDialog.OnClickListener positiveBtnListener;
       IDialog.OnClickListener negativeBtnListener;
       String titleStr;//默认标题
       String contentStr;//默认内容
       String positiveStr;//右边按钮文字
       String negativeStr;//左边按钮文字
       boolean showBtnLeft,showBtnRight;
       int animRes;//Dialog动画style
       void apply(ExDialogController controller){
           controller.dimAmount=dimAmount;
           controller.gravity=gravity;
           controller.isCancelableOutside=isCancelableOutside;
           controller.cancelable=cancelable;
           controller.animRes=animRes;
           controller.titleStr=titleStr;
           controller.contentStr=contentStr;
           controller.positiveStr=positiveStr;
           controller.negativeStr=negativeStr;
           controller.showBtnLeft=showBtnLeft;
           controller.showBtnRight=showBtnRight;
           controller.mPositiveButtonListener=positiveBtnListener;
           controller.mNegativeButtonListener=negativeBtnListener;
           if (layoutRes>0){
               controller.setLayoutRes(layoutRes);
           }else if (dialogView!=null){
               controller.dialogView=dialogView;
           }else{
               throw new IllegalArgumentException("Dialog View can't be null,brother");
           }
           if (dialogWidth>0){
               controller.dialogWidth=dialogWidth;
           }
           if (dialogHeight>0){
               controller.dialogHeight=dialogHeight;
           }
       }
   }






















































}
