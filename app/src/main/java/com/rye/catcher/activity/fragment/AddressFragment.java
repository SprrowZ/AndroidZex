package com.rye.catcher.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rye.catcher.R;

/**
 * Created by zzg on 2017/10/10.
 */

public class AddressFragment extends BaseFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.tab03;
    }

    @Override
    protected void initData() {
        setBarTitle("天行九歌");
    }


}
