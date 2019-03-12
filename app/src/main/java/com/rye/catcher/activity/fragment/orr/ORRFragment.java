package com.rye.catcher.activity.fragment.orr;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.activity.fragment.orr.interfaces.DownLoadListener;
import com.rye.catcher.project.ctmviews.HorizontalProgress;
import com.rye.catcher.project.dialog.ctdialog.ExDialog;
import com.rye.catcher.project.services.DownLoadService;
import com.rye.catcher.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
*
 */
public class ORRFragment extends BaseFragment {
    private static  final  String TAG="ORRFragment";
   private Unbinder unbinder;
   private View view;
   @BindView(R.id.test1) Button test1;
   private static final String VEDIO_URL="https://media.w3.org/2010/05/sintel/";

   private HorizontalProgress progressBar;
   private View dialogView;

   //文件是否在下载中
    private int statusCode=-1;

   private static  HashMap<String,ExDialog> hashMap=new HashMap<>();
   //会造成内存泄露
   private Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           switch (msg.what){
               case 10:
                   Log.i(TAG, "handleMessage: 文件下载完成，path："+msg.obj);
                   getActivity().runOnUiThread(()->{
                       ToastUtils.shortMsg("文件已下载至："+msg.obj);
                   });
                   break;
           }
           super.handleMessage(msg);
       }
   };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_orr,container,false);
        unbinder=ButterKnife.bind(this,view) ;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //添加监听器
        DownLoadService.addListener(new DownLoadProcess(this));
        dialogView=LayoutInflater.from(getContext()).inflate(R.layout.progress_dialog,null,false);
        progressBar=dialogView.findViewById(R.id.progress);
    }

    /**
     * 创建dialog
     */
    public void showProgessDialog(){
        Log.i(TAG, "showProgessDialog: "+dialogView.getParent()+"statusCode:"+statusCode);
         if (dialogView.getParent()==null&&statusCode==-1){
            ExDialog   exDialog=new ExDialog.Builder(getContext())
                    .setDialogView(dialogView)
                    .setWindowBackgroundP(0.5F)
                    .setGravity(Gravity.CENTER)
                    .show();
            hashMap.put(getClass().getSimpleName(),exDialog);
        }
    }

    /**
     * 关闭Dialog
     */
    public  void closeProgressDialog(){
        ExDialog exDialog=hashMap.get(getClass().getSimpleName());
        if (exDialog!=null){
            hashMap.remove(getClass().getSimpleName());
            exDialog.dismiss();
        }
    }

   @OnClick({R.id.test1})
   public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.test1:
               startDownLoad();
                break;
        }
    }

    /**
     * 通过Service下载任务
     */
    private void startDownLoad(){
        switch (statusCode){
            case -1:
                Intent intent=new Intent(getActivity(),DownLoadService.class);
                intent.putExtra(DownLoadService.BASE_URL,VEDIO_URL);
                getActivity().startService(intent);
                break;
            case 1:
                ToastUtils.shortMsg("文件在下载中...");
                break;
            case 2:
                ToastUtils.shortMsg("文件已经下载!");
                break;
            case 3:
                ToastUtils.shortMsg("文件下载失败！");
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private static class DownLoadProcess implements DownLoadListener{
        private WeakReference<ORRFragment> weakReference;
        public DownLoadProcess(ORRFragment fragment){
            weakReference=new WeakReference<>(fragment);
        }
        private ORRFragment zFragment=weakReference.get();
        @Override
        public void onStart() {
            Log.i(TAG, "onStart: ...");
            zFragment.showProgessDialog();
        }

        @Override
        public void onProgress(int progress) {
            Log.i(TAG, "onProgress: "+progress);
            zFragment.statusCode=1;
            zFragment.progressBar.setProgress(progress);
        }

        @Override
        public void onFinish(String path) {
            Log.i(TAG, "onFinish: ...");
            zFragment.statusCode=2;
            zFragment.closeProgressDialog();
            //发送消息
            Message message=new Message();
            message.what=10;
            message.obj=path;
            zFragment.handler.sendMessage(message);
        }

        @Override
        public void onFail(String errorInfo) {
            Log.i(TAG, "onFail: "+errorInfo);
            zFragment.statusCode=3;
        }
    }


}
