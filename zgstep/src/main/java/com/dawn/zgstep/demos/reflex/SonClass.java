package com.dawn.zgstep.demos.reflex;

/**
 * Created by 18041at 2019/6/22
 * Function:
 */
public class SonClass extends  FatherClass{
    private String mSonName;
    protected  int mSonAge;
    public String mSonBirthday;
    public void printSonMsg(){
        System.out.println("Son Msg-name:"+mSonName
        +",age:"+mSonAge);
    }
    private void setmSonName(String name){
        mSonName=name;
    }
    private void setmSonAge(int age){
        mSonAge=age;
    }
    private int getmSonAge(){
        return mSonAge;
    }
    private String getmSonName(){
        return mSonName;
    }

    public String getmSonBirthday() {
        return mSonBirthday;
    }

    public void setmSonBirthday(String mSonBirthday) {
        this.mSonBirthday = mSonBirthday;
    }
}
