package com.dawn.zgstep.ui.activity.ui.main

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager.widget.ViewPager
import com.dawn.zgstep.R
import com.dawn.zgstep.ui.ctm.views.StickyNavLayout
import com.dawn.zgstep.ui.fragment.nested.BaseFragmentItemAdapter
import com.dawn.zgstep.ui.fragment.nested.TabFragment
import com.google.android.material.tabs.TabLayout

class NestedScrollFragment : Fragment() {
    private var mRoot:View?= null
    private var mTabLayout: TabLayout? = null
    private var mViewPager: ViewPager? = null

    private var mStickyNavLayout: StickyNavLayout? = null

    private var mBackImageView: ImageView? = null
    private var mTitleView: TextView? = null

    val FRAGMENT_COUNT: Int = 4

    companion object {
        fun newInstance() = NestedScrollFragment()
    }

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRoot = inflater.inflate(R.layout.fragment_nested_scroll_parent, container, false)
        return mRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }


    private fun initView() {
        mTabLayout = mRoot?.findViewById(R.id.tab_layout)
        mViewPager = mRoot?.findViewById(R.id.view_pager)
        mStickyNavLayout = mRoot?.findViewById(R.id.sticky_layout)
        mBackImageView = mRoot?.findViewById(R.id.iv_back)
        mTitleView = mRoot?.findViewById(R.id.tv_title)
        mBackImageView!!.setOnClickListener(View.OnClickListener {
            activity?.finish()
        })
        initToolBar(R.drawable.ic_action_back_black,0f)
    }

    private fun initData() {
        mViewPager!!.adapter =
            BaseFragmentItemAdapter(activity?.supportFragmentManager, initFragments(), initTitles())
        mTabLayout!!.setupWithViewPager(mViewPager)
        mStickyNavLayout!!.setScrollChangeListener(object : StickyNavLayout.ScrollChangeListener {
            override fun onScroll(moveRatio: Float) {
                initToolBar(R.drawable.ic_action_back_white, moveRatio)
            }
        })
    }
    private fun initTitles(): List<String> {
        val titles: MutableList<String> = java.util.ArrayList()
        titles.add("首页")
        titles.add("全部")
        titles.add("作者")
        titles.add("专辑")
        return titles
    }

    private fun initFragments(): List<Fragment> {
        val fragments: MutableList<Fragment> = ArrayList()
        for (i in 0 until FRAGMENT_COUNT) {
            fragments.add(TabFragment.newInstance("NestedScrolling2Demo"))
        }
        return fragments
    }

    private fun initToolBar(@DrawableRes backResId: Int, moveRatio: Float) {

        val argbEvaluator = ArgbEvaluator()
        val color = argbEvaluator.evaluate(moveRatio, Color.WHITE, Color.BLACK) as Int
        val wrapDrawable: Drawable = DrawableCompat.wrap(resources.getDrawable(backResId))
        DrawableCompat.setTint(wrapDrawable, color)

        mBackImageView!!.setImageDrawable(wrapDrawable)
        mTitleView!!.alpha = moveRatio
    }

}