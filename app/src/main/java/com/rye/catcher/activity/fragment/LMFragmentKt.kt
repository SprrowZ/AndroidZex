package com.rye.catcher.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.rye.catcher.R

class LMFragmentKt:BaseFragment() {
    override fun getLayoutResId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        R.layout.tab02
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val bundle=arguments
        val argumentValue=bundle?.get("key")

    }

    companion object{
        fun getInstance():Fragment {
            val bundle =Bundle()
            bundle.putString("key","value-001")
            val frament=LMFragmentKt()
            frament.arguments=bundle
            return frament
        }
    }
}