package com.dawn.zgstep.test_activitys

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import com.dawn.zgstep.others.life.ZLifeObserver
import com.dawn.zgstep.test_activitys.adapters.SeniorUiAdapter
import com.dawn.zgstep.test_fragments.ViewModelFragment
import com.dawn.zgstep.test_fragments.XfermodeFragment
import com.rye.base.BaseFragmentActivity
import com.rye.base.utils.DeviceUtils
import com.rye.base.widget.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_xfermode.*

class DemoActivity : BaseFragmentActivity(), OnItemClickListener {
    private var mRecycler: RecyclerView? = null
    private var mAdapter: SeniorUiAdapter? = null

    private var mXfer: TextView? = null
    private var mViewModel: TextView? = null


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
    }

    override fun initEvent() {
        val viewModelFragment = ViewModelFragment()
        val xfermodeFragment = XfermodeFragment()

        mXfer?.setOnClickListener{
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
        mViewModel?.setOnClickListener{
            addFragment(viewModelFragment)
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