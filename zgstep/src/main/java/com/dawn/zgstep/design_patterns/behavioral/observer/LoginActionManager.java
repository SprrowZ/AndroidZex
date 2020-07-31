package com.dawn.zgstep.design_patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoginActionManager implements ObservableManager {
    private static List<LoginObserver> observers;

    private LoginActionManager() {
    }
    static {
        observers = new CopyOnWriteArrayList<>();
    }

    private static LoginActionManager mInstance = null;

    public static LoginActionManager getInstance() {
        if (mInstance == null) {
            synchronized (LoginActionManager.class) {
                if (mInstance == null) {
                    mInstance = new LoginActionManager();
                }
            }
        }
        return mInstance;
    }


    @Override
    public void addListener(LoginObserver observer) {
        observers.add(observer);
    }
    public synchronized void addListeners(List<LoginObserver> obs){
        for (LoginObserver observer:obs){
            observers.add(observer);
        }
    }



    @Override
    public void removeListener(LoginObserver observer) {
        observers.remove(observer);
    }

    /**
     * 登陆成功，通知所有监听者
     * @param personalInfo
     */
    @Override
    public void notifyObserver(String personalInfo) {
        for (LoginObserver observer : observers) {
            observer.loginSuccess(personalInfo);
        }
    }
}
