package com.rye.catcher.project.netdiagnosis;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.rye.base.mvp.BaseMvpActivity;
import com.rye.catcher.R;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by rye
 * at 2020-08-24
 *
 * @description:
 */
public class NetDiagnosisActivity extends BaseMvpActivity<INetWorkContract.INetView, NetPresenter>
        implements INetWorkContract.INetView {
    @BindView(R.id.ellipsis_view)
    NetEllipsisView mNetEllipsisView;


    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NetDiagnosisActivity.class);
        context.startActivity(intent);
    }


    @NotNull
    @Override
    public NetPresenter createPresenter() {
        return new NetPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diagnosis;
    }

    @Override
    public void initEvent() {
      mNetEllipsisView.startLoop();
    }

    @OnClick({R.id.get_route_ip, R.id.judge_wifi_state, R.id.ping_server})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_route_ip:
                getPresenter().getRouteIp(this);
                break;
            case R.id.judge_wifi_state:
                getPresenter().judgeWifiState(new WeakReference<>(this));
                break;
            case R.id.ping_server:
                getPresenter().pingRoute(new WeakReference<>(this));
                break;
        }
    }


    @Override
    public void printRouteIp(String routeIp) {
        Log.i("diagnosis", "ifconfig:" + routeIp);
    }

    @Override
    public void printWifiState(String wifiState) {

    }

    @Override
    public void pingRoute(String result) {

    }

    @Override
    public void isWifiConnected(boolean isConnected) {

    }

    @Override
    public void pingServer(boolean isSuccess) {

    }
}
