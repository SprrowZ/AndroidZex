package com.dawn.zgstep.jetpack

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private var currentSecondModel: MutableLiveData<Int> = MutableLiveData()

    var currentSecond: Int?
        get() {
            return currentSecondModel.value
        }
        set(value) {
            currentSecondModel.value = value
        }

    fun addCurrentSecondObserver(owner: LifecycleOwner, observer: Observer<Int>) {
        currentSecondModel.observe(owner, observer)
    }
    fun testCoroutineScope(){

    }

//    fun removeCurrentSecondObserver(observer: Observer<Int>) {
//        currentSecondModel.removeObserver(observer){}
//    }

}