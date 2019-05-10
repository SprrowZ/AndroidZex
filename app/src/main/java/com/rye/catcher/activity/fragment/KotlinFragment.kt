package com.rye.catcher.activity.fragment

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.rye.catcher.R

class KotlinFragment:Fragment(),MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {


    //播放器
    var mediaPlayer:MediaPlayer?=null
    //播放源
    lateinit var url:Uri
    //是否已经准备好
    var hasPrepared=false
    //
    lateinit var rootView: View
    //

     lateinit var start:TextView

     lateinit var  pause:TextView

    lateinit var  reStart:TextView

    lateinit var  stop:TextView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_kotlin,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }
    private fun initData() {

        val bundle=arguments
        val argumentValue=bundle?.get("key") as String
        Log.i("arguments",argumentValue)


        //MediaPlayer---Demo
        initPlayer()
        initEvent()
    }
    private fun initEvent(){
        //
        start=rootView.findViewById(R.id.start)
        pause=rootView.findViewById(R.id.pause)
        reStart=rootView.findViewById(R.id.reStart)
        stop=rootView.findViewById(R.id.stop)

        start.setOnClickListener{
            start()
        }
        pause.setOnClickListener {
            pause()
        }
        reStart.setOnClickListener {
            reStart()
        }
        stop.setOnClickListener {
            stop()
        }
    }

    private fun  initPlayer(){
        url =Uri.parse("http://5.595818.com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3");
        mediaPlayer=MediaPlayer()
        //设置播放源
        if (::url.isInitialized){//是否初始化
            mediaPlayer!!.setDataSource(activity as Context,url!!)
            //异步播放，边加载边播放
            mediaPlayer!!.prepareAsync()
            //获取到资源后开始播放
            mediaPlayer!!.setOnPreparedListener (this)
            mediaPlayer!!.setOnCompletionListener (this)
        }

        }


    //准备完毕
    override fun onPrepared(mp: MediaPlayer?) {
         hasPrepared = true
    }
    //播放完成
    override fun onCompletion(mp: MediaPlayer?) {

    }
    
    //开始播放
   fun  start(){
       if (mediaPlayer!=null && hasPrepared){
           mediaPlayer!!.start()
       }
   }

    //暂停播放
   fun  pause(){
       if (mediaPlayer!=null && hasPrepared && mediaPlayer!!.isPlaying){
           mediaPlayer!!.pause()
       }
   }

    //继续播放
   fun reStart(){
          mediaPlayer!!.start();
   }
   //停止播放
    fun stop(){
         if (mediaPlayer!!.isPlaying){
             mediaPlayer!!.stop()
         }
   }


    companion object{
        fun getInstance(value:String):Fragment {
            val bundle =Bundle()
            bundle.putString("key",value)
            val fragment=KotlinFragment()
            fragment.arguments=bundle
            return fragment
        }
    }
}