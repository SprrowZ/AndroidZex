package com.rye.catcher.activity.fragment;


import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.beans.AppBean;
import com.rye.catcher.utils.DateUtils;
import com.rye.catcher.utils.PackageUtils;
import com.tencent.bugly.crashreport.common.info.AppInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class PackageManagerFragment extends BaseFragment {
    private  View view;
    private Unbinder unbinder;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.appName)
    TextView content;
    public PackageManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view=inflater.inflate(R.layout.fragment_package_manager, container, false);
        unbinder=ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick({R.id.tv1,R.id.tv2})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv1:
                setPackageInfo();
                break;
            case R.id.tv2:
                getPackageNames();
                break;
        }
    }

    /**
     * 当前包信息
     */
    private void setPackageInfo( ){

        PackageInfo info=PackageUtils.instance().getPackageInfo(getContext());
        StringBuffer stringBuffer=new StringBuffer();
        String  packageName=info.packageName;//packageName
        ActivityInfo[] activityInfos= info.activities;//activities
        ApplicationInfo applicationInfo=info.applicationInfo ;//applicationInfo
        int[] gids=info.gids;

        String firstInstallTime=DateUtils.getTime(info.firstInstallTime,DateUtils.FORMAT_DATETIME);//初次安装时间
        String lastUpdateTime=DateUtils.getTime(info.lastUpdateTime,DateUtils.FORMAT_DATETIME);//上次更新时间
        String versionName=info.versionName;
        int versionCode=info.versionCode;
        stringBuffer.append("packageName:\n"+packageName+"\n")
                    .append("activityInfos:\n"+activityInfos.toString()+"\n")
                    .append("applicationInfos:\n"+applicationInfo.toString()+"\n")
                    .append("firstInstallTime:\n"+firstInstallTime+"\n")
                    .append("lastUpdateTime:\n"+lastUpdateTime+"\n")
                    .append("versionName:\n"+versionName+"\n")
                .append("versionCode:\n"+String.valueOf(versionCode));
        content.setText(stringBuffer);
    }

    /**
     * 得到所有包名
     */
    private void getPackageNames(){
        List<AppBean> packages=PackageUtils.getPackagesInfo(getContext());
        StringBuilder builder=new StringBuilder();
        for (AppBean appBean:packages){
            builder.append("AppName:"+appBean.getAppName()+"\n")
                   .append("Package:"+appBean.getPackageName()+"\n\n");
        }
        content.setText(builder);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();//别忘了销毁
        super.onDestroyView();
    }
}
