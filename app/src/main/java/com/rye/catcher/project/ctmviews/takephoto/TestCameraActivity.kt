package com.rye.catcher.project.ctmviews.takephoto


import android.os.Bundle
import android.support.v4.app.Fragment
import com.rye.catcher.BaseActivity
import com.rye.catcher.R
import kotlinx.android.synthetic.main.activity_orr.*
import kotlinx.android.synthetic.main.activity_orr.tabLayout
import kotlinx.android.synthetic.main.activity_orr.viewPager
import kotlinx.android.synthetic.main.activity_test_camera.*

class TestCameraActivity : BaseActivity() {

    private lateinit var adapter: CameraFragmentAdapter
    private lateinit var indictors: List<String>
    private lateinit var fragmentList: List<Fragment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_camera)
        initView()
    }

    private fun initView() {
        indictors = mutableListOf("Camera1", "Camera2")
        fragmentList = mutableListOf(CameraOneFragment(), CameraTwoFragment())

        adapter = CameraFragmentAdapter(supportFragmentManager, indictors, fragmentList)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }
}
