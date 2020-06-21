package com.dawn.zgstep.test_activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dawn.zgstep.test_fragments.XfermodeFragment
import com.rye.base.BaseFragmentActivity

class SeniorUIActivity : BaseFragmentActivity() {

    override fun initEvent() {
        super.initEvent()
        val xfermodeFragment = XfermodeFragment()
        addFragment(xfermodeFragment)
    }
}