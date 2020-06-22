package com.rye.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rye.base.utils.FileUtils
import com.rye.base.utils.GsonUtils
import com.rye.base.utils.SDHelper
import com.rye.base.widget.BaseRecyclerAdapter
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * Create by rye
 * at 2020-06-20
 * @description:
 */
abstract class BaseRecyclerActivity<T> : BaseActivity() {
    var recyclerView: RecyclerView? = null
    var mAdapter: BaseRecyclerAdapter<T>? = null
    override fun initWidget() {
        super.initWidget()
        recyclerView = findViewById(R.id.recycler)
        mAdapter = initAdapter()
        mAdapter?.setData(getDataList())
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = mAdapter
    }

    companion object {
        const val FILE_SUFFIX = ".json"
    }

    /**
     * 从Assets拷到文件中，再读？什么玩意？？
     */
    private fun getDataList(): List<T>? {
        val dataInfo = getDataInfo()
        val manager = BaseApplication.getInstance().assets
        val desPath = SDHelper.getAppExternal()
        val fileName = dataInfo.prefix + FILE_SUFFIX
        FileUtils.copyAssetToSDCard(manager, fileName, desPath + fileName)
        val jsonFile = File(desPath + fileName)
        try {
            val reader = FileReader(jsonFile)
            return GsonUtils.toList<T>(reader, dataInfo.type)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    abstract fun getDataInfo(): DataInfo<T>

    data class DataInfo<T>(val prefix: String, val type: Class<Array<T>>)


    abstract fun initAdapter(): BaseRecyclerAdapter<T>
}