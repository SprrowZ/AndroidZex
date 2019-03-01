package com.rye.catcher.common.download;
import com.rye.catcher.utils.ExtraUtil.Bean;

/**
 *
 * Created by jinyunyang on 15/3/6.
 */
public interface ShortConnHandler {
    /**
     * 执行成功的回调方法
     * @param outBean
     */
    public void onSuccess(Bean outBean);

    /**
     * 执行失败的回调方法
     * @param outBean
     */
    public void onError(Bean outBean);
}
