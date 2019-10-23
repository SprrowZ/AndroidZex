package com.rye.catcher.beans.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by 18041at 2019/4/28
 * Function:
 */
public class TestViewModel extends ViewModel {
    private int num1=0;
    private int num2=0;
    private MutableLiveData<String> mCurrentName;
    //LiveData
    public MutableLiveData<String> getCurrentName( ){
        if (mCurrentName==null){
            return new MutableLiveData<>();
        }
        return mCurrentName;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }
}
