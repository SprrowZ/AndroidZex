package com.rye.catcher.utils.permission;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.rye.catcher.R;
import com.rye.catcher.utils.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PermissionUtils {


    public static void requestPermission(Context context, String errormsg, boolean isenForce,
                                         Action<List<String>> actionGrant, int actionType, String... permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(isenForce ? new EnforceRuntimeRationale() : new RuntimeRationale())
                .onGranted(actionGrant)
                .onDenied(permissions12 -> {
                    ToastUtils.shortMsg(permissionNames + "权限申请失败,请重试");
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions12)) {
                        showSettingDialog(context, isenForce, permissions12, errormsg, actionType);
                    } else {
                        if (actionType != 0) {
                            EventBus.getDefault().post(new PermissionEvent(actionType));
                        } else {
                            EventBus.getDefault().post(new PermissionEvent(PermissionEvent.rejectAction));
                        }

                    }
                })
                .start();
    }


    public static void showSettingDialog(Context context, boolean isenForce, final
    List<String> permissions, String message, int actionType) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage(message + "\n" + "\n" + permissionNames);

        if (isenForce) {
            builder.setPositiveButton("设置", (dialog, which) -> setPermission(context));
        } else {
            builder.setPositiveButton("设置", (dialog, which) -> setPermission(context))
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        ToastUtils.shortMsg(permissionNames + "权限申请失败,请重试");
                        if (actionType != 0) {
                            EventBus.getDefault().post(new PermissionEvent(actionType));
                        } else {
                            EventBus.getDefault().post(new PermissionEvent(PermissionEvent.rejectAction));
                        }
                    });
        }

        builder.show();

    }


    public static void setPermission(Context context) {
        AndPermission.with(context)
                .runtime()
                .setting()
                .onComeback(() -> EventBus.getDefault().post(new PermissionEvent(PermissionEvent.backSettingAction)))
                .start();
    }
}
