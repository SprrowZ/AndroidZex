package com.rye.catcher.project.camera


import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rye.base.BaseActivity

import com.rye.catcher.R
import com.rye.catcher.utils.PermissionsUtil
import com.rye.catcher.utils.PermissionsUtil.IPermissionsResult
import com.rye.catcher.utils.ToastUtils
import com.rye.catcher.utils.permission.Permissions

/**
 * camera1 / camera2 使用汇总
 */
class CameraMainActivity : BaseActivity() {
    private lateinit var adapter: CameraFragmentAdapter
    private lateinit var indictors: List<String>
    private lateinit var fragmentList: List<Fragment>
    private var mViewPager: ViewPager? = null
    private var mTabLayout: TabLayout? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CameraMainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_camera_main
    }

    override fun initWidget() {
        super.initWidget()
        mTabLayout = findViewById(R.id.tabLayout)
        mViewPager = findViewById(R.id.viewPager)
    }

    override fun initEvent() {
        checkAuthority()
        indictors = mutableListOf("Camera1", "Camera2Practice..")
        fragmentList = mutableListOf(CameraEntranceFragment(), Camera2Fragment())

        adapter = CameraFragmentAdapter(supportFragmentManager, indictors, fragmentList)
        mViewPager?.adapter = adapter

        mTabLayout?.setupWithViewPager(mViewPager)
    }

    private fun checkAuthority() {
        //这里的this不是上下文，是Activity对象！
        PermissionsUtil.INSTANCE.checkPermissions(this, object : IPermissionsResult {
            override fun passPermissons() {
                ToastUtils.shortMsg("权限申请成功")
            }

            override fun forbitPermissons() {
                ToastUtils.shortMsg("权限申请失败")
            }
        }, Permissions.CAMERA)
    }

}
