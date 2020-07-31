package com.dawn.zgstep.design_patterns.behavioral.observer;

public interface ObservableManager {

   void addListener(LoginObserver observable);
   void removeListener(LoginObserver observable);
   void notifyObserver(String personalInfo);
}
