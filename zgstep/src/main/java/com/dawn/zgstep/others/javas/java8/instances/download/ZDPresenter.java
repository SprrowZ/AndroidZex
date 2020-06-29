package com.dawn.zgstep.others.javas.java8.instances.download;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 具体逻辑实现，调用函数式接口
 */
public class ZDPresenter {
    static {
        System.out.println("static 代码块...");
    }

    /**
     * 只获取进度，不success Or error
     *
     * @param url
     * @param callback
     */
    public static void download(final String url, final ZDCallback callback) {
        //....进行网络请求
        System.out.println("开始进行网络请求..." + url);
//        File file=new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/flow.txt");
        new Timer().schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                callback.progress(count);

                int error = new Random().nextInt(100);

                if (error == 50) {
                    System.out.println("下载失败...");
                    cancel();
                }
                if (count == 100) {
                    System.out.println("下载完成...");
                    cancel();
                }
            }
        }, 0, 100);
    }

}
