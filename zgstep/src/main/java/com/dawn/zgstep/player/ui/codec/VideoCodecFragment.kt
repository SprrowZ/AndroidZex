package com.dawn.zgstep.player.ui.codec

import com.dawn.zgstep.R
import com.rye.base.BaseFragment

/**
 * Create by  [Rye]
 *
 * at 2022/1/7 2:12 下午
 */
class VideoCodecFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun create(): VideoCodecFragment {
            return VideoCodecFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_codec
    }
}