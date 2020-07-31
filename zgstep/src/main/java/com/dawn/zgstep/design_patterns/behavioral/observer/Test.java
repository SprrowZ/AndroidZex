package com.dawn.zgstep.design_patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test {
    public static void main(String[] args) {
        PersonalPage personalPage = new PersonalPage();
        MainPage mainPage = new MainPage();
        List<LoginObserver> observers = new CopyOnWriteArrayList<>();
        observers.add(personalPage);
        observers.add(mainPage);
        LoginActionManager.getInstance()
                .addListeners(observers);
        LoginActionManager.getInstance()
                .notifyObserver("用户：JackChen");
    }
}
