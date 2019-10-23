package com.rye.catcher.project.ctmviews.takephoto

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *Created by 18041at 2019/7/6
 *Function:
 */
class CameraFragmentAdapter : FragmentPagerAdapter {
    private lateinit var  tabIndicators:List<String>
    private lateinit var  fragments:List<Fragment>
    constructor(fm: FragmentManager, indictors:List<String>, fragments:List<Fragment>) : super(fm) {
       this.tabIndicators=indictors
       this.fragments=fragments
    }


    override fun getCount(): Int {
         return  fragments.size
    }

    override fun getItem(index: Int): Fragment {
       return  fragments.get(index)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabIndicators.get(position)
    }
}