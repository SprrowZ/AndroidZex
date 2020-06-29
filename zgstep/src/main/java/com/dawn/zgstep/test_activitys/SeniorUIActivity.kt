package com.dawn.zgstep.test_activitys

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.dawn.zgstep.test_fragments.XfermodeFragment
import com.rye.base.BaseFragmentActivity

class SeniorUIActivity : BaseFragmentActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SeniorUIActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initEvent() {
        super.initEvent()
        val xfermodeFragment = XfermodeFragment()
        addFragment(xfermodeFragment)
    }
}