package com.rye.catcher.project.netdiagnosis;

import android.content.Context;

import com.rye.base.mvp.BaseContract;
import com.rye.base.mvp.BaseView;

import java.lang.ref.WeakReference;

/**
 * Create by rye
 * at 2020-08-24
 *
 * @description:
 */
public interface INetWorkContract {
    interface INetView extends BaseView {
        void printRouteIp(String routeIp);

        void printWifiState(String wifiState);

        void pingRoute(String result);

        void isWifiConnected(boolean isConnected);

        void pingServer(boolean isSuccess);
    }

    interface INetPresenter extends BaseContract.IPresenter<INetView> {
        void getRouteIp(Context context);

        void judgeWifiState(WeakReference<Context> wrf);

        boolean pingRoute(WeakReference<Context> wrf);

        boolean pingServer(WeakReference<Context> wrf);
    }
}
