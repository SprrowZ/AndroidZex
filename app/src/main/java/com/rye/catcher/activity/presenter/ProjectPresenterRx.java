package com.rye.catcher.activity.presenter;

import android.content.Context;
import android.content.res.AssetManager;

import com.rye.base.rxmvp.RxBasePresenter;
import com.rye.base.utils.FileUtils;
import com.rye.base.utils.GsonUtils;
import com.rye.catcher.beans.ProjectBean;
import com.rye.catcher.utils.SDHelper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created By RyeCatcher
 * at 2019/9/3
 */
public class ProjectPresenterRx extends RxBasePresenter {


    public ProjectPresenterRx(@NotNull Class service) {
        super(service);
    }
    public ProjectPresenterRx(){

    }
    /**
     * 获取数据列表
     * @return
     */
    public List<ProjectBean> getDataList(Context context){
        AssetManager manager=context.getAssets();
        String desPath= SDHelper.getAppExternal();
        FileUtils.copyAssetToSDCard(manager,"plist.json",desPath+"plist.json");
        File jsonFile=new File(desPath+"plist.json");
        try {
            Reader reader=new FileReader(jsonFile);
          return  GsonUtils.toList(reader,ProjectBean[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setOnItemClickListener(){

    }
    interface OnItemClick{
        void click();
    }




}
