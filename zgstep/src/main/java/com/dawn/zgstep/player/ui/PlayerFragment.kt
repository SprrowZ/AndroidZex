package com.dawn.zgstep.player.ui

import android.graphics.Rect
import android.util.Log
import android.view.SurfaceView
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import com.dawn.zgstep.player.IPlayerController
import com.dawn.zgstep.player.VideoDetail
import com.dawn.zgstep.player.ui.other.IVideoItemClickCallback
import com.dawn.zgstep.player.ui.other.VideoAdapter
import com.rye.base.BaseFragment
import com.rye.base.utils.DensityUtil
import com.rye.base.utils.MediaManager
import com.rye.base.utils.MediaVideo
import com.rye.base.utils.SDHelper

/**
 * |详情参考Video.md
 */
class PlayerFragment : BaseFragment(), IVideoItemClickCallback {
    private var mSurfaceView: SurfaceView? = null
    private lateinit var mPlayerController: IPlayerController
    private var mRecycler: RecyclerView? = null
    private var mVideoAdapter: VideoAdapter? = null
    private var mLayoutManager: GridLayoutManager? = null

    companion object {
        private const val VIDEO_URL_ONE = "https://media.w3.org/2010/05/sintel/trailer.mp4" //50s
        private const val VIDEO_URL_TWO =
            "https://www.w3school.com.cn/example/html5/mov_bbb.mp4" //10s
        private const val VIDEO_URL_THREE =
            "https://vod-progressive.akamaized.net/exp=1630764570~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2670%2F7%2F188350983%2F623685558.mp4~hmac=0579ce819bcbbae48a13eca2ea3b095433e465ba52eb8b223dfe10789ad7e45b/vimeo-prod-skyfire-std-us/01/2670/7/188350983/623685558.mp4?filename=Emoji+Saver+-+Patterns+in+the+Rain.mp4"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_player
    }

    override fun initWidget() {
        super.initWidget()
        mSurfaceView = view?.findViewById(R.id.surface)
        mRecycler = view?.findViewById(R.id.video_recycler)
        mLayoutManager = GridLayoutManager(context, 2)
        mRecycler?.addItemDecoration(itemDecoration)
        mRecycler?.layoutManager = mLayoutManager
        mVideoAdapter = VideoAdapter(this)

        mRecycler?.adapter = mVideoAdapter

        MediaManager.getVideoFiles()?.let { mVideoAdapter?.setData(it) }
    }

    override fun initEvent() {
        super.initEvent()
        mPlayerController =
            IPlayerController.create()
        mPlayerController.goPlay(
            VideoDetail(
                VIDEO_URL_ONE
            ), mSurfaceView
        )
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

    private fun getLocalVideoFilePath(): String {
        return SDHelper.external + "1736812285.mp4"
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayerController.release()
    }

    override fun onItemClick(item: View, data: MediaVideo?) {
        Log.i("RRye", "onItemClick,dataName:${data?.name}")
    }
}