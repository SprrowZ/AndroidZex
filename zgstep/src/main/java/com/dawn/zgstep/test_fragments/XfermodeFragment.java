package com.dawn.zgstep.test_fragments;

import com.dawn.zgstep.R;
import com.dawn.zgstep.others.demos.annotations.BindView;
import com.dawn.zgstep.others.views.xfermode.XferModeTwoView;
import com.rye.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class XfermodeFragment  extends BaseFragment {
   public XferModeTwoView xferModeTwoView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xfermode;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        xferModeTwoView = mRoot.findViewById(R.id.xfer);
    }

    public List<String> getFakeDatas(){
        List<String> dataList = new ArrayList<>();
        dataList.add("CLEAR");
        dataList.add("SRC");
        dataList.add("DST");
        dataList.add("SRC_OVER");
        dataList.add("DST_OVER");
        dataList.add("SRC_IN");
        dataList.add("DST_IN");
        dataList.add("SRC_OUT");
        dataList.add("DST_OUT");
        dataList.add("SRC_ATOP");
        dataList.add("DST_ATOP");
        dataList.add("XOR");
        dataList.add("DARKEN");
        dataList.add("LIGHTEN");
        dataList.add("MULTIPLY");
        dataList.add("SCREEN");
        dataList.add("ADD");
        dataList.add("OVERLAY");
        return dataList;
    }
}
