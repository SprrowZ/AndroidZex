package com.rye.base.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerHolder {
        return BaseRecyclerHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseRecyclerHolder, position: Int) {
        bindHolder(holder, position)
    }

    fun setData(mDatas: List<T>?) {
        mDataList = mDatas
    }

    abstract fun bindHolder(holder: BaseRecyclerHolder, position: Int)

    class BaseRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        val content: TextView = itemView.findViewById(R.id.content)

        companion object {
            fun create(parent: ViewGroup): BaseRecyclerHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val root = layoutInflater.inflate(R.layout.item_base_recycler, parent)
                return BaseRecyclerHolder(root)
            }
        }
    }
}