package com.rye.catcher.activity.fragment;


import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.rye.catcher.BaseFragment;
import com.rye.catcher.R;
import com.rye.catcher.beans.AppBean;
import com.rye.catcher.utils.DateUtils;
import com.rye.base.utils.DialogUtil;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.PackageUtils;
import com.rye.catcher.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class PackageManagerFragment extends BaseFragment {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.appName)
    TextView content;

    public PackageManagerFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_package_manager;
    }

    @Override
    protected void initData() {

    }





    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                setPackageInfo();
                break;
            case R.id.tv2:
                getThirdApplications();
                break;
            case R.id.tv3:
                startWeChat();
                break;
        }
    }

    private void startWeChat() {
        if (PackageUtils.isAppInstalled(Constant.APP_WECHAT_PACKAGENAME,getContext())){
           PackageUtils.openOtherAppByMain(Constant.APP_WECHAT_PACKAGENAME,Constant.APP_WECHAT_MAINACTIVITY,
                   getContext());
        }else {
            ToastUtils.shortMsg("请先安装微信！");
        }
    }

    /**
     * 当前包信息
     */
    private void setPackageInfo() {

        PackageInfo info = PackageUtils.instance().getPackageInfo(getContext());
        StringBuffer stringBuffer = new StringBuffer();
        String packageName = info.packageName;//packageName
        ActivityInfo[] activityInfos = info.activities;//activities
        ApplicationInfo applicationInfo = info.applicationInfo;//applicationInfo
        int[] gids = info.gids;

        String firstInstallTime = DateUtils.getTime(info.firstInstallTime, DateUtils.FORMAT_DATETIME);//初次安装时间
        String lastUpdateTime = DateUtils.getTime(info.lastUpdateTime, DateUtils.FORMAT_DATETIME);//上次更新时间
        String versionName = info.versionName;
        int versionCode = info.versionCode;
        stringBuffer.append("packageName:\n" + packageName + "\n")
                .append("activityInfos:\n" + activityInfos.toString() + "\n")
                .append("applicationInfos:\n" + applicationInfo.toString() + "\n")
                .append("firstInstallTime:\n" + firstInstallTime + "\n")
                .append("lastUpdateTime:\n" + lastUpdateTime + "\n")
                .append("versionName:\n" + versionName + "\n")
                .append("versionCode:\n" + String.valueOf(versionCode));
        content.setText(stringBuffer);
    }

    /**
     * 得到所有包名
     */
    private void getThirdApplications() {
        DialogUtil.createLoadingDialog(getContext());

        new Thread(() -> {
            List<AppBean> packages = PackageUtils.getThirdApplications(getContext());
            StringBuilder builder = new StringBuilder();
            for (AppBean appBean : packages) {
                builder.append("AppName:" + appBean.getAppName() + "\n")
                        .append("Package:" + appBean.getPackageName() + "\n\n");
            }
            getActivity().runOnUiThread(() -> {
                content.setText(builder);
                DialogUtil.closeDialog(getContext());
            });
        }).start();
    }


}
