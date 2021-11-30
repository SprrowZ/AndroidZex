package com.dawn.zgstep.player.ui.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import com.rye.base.utils.MediaVideo

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private val videoList = mutableListOf<MediaVideo>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoAdapter.VideoViewHolder {
        return VideoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: VideoAdapter.VideoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup): VideoViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_local_video, null)
                return VideoViewHolder(itemView)
            }
        }
    }
}