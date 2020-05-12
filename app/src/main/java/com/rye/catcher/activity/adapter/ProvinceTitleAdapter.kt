package com.rye.catcher.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rye.catcher.R

/**
 * Title内容，固定数据源;相当于项目中的"我的收藏"，"我的追番"
 */
class ProvinceTitleAdapter(val clickListenter:TitleClickListener) : RecyclerView.Adapter<ProvinceTitleAdapter.TitleHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleHolder {
        return TitleHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: TitleHolder, position: Int) {
        when (position) {
            0 -> holder.title.text = "我的收藏"
            1 -> holder.title.text = "我的追番"
            2 -> holder.title.text = "我的追剧"
            3 -> holder.title.text = "历史记录"
            4 -> holder.title.text = "关注UP主"
        }

        holder.title.setOnClickListener { clickListenter.changeAdapter(position) }
    }


    class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)

        companion object {
            fun create(parent: ViewGroup): TitleHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val rootView= layoutInflater.inflate(R.layout.recycler_common_title_item, parent, false)
                return TitleHolder(rootView)
            }
        }
    }

    interface TitleClickListener {
        fun changeAdapter(position:Int)
    }
}