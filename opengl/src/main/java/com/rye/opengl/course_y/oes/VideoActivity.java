package com.rye.opengl.course_y.oes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rye.opengl.R;
import com.rye.opengl.course_y.DisplayUtil;
import com.rye.opengl.course_y.oes.camera.OESCameraView;
import com.rye.opengl.course_y.oes.encodec.BaseMediaEncoder;
import com.rye.opengl.course_y.oes.encodec.EncoderRender;
import com.rye.opengl.course_y.oes.encodec.MediaEncoder;

public class VideoActivity extends AppCompatActivity {
    private OESCameraView mCameraView;
    private Button mBtnRecord;
    private MediaEncoder mMediaEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mCameraView = findViewById(R.id.camera_view);
        mBtnRecord = findViewById(R.id.btn_record);
    }
    public static void start(Context context){
        Intent intent = new Intent(context,VideoActivity.class);
        context.startActivity(intent);
    }

    public void record(View view) {
       if (mMediaEncoder == null) {
           mMediaEncoder = new MediaEncoder(this,mCameraView.getTextureId());
           String savePath = Environment.getExternalStorageDirectory().getAbsolutePath();
           mMediaEncoder.initEncodec(mCameraView.getEglContext(),savePath +"/live_pusher.mp4", MediaFormat.MIMETYPE_VIDEO_AVC,
                   DisplayUtil.getScreenWidth(this),DisplayUtil.getScreenHeight(this));
           mMediaEncoder.setMediaInfoListener(new BaseMediaEncoder.VideoEncodecThread.OnMediaInfoListener() {
               @Override
               public void onMediaTime(long times) {
                   Log.i("RRye","onMediaTime..times:"+times);
               }
           });
           mMediaEncoder.startRecord();
           mBtnRecord.setText("正在录制");
       }else {
           mMediaEncoder.stopRecord();
           mBtnRecord.setText("开始录制");
           mMediaEncoder = null;
       }
    }
}