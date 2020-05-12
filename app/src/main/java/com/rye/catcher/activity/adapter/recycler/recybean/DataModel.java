package com.rye.catcher.activity.adapter.recycler.recybean;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * Created at 2018/9/16.
 *
 * @author Zzg
 */
public class DataModel {
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public int mType;
    @Nullable
    public  int mPortraitUrl;

    public String mContent;
    @DrawableRes
    public int mBgUrl;
    @Nullable
    public String other;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof DataModel){
            if (((DataModel)obj).mBgUrl == this.mBgUrl && ((DataModel)obj).mContent == this.mContent ) {
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
