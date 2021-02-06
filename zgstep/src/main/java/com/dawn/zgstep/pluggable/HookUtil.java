package com.dawn.zgstep.pluggable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dawn.zgstep.design_patterns.behavioral.mediator.FakeLoginMediator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description:
 */
public class HookUtil {
    private static final String PROXY_METHOD_ONE_NAME = "startActivity";
    private static final String TARGET_INTENT = "targetIntent";


    public static void testHookAms(Activity activity) {
        try {
            Class<?> activityClass = Class.forName("android.app.Activity");
            Field tagField = activityClass.getDeclaredField("TAG");
            tagField.setAccessible(true);
            Object activityTag = tagField.get(activity);
            Log.e("Rye", "activityTag:" + activityTag);
        } catch (Exception e) {

        }
    }

    public static void hookAms() {
        //-------------------目的：获取系统的IActivityManager对象  private IActivityManager mInstance----------------------------
        //动态代理替换的是IActivityManager
        try {
            //获取Singleton对象
            Class<?> clazz = Class.forName("android.app.ActivityManager");
            Field iActivityManagerSingletonField = clazz.getDeclaredField("IActivityManagerSingleton");
            iActivityManagerSingletonField.setAccessible(true);
            //因为IActivityManagerSingleton,所以可以很方便的传null(本来是要传ActivityManager的一个对象的);
            // 这也是最好拿静态或者单例对象的好处
            Object singleton = iActivityManagerSingletonField.get(null);

            //拿Singleton里的mInstance对象 --->IActivityManager对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //这里就拿到了Singleton的mInstance对象
            Object mInstance = mInstanceField.get(singleton);
         //----------------------------------------------------------------------

            Class<?> IActivityManagerClass = Class.forName("android.app.IActivityManager");
            //这里动态动态代理的作用就是用来替换intent(用proxyIntent替换掉pluginIntent,躲过AMS检查)
            Object mInstanceProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{IActivityManagerClass}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            //修改Intent
                            if (PROXY_METHOD_ONE_NAME.equals(method.getName())) {
                                int index = 0; //注释①
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        index = i;
                                        break;
                                    }
                                }
                                //获取插件的intent
                                Intent intent = (Intent) args[index];
                                //改成启动代理intent
                                Intent intentProxy = new Intent();
                                intentProxy.setClassName("com.dawn.zgstep.ui.activity",
                                        "com.dawn.zgstep.ui.activity.ProxyActivity");
                                //代理intent保存真实intent，方便出来的时候替换
                                intentProxy.putExtra(TARGET_INTENT, intent);
                                //替换
                                args[index] = intentProxy;
                            }


                            //传入第一个参数：系统的IActivityManager对象:就是Singleton里的mInstance对象
                            Object result = method.invoke(mInstance, args);
                            return result;
                        }
                    });

            //注释②
            //mInstance = mInstanceProxy 斯阔一！！
            mInstanceField.set(singleton, mInstanceProxy);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 注释① ActivityManager.getService()
     *                 .startActivity(whoThread, who.getBasePackageName(), intent,
     *                         intent.resolveTypeIfNeeded(who.getContentResolver()),
     *                         token, target != null ? target.mEmbeddedID : null,
     *                         requestCode, 0, null, options)
     * 需要获取到IActivityManager的intent参数，这样才能替换掉这个intent
     */

    /**注释②
     *  用代理对象替换系统的IActivityManger对象 通过field来，看看这个field是哪来的，给替换掉
     *  mInstanceProxy-->替换系统的IActivityManager对象 -->通过反射拿到系统的这个要被替换掉的对象
     *
     *  IActivityManager获取路径 ---> ActivityManager.getService() -->IActivityManagerSingleton.get()
     *  --> Singleton<T>.get()
     *  所以 反射就要反向获取对象，要调用get方法，就要获取Singleton对象，要拿到Singleton对象，就要拿到IActivityManagerSingleton.get()
     *  这个对象，也就是ActivityManger 里的 IActivityManagerSingleton
     */


    @SuppressLint("PrivateApi")
    public static void hookHandler(){

        //注释②
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            Object activityThread = activityThreadField.get(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(activityThread);

            Class<?> handlerClass = Class.forName("android.os.Handler");
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);


            //注释①
            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 100: //8.0 ActivityThread 里的 LAUNCH_ACTIVITY
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                //启动代理的intent
                                Intent intentProxy = (Intent) intentField.get(msg.obj);
                                //启动插件的intent
                                Intent intent = intentProxy.getParcelableExtra(TARGET_INTENT);
                                if (intent != null) { //替换成插件的intent
                                    intentField.set(msg.obj, intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                    return false;
                }
            };
            //重新赋值
            mCallbackField.set(mH,callback);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * 注释①
     * 找intent，然后替换掉他
     *  ActivityThread.scheduleLaunchActivity  -=-》ActivityClientRecord（ActivityThread静态内部类）中的intent --> msg.obj（只要拿到msg就成功了~）
     *
     *
     *就可以往回找ActivityClientRecord的对象 (会走到handleMessage里的：final ActivityClientRecord r = (ActivityClientRecord) msg.obj;)
     * Handler dispatchMessage  mCallback为null
     * 这样我们就可以创建一个Callback，替换系统里Handler里的mCallback
     *       {
     *             if (mCallback != null) {
     *                 if (mCallback.handleMessage(msg)) {
     *                     return;
     *                 }
     *             }
     *             handleMessage(msg);
     *         }
     * 然后修改msg，就会修改Handler里的msg，那么ActivityClientRecord的msg也就会修改
     */

    /**
     * 注释②
     * 用我们创建的Callback对象替换系统的Callback
     * 也就是替换ActivityThread里的mH，Handler对象;因为mH是非静态的，所以这就需要我们拿到ActivityThread对象。
     * 在ActivityThread里其静态变量  private static volatile ActivityThread sCurrentActivityThread;
     * 静态的就可以直接获取了
     */

}
