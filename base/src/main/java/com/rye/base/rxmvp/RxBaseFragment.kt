package com.rye.base.rxmvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject

/**
 *Created By RyeCatcher
 * at 2019/8/22
 */
abstract class RxBaseFragment: Fragment(), RxIView {
    protected  lateinit var TAG:String
    protected  lateinit var mContentView:ViewGroup
    protected  lateinit var presenterHelper: PresenterHoler
    /**
     * Rx lifecycle
     */
    private val lifecycleSubject=BehaviorSubject.create<ActivityEvent>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG=javaClass.simpleName

        lifecycleSubject.onNext(ActivityEvent.CREATE)

        presenterHelper= PresenterHoler(this)

    }

}