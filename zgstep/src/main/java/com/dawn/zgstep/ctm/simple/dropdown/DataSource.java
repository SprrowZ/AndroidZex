package com.dawn.zgstep.ctm.simple.dropdown;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2018/12/19.
 *
 * @author Zzg
 */
public class DataSource {
    private String citys[]={"不限","北京","上海","武汉","成都","广州","深圳","天津","西安","南京","杭州","郑州"};
    private String ages[]={"不限","18岁以下","18-22岁","23-26岁","27-35岁","35岁以上"};
    private String sexs[]={"不限","男","女"};
    private String constellations[]={"不限","白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座"};
    public List<DropBean> headerList=new ArrayList<>();
    public List<DropBean> cityList=new ArrayList<>();
    public List<DropBean> ageList=new ArrayList<>();
    public List<DropBean> sexList=new ArrayList<>();
    public List<DropBean> constellationList=new ArrayList<>();
    private static  DataSource instance=new DataSource();

    public static DataSource getInstance() {
        return instance;
    }



    public List<DropBean> getCityList(){
        cityList.clear();
        for (String item:citys) {
            DropBean bean=new DropBean();
            bean.setContent(item);
            bean.setType(1);
            cityList.add(bean);
        }
        return cityList;
    }

    public List<DropBean> getAgeList(){
        ageList.clear();
        for (String item:ages) {
            DropBean bean=new DropBean();
            bean.setContent(item);
            bean.setType(2);
            ageList.add(bean);
        }
        return ageList;
    }

    public List<DropBean> getSexList(){
        sexList.clear();
        for (String item:sexs) {
            DropBean bean=new DropBean();
            bean.setContent(item);
            bean.setType(2);
            sexList.add(bean);
        }
        return sexList;
    }

    public List<DropBean> getConstellationList(){
        constellationList.clear();
        for (String item:constellations) {
            DropBean bean=new DropBean();
            bean.setContent(item);
            bean.setType(3);
            constellationList.add(bean);
        }
        return constellationList;
    }
}
