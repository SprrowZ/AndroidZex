package com.rye.catcher.sdks.gmap;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;

import com.rye.catcher.utils.ToastUtils;

import java.util.Date;


/**
 * Created by ZZG on 2018/9/7.
 */
public class AmapManager {
    public static final String TAG="AmapManager";
    public static AmapManager instance;
    private static  AMapLocationClient locationClient = null;
    private static  AMapLocationClientOption locationOption = null;
    private static AmapResult amapResult;
    //private static Handler mapHandler;
    //构造器私有化
    private AmapManager(){}
    //静态内部类单例模式
    public static AmapManager getInstance(){
        return  Singleton.INSTANCE;
    }
    private static class Singleton{
        private static final AmapManager INSTANCE=new AmapManager();
    }


    /**
     * 默认连续定位
     * @param context
     */
    public  AmapResult initLocation(Context context) {
//        mapHandler=handler;
        amapResult=new AmapResult();
        locationClient = new AMapLocationClient(context);
        locationOption=getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        // TODO: 2018/9/15 onLocationChanged不回调，只能取last，但是每次都调用，需要优化 
        //onLocationChanged方法可能不回调，这时候就用LastKnowLocation方法获取位置,目前是每次都调用
        //暂时没有解决方案
        AMapLocation  location=locationClient.getLastKnownLocation();
//        if (location!=null){
//            Log.i(TAG, "getLastKnownLocation: "+location.getCity());
//            setLastKnowLocation(location);
//            Message message=new Message();
//            message.what=11;
//            message.obj=amapResult;
//            mapHandler.sendMessage(message);
//        }else{
//            locationClient.startLocation();//开启定位
//        }
        if (location!=null){
           setLastKnowLocation(location);
           return amapResult;
        }else{
            locationClient.startLocation();
        }
        // TODO: 2019/3/25 暂时处理，如果没有上次位置信息就返回null
        return null;
    }
    private  void setLastKnowLocation(AMapLocation location){
        amapResult.setErrorCode(String.valueOf(location.getErrorCode()));
        amapResult.setLocationType(String.valueOf(location.getLocationType()));
        amapResult.setLongitude(String.valueOf(location.getLongitude()));
        amapResult.setLatitude(String.valueOf(location.getLatitude()));
        amapResult.setAccuracy(String.valueOf(location.getAccuracy()));
        amapResult.setProvider(location.getProvider());
        amapResult.setSpeed(String.valueOf(location.getSpeed()));
        amapResult.setBearing(String.valueOf(location.getBearing()));
        amapResult.setSatellites(String.valueOf(location.getSatellites()));
        amapResult.setCountry(location.getCountry());
        amapResult.setProvider(location.getProvider());
        amapResult.setCity(location.getCity());
        amapResult.setCityCode(location.getCityCode());
        amapResult.setDistrict(location.getDistrict());
        amapResult.setADCODE(location.getAdCode());
        amapResult.setAddress(location.getAddress());
        amapResult.setPoiname(location.getPoiName());
        amapResult.setTime(new Date(location.getTime()));
    }

    public   void  stopLocation(){
        // 停止定位
        if (locationClient!=null){
            locationClient.stopLocation();
        }else {
            Log.i(TAG, "stopLocation: is error"+"--->"+locationClient.toString());
        }

    }
    /**
     * 定位参数
     * @return defaultOption
     */
    private static  AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //有三种模式
        mOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        /**
         * 获取最近3s内精度最高的一次定位结果,默认false,如果设置为true,会自动变为单次定位
         */
       //  mOption.setOnceLocationLatest(true);
        /**
         * 获取一次定位结果：
         */
        mOption.setOnceLocation(true);
        return mOption;
    }

    /**
     * 定位监听
     */
    private  AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            Log.i(TAG, "onLocationChanged: ......");
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                 amapResult.setErrorCode(String.valueOf(location.getErrorCode()));
                 amapResult.setLocationType(String.valueOf(location.getLocationType()));
                 amapResult.setLongitude(String.valueOf(location.getLongitude()));
                 amapResult.setLatitude(String.valueOf(location.getLatitude()));
                 amapResult.setAccuracy(String.valueOf(location.getAccuracy()));
                 amapResult.setProvider(location.getProvider());
                 amapResult.setSpeed(String.valueOf(location.getSpeed()));
                 amapResult.setBearing(String.valueOf(location.getBearing()));
                 amapResult.setSatellites(String.valueOf(location.getSatellites()));
                 amapResult.setCountry(location.getCountry());
                 amapResult.setProvider(location.getProvider());
                 amapResult.setCity(location.getCity());
                 amapResult.setCityCode(location.getCityCode());
                 amapResult.setDistrict(location.getDistrict());
                 amapResult.setADCODE(location.getAdCode());
                 amapResult.setAddress(location.getAddress());
                 amapResult.setPoiname(location.getPoiName());
                 amapResult.setTime(new Date(location.getTime()));
                    Log.i(TAG, "定位成功: "+amapResult.getAddress());
                } else {
                    //定位失败
                    amapResult.setErrorCode(String.valueOf(location.getErrorCode()));
                    amapResult.setErrorInfo(location.getErrorInfo());
                    amapResult.setLocationDetail(location.getLocationDetail());
                    Log.i(TAG, "定位失败: "+amapResult.getAddress());
                }
                  amapResult.setWifiAble(String.valueOf(location.getLocationQualityReport().isWifiAble()));
                  amapResult.setGPSStatus(getGPSStatusString(location.getLocationQualityReport().getGPSStatus()));
                  amapResult.setGPSSatellites(String.valueOf(location.getLocationQualityReport().getGPSSatellites()));
                  amapResult.setNetWorkType(String.valueOf(location.getLocationQualityReport().getNetworkType()));
                  amapResult.setNetUseTime(String.valueOf(new Date(location.getLocationQualityReport().getNetUseTime())));
                 //数据回调
//                Message message=new Message();
//                message.what=1;
//                message.obj=amapResult;
//                mapHandler.sendMessage(message);
            } else {
                ToastUtils.shortMsg("定位失败，loc is null");
            }
            //
         //   stopLocation();
        }

    };
    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private static String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public static void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
