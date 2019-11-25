package com.rye.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import com.rye.base.mvp.PresenterHoler
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject

/**
 *Created By RyeCatcher
 * at 2019/8/22
 */
abstract class BaseFragment: Fragment(),IView {
    protected  lateinit var TAG:String
    protected  lateinit var mContentView:ViewGroup
    protected  lateinit var presenterHelper:PresenterHoler
    /**
     * Rx lifecycle
     */
    private val lifecycleSubject=BehaviorSubject.create<ActivityEvent>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG=javaClass.simpleName

        lifecycleSubject.onNext(ActivityEvent.CREATE)

        presenterHelper=PresenterHoler(this)

    }

}