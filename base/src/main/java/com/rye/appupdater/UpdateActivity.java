package com.rye.appupdater;

import android.content.Intent;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.rye.appupdater.updater.AppUpdater;
import com.rye.appupdater.updater.beans.DownloadBean;
import com.rye.appupdater.updater.net.INetCallback;
import com.rye.appupdater.updater.ui.UpdateVersionDialog;
import com.rye.base.BaseActivity;
import com.rye.base.R;
import com.rye.base.utils.AppUtils;

// TODO: 2019/9/2  将其改造为MVP风格实现 
public class UpdateActivity extends BaseActivity {
    private Button  btn;
    @Override
    public int bindLayout() {
        return R.layout.activity_update;
    }

    @Override
    public void initEvent() {
      btn=findViewById(R.id.get);
      btn.setOnClickListener(v -> {
          /**
           * 1.检测
           * 2.做版本匹配
           * 如果需要更新
           * 3.弹框
           * 4.点击下载
           */
          AppUpdater.getInstance().getNetManager().get(AppUpdater.CHECK_URL, new INetCallback() {
              @Override
              public void success(String response) {
                  Log.i(TAG,response);
                  DownloadBean bean=DownloadBean.parse(response);
                  if (bean==null){
                      Toast.makeText(UpdateActivity.this,"文件检测失败！",Toast.LENGTH_SHORT).show();
                      return;
                  }
                  //检测版本号
                  try{
                      long versionCode=Long.parseLong(bean.versionCode);
                      if (versionCode<=AppUtils.getAppVersionCode()){
                        Log.i(TAG,"已经是最新版本了");
                        return;
                      }
                  }catch (NumberFormatException exception){
                       Log.e(TAG,exception.toString());
                  }
                //弹窗
                  UpdateVersionDialog.show(UpdateActivity.this,bean);



              }

              @Override
              public void failed(Throwable throwable) {
                  Log.e(TAG,"版本更新接口请求失败！");
                  throwable.printStackTrace();
              }
          },UpdateActivity.this);
      });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==UpdateVersionDialog.APK_REQUEST){//更新

        }
    }
}
