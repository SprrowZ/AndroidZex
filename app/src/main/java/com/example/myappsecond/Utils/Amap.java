package com.example.myappsecond.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.myappsecond.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by ZZG on 2017/10/30.
 */

public class Amap extends Activity {
    public AMapLocationClient aMapLocationClient = null;
    public AMapLocationClientOption mapLocationClientOption;
    private Button fab;
    private  static   AMapLocation location;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amap);
        fab=findViewById(R.id.fab);
       // initGDLocation();
        getLocations(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(Amap.this,location.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void initGDLocation(){
//       aMapLocationClient=new AMapLocationClient(getApplicationContext());//相当于控制台
//        mapLocationClientOption=new AMapLocationClientOption();//选项
//        //设置定位模式为AMapLocation.Hight_Accuracy,高精度模式
//        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //可选，设置是否gps优先，只在高精度模式下有效，默认关闭
//        mapLocationClientOption.setGpsFirst(false);
//        //可选，设置网络请求超时时间，默认为30秒，在仅设备模式下无效
//        mapLocationClientOption.setHttpTimeOut(30000);
//        //可选，选择定位间隔，默认为两秒
//        mapLocationClientOption.setInterval(2000);
//        //可选，设置是否返回逆地理地址信息，默认是true
//        mapLocationClientOption.setNeedAddress(true);
//        //获取一次定位结果：该方法默认为false
//        mapLocationClientOption.setOnceLocation(true);
//        //可选，设置网络请求的协议，可选Http或者https，默认为http
//        //https方式请求定位对定位速度和性能有一定的损耗，定位流量会增大，但安全性更好
//        mapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
//        //给定位客户端对象设置定位参数
//        aMapLocationClient.setLocationOption(mapLocationClientOption);
//        //定位回调监听器,用于接受异步返回的定位结果，回调桉树是AMapLocation
//        aMapLocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                if (aMapLocation!=null){
//                    if (aMapLocation.getErrorCode()==0){
//                        aMapLocation.getLocationType();
//                        aMapLocation.getLatitude();
//                        aMapLocation.getLongitude();
//                        double longitude=aMapLocation.getLongitude();
//                        aMapLocation.getAccuracy();
//                        aMapLocation.getAddress();
//                        aMapLocation.getCountry();
//                        aMapLocation.getProvince();
//                        String city=aMapLocation.getCity();
//                        aMapLocation.getDistrict();
//                        aMapLocation.getStreet();
//                        aMapLocation.getStreetNum();
//                        aMapLocation.getCityCode();
//                        aMapLocation.getAdCode();
//                        aMapLocation.getAoiName();
//                        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date=new Date(aMapLocation.getTime());
//                        df.format(date);
//                        Toast.makeText(Amap.this, String.valueOf(longitude),Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(Amap.this,"定位失败",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapLocationClient.onDestroy();
    }
    public static void getLocations(Activity activity){
        AMapLocationClient aMapLocationClient=new AMapLocationClient(activity);
        AMapLocationClientOption option=new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        option.setOnceLocation(true);
        aMapLocationClient.setLocationOption(option);
        aMapLocationClient.stopLocation();
        aMapLocationClient.startLocation();
        location=  aMapLocationClient.getLastKnownLocation();

            double latitude=location.getLatitude();//获取纬度
            double longitude=location.getLongitude();//获取经度
    }
}
