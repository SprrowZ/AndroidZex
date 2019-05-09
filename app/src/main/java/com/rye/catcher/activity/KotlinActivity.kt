package com.rye.catcher.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout.Behavior.getTag
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.rye.catcher.BaseActivity
import com.rye.catcher.MainActivity
import com.rye.catcher.R
import com.rye.catcher.activity.fragment.LMFragment
import com.rye.catcher.activity.fragment.LMFragmentKt
import com.rye.catcher.activity.fragment.SettingsFragment
import com.rye.catcher.activity.fragment.YLJFragment
import com.rye.catcher.project.kotlins.*
import com.rye.catcher.utils.ToastUtils

/**
 *Created by 18041at 2019/5/6
 *Function:
 */
private const val activityExProperty:String ="OK"

class KotlinActivity :BaseActivity() ,View.OnClickListener{


    private var container:RelativeLayout?=null
    private var title:TextView?=null

    private var design_bottom_sheet:BottomNavigationView?=null
    private var currentPos=-1
    private var currentFragment:Fragment?=null
    private var fragmentList:MutableList<Fragment>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        init()
        test()
    }
    fun init()  {

        container=findViewById(R.id.container)
        title=findViewById(R.id.title)
        design_bottom_sheet=findViewById(R.id.design_bottom_sheet)
        title?.text=getString(R.string.title)
//        title?.setOnClickListener {
//            ToastUtils.shortMsg("fine..${it.isActivated}")
//        }
        title?.setOnClickListener(this)
        design_bottom_sheet?.setOnNavigationItemSelectedListener {
           when(it.itemId){
               
           }
            true
        }

        selectItem(0)
    }
    fun test(){
        val demo: KotlinDemo1
        KotlinDemo1.testProperty2
         println(TEST_PROPERTY)
        println(activityExProperty)
        //发送登陆成功的广播
        BroadcastManager.sendLoginSuccessBroadcast()
        //数据操作处理
        var userData=UserData()
        var userInfo=UserData.UserInfo()
        userInfo.age=24
        userInfo.name="RyeCat"
        userInfo.sex=true
        userData.userInfo=userInfo
        UserConfig.userData=userData
        UserConfig.userData?.let {
            println("userData:${it.userInfo?.name}")
        }

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
            1->currentFragment=LMFragmentKt.getInstance()
            2->currentFragment=SettingsFragment()
        }
        return currentFragment
    }
    override fun onClick(v: View?) {
        when(v?.id){
//            R.id.title->ToastUtils.shortMsg("Fine..${v.id.toString().substring(v.id.toString().lastIndexOf('.'))}")
            R.id.title->{
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}