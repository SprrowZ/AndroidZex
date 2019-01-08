package com.rye.catcher.project.dao;

import android.content.Context;
import android.util.Log;

import com.rye.catcher.BuildConfig;
import com.rye.catcher.utils.DeviceUtils;
import com.rye.catcher.utils.EchatAppUtil;

import java.io.File;

/**
 * 安卓服务环境的上下文
 * Created by jinyunyang on 15/3/5.
 */
public class ServiceContext {

    private static final String HTTP = "http://";

	private static final String HTTPS = "https://";

    private static String socketServer = null;

    private static String paraseUrlServer = null;

    private static String _uuid = null;

    /**
     * 是否显示ip：不可更改，更改无效
     */
    public static boolean isDebug = true;
    private static String serverAddress = BuildConfig.ip;
    static {
        if (serverAddress != null) {
            if (!serverAddress.startsWith(HTTP) && !serverAddress.startsWith(HTTPS)) {
                serverAddress = HTTP + serverAddress;
            }
        }
        isDebug = true;
    }

    /**
     *
     * @param url 服务器地址
     */
    public static void setParaseUrlServer(String url) {
        paraseUrlServer = url;
    }

    /**
     *
     * @return 众信Http Url解析 服务器URL，url以"/"结尾。格式为：http://172.16.0.183:8093/api/html/trans
     */
    public static String getParaseUrlServer() {
        return paraseUrlServer;
    }

    /**
     * @return 众信消息服务器地址。格式为：cochat.cn:81
     */
    public static String getSocketServer() {
        return socketServer;
    }

    /**
     *
     * @param addr 服务器地址
     */
    public static void setSocketServer(String addr) {
         socketServer = addr;
    }

    /**
     *
     * @return 众信Http服务器URL，url以"/"结尾。格式为：http://cochat.cn:81/
     */
    public static String getHttpServer() {
        if (!serverAddress.endsWith("/")) {
            return serverAddress + "/";
        } else {
            return serverAddress;
        }
    }

    /**
     * 设置众信服务器地址
     *
     * @param server 服务器地址
     */
//    public static void setServer(String server) {
////        if (isDebug) { //IP地址可变，才允许执行以下逻辑
//            if (server != null && !server.isEmpty()) {
//                if (server.endsWith("/")) { // 去掉结尾的斜线
//                    server = server.substring(0, server.length() - 1);
//                }
//
//                if (!server.startsWith(HTTP) && !server.startsWith(HTTPS)) {
//                    server = HTTP + server;
//                }
//                serverAddress = server;
//                HxHelper.getInstance().getModel().saveServer(getServerAddress());
//                //初始化存储路径//// TODO: 2017/4/26 由于改了服务器地址，初始化过的这个path也要重新设置;
//                StorageHelper.getInstance().init();
//            }
////        }
//    }

    /**
     *
     * @return 服务器地址
     */
    public static String getServerAddress() {
            return serverAddress;
    }


    /**
     *
     * @return 设备的UUID
     */
    public static String getUUID () {
        return DeviceUtils.getUUID(EchatAppUtil.getAppContext());
    }

    /**
     *
     * @param uuid 设备的UUID
     */
    public static void setUUID (String uuid) {
        _uuid = DeviceUtils.getUUID(EchatAppUtil.getAppContext());
    }


    /**
     * 初始化服务器地址和UUID
     * @param context android的Context对象
     */
//    public static void initBaseInfo(Context context) {
//        // 先从数据库中读取UUID，如果没有再重新生成。
//        String uuid = null;
//
//        uuid = DeviceUtils.getUUID(context);
//        // 设置UUID
//        setUUID(uuid);
//
//        // 初始化服务器地址
//        if(HxHelper.getInstance().getModel().getServer()== null) {
//            HxHelper.getInstance().getModel().saveServer(getServerAddress());
//        } else {
//            setServer(HxHelper.getInstance().getModel().getServer());
//        }
//    }

    /**
     * 远程销毁数据接口
     */
//    public static void remoteDestroy() {
//        // 先记录目录地址，避免用户退出登录后获取不到数据。
//        final File imgCachePath = StorageHelper.getInstance().getImagePath();
//
//        // 1，清理数据库
//        DbOpenHelper helper = DbOpenHelper.getInstance();
//        if (helper != null) {
//            helper.destory();
//        }
//        // 3，清理浏览器的LocalStore TODO 暂时没有集成浏览器
//
//        // 4，清理文件系统。例如图片目录、文件目录
//        // 文件列表
//        File[] files = imgCachePath.listFiles();
//        for (File file : files) {
//            boolean hasDeleted = file.delete();
//            Log.d("ServiceContext", "**Delete File:" + file.getPath() + ", " + hasDeleted);
//        }
//        // 2，退出登录
//        EchatAppUtil.gotoMain(null, false, "", "",true);
//
//    }

}
