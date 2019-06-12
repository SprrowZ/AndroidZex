package com.rye.catcher.base.sdks.gmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZZG on 2018/9/7.
 */
public class AmapResult implements Serializable {
    private  String LocationType;
    private  String longitude;
    private String latitude;
    private String accuracy;
    private String provider;
    private String speed;
    private String bearing;
    private String satellites;
    private String country;
    private String province;
    private String city;
    private String cityCode;
    private String district;
    private String adcode;
    private String address;
    private String poiname;
    private Date time;
    //错误信息
    private String errorCode;
    private String errorInfo;
    private String locationDetail;
    //公共信息
    private String wifiAble;
    private String GPSStatus;
    private String GPSSatellites;
    private String netWorkType;
    private String netUseTime;

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    private String lastLocation;
    public String getLocationType() {
        return LocationType;
    }

    public void setLocationType(String locationType) {
        this.LocationType = locationType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getSatellites() {
        return satellites;
    }

    public void setSatellites(String satellites) {
        this.satellites = satellites;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getADCODE() {
        return adcode;
    }

    public void setADCODE(String ADCODE) {
        this.adcode = ADCODE;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getWifiAble() {
        return wifiAble;
    }

    public void setWifiAble(String wifiAble) {
        this.wifiAble = wifiAble;
    }

    public String getGPSStatus() {
        return GPSStatus;
    }

    public void setGPSStatus(String GPSStatus) {
        this.GPSStatus = GPSStatus;
    }

    public String getGPSSatellites() {
        return GPSSatellites;
    }

    public void setGPSSatellites(String GPSSatellites) {
        this.GPSSatellites = GPSSatellites;
    }

    public String getNetWorkType() {
        return netWorkType;
    }

    public void setNetWorkType(String netWorkType) {
        this.netWorkType = netWorkType;
    }

    public String getNetUseTime() {
        return netUseTime;
    }

    public void setNetUseTime(String netUseTime) {
        this.netUseTime = netUseTime;
    }
}
