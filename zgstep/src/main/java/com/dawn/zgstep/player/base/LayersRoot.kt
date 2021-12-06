package com.dawn.zgstep.player.base

import android.content.Context
import android.widget.FrameLayout
import com.dawn.zgstep.player.IPlayerController
import com.dawn.zgstep.player.layers.PlayerControlLayer

/**
 * Create by  [Rye]
 *
 * at 2021/12/1 5:02 下午
 *
 * 视图分层处理,这应该是一个ViewGroup,每一个层级都addView
 */
class LayersRoot(private val mPlayerController: IPlayerController):ILayerMessage {
    private var mTopContainer: FrameLayout? = null

    companion object {
        private const val CONTROL_LAYER_INDEX = 1
        private const val SURFACE_LAYER_INDEX = 0
    }

    private val mContext: Context?
        get() {
            return mTopContainer?.context
        }

    private fun initViewLayers() {
        val con = mContext ?: return
        val controlLayer = PlayerControlLayer.create(con, mPlayerController)
        mTopContainer?.addView(controlLayer, CONTROL_LAYER_INDEX)


    }

    override fun onActive(container: FrameLayout) {
        mTopContainer = container
    }

}

interface ILayerMessage {
    fun onActive(container: FrameLayout)
}