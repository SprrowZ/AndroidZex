package com.dawn.zgstep.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dawn.zgstep.R;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/4/10 14:40
 */
public class BannerViewFragment extends Fragment {
   private View mRoot;
    public static BannerViewFragment newInstance() {
        BannerViewFragment fragment = new BannerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mRoot = inflater.inflate(R.layout.fragment_banner_view,container,false);

        return mRoot;
    }
}
