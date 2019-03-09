package com.rye.catcher.project.Ademos.adpterpattern.classpattern;

import com.rye.catcher.project.Ademos.adpterpattern.Adaptee;
import com.rye.catcher.project.Ademos.adpterpattern.Adapter;
import com.rye.catcher.project.Ademos.adpterpattern.Target;

/**
 * Created at 2019/3/8.
 *
 * @author Zzg
 * @function: 对象适配器
 */
public class Adapter2  implements Target{
    //并没有像类适配器那样继承Apaptee
    private Adaptee mAdaptee;//持有Adaptee引用
    public Adapter2(Adaptee adaptee){//做为参数传进来
        mAdaptee=adaptee;
    }

    @Override
    public void sampleOpertion1() {
        mAdaptee.sampleOpertion1();//在这里引用
    }

    @Override
    public void sampleOpertion2() {

    }
}
