package com.dawn.zgstep.others.job1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawn.zgstep.R
import kotlinx.android.synthetic.main.activity_job_one.*

class JobOneActivity : AppCompatActivity() {
    var adapter: JobOneOuterAdapter? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_one)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initView() {
        adapter = JobOneOuterAdapter()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter?.setData(JobOneBean.getFakeDatas().datas)
    }
}
