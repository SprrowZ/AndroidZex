package com.dawn.zgstep.player.base

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.dawn.zgstep.player.layers.PlayerControlLayer
import com.dawn.zgstep.player.layers.PlayerSurfaceLayer
import java.util.*
import kotlin.collections.ArrayList

/**
 * Create by  [Rye]
 *
 * at 2021/12/1 5:02 下午
 *
 * 视图分层处理,这应该是一个ViewGroup,每一个层级都addView
 */
class LayersRoot(
    private val mPlayerController: IPlayerController,
    private val mRootContainer: FrameLayout
):ILayerRoot {

    private var mLayerMessageList = Collections.synchronizedList(ArrayList<ILayerObserver>())

    companion object {
        private const val CONTROL_LAYER_INDEX = 1
        private const val SURFACE_LAYER_INDEX = 0
        fun create(mPlayerController: IPlayerController, mTopContainer: FrameLayout): LayersRoot {
            return LayersRoot(mPlayerController, mTopContainer)
        }
    }

    private val mContext: Context?
        get() {
            return mRootContainer.context
        }

    init {
        initViewLayers()
    }

    private fun initViewLayers() {
        val con = mContext ?: return
        if (filterOperation()) return
        val surfaceLayer = PlayerSurfaceLayer.create(con, mPlayerController)
        mRootContainer.addView(surfaceLayer, SURFACE_LAYER_INDEX) //不能跳index！

        val controlLayer = PlayerControlLayer.create(con, mPlayerController)
        mRootContainer.addView(controlLayer, CONTROL_LAYER_INDEX)

    }

    private fun filterOperation(): Boolean {
        return mRootContainer.getChildAt(0) != null
    }


    private fun notifyViewChanged(container: FrameLayout) {
        mLayerMessageList.forEach {
            it.onActive(container)
        }
    }

    fun registerContainerObserver(observer: ILayerObserver) {
        if (mLayerMessageList.contains(observer)) return
        mLayerMessageList.add(observer)
    }

    override fun getRootContainer(): View {
        return mRootContainer
    }

}
interface ILayerRoot {
    fun getRootContainer():View
}

interface ILayerObserver {
    fun onActive(container: FrameLayout)
}