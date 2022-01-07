package com.dawn.zgstep.player.ui.codec;


import android.app.Activity;
import android.content.Intent;
import com.rye.base.BaseFragmentActivity;

/**
 * 硬编码-> 直播&视频
 */
public class MediaCodecActivity extends BaseFragmentActivity {
    private static final String TAG = "MediaCodecActivity";

    public final static int REQUEST_CODE = 0X13;

    public final static int TYPE_LIVE = 0X01;
    public final static int TYPE_VIDEO = 0X02;

    private LiveCodecFragment mLiveCodecFragment;
    private VideoCodecFragment mVideoCodecFragment;



    public static void jump(Activity activity, int type) {
        Intent intent = new Intent(activity, MediaCodecActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void initEvent() {
          mLiveCodecFragment = LiveCodecFragment.create();
          mVideoCodecFragment = VideoCodecFragment.create();
          replaceFragment(mLiveCodecFragment);
    }

    @Override
    public void initWidget() {
        super.initWidget();

    }

}
