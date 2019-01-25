// IMyAidlInterface.aidl
package com.rye.catcher.activity;
import com.rye.catcher.activity.PersonBean;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     *
     * AIDL支持的基本类型,不支持short
     */
//    List<String> basicTypes(byte aByte,int anInt, long aLong, boolean aBoolean,
//    float aFloat,double aDouble,
//    char aChar,
//    String aString, in List<String> aList);
List<PersonBean> add( in PersonBean  person);
}
