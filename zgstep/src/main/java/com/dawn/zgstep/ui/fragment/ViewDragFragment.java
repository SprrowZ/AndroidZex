package com.dawn.zgstep.ui.fragment;


import androidx.fragment.app.Fragment;

import com.dawn.zgstep.R;
import com.rye.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDragFragment extends BaseFragment {

    public static ViewDragFragment newInstance() {
        return new ViewDragFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_drag;
    }
}
