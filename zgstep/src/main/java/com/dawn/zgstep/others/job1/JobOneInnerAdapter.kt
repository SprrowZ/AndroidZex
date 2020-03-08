package com.dawn.zgstep.others.job1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import kotlinx.android.synthetic.main.job_one_inner_item.view.*

class JobOneInnerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList:MutableList<JobOneBean.Episode> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return JobOneInnerVH.create(parent)
    }

    override fun getItemCount(): Int {
      return  dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.img.setImageResource(R.drawable.my2)//换成cover
        holder.itemView.title.text=dataList[position].title
    }
    fun setData(data:MutableList<JobOneBean.Episode>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
    class JobOneInnerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img:ImageView=itemView.findViewById(R.id.img)
        val title:TextView=itemView.findViewById(R.id.title)
        companion object{
            fun create(parent: ViewGroup):JobOneInnerVH{
                val layoutInflater=LayoutInflater.from(parent.context)
                val itemView=layoutInflater.inflate(R.layout.job_one_inner_item,parent,false)
                return JobOneInnerVH(parent)
            }
        }
    }
}