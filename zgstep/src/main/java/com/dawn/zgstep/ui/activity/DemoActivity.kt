package com.dawn.zgstep.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import com.dawn.zgstep.others.life.ZLifeObserver
import com.dawn.zgstep.ui.activity.adapters.SeniorUiAdapter
import com.dawn.zgstep.ui.fragment.ClassLoaderFragment
import com.dawn.zgstep.ui.fragment.ViewModelFragment
import com.dawn.zgstep.ui.fragment.XfermodeFragment
import com.rye.base.BaseFragmentActivity
import com.rye.base.widget.OnItemClickListener

class DemoActivity : BaseFragmentActivity(), OnItemClickListener {
    private var mRecycler: RecyclerView? = null
    private var mAdapter: SeniorUiAdapter? = null

    private var mXfer: TextView? = null
    private var mViewModel: TextView? = null
    private var mClassLoader :TextView?=null

    companion object {


        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, DemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_demo
    }

    override fun initWidget() {
        super.initWidget()
        mRecycler = findViewById(R.id.recycler)
        mXfer = findViewById(R.id.tv_xf)
        mViewModel = findViewById(R.id.tv_viewModel)
        mClassLoader = findViewById(R.id.class_loader)
    }

    override fun initEvent() {
        val viewModelFragment = ViewModelFragment()
        val xfermodeFragment = XfermodeFragment()
        var classLoaderFragment = ClassLoaderFragment.create()
        mXfer?.setOnClickListener {
            mRecycler?.visibility = View.GONE
            replaceFragment(xfermodeFragment)
            if (currentFragment is XfermodeFragment) {
                mRecycler?.visibility = View.VISIBLE
                mAdapter = SeniorUiAdapter()
                mAdapter?.setData((currentFragment as XfermodeFragment).fakeDatas)
                mAdapter?.setOnClickListener(this)
                mRecycler?.layoutManager = GridLayoutManager(this, 4)
                mRecycler?.adapter = mAdapter
            }
        }

        mViewModel?.setOnClickListener {
            replaceFragment(viewModelFragment)
        }

        mClassLoader?.setOnClickListener {
            replaceFragment(classLoaderFragment)
        }

        //生命周期监听
        lifecycle.addObserver(ZLifeObserver())


    }

    override fun onItemClick(position: Int) {
        if (currentFragment is XfermodeFragment) {
            val xferView = (currentFragment as XfermodeFragment)?.xferModeTwoView
            xferView.refresh(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}