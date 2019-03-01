package com.rye.catcher.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebView;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/25 0025.
 */
public class ActivityManager {
    private List<Activity> activityList = new LinkedList<Activity>();
    private WeakReference<Activity> sCurrentActivityWeakRef;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    // 单例模式中获取唯一的MyApplication实例
    public static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit2() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
    //获取activity
    public <T extends Activity> T getActivity(Class<T> cls){
        Activity act ;
        for (int i = 0, size = activityList.size(); i < size; i++){
            act =  activityList.get(i);
            if(act.getClass() == cls){
                return (T)act;
            }
        }
        return null;
    }
    //出栈
    public  void popActivity(){
        activityList.get(activityList.size()-1).finish();
        activityList.remove(activityList.size()-1);
    }
    //从容器中删除Activity
    public void removeActivity(Class cls){
        Activity act ;
        for (int i = 0, size = activityList.size(); i < size; i++){
            act =  activityList.get(i);
            if(act.getClass() == cls){
                 activityList.remove(i);
            }
        }
    }

    public void removeActivity(Activity act) {
        if (activityList != null && activityList.contains(act)) {
            activityList.remove(act);
            act.finish();
            act=null;
        }
    }


    public void finishActivity(Class<?> cls){
        Activity classActivity = null;
        for (Activity activity : activityList) {
            if(activity.getClass().equals(cls)){
                classActivity = activity;
                break;
            }
        }
        removeActivity(classActivity);
    }
    //获取activity
    public <T extends Activity> T getLastActivity(){
            return  (T)activityList.get(activityList.size()-1);

    }
    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    /**
     * 如有错误请用getNewCurrentActivity替代
     */
    public Activity getCurrentActivity() {
        if (activityList == null || activityList.size() < 1) {
            return null;
        } else {
            for (int i = activityList.size() - 1; i >= 0; i--) {
                Activity activity = activityList.get(i);
                return  activity;
            }
            return null;
        }
    }

//    public WebView getWebViewInActivity(Activity mActivity) {
//        if (mActivity instanceof MBOMultiWebViewActivity) {
//            MBOMultiWebViewActivity activity = (MBOMultiWebViewActivity) mActivity;
//            return activity.getWebview();
//        }
////        else if (mActivity instanceof MBOWebViewActivity) {
////            MBOWebViewActivity activity = (MBOWebViewActivity) mActivity;
////            return activity.getWebview();
////        }
//        else if (mActivity instanceof MBOPhoneHomePageActivity) {
//            MBOPhoneHomePageActivity activity = (MBOPhoneHomePageActivity) mActivity;
//            Fragment fragment = activity.getCurrentFragment();
//            if (fragment instanceof MBOMultiWebViewFragment) {
//                MBOMultiWebViewFragment mFragment = (MBOMultiWebViewFragment) fragment;
//                return mFragment.getWebview();
//            }
//        }
//        return null;
//    }

//    public WebView getCurrentWebView() {
//        if (getCurrentActivity() instanceof MBOMultiWebViewActivity) {
//            MBOMultiWebViewActivity activity = (MBOMultiWebViewActivity)getCurrentActivity();
//            return activity.getWebview();
//        }
//        else if (getCurrentActivity() instanceof MBOPhoneHomePageActivity) {
//            MBOPhoneHomePageActivity activity = (MBOPhoneHomePageActivity)getCurrentActivity();
//            Fragment fragment = activity.getCurrentFragment();
//            if (fragment instanceof MBOMultiWebViewFragment) {
//                MBOMultiWebViewFragment mFragment = (MBOMultiWebViewFragment) fragment;
//                return mFragment.getWebview();
//            }
//        }
//        return null;
//    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void resetToHomePage() {
        for(int i = activityList.size() - 1; i > 0; i--){
            Activity activity = activityList.remove(i);
            if(activity.isDestroyed() || activity.isFinishing()){
                continue;
            }
            activity.finish();
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void backToPage(int index) {
        if(index >= 0){
            return;
        }
        int activitiesCount = activityList.size();
        int subCount = activitiesCount + index;
        if(subCount <= 0){
            subCount = 1;
        }
        for(int i = activitiesCount - 1; i >= subCount; i--){
            Activity activity = activityList.remove(i);
            if(activity.isDestroyed() || activity.isFinishing()){
                continue;
            }
            activity.finish();
        }
    }

    public Activity getNewCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }
}
