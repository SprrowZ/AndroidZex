package com.rye.catcher.utils;

import android.util.Log;

import com.rye.base.utils.DateUtils;
import com.rye.base.utils.FileUtils;
import com.rye.base.utils.SDHelper;
import com.rye.catcher.RyeCatcherApp;
import com.rye.catcher.project.catcher.DelayHandleUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created By RyeCatcher
 * at 2019/9/4
 * FileUtils 已经迁移至base模块，这里只保留一个写日志的方法
 */
public class FileUtil {
   private static final String TAG="userLog";
    /**
     * 写日志专用方法
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        FileUtils.makeDirs(filePath);
        List<File> files = FileUtils.getFilesSortByTime(filePath, false);
        if (files.size() == 20) {
            files.get(0).delete();
        }
        try {
            file = new File(filePath +File.separator+ fileName);
            if (!file.exists()) {
                Log.i(TAG, "makeFilePath: "+file.exists());
                file.createNewFile();
            }
        } catch (Exception e) {
            Log.i(TAG, "makeFilePath: "+e.toString());
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 写入日志
     */
    private static   StringBuffer stringBuffer=new StringBuffer();
    public static void writeUserLog(String content) {
        stringBuffer.append(DateUtils.getCurrentTime(DateUtils.FORMAT_DATETIME_MS) + "  统一认证号：" +
                "  版本号：" + RyeCatcherApp.getInstance().getVersion() +
                " 手机信息：" + DeviceUtils.getDeviceName() + DeviceUtils.getReleaseVersion() + content + "\r\n");
        DelayHandleUtil.setDelay(DelayHandleUtil.DELAY_ACTION_UPDATE_MSG_STATUS, 0L, 2000, new DelayHandleUtil.HandleListener() {
            @Override
            public void ReachTheTime() {
                write();
            }
        });
    }
    private  static synchronized void write(){
        if (stringBuffer.length()==0){
            return;
        }
        File file = makeFilePath(SDHelper.getAppExternal() + "logs",
                DateUtils.getCurrentTime(DateUtils.FORMAT_DATE1) + ".log");
        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            OutputStreamWriter out = new OutputStreamWriter(outputStream);
            out.write(stringBuffer.toString());
            out.close();
            stringBuffer.setLength(0);
        } catch (Exception e) {

        }
    }


    /**
     * 保存图片到本地,如果已经存在相同名字的图片，则覆盖
     * @param remotePath 网络地址
     */
    public static void saveImage(String remotePath,String imgName){
        File  file=null;
        //先判断本地有没有，没有再下载
        file=new File(SDHelper.getImageFolder()+imgName);
        InputStream inputStream=FileUtils.downloadFileI(remotePath);
        if (inputStream==null){
            Log.i("saveImage", "saveImage: error:"+remotePath);
             writeUserLog("图片下载失败："+remotePath);
            return;
        }
        FileUtils.writeFile(file,inputStream);
    }
}
