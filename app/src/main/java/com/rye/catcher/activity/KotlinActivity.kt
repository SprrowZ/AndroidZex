package com.rye.catcher.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout.Behavior.getTag
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.rye.catcher.BaseActivity
import com.rye.catcher.R
import com.rye.catcher.activity.fragment.LMFragment
import com.rye.catcher.activity.fragment.SettingsFragment
import com.rye.catcher.activity.fragment.YLJFragment

/**
 *Created by 18041at 2019/5/6
 *Function:
 */
class KotlinActivity :BaseActivity() {
    private var container:RelativeLayout?=null
    private var currentPos=-1;
    private var currentFragment:Fragment?=null
    private var fragmentList:MutableList<Fragment>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    fun init():Unit{
        container=findViewById(R.id.container)

    }

    /**
     * 获取当前Fragment
     */
    private fun selectItem(pos: Int) {
        //点击的正是当前正在显示的，直接返回
        if (currentPos == pos) return
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (currentFragment != null) {
            //隐藏当前正在显示的fragment
            transaction.hide(currentFragment!!)
        }
        currentPos = pos
        val fragment = manager.findFragmentByTag(getTag(pos))
        //通过findFragmentByTag判断是否已存在目标fragment，若存在直接show，否则去add
        if (fragment != null) {
            currentFragment = fragment
            transaction.show(fragment)
        } else {
            //let???
            getFragment(pos)?.let { transaction.add(R.id.container, it, getTag(pos)) }//加TAG
        }
        transaction.commitAllowingStateLoss()
        //改变颜色值
        //  setSelect(pos);
    }

    private fun getTag(pos:Int):String{
        return "Zzg_$pos"
    }
    private fun  getFragment(pos: Int): Fragment? {
        when(pos){
            0->currentFragment=YLJFragment()
            1->currentFragment=LMFragment()
            2->currentFragment=SettingsFragment()
        }
        return currentFragment
    }

}