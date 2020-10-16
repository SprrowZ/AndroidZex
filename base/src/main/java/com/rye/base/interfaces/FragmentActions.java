package com.rye.base.interfaces;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

public interface FragmentActions {
    void addFragment(Fragment fg);

    void removeFragment(Fragment fg);

    void hideFragment();

    void showFragment();

    void replaceFragment(Fragment fg);
    void replaceFragment(@IdRes int containerId,Fragment fg);

    Fragment getCurrentFragment();
}
