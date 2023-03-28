package com.dawn.zgstep.ui.fragment.assist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dawn.zgstep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoopItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoopItemFragment extends Fragment {

    private ImageView  mImage;
    private View mRoot;
    private int mSourceId;
    public LoopItemFragment() {
        // Required empty public constructor
    }

    public static LoopItemFragment newInstance(int mSourceId) {
        LoopItemFragment fragment = new LoopItemFragment();
        fragment.setData(mSourceId);
        return fragment;
    }

    public void setData(int resourceId) {
        this.mSourceId = resourceId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_loop_item, container, false);
        mImage = mRoot.findViewById(R.id.img_content);
        return  mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        mImage.setImageResource(mSourceId);
    }
}