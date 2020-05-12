package com.dawn.zgstep.datas

import com.dawn.zgstep.R
import com.dawn.zgstep.kotlins.runOnMainThread

class FakeDatas {
    /**
     * 模仿TV项目数据源，数据格式不一定
     */
    companion object {

        var avatars= intArrayOf(R.drawable.my2,R.drawable.my3,R.drawable.my4)

        fun getProvinceLists() :List<ProvinceModel>{
            var dataList= mutableListOf<ProvinceModel>()
            for (i in 0..19) {
                val type = (Math.random() * 3 + 1).toInt()
                val data = ProvinceModel(avatars[type - 1], "未知省份", "未知省会",
                        1000)
                dataList.add(data)
            }
            //牛皮
            runOnMainThread()
            return dataList
        }
    }
}