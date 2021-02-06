package com.rye.catcher.project.ctmviews.takephoto


import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rye.base.BaseActivity

import com.rye.catcher.R


class TestCameraActivity : BaseActivity() {
    private lateinit var adapter: CameraFragmentAdapter
    private lateinit var indictors: List<String>
    private lateinit var fragmentList: List<Fragment>
    private var mViewPager: ViewPager? = null
    private var mTabLayout: TabLayout? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_test_camera
    }

    override fun initWidget() {
        super.initWidget()

    }

    override fun initEvent() {
        indictors = mutableListOf("Camera1", "Camera2Practice..")
        fragmentList = mutableListOf(CameraOneFragment(), CameraTFragment())

        adapter = CameraFragmentAdapter(supportFragmentManager, indictors, fragmentList)
        mViewPager?.adapter = adapter

        mTabLayout?.setupWithViewPager(mViewPager)
    }


}
