/*
 * Copyright 2018 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rye.catcher.utils.permission;

import android.app.AlertDialog;
import android.content.Context;

import com.rye.catcher.R;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.io.File;

/**
 * Created by YanZhenjie on 2018/4/29.
 */
public class InstallRationale implements Rationale<File> {

    @Override
    public void showRationale(Context context, File data, final RequestExecutor executor) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("安装没有权限")
                .setPositiveButton("设置", (dialog, which) -> executor.execute())
                .setNegativeButton(R.string.cancel, (dialog, which) -> executor.cancel())
                .show();
    }
}