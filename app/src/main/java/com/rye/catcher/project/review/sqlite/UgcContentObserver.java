package com.rye.catcher.project.review.sqlite;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * Create by rye
 * at 2020-09-29
 *
 * @description:
 */
public class UgcContentObserver extends ContentObserver {
    private Handler mHandler;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public UgcContentObserver(Handler handler) {
        super(handler);
        this.mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        mHandler.obtainMessage(TSqliteFragment.UGC_EVENT_ID,"O。O").sendToTarget();
    }
}
