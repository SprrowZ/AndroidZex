package com.dawn.zgstep.player.ui

import android.content.Context
import android.content.Intent
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.dawn.zgstep.R
import com.dawn.zgstep.player.ui.demo.DemoExoFragment
import com.dawn.zgstep.player.ui.demo.DemoMediaPlayerFragment
import com.rye.base.BaseActivity

/**
 * 视频测试Activity
 */
class PlayerDetailActivity : BaseActivity() {
    private var mPlayerContainer: FrameLayout? = null
    private var currentFragment: Fragment? = null
    private var currentPos = -1


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, PlayerDetailActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_player
    }

    override fun initWidget() {
        super.initWidget()
        mPlayerContainer = findViewById(R.id.container_video)
    }

    override fun initEvent() {
        doSelect(3)
    }

    private fun doSelect(pos: Int) {
        if (pos == currentPos) {
            return
        }
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (currentFragment != null) {
            transaction.hide(currentFragment!!)
        }
        currentPos = pos
        val fragment = manager.findFragmentByTag(getTag(pos))
        if (fragment != null) {
            currentFragment = fragment
            transaction.show(currentFragment!!)
        } else {
            getFragment(pos)?.let { transaction.add(R.id.container_video, it, getTag(pos)) }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun getFragment(pos: Int): Fragment? {
        when (pos) {
            0 -> currentFragment = PlayerFragment() //封装播放器
            1 -> currentFragment = LocalMediaFragment() //MediaExtractor
            2-> currentFragment = DemoMediaPlayerFragment() //简易播放器
            3->currentFragment = DemoExoFragment() //exo
        }
        return currentFragment
    }

    private fun getTag(pos: Int): String? {
        return "Zzg_$pos"
    }
}