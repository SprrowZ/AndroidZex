package com.example.myappsecond.activity;

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
import com.example.myappsecond.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZG on 2018/1/19.
 */

public class FileStorageTest extends BaseActivity {
    @BindView(R.id.thistitle)
    TextView thistitle;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.someting_new)
    Button sometingNew;
    @BindView(R.id.animation)
    Button animation;
    @BindView(R.id.drawable)
    Button drawable;
    @BindView(R.id.linear4)
    LinearLayout linear4;
    @BindView(R.id.custom)
    Button custom;
    @BindView(R.id.project)
    Button project;
    @BindView(R.id.linear5)
    LinearLayout linear5;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.display)
    LinearLayout display;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.linear8)
    LinearLayout linear8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_storage_test);
        ButterKnife.bind(this);

    }



    private void show() {
        linear8.setVisibility(View.VISIBLE);
    }

    private void writeFileDir() {
        String privatePath = this.getExternalFilesDir(null) + "/";
        //先创建个目录，然后往里塞文件
        String innerDir = privatePath + "ZZG_PRIVATE_FILE";
        File fileDir = new File(innerDir);
        if (fileDir.exists()) {
            Log.i("zzg", "writeFileDir: 文件夹已经存在");
        } else {
            fileDir.mkdirs();
        }
        File file = new File(innerDir + "ZZG_LOG" + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //然后往txt文件里面写东西呗,怎么写呢？
        if (file.canRead() && file.canWrite()) {
            //BufferedWriter
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
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
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
            //再写个方法，查看file里面的内容
        }

    }

    private void createDirFile() {
        FileUtils.createNewFile("天玄九变","虎啸山林"+".txt","青林");
        Toast.makeText(this, "ojbk", Toast.LENGTH_SHORT).show();
    }



    private void createFile() {
        FileUtils.createNewFile("aSprrowZ","Zzg.jpg");
        Toast.makeText(this, "创建成功！", Toast.LENGTH_LONG).show();
    }

    private void showPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //SD卡已装入
            //内嵌sd卡路径（也可能是外部SD卡）
            String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
            //公用路径
            String publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath().toString();
            //app专属存储路径.......终于找到他了,app私属文件存储位置
            String externalFilesDir = getExternalFilesDir(null).toString();
            //用户数据目录
            String privatedata = Environment.getDataDirectory().toString();
            //Android根目录
            String rootDirectory = Environment.getRootDirectory().toString();
            //应用外部缓存目录........专属缓存路径
            String externalCache = getExternalCacheDir().toString();
            //私有文件地址
            String filePath = this.getFilesDir().getPath() + "//";
            text1.setText("SD卡路径(一般为内嵌)：" + "\n" + storageDir + "\n"
                    + "公用路径：" + "\n" + publicDir + "\n" + "app专属存储路径：" + "\n" + externalFilesDir
                    + "\n" + "用户数据目录" + "\n" + privatedata + "\n" + "Android根目录：" + "\n" + rootDirectory
                    + "\n" + "应用外部缓存目录" + "\n" + externalCache + "\n" + "私有文件路径" + "\n" + filePath);
        }

    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.someting_new, R.id.animation, R.id.drawable, R.id.custom, R.id.project, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                showPath();
                break;
            case R.id.btn2:
                createFile();
                break;
            case R.id.btn3:

                break;
            case R.id.btn4:
                show();
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                createDirFile();
                break;
            case R.id.btn7:
                break;
            case R.id.someting_new:
                break;
            case R.id.animation:
                break;
            case R.id.drawable:
                break;
            case R.id.custom:
                break;
            case R.id.project:
                break;
            case R.id.submit:
                writeFileDir();
                break;
        }
    }
}
