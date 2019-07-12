package com.rye.catcher.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rye.catcher.BaseActivity
import com.rye.catcher.R
import kotlinx.android.synthetic.main.activity_zt.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class KtCoroutineActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt_coroutine)
        initEvent()
    }
    private fun initEvent(){
        aa.setOnClickListener{
            testLaunch()
        }
        bb.setOnClickListener{
            testAsync()
        }
    }
    fun testLaunch(){
        GlobalScope.launch(Dispatchers.IO) {
            //调度器指定在IO线程中运行
            Log.i("zzz",Thread.currentThread().name)
            val today=Date(System.currentTimeMillis())
            val date=SimpleDateFormat("yyyy-MM-dd").format(today)
            delay(2000)
            //暂记问题---withContext里的代码不执行
            withContext(Dispatchers.Main){
                bb.text="kugou!"
                Log.i("zzz",Thread.currentThread().name+"---time:$date")
           }
        }
    }
    fun  testAsync(){
         GlobalScope.launch {
             Log.i("zzz","launch...")
             var result=-1
            val re= async {
                 delay(10000)
                 result=200
                return@async result
             }
             Log.i("zzz","result:${re.await()}")
         }
    }
}
