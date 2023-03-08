package com.rye.opengl.course_y.oes.encodec;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;

import android.view.Surface;

import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.course_y.EglHelper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLContext;


public abstract class BaseMediaEncoder {
    public Surface surface;
    public EGLContext eglContext;

    public CustomEglSurfaceView.CustomGLRender mGLRender; //
    //渲染模式
    public final static int RENDERMODE_WHEN_DIRTY = 0; //手动刷新
    public final static int RENDERMODE_CONTINUOUSLY = 1; //自动刷新，60FPS
    private int mRenderMode = RENDERMODE_CONTINUOUSLY;
    public int width;
    public int height;

    private MediaCodec videoEncodec;
    private MediaFormat videoFormat;
    private MediaCodec.BufferInfo videoBufferInfo;

    private MediaMuxer mediaMuxer;


    private EGLMediaThread mEglMediaThread;
    private VideoEncodecThread mVideoEncodecThread;

    private VideoEncodecThread.OnMediaInfoListener mediaInfoListener;




    public  BaseMediaEncoder(Context context) {
    }
    public void setRender(CustomEglSurfaceView.CustomGLRender render) {
        this.mGLRender = render;
    }
    public void setRenderMode(int renderMode) {
        if (mGLRender == null) { //必须设置Render再设置其mode
            throw new RuntimeException("must set render before mode");
        }
        this.mRenderMode = renderMode;
    }

    public void initEncodec(EGLContext eglContext,String savePath,String mimeType,int width,int height ){
        this.width = width;
        this.height = height;
        this.eglContext = eglContext;
        initMediaCodec(savePath,mimeType,width,height);
    }

    public void setMediaInfoListener(VideoEncodecThread.OnMediaInfoListener mediaInfoListener) {
        this.mediaInfoListener = mediaInfoListener;
    }

    public void startRecord() {
        if (surface==null || eglContext == null) return;
        mEglMediaThread = new EGLMediaThread(new WeakReference<>(this));
        mVideoEncodecThread = new VideoEncodecThread(new WeakReference<>(this));
        mEglMediaThread.isCreated = true;
        mEglMediaThread.isChanged = true;
        mEglMediaThread.start();
        mVideoEncodecThread.start();
    }

    public void stopRecord() {
        if (mEglMediaThread!=null && mVideoEncodecThread!=null) {
            mVideoEncodecThread.exit();
            mEglMediaThread.onDestroy();
            mVideoEncodecThread = null;
            mEglMediaThread = null;
        }
    }

    private void initMediaCodec(String savePath,String mimeType,int width,int height) {
        try{
            mediaMuxer = new MediaMuxer(savePath,MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            initVideoEncodec(mimeType,width,height);
        }catch (Exception e) {

        }
    }

    private void initVideoEncodec(String mimeType,int width,int height) {

        try {
            videoBufferInfo = new MediaCodec.BufferInfo();
            videoFormat = MediaFormat.createVideoFormat(mimeType,width,height);
            videoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            videoFormat.setInteger(MediaFormat.KEY_BIT_RATE,width*height*4);//设置码率
            videoFormat.setInteger(MediaFormat.KEY_FRAME_RATE,30);//设置码率
            videoFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL,1);//1s一个关键帧
            videoEncodec = MediaCodec.createEncoderByType(mimeType);
            videoEncodec.configure(videoFormat,null,null,MediaCodec.CONFIGURE_FLAG_ENCODE);//最后一个参数代表编码器
            surface = videoEncodec.createInputSurface();
        } catch (IOException e) {
            e.printStackTrace();
            videoEncodec = null;
            videoFormat = null;
            videoBufferInfo = null;
        }
    }




    static  class EGLMediaThread extends  Thread { /**参照CustomEGLThread*/

        private WeakReference<BaseMediaEncoder> encoderWrf;
        private EglHelper mEglHelper;
        private Object object = new Object();

        public EGLMediaThread(WeakReference<BaseMediaEncoder> weakReference){
            this.encoderWrf = weakReference;
        }

        private boolean isExit;
        private boolean isCreated = false;//判断EGLThread是否已经创建
        private boolean isChanged = false;
        private boolean isStart = false;//第一次不阻塞渲染，RenderMode

        @Override
        public void run() {
            super.run();
            isExit = false;
            mEglHelper = new EglHelper();
            mEglHelper.initEgl(encoderWrf.get().surface,encoderWrf.get().eglContext);
            while (true) {
                if (isExit) {
                    //释放资源
                    release();
                    break;
                }

                if (isStart) {//兼容逻辑，第一次swapBuffers可能不渲染
                    //根据renderMode改变渲染模式
                    if (encoderWrf.get().mRenderMode == RENDERMODE_WHEN_DIRTY) {//手动刷新，等待
                        synchronized (object) {
                            try {
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (encoderWrf.get().mRenderMode == RENDERMODE_CONTINUOUSLY) {
                        try {
                            Thread.sleep(1000 / 60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        throw new RuntimeException("renderMode is invalid value");
                    }
                }

                onCreate();
                onChange(encoderWrf.get().width, encoderWrf.get().height);
                onDraw();
                isStart = true;
            }
        }
        private void wakeUpRender() {//唤醒
            if (object != null) {
                synchronized (object) {
                    object.notifyAll();
                }
            }
        }

        public void onDestroy() {
            isExit = true;
            wakeUpRender();//可能会阻塞在object.wait处，所以需要唤醒一下，保证能正常退出
        }
        private void onCreate() {
            if (isCreated && encoderWrf.get().mGLRender != null) {
                isCreated = false;
                encoderWrf.get().mGLRender.onSurfaceCreated();
            }
        }

        private void onChange(int width, int height) {
            if (isChanged && encoderWrf.get().mGLRender != null) {
                isChanged = false;
                encoderWrf.get().mGLRender.onSurfaceChanged(width, height);
            }
        }

        private void onDraw() {
            if (encoderWrf.get().mGLRender != null && mEglHelper != null) {
                encoderWrf.get().mGLRender.onDrawFrame();
                if (!isStart) {//第一次需要手动调用一次，才会刷新//TODO 详解！！
                    encoderWrf.get().mGLRender.onDrawFrame();
                }
                mEglHelper.swapBuffers();
            }
        }

        public void release() {//释放资源
            if (mEglHelper == null) return;
            mEglHelper.destroyEgl();
            mEglHelper = null;
            object = null;
            encoderWrf = null;
        }

    }

    public static  class VideoEncodecThread extends Thread {
        private WeakReference<BaseMediaEncoder> encodeWrf;
        private boolean isExit;
        private MediaCodec videoEncodec;
        private MediaFormat videoFormat;
        private MediaCodec.BufferInfo videoBufferInfo;
        private MediaMuxer mediaMuxer;
        //视频中的音频轨道
        private int videoTrackIndex;

        private long pts;  //时间戳


        public VideoEncodecThread(WeakReference<BaseMediaEncoder> encodeWrf) {
            this.encodeWrf = encodeWrf;
            videoEncodec = encodeWrf.get().videoEncodec;
            videoFormat = encodeWrf.get().videoFormat;
            videoBufferInfo = encodeWrf.get().videoBufferInfo;
            mediaMuxer = encodeWrf.get().mediaMuxer;
        }

        @Override
        public void run() {
            super.run();
            isExit = false;
            pts = 0;
            videoTrackIndex = -1;
            videoEncodec.start();
            while (true) {
                if (isExit) {

                    videoEncodec.stop();
                    videoEncodec.release();
                    videoEncodec = null;
                    //录制完成
                    mediaMuxer.stop(); //只有合并完才会写入头信息，这一步必不可缺！
                    mediaMuxer.release();
                    mediaMuxer = null;
                }

                int outputBufferIndex = videoEncodec.dequeueOutputBuffer(videoBufferInfo,0);
                //设置编码格式
                if(outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    videoTrackIndex = mediaMuxer.addTrack(videoEncodec.getOutputFormat());
                    mediaMuxer.start();
                } else {

                    while(outputBufferIndex >=0) {
                        ByteBuffer outputBuffer =  videoEncodec.getOutputBuffers()[outputBufferIndex];
                        outputBuffer.position(videoBufferInfo.offset);
                        outputBuffer.limit(videoBufferInfo.offset + videoBufferInfo.size);
                        if (pts == 0 ) {
                            pts = videoBufferInfo.presentationTimeUs;
                        }
                        videoBufferInfo.presentationTimeUs = videoBufferInfo.presentationTimeUs - pts;

                        mediaMuxer.writeSampleData(videoTrackIndex,outputBuffer,videoBufferInfo);

                        if (encodeWrf.get().mediaInfoListener!=null) {
                            encodeWrf.get().mediaInfoListener.onMediaTime(videoBufferInfo.presentationTimeUs/1000000); //微秒
                        }

                        videoEncodec.releaseOutputBuffer(outputBufferIndex,false);
                        outputBufferIndex = videoEncodec.dequeueOutputBuffer(videoBufferInfo,0);

                    }

                }

            }

        }

        public void exit() {
            isExit = true;
        }

        public  interface  OnMediaInfoListener{
            void onMediaTime(long times);
        }
    }
}
