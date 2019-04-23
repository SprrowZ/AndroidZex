package com.rye.catcher.beans.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created at 2019/4/23.
 *
 * @author Zzg
 * @function: DeviceInfo Bean
 */
public class DeviceBean extends BaseObservable{
    @Bindable
    private String title;
    @Bindable
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
         notifyPropertyChanged(BR.title);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
}
