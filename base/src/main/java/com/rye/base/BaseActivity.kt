package com.rye.base


import android.content.Context
import android.os.Bundle
import androidx.annotation.CheckResult
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import com.MutableMaprye.base.utils.LanguageUtil
import com.rye.base.common.LibConfig
import com.rye.base.mvp.PresenterHoler
import com.rye.base.utils.DialogUtil
import com.rye.base.utils.utils_context.FragmentLauncher
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception

/**
 * BaseActivity ----包含mvp
 */

abstract class BaseActivity:AppCompatActivity(),IView {


    protected lateinit var TAG:String

    protected lateinit var rootLayout:ViewGroup
    /**
     * 创建和缓存Presenter实例
     */
    private lateinit var presenterHelper:PresenterHoler

//    protected lateinit var progressDialog

    private lateinit var fragmentLauncher:FragmentLauncher

    private lateinit var unBinder: Any

    private val lifecycleSubject=BehaviorSubject.create<ActivityEvent>()
    /**
     * 设置app语言首选项
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageUtil.attachBaseContext(newBase!!,LibConfig.LANGUAGE ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG=javaClass.simpleName
        //rx内存泄漏解决方法
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        //Mvp，presenter工具类
        presenterHelper=PresenterHoler(this)
        initRootLayout()
    }
   /**********************************RxLifeCycle**************************************/
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        //progressDialog.dismissLoading()
    }

    override fun onStop() {
        super.onStop()
        lifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (unBinder!=null){
//            LibConfig.unBindView(unBinder)
//        }
        lifecycleSubject.onNext(ActivityEvent.DESTROY)

        presenterHelper.onDestroy()
    }

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    /**
     * 获取根布局
     */
    fun getContentView():ViewGroup{
        return rootLayout
    }
   /***************************************Http请求****************************************/

   override fun onHttpStart(action: String, isShowLoading: Boolean) {
        DialogUtil.createLoadingDialog(this)
   }

    override fun onHttpSuccess(action: String, result: Any)  {
       DialogUtil.closeDialog(this)
    }

    override fun onHttpError(action: String, errorMsg: String)  {
       DialogUtil.createErrorDialog(this)
    }

    override fun onHttpFinish(action: String)  {
        DialogUtil.closeDialog(this)
    }


    private fun initRootLayout(){
       //....do something
        setContentView(R.layout.base_activity_root)
        rootLayout = findViewById(R.id.root_layout)

      if (bindLayout()!=null&&bindLayout()!=0){
           setContentView(bindLayout())
      }else{
          throw Exception("...are you kidding me ? Ah..")
      }
      initEvent()
    }

    @LayoutRes
    abstract  fun bindLayout():Int

    abstract  fun initEvent()

    @IdRes
    fun bindFragmentLayout():Int{
        return 0
    }

    fun getFragmentLauncher():FragmentLauncher{
        if (bindFragmentLayout()!=0&&fragmentLauncher!=null){
            fragmentLauncher= FragmentLauncher(this,bindFragmentLayout())
        }
        return fragmentLauncher
    }
    //-----------------------------Presenter----------------------
    fun <T :BasePresenter<*>> getPresenter(clz:Class<T>):T?{
        return presenterHelper.getPresenter(clz)
    }
   //------------------------------Fragment------------------------
    fun startFragment(fragment: Fragment, isOpenAnim:Boolean){
       if (getFragmentLauncher()!=null){
           var config: FragmentLauncher.TransitionConfig? =null
           if (isOpenAnim){
               config=FragmentLauncher.TransitionConfig(
                       R.anim.slide_open_enter,R.anim.slide_open_exit,
                       R.anim.slide_close_enter,R.anim.slide_close_exit
               )
           }
           getFragmentLauncher().startFragment(fragment,config,isOpenAnim)
       }
   }

   fun startFragmentAndDestoryCurrent(fragment: Fragment, isOpenAnim:Boolean){
       if (getFragmentLauncher()!=null){
           var config:FragmentLauncher.TransitionConfig?=null
           if (isOpenAnim){
               config=FragmentLauncher.TransitionConfig(
                       R.anim.slide_open_enter,R.anim.slide_open_exit,
                       R.anim.slide_close_enter,R.anim.slide_close_exit
               )
           }
           getFragmentLauncher().startFragmentAndDestroyCurrent(fragment,config)
       }
   }


    /**
     * 延迟关闭
     */
    fun finishDelayed(ms:Int){
          rootLayout.postDelayed({ finish() },ms.toLong())
    }
}