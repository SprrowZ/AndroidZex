package com.rye.appupdater.updater.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.rye.appupdater.UpdateActivity;
import com.rye.appupdater.updater.AppUpdater;
import com.rye.appupdater.updater.beans.DownloadBean;
import com.rye.appupdater.updater.net.INetDownloadCallBack;
import com.rye.base.R;
import com.rye.base.utils.AppUtils;

import java.io.File;

/**
 * Created By RyeCatcher
 * at 2019/9/19
 */
public class UpdateVersionDialog extends DialogFragment {
    private  final String TAG= this.getClass().getSimpleName();
    private static final String KEY_DOWNLOAD_BEAN="download_bean";
    private  DownloadBean mDownloadBean;

    public  static final int  APK_REQUEST = 1000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments=getArguments();
        if (arguments!=null){
            mDownloadBean= (DownloadBean) arguments.getSerializable(KEY_DOWNLOAD_BEAN);
        }
    }
     //绑定ui两种方法：oncreateView/onCreateDialog


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dialog_update_version,container,false);
         bindEvent(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void bindEvent(View view){
        TextView title=view.findViewById(R.id.title);
        TextView content=view.findViewById(R.id.content);
        TextView update=view.findViewById(R.id.update);

        title.setText(mDownloadBean.title);
        content.setText(mDownloadBean.content);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下载apk包
                v.setEnabled(false);//下载的时候不要重复点击
                downloadApk(v);
            }
        });
    }

    private void downloadApk(View updater){
        File targetFile=new File(getActivity().getCacheDir(),"patch.apk");
        AppUpdater.getInstance().getNetManager().download(mDownloadBean.url, targetFile, new INetDownloadCallBack() {
            @Override
            public void success(File apkFile) {
                Log.i(TAG,"apk文件路径："+apkFile.getAbsolutePath());
                updater.setEnabled(true);
                dismiss();
                // TODO: 2019/9/20  ---md5包完整性检验
                String fileMD5=AppUtils.getFileMD5(targetFile);

                if (fileMD5!=null && fileMD5.equals(mDownloadBean.md5)){
                    //开始安装apk文件
                    AppUtils.installApk(getActivity(),apkFile);
                }else{
                    Log.e(TAG,"文件md5 ---匹配失败");
                }

            }

            @Override
            public void progress(int progress) {
                Log.i(TAG,"下载进度："+progress);
                ((TextView)updater).setText(progress+"%");
            }

            @Override
            public void failed(Throwable throwable) {
                updater.setEnabled(true);
                Toast.makeText(getContext(),"文件下载失败！",Toast.LENGTH_SHORT).show();
            }
        },UpdateVersionDialog.this);
    }
    public static void show(FragmentActivity activity, DownloadBean bean){
        Bundle bundle =new Bundle();
        bundle.putSerializable(KEY_DOWNLOAD_BEAN,bean);
        UpdateVersionDialog dialog=new UpdateVersionDialog();
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(),"update_dialog");
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(TAG,"dialog dismiss");
        AppUpdater.getInstance().getNetManager().cancel(this);
    }
}
