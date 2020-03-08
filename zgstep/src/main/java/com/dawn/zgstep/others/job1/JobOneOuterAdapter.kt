package com.dawn.zgstep.others.job1

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import kotlinx.android.synthetic.main.job_one_recycler_item.view.*

class JobOneOuterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList: MutableList<JobOneBean.BangumiBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JobOneVH.create(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(datas:List<JobOneBean.BangumiBean>){
        dataList.clear()
        dataList.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JobOneVH){
            holder.date.text=dataList[position].date
            holder.outerRecycler.layoutManager=GridLayoutManager(holder.itemView.context,5)
            var adapter=JobOneInnerAdapter()
            holder.outerRecycler.adapter=adapter
            adapter.setData(dataList[position].episodes)//12天，每一天的数据传递进去

        }
    }

    class JobOneVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeLine:LinearLayout=itemView.findViewById(R.id.time_line)
        val date:TextView=itemView.findViewById(R.id.date)
        val outerRecycler:RecyclerView=itemView.findViewById(R.id.outer_recycler)
        companion object {
            fun create(parent: ViewGroup): JobOneVH {
                val layoutInflate = LayoutInflater.from(parent.context)
                val rootView = layoutInflate.inflate(R.layout.job_one_outer_item, parent, false)

                return JobOneVH(rootView)
            }
        }
    }
}