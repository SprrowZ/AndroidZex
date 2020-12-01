package com.dawn.zgstep.others.tests.testinterfaces;


/**
 * Create by rye
 * at 2020-08-01
 *
 * @description:
 */
public class Response implements ResponseCallback , ResponseCallback.OtherDeal {
    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCancel() {

    }
}
