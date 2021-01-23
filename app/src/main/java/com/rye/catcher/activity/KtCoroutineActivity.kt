package com.rye.catcher.activity


import android.util.Log
import android.widget.Button
import com.rye.base.BaseActivity
import com.rye.catcher.R

import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class KtCoroutineActivity : BaseActivity() {
    private var mLaunchBtn: Button?=null
    private var mAsyncBtn:Button?=null
    override fun getLayoutId(): Int {
        return R.layout.activity_kt_coroutine
    }

    override fun initWidget() {
        super.initWidget()
        mLaunchBtn = findViewById(R.id.aa)
        mAsyncBtn = findViewById(R.id.bb)
    }
    override fun initEvent() {
        mLaunchBtn?.setOnClickListener {
            testLaunch()
        }
        mAsyncBtn?.setOnClickListener {
            testAsync()
        }
    }

    fun testLaunch() {
        GlobalScope.launch(Dispatchers.IO) {
            //调度器指定在IO线程中运行
            Log.i("zzz", Thread.currentThread().name)
            val today = Date(System.currentTimeMillis())
            val date = SimpleDateFormat("yyyy-MM-dd").format(today)
            delay(2000)
            //暂记问题---withContext里的代码不执行
            withContext(Dispatchers.Main) {
                mAsyncBtn?.text = "kugou!"
                Log.i("zzz", Thread.currentThread().name + "---time:$date")
            }
        }
    }

    fun testAsync() {
        GlobalScope.launch {
            Log.i("zzz", "launch...")
            var result = -1
            val re = async {
                delay(10000)
                result = 200
                return@async result
            }
            Log.i("zzz", "result:${re.await()}")
        }
    }
}
