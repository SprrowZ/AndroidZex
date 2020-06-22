package com.rye.catcher.project.ctmviews.takephoto


import androidx.fragment.app.Fragment
import com.rye.base.BaseActivity

import com.rye.catcher.R

import kotlinx.android.synthetic.main.activity_orr.tabLayout
import kotlinx.android.synthetic.main.activity_orr.viewPager


class TestCameraActivity : BaseActivity() {
    private lateinit var adapter: CameraFragmentAdapter
    private lateinit var indictors: List<String>
    private lateinit var fragmentList: List<Fragment>

    override fun getLayoutId(): Int {
        return R.layout.activity_test_camera
    }

    override fun initEvent() {
        indictors = mutableListOf("Camera1", "Camera2Practice.." )
        fragmentList = mutableListOf(CameraOneFragment(),CameraTFragment() )

        adapter = CameraFragmentAdapter(supportFragmentManager, indictors, fragmentList)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }




}
