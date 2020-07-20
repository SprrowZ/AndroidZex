package com.rye.catcher.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.rye.base.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ZZG on 2018/1/10.
 */

public class IntentsActivity extends BaseActivity {
    private static boolean flag = true;
    private static final String TAG = "LivePreservationActivit";
    private static final String TAG2 = "LifeCycle-B";
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.callPhone)
    Button callPhone;
    @BindView(R.id.sendSMS)
    Button sendSMS;
    @BindView(R.id.sendEmail)
    Button sendEmail;
    private static final String DATE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";


    @Override
    public int getLayoutId() {
        return R.layout.intents;
    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.callPhone, R.id.sendSMS, R.id.sendEmail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.callPhone:
                PermissionUtils.requestPermission(this, "权限申请失败，请到手机设置中设置"
                        , false,
                        data -> {
                            //打电话
                            callPhone();
                        }, 0, Permission.CALL_PHONE);
                break;
            case R.id.sendSMS:
                PermissionUtils.requestPermission(this, "权限申请失败，请到手机设置中设置",
                        false,
                        data -> {
                            sendSMS();
                        }, 0, Permission.CALL_PHONE);
                break;
            case R.id.sendEmail:
                PermissionUtils.requestPermission(this, "权限申请失败，请到手机设置中设置",
                        false,
                        data -> {
                            sendEmail();
                        }, 0, Permission.CALL_PHONE);
                break;
        }
    }

    private void callPhone() {
        Intent intent2 = new Intent(Intent.ACTION_DIAL);
        Uri datas;
        if (flag) {
            datas = Uri.parse("tel:");
        } else {
            datas = Uri.parse("tel:" + "13201383679");
        }
        flag = false;
        intent2.setData(datas);
        startActivity(intent2);
    }

    private void sendSMS() {
        Uri smsToUri;
        if (flag) {
            smsToUri = Uri.parse("smsto:" + "13201383679");
        } else {
            smsToUri = Uri.parse("smsto:");
        }
        flag = true;
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    private void sendEmail() {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:1804124963@qq.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, " ");
        data.putExtra(Intent.EXTRA_TEXT, "内容");
        startActivity(data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG, "onSaveInstanceState: ---------->");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG2, "onStart: ...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG2, "onResume: ...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG2, "onPause: ....");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG2, "onStop: ...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG2, "onDestroy: ....");
    }


}
