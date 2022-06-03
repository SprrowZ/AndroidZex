package com.rye.opengl.course_y;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLContext;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/5/29 10:55 上午
 * 自定义GLSurfaceView
 */
public abstract class CustomEglSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Surface mSurface;  //画布
    private EGLContext mEglContext; //上下文环境
    private CustomEGLThread mEglThread; //绘制线程

    private CustomGLRender mGLRender; //
    //渲染模式
    public final static int RENDERMODE_WHEN_DIRTY = 0; //手动刷新
    public final static int RENDERMODE_CONTINUOUSLY = 1; //自动刷新，60FPS


    private int mRenderMode = RENDERMODE_CONTINUOUSLY;

    public CustomEglSurfaceView(Context context) {
        super(context, null);
    }

    public CustomEglSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomEglSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);//important！这个别忘了
    }

    public void setSurfaceAndEglContext(Surface surface, EGLContext eglContext) {
        if (surface != null && eglContext != null) {
            this.mSurface = surface;
            this.mEglContext = eglContext;
        }
    }

    public void setGLRender(CustomGLRender mGLRender) {
        this.mGLRender = mGLRender;
    }

    public void setRenderMode(int renderMode) {
        if (mGLRender == null) { //必须设置Render再设置其mode
            throw new RuntimeException("must set render before mode");
        }
        this.mRenderMode = renderMode;
    }

    public EGLContext getEglContext() { //获取上下文，共享关键所在
        if (mEglThread != null) {
            return mEglThread.getEglContext();
        }
        return null;
    }

    public void wakeUpRender() {
        if (mEglThread == null) return;
        mEglThread.wakeUpRender();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (mSurface == null) {
            mSurface = holder.getSurface();
        }
        mEglThread = new CustomEGLThread(new WeakReference<>(this));
        mEglThread.isCreated = true;
        mEglThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        mEglThread.isChanged = true;
        mEglThread.width = width;
        mEglThread.height = height;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (mEglThread != null) {
            mEglThread.onDestroy();
            mEglThread = null;
            mSurface = null;
            mEglContext = null;
        }
    }


    public interface CustomGLRender {
        void onSurfaceCreated();

        void onSurfaceChanged(int width, int height);

        void onDrawFrame();
    }

    static class CustomEGLThread extends Thread {//仿照GLSurfaceView#GLThread
        private EglHelper mEglHelper;
        private WeakReference<CustomEglSurfaceView> mCustomSurfaceWeakReference;
        private boolean isExit;
        private boolean isCreated = false;//判断EGLThread是否已经创建
        private boolean isChanged = false;
        private boolean isStart = false;//第一次不阻塞渲染，RenderMode
        private int width;
        private int height;
        private Object object = new Object();

        public CustomEGLThread(WeakReference<CustomEglSurfaceView> customEglSurfaceViewWeakReference) {
            this.mCustomSurfaceWeakReference = customEglSurfaceViewWeakReference;
        }

        @Override
        public void run() {
            super.run();
            mEglHelper = new EglHelper();
            mEglHelper.initEgl(mCustomSurfaceWeakReference.get().mSurface, mCustomSurfaceWeakReference.get().mEglContext);
            while (true) {
                if (isExit) {
                    //释放资源
                    release();
                    break;
                }
                if (isStart) {//兼容逻辑，第一次swapBuffers可能不渲染
                    //根据renderMode改变渲染模式
                    if (mCustomSurfaceWeakReference.get().mRenderMode == RENDERMODE_WHEN_DIRTY) {//手动刷新，等待
                        synchronized (object) {
                            try {
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (mCustomSurfaceWeakReference.get().mRenderMode == RENDERMODE_CONTINUOUSLY) {
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
                onChange(width, height);
                onDraw();
                isStart = true;
            }

        }

        private void onCreate() {
            if (isCreated && mCustomSurfaceWeakReference.get().mGLRender != null) {
                isCreated = false;
                mCustomSurfaceWeakReference.get().mGLRender.onSurfaceCreated();
            }
        }

        private void onChange(int width, int height) {
            if (isChanged && mCustomSurfaceWeakReference.get().mGLRender != null) {
                isChanged = false;
                mCustomSurfaceWeakReference.get().mGLRender.onSurfaceChanged(width, height);
            }
        }

        private void onDraw() {
            if (mCustomSurfaceWeakReference.get().mGLRender != null) {
                mCustomSurfaceWeakReference.get().mGLRender.onDrawFrame();
                if (!isStart) {//第一次需要手动调用一次，才会刷新//TODO 详解！！
                    mCustomSurfaceWeakReference.get().mGLRender.onDrawFrame();
                }
                if (mEglHelper != null) {
                    mEglHelper.swapBuffers();
                }
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


        public void release() {//释放资源
            if (mEglHelper == null) return;
            mEglHelper.destroyEgl();
            mEglHelper = null;
            object = null;
            mCustomSurfaceWeakReference = null;
        }

        public EGLContext getEglContext() {//返回共享上下文！
            if (mEglHelper == null) return null;
            return mEglHelper.getEglContext();
        }
    }
}
