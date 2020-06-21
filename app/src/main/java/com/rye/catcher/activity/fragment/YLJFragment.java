package com.rye.catcher.activity.fragment;

import android.util.Log;
import android.widget.TextView;

import com.rye.catcher.BaseOldFragment;
import com.rye.catcher.R;
import com.rye.catcher.base.sdks.beans.TangBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zzg on 2017/10/10.
 */

public class YLJFragment extends BaseOldFragment {
    private static final String TAG = "YLJFragment";


    //头像本地地址
//    private static final String pLocal = SDHelper.getImageFolder() + "portrait.png";
//    @BindView(R.id.portrait)
//    DistortionViews portrait;
//    @BindView(R.id.iv1)
//    ImageView iv1;
@BindView(R.id.poetry_title)
    TextView poetryTitle;
@BindView(R.id.poetry_content)
   TextView poepryContent;
@BindView(R.id.poetry_author)
   TextView poepryAuthor;
    @Override
    protected int getLayoutResId() {
        return R.layout.tab01;
    }

    @Override
    protected void initData() {
        setBarTitle("秦时明月");
        //     initEvent();
        EventBus.getDefault().register(this);
    }


//    private void initEvent() {
//
//        ImageUtils.getIntance().displayImage(iv1, Constant.PORTRAIT_URL);
//        //点击头像进大图
//        portrait.setOnItemClickListener(data -> {
//            Intent intent = new Intent(getActivity(), ImageActivity.class);
//            ArrayList<String> imgList = new ArrayList<>();
//            imgList.add(pLocal);
//            intent.putStringArrayListExtra(ImageActivity.IMAGE_LIST, imgList);
//            startActivity(intent);
//        });
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void setPoetry(TangBean bean) {
         TangBean.ResultBean result=bean.getResult();
         String title=result.getTitle();
         String author=result.getAuthors();
         String content=result.getContent();
         poetryTitle.setText(title);
         poepryAuthor.setText(author);
         //内容
         String[] contentSplit= content.split("\\|");
         StringBuilder sb=new StringBuilder();
         for (int i=0;i<contentSplit.length;i++){
             Log.i(TAG, "setPoetry: "+contentSplit[i]);
             if (i!=contentSplit.length-1){
                 sb.append(contentSplit[i]+"\n\n");
             }else {
                 sb.append(contentSplit[i]+"\n");
             }

         }
         poepryContent.setText(sb);
    }

}
