package com.dawn.zgstep.player.ui

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import com.dawn.zgstep.player.ui.other.IVideoItemClickCallback
import com.dawn.zgstep.player.ui.other.VideoAdapter
import com.rye.base.BaseFragment
import com.rye.base.utils.DensityUtil
import com.rye.base.utils.MediaManager
import com.rye.base.utils.MediaVideo

/**
 * Create by  [Rye]
 *
 * at 2021/12/1 2:09 下午
 */
class LocalVideoFragment: BaseFragment()  , IVideoItemClickCallback {
    private var mRecycler: RecyclerView? = null
    private var mVideoAdapter: VideoAdapter? = null
    private var mLayoutManager: GridLayoutManager? = null
    override fun getLayoutId(): Int {
       return R.layout.fragment_local_video
    }

    override fun initWidget() {
        mRecycler = view?.findViewById(R.id.video_recycler)
        mLayoutManager = GridLayoutManager(context, 2)
        mRecycler?.addItemDecoration(itemDecoration)
        mRecycler?.layoutManager = mLayoutManager
        mVideoAdapter = VideoAdapter(this)

        mRecycler?.adapter = mVideoAdapter

        MediaManager.getVideoFiles()?.let { mVideoAdapter?.setData(it) }
    }
    private val itemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val context = view.context
            val currentPos = parent.getChildAdapterPosition(view)
            outRect.left = if (currentPos % 2 == 0) {
                0
            } else {
                DensityUtil.dip2px(context, 10f)
            }
            outRect.bottom = DensityUtil.dip2px(context, 10f)
        }
    }
    override fun onItemClick(item: View, data: MediaVideo?) {
        Log.i("RRye", "onItemClick,dataName:${data?.name}")
    }
}