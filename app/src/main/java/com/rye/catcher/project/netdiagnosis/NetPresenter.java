package com.rye.catcher.project.netdiagnosis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.rye.base.mvp.BasePresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;


/**
 * Create by rye
 * at 2020-08-24
 *
 * @description:
 */
public class NetPresenter extends BasePresenter<INetWorkContract.INetView>
        implements INetWorkContract.INetPresenter {

    @Override
    public void getRouteIp(Context context) { //WeakReference
        String routeIp = getDNSIp2();
        Log.i("ZZG","routeIp:"+routeIp);
        getView().printRouteIp(routeIp);
    }

    @Override
    public void judgeWifiState(WeakReference<Context> wrf) {
        Context context = wrf.get();
        boolean isWifiConnected = isWifiConnected(context);
        getView().isWifiConnected(isWifiConnected);
    }


    @Override
    public boolean pingRoute(WeakReference<Context> wrf) {
        Context context = wrf.get();
        String ip = getDNSIp2();
        if (!isIpAddress(ip)) {
            return false;
        }
        Log.e("diagnosis","ping ip:"+ip);
        boolean result = ping(ip, 5, 5000);
        Log.e("diagnosis", "ping结果：" + result);
        return false;
    }

    @Override
    public boolean pingServer(WeakReference<Context> wrf) {
        return false;
    }


    private boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader reader = null;
        Runtime runtime = Runtime.getRuntime();
        String pingCommand = "ping " + " -c " + pingTimes + " " + ipAddress;
        try {
            Process process = runtime.exec(pingCommand);
            if (process == null) {
                return false;
            }
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("diagnosis", "line: " + line + "\n");
                if (line.contains("packets transmitted")) { //说明是结果行
                    return isConnectRoute(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private String long2ip(long ip) {
        StringBuffer sb = new StringBuffer();
        sb.append((int) (ip & 0xff));
        sb.append('.');
        sb.append((int) ((ip >> 8) & 0xff));
        sb.append('.');
        sb.append((int) ((ip >> 16) & 0xff));
        sb.append('.');
        sb.append((int) ((ip >> 24) & 0xff));
        return sb.toString();
    }

    private String getDNSIp(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        DhcpInfo di = wm.getDhcpInfo();
        String gateWayIp = long2ip(di.gateway);//网关地址
        return gateWayIp;
    }

    private String getDNSIp2() {
        String routeIp = "";
        String[] splitList;
        try {
            Process process = Runtime.getRuntime().exec("ip route list table 0");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String ipRoute = in.readLine();
            splitList = ipRoute.split("\\s+");
            Log.i("diagnosis", "ipRoute: " + ipRoute);
            if (ipRoute.length() > 2) {//两种获取网关地址的方法
                routeIp = splitList[2];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return routeIp;
    }


    private boolean isIpAddress(String ipAddress) {
        return ipAddress.matches("(\\d+\\.){3}\\d+") &&
                !TextUtils.equals(ipAddress, "0.0.0.0");
    }

    private boolean isConnectRoute(String line) {

        String[] chip = line.split(",");
        try {
            int send = Integer.valueOf(chip[0].substring(0, 1));
            int received = Integer.valueOf(chip[1].trim().substring(0, 1));
            if (send - received < 3) {//三次之内网关正常？或者百分之百？
                return true;
            }
        } catch (Exception e) { //index/format
            e.printStackTrace();
        }

        return false;
    }


    private boolean isWifiConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }
}
