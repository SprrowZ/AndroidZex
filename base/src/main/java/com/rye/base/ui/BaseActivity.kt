package com.rye.base.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import butterknife.ButterKnife
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.lang.Exception

abstract class BaseActivity : RxAppCompatActivity() {
    val TAG = javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         beforeCreate()
        if (getLayoutId() != null && getLayoutId() != 0) {
            setContentView(getLayoutId())
        } else {
            throw IllegalArgumentException("layout cannot be null!")
        }
        //绑定
        ButterKnife.bind(this)


        initEvent()

    }

    @LayoutRes
    abstract fun getLayoutId(): Int


    abstract fun initEvent()

    open fun beforeCreate(){}
}