package com.rye.base.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import butterknife.ButterKnife
import com.rye.base.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


abstract class BaseActivity : RxAppCompatActivity() {
    val TAG = javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setTheme(R.style.default_activity_theme)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //底部导航栏
            //   getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        beforeCreate()
        if (getLayoutId() != null && getLayoutId() != 0) {
            setContentView(getLayoutId())
        } else {
            throw IllegalArgumentException("layout cannot be null!")
        }
        //绑定
        ButterKnife.bind(this)

        initWidget()

        initEvent()
    }

    open fun initWidget() {}

    @LayoutRes
    abstract fun getLayoutId(): Int


    abstract fun initEvent()

    open fun beforeCreate() {}
}