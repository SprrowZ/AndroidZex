package com.rye.base.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rye.base.R

/**
 * Create by rye
 * at 2020-06-20
 * @description: base Adapter
 */
abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerHolder>() {
    @JvmField
    var mDataList: List<T>? = null
    var mHolder: BaseRecyclerHolder? = null
    private var holderType: HolderType = HolderType.HOLDER_TYPE_MATCH
    private var clickListener:OnItemClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerHolder {
        mHolder = if (holderType == HolderType.HOLDER_TYPE_MATCH) {
            BaseRecyclerHolderM.create(parent)
        } else {
            BaseRecyclerHolderW.create(parent)
        }
        return mHolder!!
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseRecyclerHolder, position: Int) {
        bindHolder(holder, position)
        holder.root.setOnClickListener{
            clickListener?.onClick(position)
        }
    }

    fun setData(mDatas: List<T>?) {
        mDataList = mDatas
    }

    abstract fun bindHolder(holder: BaseRecyclerHolder, position: Int)
    open fun setHolderType(type: HolderType) {
        holderType = type
    }

    open fun setOnClickListener(listener:OnItemClickListener){
        clickListener = listener
    }


    open class BaseRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        val content: TextView = itemView.findViewById(R.id.content)

        @JvmField
        var root: FrameLayout = itemView.findViewById(R.id.root)
    }

    class BaseRecyclerHolderM(itemView: View) : BaseRecyclerHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup): BaseRecyclerHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val root = layoutInflater.inflate(R.layout.item_base_recycler_m, parent, false)
                return BaseRecyclerHolder(root)
            }
        }
    }

    class BaseRecyclerHolderW(itemView: View) : BaseRecyclerHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup): BaseRecyclerHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val root = layoutInflater.inflate(R.layout.item_base_recycler_w, parent, false)
                return BaseRecyclerHolder(root)
            }
        }
    }


}
interface  OnItemClickListener{
    fun onClick(position: Int)
}
enum class HolderType(var type: Int) {
    HOLDER_TYPE_MATCH(1),
    HOLDER_TYPE_WRAP(2)
}