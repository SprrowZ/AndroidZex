package com.rye.catcher.activity.adapter.recycler.recybean;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * Created at 2018/9/16.
 * 重写equals和hashCode
 * @author Zzg
 */
public class DataModel {
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public int mType;
    @Nullable
    public int mPortraitUrl;

    public String mContent;
    @DrawableRes
    public int mBgUrl;
    @Nullable
    public String other;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DataModel dataModel = (DataModel) obj;
        return Objects.equals(mBgUrl, dataModel.mBgUrl) &&
                Objects.equals(mContent, dataModel.mContent);
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + mBgUrl;
        result = 31 * result + (mContent == null ? 0 : mContent.hashCode());
        return result;
    }
}
