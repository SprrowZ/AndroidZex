package com.rye.appupdater.updater.net;

/**
 * Created By RyeCatcher
 * at 2019/9/2
 */
public interface INetCallback {
   void success(String response);

   void failed(Throwable throwable);
}
