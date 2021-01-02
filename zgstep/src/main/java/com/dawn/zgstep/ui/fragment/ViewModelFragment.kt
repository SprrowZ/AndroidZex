package com.dawn.zgstep.ui.fragment

import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.dawn.zgstep.R
import com.dawn.zgstep.mvvm.model.ConfigViewModel
import com.rye.base.BaseFragment

/**
 * Create by rye
 * at 2020-11-19
 * @description:
 */
class ViewModelFragment :BaseFragment() {
    private var tvData:TextView?=null
    private var etInput:EditText?=null
    private var tvConfirm:TextView?=null

    override fun getLayoutId(): Int {
        return R.layout.fragment_viewmodel
    }

    override fun initWidget() {
        super.initWidget()
        val repository =  ConfigViewModel.get(context as FragmentActivity).getConfigDataRepository()

        tvData = view?.findViewById(R.id.tv_data)
        etInput = view?.findViewById(R.id.et_input)
        tvConfirm = view?.findViewById(R.id.tv_confirm)

        tvData?.text = repository.appTheme
        //数据监听

        repository.addAppThemeObserver(this, Observer { t -> tvData?.text = t })




        tvConfirm?.setOnClickListener{
             repository.setTheme(etInput?.text.toString())
        }
    }
}