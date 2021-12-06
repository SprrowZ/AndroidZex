package com.dawn.zgstep.player.layers

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import android.view.TextureView
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dawn.zgstep.player.IPlayerController

/**
 * Create by  [Rye]
 *
 * at 2021/12/6 8:06 下午
 */
class PlayerSurfaceLayer @JvmOverloads constructor(
    private val mPlayerController: IPlayerController,
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private var mSurfaceType = SurfaceType.SurfaceView

    companion object {
        fun create(context: Context, playController: IPlayerController): PlayerSurfaceLayer {
            return PlayerSurfaceLayer(playController, context)
        }
    }

    init {
        //SurfaceView
        val surfaceView = SurfaceView(context)
        surfaceView.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(surfaceView, 0)
        setHolder(surfaceView)
    }

    fun setSurfaceType() {

    }

    private fun initSurface() {

    }

    private fun getSurfaceView() {

    }

    private fun getTextureView() {

    }

}

enum class SurfaceType(var type: Int) {
    SurfaceView(1),
    TextureView(2)
}