package com.rye.catcher.utils;

/**
 * Created at 2019/1/29.
 *
 * @author Zzg
 * @function:
 */
public class DownLoadUtil {

    public static DownLoadUtil getInstance(){
        return SingInstance.INSTANCE;
    }
    private static class SingInstance{
        public static  DownLoadUtil INSTANCE=new DownLoadUtil();
    }

}
