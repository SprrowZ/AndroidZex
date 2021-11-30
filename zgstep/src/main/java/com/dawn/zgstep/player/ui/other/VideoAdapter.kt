package com.dawn.zgstep.player.ui.other

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dawn.zgstep.R
import com.rye.base.utils.Handler_
import com.rye.base.utils.MediaManager
import com.rye.base.utils.MediaVideo
import com.rye.base.utils.ThreadPoolManager

class VideoAdapter(private val itemClickListener: IVideoItemClickCallback) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private val videoList = mutableListOf<MediaVideo>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoViewHolder {
        return VideoViewHolder.create(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val data = videoList[position]
        holder.apply {
            setData(data)
            mVideoName.text = data.name
            mVideoDuration.text = data.duration?.toString()
            //
            ThreadPoolManager.getInstance().execute {
                val cover = data.id?.let { MediaManager.getVideoThumbnail(it) }
                Log.e("RRye", "cover height:${cover?.height}")
                Handler_.getInstance().post {
                    mVideoCover.setImageBitmap(cover)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun setData(data: List<MediaVideo>) {
        videoList.clear()
        videoList.addAll(data)
        notifyDataSetChanged()
    }

    class VideoViewHolder(itemView: View, private val itemClickListener: IVideoItemClickCallback) :
        RecyclerView.ViewHolder(itemView) {
        private var mData: MediaVideo? = null
        private val mVideoRoot: ConstraintLayout = itemView.findViewById(R.id.video_root)
        val mVideoCover: ImageView = itemView.findViewById(R.id.video_cover)
        val mVideoName: TextView = itemView.findViewById(R.id.video_name)
        val mVideoDuration: TextView = itemView.findViewById(R.id.video_duration)

        fun setData(data: MediaVideo) {
            mData = data
        }

        init {
            mVideoRoot.setOnClickListener {
                itemClickListener.onItemClick(it, mData)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                itemClickListener: IVideoItemClickCallback
            ): VideoViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_local_video, parent,false)
                return VideoViewHolder(itemView, itemClickListener)
            }
        }
    }
}

interface IVideoItemClickCallback {
    fun onItemClick(item: View, data: MediaVideo?)
}