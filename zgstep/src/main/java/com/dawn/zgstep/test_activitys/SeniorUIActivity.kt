package com.dawn.zgstep.test_activitys

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import com.dawn.zgstep.test_activitys.adapters.SeniorUiAdapter
import com.dawn.zgstep.test_fragments.XfermodeFragment
import com.rye.base.BaseFragmentActivity
import com.rye.base.utils.DeviceUtils
import com.rye.base.widget.OnItemClickListener

class SeniorUIActivity : BaseFragmentActivity(), OnItemClickListener {
    private var mRecycler: RecyclerView? = null
    private var mAdapter: SeniorUiAdapter? = null
    private val screenWidth: Int = DeviceUtils.getScreenWidth()
    private var scrollX: Int = 0

    companion object {


        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SeniorUIActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_xfermode
    }

    override fun initWidget() {
        super.initWidget()
        mRecycler = findViewById(R.id.recycler)
    }

    override fun initEvent() {
        super.initEvent()
        val xfermodeFragment = XfermodeFragment()
        addFragment(xfermodeFragment)
        if (currentFragment is XfermodeFragment) {
            mAdapter = SeniorUiAdapter()
            mAdapter?.setData((currentFragment as XfermodeFragment).fakeDatas)
            mAdapter?.setOnClickListener(this)
            mRecycler?.layoutManager = GridLayoutManager(this, 4)
            mRecycler?.adapter = mAdapter
        }
    }

    override fun onClick(position: Int) {
        if (currentFragment is XfermodeFragment) {
            val xferView = (currentFragment as XfermodeFragment)?.xferModeTwoView
            xferView.refresh(position)
        }
    }
}