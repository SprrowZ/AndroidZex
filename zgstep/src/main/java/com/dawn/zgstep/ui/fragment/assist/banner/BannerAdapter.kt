package com.dawn.zgstep.ui.fragment.assist.banner

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R


class BannerAdapter : BaseBannerAdapter<StaticBannerBean, BannerAdapter.ViewHolder>() {

    override fun getLayoutId(viewType: Int) = R.layout.item_banner_samll

    override fun onBind(holder: ViewHolder, data: StaticBannerBean, position: Int, pageSize: Int) {

        holder.imageView.setImageResource(data.imgUrl)
    }


    override fun createViewHolder(parent: ViewGroup, itemView: View, viewType: Int): ViewHolder {
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_banner)
    }


}