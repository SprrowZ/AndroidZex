package com.rye.base.utils.utils_context;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.rye.base.utils.GeneralUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By RyeCatcher
 * at 2019/8/19
 * Fragment 管理工具类
 */
public class FragmentLauncher {
    private FragmentActivity activity;
    /**
     * 绑定的Layout
     */
    private int fragmentLayout;
    /**
     * 当前Fragment
     */
    private Fragment currentFragment;
    /**
     * Fragment缓存--set，不加重复Fragment
     */
    private Map<String,Fragment> fragmentSet;

    public Fragment getCurrentFragment(){
        return currentFragment;
    }

    public FragmentLauncher(FragmentActivity activity, @IdRes int fragmentLayout){
        this.activity=activity;
        this.fragmentLayout=fragmentLayout;
        fragmentSet= new HashMap<>();
    }

    /**
     * 切换Fragment，且隐藏之前的Fragment
     * @param clz
     * @param <T>
     * @return
     */
    public <T extends Fragment> T showFragmentAndHideCurrent(Class<T> clz){
        return changeFragment(clz,null,null,false);
    }

    /**
     * 切换Fragment，且销毁之前的Fragment
     * @param clz
     * @param <T>
     * @return
     */
    public <T extends Fragment> T showFragmentAndDestroyCurrent(Class<T> clz){
        return changeFragment(clz,null,null,true);
    }

    /**
     * Fragment创建的时候加个监听
     * @param clz
     * @param listener
     * @param <T>
     * @return
     */
    public <T extends Fragment> T showFragmentAndHideCurrent(Class<T> clz, OnFragmentCreateListener<T> listener) {
        return changeFragment(clz, listener, null, false);
    }

    public <T extends Fragment> T showFragmentAndDestoryCurrent(Class<T> clz, OnFragmentCreateListener<T> listener) {
        return changeFragment(clz, listener, null, true);
    }

    /**
     * Fragment---加Arguments；
     * @param clz
     * @param arguments
     * @param <T>
     * @return
     */
    public <T extends Fragment> T showFragmentAndHideCurrent(Class<T> clz,Bundle arguments){
        return changeFragment(clz,null,arguments,false);
    }

    public <T extends Fragment> T showFragmentAndDestroyCurrent(Class<T> clz,Bundle arguments){
        return changeFragment(clz,null,arguments,true);
    }

    /**
     * 实际切换Fragment的方法，没有Fragment则先创建，并隐藏当前的Fragment
     * @param clz
     * @param listener
     * @param arguments
     * @param isDestroy
     * @param <T>
     * @return
     */
    private <T extends Fragment> T changeFragment(Class<T> clz, OnFragmentCreateListener<T> listener,
                                                  Bundle arguments,boolean isDestroy){
        T fragment=null;
        String className=clz.getName();
        if (fragmentSet.containsKey(className)){
            fragment=(T) fragmentSet.get(className);
            if (fragment==currentFragment){
                fragment.setArguments(arguments);
                return fragment;
            }
        }
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        if (currentFragment!=null){
            if (isDestroy){
                transaction.remove(currentFragment);
                fragmentSet.remove(currentFragment.getClass().getName());
            }else{
                transaction.hide(currentFragment);
            }
        }
        //如果Fragment还未被创建，则新建后添加到FragmentManager
        if (fragment==null){
            fragment=(T) T.instantiate(activity,className);
            fragmentSet.put(className,fragment);
            transaction.add(fragmentLayout,fragment);
        }else{
            //如果Fragment已被创建，取出缓存显示
            transaction.show(fragment);
        }
        //更新当前页
        currentFragment=fragment;
        if (listener!=null){
            listener.onFragmentCreated(fragment);
        }
        fragment.setArguments(arguments);
        transaction.commit();
        return fragment;
    }

    /***
     * 打开一个Fragment
     * @param fragment
     * @param config
     * @param isAddToBackStack
     * @return
     */
    public int startFragment(Fragment fragment,TransitionConfig config,boolean isAddToBackStack){
        if (fragmentLayout!=0){
            String tagName=fragment.getClass().getSimpleName();
            FragmentTransaction transaction=activity.getSupportFragmentManager()
                    .beginTransaction();
             if (config!=null){
                 transaction.setCustomAnimations(config.enter,config.exit,config.popenter,config.popout);
             }
             transaction.replace(fragmentLayout,fragment,tagName);
             if (currentFragment!=null && fragmentSet.containsKey(currentFragment)){
                 fragmentSet.remove(currentFragment.getClass().getName());
                 currentFragment=null;
             }
             if (isAddToBackStack){
                 transaction.addToBackStack(tagName);
             }
             return transaction.commit();
        }
        return 0;
    }

    /**
     * 需要分析分析
     * 打开一个Fragment，且替换BackStack栈顶Fragment
     */
    public int startFragmentAndDestroyCurrent(final Fragment fragment, TransitionConfig config) {
        int index = startFragment(fragment, config, false);
        GeneralUtils.findAndModifyOpInBackStackRecord(activity.getSupportFragmentManager(), -1, new GeneralUtils.OPHandler() {
            @Override
            public boolean handle(Object op) {
                Field cmdField = null;
                try {
                    cmdField = op.getClass().getDeclaredField("cmd");
                    cmdField.setAccessible(true);
                    int cmd = (int) cmdField.get(op);
                    if (cmd == 1) {
                        Field oldFragmentField = op.getClass().getDeclaredField("fragment");
                        oldFragmentField.setAccessible(true);
                        Object fragmentObj = oldFragmentField.get(op);
                        oldFragmentField.set(op, fragment);
                        Field backStackNestField = Fragment.class.getDeclaredField("mBackStackNesting");
                        backStackNestField.setAccessible(true);
                        int oldFragmentBackStackNest = (int) backStackNestField.get(fragmentObj);
                        backStackNestField.set(fragment, oldFragmentBackStackNest);
                        backStackNestField.set(fragmentObj, --oldFragmentBackStackNest);
                        return true;
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        return index;
    }


    /**
     * 静态内部类，Fragment切换动画int
     */
    public static final class TransitionConfig {
        public final int enter;
        public final int exit;
        public final int popenter;
        public final int popout;

        public TransitionConfig(int enter, int popout) {
            this(enter, 0, 0, popout);
        }

        public TransitionConfig(int enter, int exit, int popenter, int popout) {
            this.enter = enter;
            this.exit = exit;
            this.popenter = popenter;
            this.popout = popout;
        }
    }
    public interface OnFragmentCreateListener<T extends Fragment> {
        void onFragmentCreated(T t);
    }
}
