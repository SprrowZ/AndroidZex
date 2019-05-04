package com.rye.catcher.activity.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rye.catcher.R
import com.rye.catcher.beans.binding.ClickHandler
import com.rye.catcher.beans.binding.SettingBean
import com.rye.catcher.databinding.Tab04Binding

/**
 * Created by zzg on 2017/10/10.
 */

class SettingsFragment : Fragment() {

    private var binding: Tab04Binding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.tab04, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValue()
    }

    private fun setValue() {
        val bean = SettingBean()
        bean.orr = getString(R.string.orr)
        bean.animation = getString(R.string.animation)
        bean.someDemo = getString(R.string.somedome)
        bean.camera = getString(R.string.camera)
        bean.customView = getString(R.string.customView)
        bean.javaMore = getString(R.string.javaMore)
        bean.retrofit = getString(R.string.retrofit)
        bean.review = getString(R.string.review)
        bean.project = getString(R.string.project)
        binding!!.settingName = bean
        binding!!.clickHandler = ClickHandler()
        //        binding.camera.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                   bean.setCamera("22222!");
        //            }
        //        });
    }


    override fun onDestroy() {
        super.onDestroy()
    }


}
