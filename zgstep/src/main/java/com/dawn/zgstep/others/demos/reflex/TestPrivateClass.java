package com.dawn.zgstep.others.demos.reflex;

/**
 * Created by 18041at 2019/6/22
 * Function:
 */
public class TestPrivateClass {
    private String MSG="Original";
    private final String FINAL_VALUE;
    //运行时可修改静态常量的值
    public TestPrivateClass(){
        this.FINAL_VALUE="FINAL";
    }
    private void privateMethod(String head,int tail){
        System.out.println(head+tail);
    }
    public String getMsg(){
        return MSG;
    }
    public String getFinalValue(){
        return FINAL_VALUE;
    }
}
