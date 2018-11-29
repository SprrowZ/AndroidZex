package com.rye.catcher.activity.fragment;


import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.utils.PackageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class PackageManagerFragment extends BaseFragment {
    private  View view;
    private Unbinder unbinder;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.content)
    TextView content;
    public PackageManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view=inflater.inflate(R.layout.fragment_package_manager, container, false);
        unbinder=ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick({R.id.tv1,R.id.tv2})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv1:
                PackageInfo info=PackageUtils.instance().getPackageInfo(getContext());
                String  packageName=info.packageName;
                content.setText(packageName);
                break;
            case R.id.tv2:

                break;
        }
    }
    @Override
    public void onDestroyView() {
        unbinder.unbind();//别忘了销毁
        super.onDestroyView();
    }
}
