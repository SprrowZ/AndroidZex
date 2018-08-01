package com.example.myappsecond.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.utils.FileHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by ZZG on 2018/1/19.
 */

public class FileStorageTest extends BaseActivity implements View.OnClickListener {
    public Button btn1;
    public Button btn2;
    public Button btn3;
    public Button btn4;
    public Button btn5;
    public Button btn6;
    public Button btn7;
    public TextView text1;
    public TextView text2;
    public TextView text3;
    public TextView text4;
   public Button submit;
   public EditText editText;
   public LinearLayout linear8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_storage_test);
        init();
    }

    @SuppressLint("WrongViewCast")
    private void init() {
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        text1=findViewById(R.id.text1);

        editText=findViewById(R.id.editText);
        submit=findViewById(R.id.submit);
        linear8=findViewById(R.id.linear8);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                showPath();
                break;
            case R.id.btn2:
                createFile();
                break;
            case R.id.btn3:
                createDir();
                break;
            case R.id.btn6:
                createDirFile();
                break;
            case R.id.btn4:
                show();
                break;
            case R.id.submit:
                writeFileDir();
                break;
        }
    }

    private void show() {
        linear8.setVisibility(View.VISIBLE);
    }

    private void writeFileDir() {
        String  privatePath=this.getExternalFilesDir(null)+"/";
        //先创建个目录，然后往里塞文件
        String innerDir=privatePath+"ZZG_PRIVATE_FILE";
        File fileDir=new File(innerDir);
        if (fileDir.exists()){
            Log.i("zzg", "writeFileDir: 文件夹已经存在");
        }else{
            fileDir.mkdirs();
        }
       File file=new File(innerDir+"ZZG_LOG"+".txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //然后往txt文件里面写东西呗,怎么写呢？
        if (file.canRead()&&file.canWrite()){
            //BufferedWriter
            BufferedWriter bw=null;
            try {
                bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
                bw.write(editText.getText().toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                try {
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            Toast.makeText(this,"写入成功",Toast.LENGTH_SHORT).show();
            //再写个方法，查看file里面的内容
        }

    }

    private void createDirFile() {
        FileHelper fileHelper=new FileHelper(this);
        try {
            fileHelper.createSDFileToDir("fuckYou?","sure!"+".txt","随便加数据");
            Toast.makeText(this,"ojbk",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDir() {
        FileHelper fileHelper=new FileHelper(this);
        fileHelper.createSDDir("SecondDir",this);
    }

    private void createFile() {
        FileHelper fileHelper=new FileHelper(this);
        try {
          File file=  fileHelper.creatSDFile("amay"+".jpg");
          Toast.makeText(this,file.getAbsolutePath().toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPath() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //SD卡已装入
            //内嵌sd卡路径（也可能是外部SD卡）
            String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
            //公用路径
            String publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath().toString();
           //app专属存储路径.......终于找到他了,app私属文件存储位置
            String externalFilesDir = getExternalFilesDir(null).toString();
            //用户数据目录
            String privatedata= Environment.getDataDirectory().toString();
           //Android根目录
            String rootDirectory= Environment.getRootDirectory().toString();
           //应用外部缓存目录........专属缓存路径
            String externalCache= getExternalCacheDir().toString();
           //私有文件地址
            String filePath = this.getFilesDir().getPath() + "//";
            text1.setText("SD卡路径(一般为内嵌)："+"\n"+storageDir+"\n"
            +"公用路径："+"\n"+publicDir+"\n"+"app专属存储路径："+"\n"+externalFilesDir
            +"\n"+"用户数据目录"+"\n"+privatedata+"\n"+"Android根目录："+"\n"+rootDirectory
            +"\n"+"应用外部缓存目录"+"\n"+externalCache+"\n"+"私有文件路径"+"\n"+filePath);
        }

    }
}
