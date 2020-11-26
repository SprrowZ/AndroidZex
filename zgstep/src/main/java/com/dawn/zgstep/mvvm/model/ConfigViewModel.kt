package com.dawn.zgstep.mvvm.model


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

/**
 * Create by rye
 * at 2020-11-18
 * @description:
 */
class ConfigViewModel : ViewModel() {
    companion object {
        fun get(activity: ViewModelStoreOwner): ConfigViewModel {
            return ViewModelProvider(activity).get(ConfigViewModel::class.java)
        }
    }

    private val configDataRepository = ConfigDataRepository()

    fun getConfigDataRepository(): ConfigDataRepository {
        return configDataRepository
    }

}

class ConfigDataRepository {
    //TODO 可以将MutableLiveData放在上面，将这些方法抛出去，看具体的业务：如果拿多，就用这种；操作多，就抛出去比较好

    private val mAppTheme: MutableLiveData<String> = MutableLiveData()

    var appTheme: String? //Normal、Grey
        set(value) {
            mAppTheme.value = value
        }
        get() {
            return mAppTheme.value
        }


    fun addAppThemeObserver(lifeOwner: LifecycleOwner, observer: Observer<String>) {
        mAppTheme.observe(lifeOwner, observer)
    }

    fun removeThemeObserver(observer: Observer<String>) {
        mAppTheme.removeObserver(observer)
    }

    fun setTheme(theme: String) {
        mAppTheme.value = theme
    }
}