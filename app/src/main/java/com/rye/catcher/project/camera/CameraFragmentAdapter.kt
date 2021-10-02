package com.rye.catcher.project.camera

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *Created by 18041at 2019/7/6
 *Function:
 */
class CameraFragmentAdapter(fragmentManager: FragmentManager,
                            private val tabIndicators: List<String>,
                            val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(index: Int): Fragment {
        return fragments[index]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabIndicators[position]
    }
}