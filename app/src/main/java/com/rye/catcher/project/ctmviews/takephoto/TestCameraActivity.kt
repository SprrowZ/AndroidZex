package com.rye.catcher.project.ctmviews.takephoto


import android.os.Bundle
import android.support.v4.app.Fragment
import com.rye.base.ui.BaseActivity

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
        indictors = mutableListOf("Camera1", "Camera2")
        fragmentList = mutableListOf(CameraOneFragment(), CameraTwoFragment())

        adapter = CameraFragmentAdapter(supportFragmentManager, indictors, fragmentList)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }




}
