package com.dawn.zgstep.ui.activity.adapters

import com.rye.base.widget.BaseRecyclerAdapter

class SeniorUiAdapter:BaseRecyclerAdapter<String>() {
    override fun bindHolder(holder: BaseRecyclerHolder, position: Int) {
        holder.content.text = mDataList?.get(position)?.toLowerCase() ?: "null"
    }
}