package com.rye.catcher.activity.adapter.recycler.diffdata

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rye.catcher.R


class TitleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Runnable {
    var rootView: View? = null
    var count: Int = -1
    override fun run() {
        Log.i("ZZG", "------滴答滴答--Runnable")
        count++
        if (count == 5) {
            rootView?.removeCallbacks(this)
        }
    }

    private var dataList: List<String>? = null

    private var itemClickListener: OnItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TitleHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleHolder) {
            holder.mTitle?.text = dataList?.get(position) ?: "--"
            holder.itemView.setOnClickListener {
                itemClickListener?.onClick(it, position)
            }
            holder.itemView.post(this)
        }

    }

    fun setDatas(datas: List<String>) {
        dataList = datas
        notifyItemRangeChanged(0, dataList?.size ?: 1 - 1)
    }

    fun setOnItemClickListener(listener: OnItemClick) {
        this.itemClickListener = listener
    }

    class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTitle: TextView = itemView.findViewById(R.id.title)

        companion object {
            fun create(parent: ViewGroup): TitleHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.recycler_common_title_item, parent, false)
                return TitleHolder(view)
            }
        }
    }

    interface OnItemClick {
        fun onClick(v: View, pos: Int)
    }
}