package com.dawn.zgstep.ui.ctm.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dawn.zgstep.R;

/**
 * Create by  [Rye]
 * <p>
 * at 2025/1/7 15:05
 */
public class MVVMDialogFragment extends DialogFragment {
    private View mRoot;
    private FrameLayout mZoom;
    private TextView mSubView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.gusture_dialog_fragment,container,false);
        mZoom = mRoot.findViewById(R.id.zoom);
        mSubView = mRoot.findViewById(R.id.sub_view);
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSubView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RRye","onClick");
            }
        });
    }
}
