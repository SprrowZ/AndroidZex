package com.catcher.zzsdk.okhttp.others;


public interface DisposeDataListener<T> {
	/**
	 * 请求成功回调事件处理
	 */
	  void onSuccess(T t);

	/**
	 * 请求失败回调事件处理
	 */
	  void onFailure(OkHttpException e);

}
