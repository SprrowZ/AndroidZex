package com.example.myappsecond.Utils;


import android.content.Context;
import android.icu.util.Output;
import android.os.Environment;
import android.widget.Toast;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static android.renderscript.ScriptGroup.*;

/**
 * Created by ZZG on 2018/1/19.待完善
 */

public class FileHelper {
    private Context context;
    private String SDPATH;//重要,公有存储路径
    private String FILESPATH;
    private String storageDir;
    private String publicDir;
    private String EXTERNAL;//APP专属存储路径，app卸载，这部分消失
    private String PRIVATEDATA;
    private String rootDirectory;
    private String externalCache;

    public FileHelper(Context context) {
        this.context = context;
        SDPATH = Environment.getExternalStorageDirectory().getPath() + "//";//公有路径,一条横线可以，两条也可以。。
        FILESPATH = this.context.getFilesDir().getPath() + "//";//data文件夹，不可见。存放一些私有信息。【重】
         storageDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        //公用路径
          publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/";
        //app专属存储路径.......终于找到他了,app私属文件存储位置
          EXTERNAL =context.getExternalFilesDir(null)+"/";
        //用户数据目录，不可见
          PRIVATEDATA= Environment.getDataDirectory()+"/";
        //Android根目录
         rootDirectory= Environment.getRootDirectory()+"/";
        //应用外部缓存目录........专属缓存路径
          externalCache= context.getExternalCacheDir()+"/";
    }
    public FileHelper(){
        SDPATH = Environment.getExternalStorageDirectory().getPath() + "//";
    }

    //在SD卡上创建文件,公有目录
    public File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }
    //在指定目录下创建文件
    public File createSDFileToDir(String dirName,String fileName,String content) throws IOException {
        File sonFile;
        if (!new File(dirName).exists()){
           File file=new File(SDPATH+dirName);
           file.mkdirs();
             sonFile=new File(SDPATH+dirName+"/"+fileName);
           sonFile.createNewFile();

        }else {
            sonFile = new File(SDPATH + dirName + "/"+fileName);
            if (sonFile.exists()){
                sonFile.delete();
                sonFile.createNewFile();
            }
        }
        //向txt文件写入内容【重】
        FileOutputStream outputStream = new FileOutputStream(sonFile);//打开文件输出流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));//写入到缓存流
        writer.write(content);//从从缓存流写入
        writer.close();//关闭流
        return sonFile;
    }
    //app专属目录，路径为：
    public File createExternalFile(String name) throws IOException{
        File file=new File(EXTERNAL+name);
        file.createNewFile();
        return file;
    }
    //删除SD卡上的文件
    public boolean delSDFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        file.delete();
        return true;
    }
    //删除专属目录的文件
    public boolean delExternalFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        file.delete();
        return true;
    }

    // 在SD卡上创建目录
    public File createSDDir(String dirName,Context mContext) {
        File dir = new File(SDPATH + dirName);
        if (dir.exists()){
            Toast.makeText(mContext,"目录已经存在",Toast.LENGTH_SHORT).show();
            return dir;
        }
        dir.mkdir();
        if (dir.exists()){
            Toast.makeText(mContext,"目录创建成功",Toast.LENGTH_SHORT).show();
        }
        return dir;
    }

    // 删除SD卡上的目录
    public boolean delSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        return delDir(dir);
    }

    // 修改SD卡上的文件或目录名
    public boolean renameSDFile(String oldfileName, String newFileName) {
        File oleFile = new File(SDPATH + oldfileName);
        File newFile = new File(SDPATH + newFileName);
        return oleFile.renameTo(newFile);
    }

    // 拷贝SD卡上的单个文件
    public boolean copySDFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(SDPATH + srcFileName);
        File destFile = new File(SDPATH + destFileName);
        return copyFileTo(srcFile, destFile);
    }

    //拷贝SD卡上指定目录的所有文件
    public boolean copySDFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(SDPATH + srcDirName);
        File destDir = new File(SDPATH + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

    // 移动SD卡上的单个文件
    public boolean moveSDFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(SDPATH + srcFileName);
        File destFile = new File(SDPATH + destFileName);
        return moveFileTo(srcFile, destFile);
    }

    // 移动SD卡上的指定目录的所有文件
    public boolean moveSDFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(SDPATH + srcDirName);
        File destDir = new File(SDPATH + destDirName);
        return moveFilesTo(srcDir, destDir);
    }


    //将文件写入sd卡。如:writeSDFile("test.txt");
//    public Output writeSDFile(String fileName) throws IOException {
//        File file = new File(SDPATH + fileName);
//        FileOutputStream fos = new FileOutputStream(file);
//        return new Output(fos);
//    }

    // 在原有文件上继续写文件。如:appendSDFile("test.txt");
//    public Output appendSDFile(String fileName) throws IOException {
//        File file = new File(SDPATH + fileName);
//        FileOutputStream fos = new FileOutputStream(file, true);
//        return new Output(fos);
//    }
//
//    // 从SD卡读取文件。如:readSDFile("test.txt");
//    public Input readSDFile(String fileName) throws IOException {
//        File file = new File(SDPATH + fileName);
//        FileInputStream fis = new FileInputStream(file);
//        return new Input(fis);
//    }


    // 建立私有文件
    public File creatDataFile(String fileName) throws IOException {
        File file = new File(FILESPATH + fileName);
        file.createNewFile();
        return file;
    }

    // 建立私有目录

    public File creatDataDir(String dirName) {
        File dir = new File(FILESPATH + dirName);
        dir.mkdir();
        return dir;
    }

    // 删除私有文件
    public boolean delDataFile(String fileName) {
        File file = new File(FILESPATH + fileName);
        return delFile(file);
    }

    // 删除私有目录
    public boolean delDataDir(String dirName) {
        File file = new File(FILESPATH + dirName);
        return delDir(file);
    }

    // 更改私有文件名
    public boolean renameDataFile(String oldName, String newName) {
        File oldFile = new File(FILESPATH + oldName);
        File newFile = new File(FILESPATH + newName);
        return oldFile.renameTo(newFile);
    }

    // 在私有目录下进行文件复制

    public boolean copyDataFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(FILESPATH + srcFileName);
        File destFile = new File(FILESPATH + destFileName);
        return copyFileTo(srcFile, destFile);
    }

    // 复制私有目录里指定目录的所有文件
    public boolean copyDataFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(FILESPATH + srcDirName);
        File destDir = new File(FILESPATH + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

    // 移动私有目录下的单个文件
    public boolean moveDataFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(FILESPATH + srcFileName);
        File destFile = new File(FILESPATH + destFileName);
        return moveFileTo(srcFile, destFile);
    }

    // 移动私有目录下的指定目录下的所有文件
    public boolean moveDataFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(FILESPATH + srcDirName);
        File destDir = new File(FILESPATH + destDirName);
        return moveFilesTo(srcDir, destDir);
    }

//    //将文件写入应用私有的files目录。如:writeFile("test.txt");
//    public Output wirteFile(String fileName) throws IOException {
//        OutputStream os = context.openFileOutput(fileName,
//                Context.MODE_WORLD_WRITEABLE);
//        return new Output(os);
//    }
//
//    // 在原有文件上继续写文件。如:appendFile("test.txt");
//    public Output appendFile(String fileName) throws IOException {
//        OutputStream os = context.openFileOutput(fileName, Context.MODE_APPEND);
//        return new Output(os);
//    }
//
//    //从应用的私有目录files读取文件。如:readFile("test.txt");
//
//    public Input readFile(String fileName) throws IOException {
//        InputStream is = context.openFileInput(fileName);
//        return new Input(is);
//    }






    // 删除一个文件
    public boolean delFile(File file) {
        if (file.isDirectory())
            return false;
        return file.delete();
    }

    // 删除一个目录（可以是非空目录）

    public boolean delDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                delDir(file);// 递归
            }
        }
        dir.delete();
        return true;
    }

    //拷贝一个文件,srcFile源文件，destFile目标文件
    public boolean copyFileTo(File srcFile, File destFile) throws IOException {
        if (srcFile.isDirectory() || destFile.isDirectory())
            return false;// 判断是否是文件
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = fis.read(buf)) != -1) {
            fos.write(buf, 0, readLen);
        }
        fos.flush();
        fos.close();
        fis.close();
        return true;
    }

    // 拷贝目录下的所有文件到指定目录
    public boolean copyFilesTo(File srcDir, File destDir) throws IOException {
        if (!srcDir.isDirectory() || !destDir.isDirectory())
            return false;// 判断是否是目录
        if (!destDir.exists())
            return false;// 判断目标目录是否存在
        File[] srcFiles = srcDir.listFiles();
        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isFile()) {
                // 获得目标文件
                File destFile = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFileTo(srcFiles[i], destFile);
            } else if (srcFiles[i].isDirectory()) {
                File theDestDir = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFilesTo(srcFiles[i], theDestDir);
            }
        }
        return true;
    }

    // 移动一个文件
    public boolean moveFileTo(File srcFile, File destFile) throws IOException {
        boolean iscopy = copyFileTo(srcFile, destFile);
        if (!iscopy)
            return false;
        delFile(srcFile);
        return true;
    }

    // 移动目录下的所有文件到指定目录
    public boolean moveFilesTo(File srcDir, File destDir) throws IOException {
        if (!srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }
        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            if (srcDirFiles[i].isFile()) {
                File oneDestFile = new File(destDir.getPath() + "//"
                        + srcDirFiles[i].getName());
                moveFileTo(srcDirFiles[i], oneDestFile);
                delFile(srcDirFiles[i]);
            } else if (srcDirFiles[i].isDirectory()) {
                File oneDestFile = new File(destDir.getPath() + "//"
                        + srcDirFiles[i].getName());
                moveFilesTo(srcDirFiles[i], oneDestFile);
                delDir(srcDirFiles[i]);
            }

        }
        return true;
    }
}