package com.rye.catcher

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.annotations.Until
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Create by rye
 * at 2020-11-12
 * @description:
 */
@RunWith(AndroidJUnit4::class)
class KtTest {
    @Test
    fun testUnit(){
        println("----------")
        val clickTask = {
            if (1==1) {
                Log.e("zzg","1111")
                return
            }
            Log.e("zzg","2222222")
        }
        clickTask.invoke()
    }
}