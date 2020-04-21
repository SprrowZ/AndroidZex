package com.rye.catcher.activity.adapter.recy.diffdata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rye.catcher.R
import kotlinx.android.synthetic.main.recycler_common_title_item.view.*

class TitleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: List<String>? = null

    private var itemClickListener : OnItemClick ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TitleHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         holder.itemView.title.text = dataList?.get(position) ?: "--"
         holder.itemView.setOnClickListener{
             itemClickListener?.onClick(it, position)
         }
    }

    fun setDatas(datas: List<String>) {
        dataList = datas
        notifyItemRangeChanged(0, dataList?.size ?: 1 - 1)
    }
    fun setOnItemClickListener(listener: OnItemClick) {
        this.itemClickListener =listener
    }

    class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTitle :TextView = itemView.findViewById(R.id.title)

        companion object {
            fun create(parent: ViewGroup): TitleHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.recycler_common_title_item, parent, false)
                return TitleHolder(view)
            }
        }
    }

  interface OnItemClick {
      fun onClick(v: View , pos: Int)
  }
}