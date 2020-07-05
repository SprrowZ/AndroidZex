package com.dawn.zgstep.test_activitys.adapters

import com.rye.base.widget.BaseRecyclerAdapter
import com.rye.base.widget.HolderType

class SeniorUiAdapter:BaseRecyclerAdapter<String>() {

    override fun bindHolder(holder: BaseRecyclerHolder, position: Int) {
        holder.content.text = mDataList?.get(position) ?: "null"
    }


}