package com.dawn.zgstep.others.tests.testinterfaces;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description:
 */
public interface ResponseCallback {
    void onSuccess(String msg);
    void onError(Throwable throwable);
    interface OtherDeal{
        void onCancel();
    }
}
