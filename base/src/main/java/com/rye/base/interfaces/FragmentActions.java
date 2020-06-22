package com.rye.base.interfaces;

import androidx.fragment.app.Fragment;

public interface FragmentActions {
    void addFragment(Fragment fg);

    void removeFragment(Fragment fg);

    void hideFragment();

    void showFragment();

    void replaceFragment(Fragment fg);

    Fragment getCurrentFragment();
}
