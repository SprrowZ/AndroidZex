package com.dawn.zgstep.player.ui
import android.widget.Button
import android.widget.FrameLayout
import com.dawn.zgstep.R
import com.dawn.zgstep.player.VideoDetail
import com.dawn.zgstep.player.base.IPlayerController
import com.rye.base.BaseFragment
import com.rye.base.utils.AliThreadPool
import com.rye.base.utils.Handler_
import com.rye.base.utils.SDHelper

/**
 * |详情参考Video.md
 */
class PlayerFragment : BaseFragment() {
    private var mContainer: FrameLayout? = null
    private var mSeparateBtn:Button?=null
    private lateinit var mPlayerController: IPlayerController

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
        mContainer = view?.findViewById(R.id.video_container)
        mSeparateBtn = view?.findViewById(R.id.test_separate)
    }

    override fun initEvent() {
        super.initEvent()
        mPlayerController =
            IPlayerController.create()
        val videoDetail = VideoDetail().run {
            url = VIDEO_URL_ONE
            container = mContainer
            this
        }
        mPlayerController.goPlay(videoDetail)
        mSeparateBtn?.setOnClickListener {

            AliThreadPool.runInBackground {
                VideoSeparate
                    .create()
                    .separate(context, VIDEO_URL_ONE)
            }
        }

    }


    private fun getLocalVideoFilePath(): String {
        return SDHelper.external + "1736812285.mp4"
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayerController.release()
    }


}