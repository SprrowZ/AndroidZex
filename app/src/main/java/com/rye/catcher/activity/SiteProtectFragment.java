package com.rye.catcher.activity;


import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SiteProtectFragment extends Fragment {

    private View view;
    private Unbinder unbinder;
    @BindView(R.id.number)
    TextView number;

    private int textNumber=0;
    public SiteProtectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view=inflater.inflate(R.layout.fragment_site_protect, container, false);
        unbinder=ButterKnife.bind(this,view);
        //现场保护，re-creation
        setRetainInstance(true);
        return  view;
    }
    @OnClick(R.id.add_btn)
    public void add(){
      textNumber++;
      number.setText(String.valueOf(textNumber));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        number.setText(String.valueOf(textNumber));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
