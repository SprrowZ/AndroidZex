package com.rye.catcher.common.download;

/**
 * Created by jinyunyang on 15/3/17.
 */
public interface HttpPostProgressHandler extends ShortConnHandler {
    /**
     *
     * @param percent 已传输数据百分比
     */
    public void onProgress(long percent);
}
