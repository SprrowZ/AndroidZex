package com.example.myappsecond.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/7/5.
 */
public class PermissionUtil {
    private Activity context;
    private int requestId;
    private List<String> permissionList;

    public PermissionUtil(Activity context){
        this.context = context;
        permissionList = new ArrayList<>();
    }


    /**
     * 查询当前权限是否禁止
     * */
    public void shouldShowPermissionRationale(int requestId,String ... permission){
        this.requestId = requestId;
        for (int i = 0; i < permission.length; i++) {
            boolean checkPermisssion  = ContextCompat.checkSelfPermission(context, permission[i])!= PackageManager.PERMISSION_GRANTED;
            //表示权限被拒绝，说明此权限作用
            if(checkPermisssion){
                permissionList.add(permission [i]);
            }
        }
        if (null != permissionList && !permissionList.isEmpty()){
            //permissionList集合里面有未授权的权限
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            requestPermissions(context,permissions);
        }
        //权限已经授权
        else {
            Toast.makeText(context,"权限已经授权",Toast.LENGTH_LONG).show();

        }

    }

    //查询当前申请的所有权限是否都被允许
    public void shouldShowPermissionRationale(String ... permission){
        permissionList.clear();
        for (int i = 0; i <permission.length ; i++) {
            boolean checkPermisssion  = ContextCompat.checkSelfPermission(context, permission[i])!= PackageManager.PERMISSION_GRANTED;
            //表示权限被拒绝，说明此权限作用
            if (checkPermisssion){
                permissionList.add(permission [i]);
            }
        }
        if (null != permissionList && !permissionList.isEmpty()){
            Toast.makeText(context,"当前权限未完全授权",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,"权限已授权",Toast.LENGTH_LONG).show();
        }
    }




    //权限申请
    public void requestPermissions(Activity activity,String[] permission){
        ActivityCompat.requestPermissions(activity, permission, requestId);
    }
    //权限回调
    //Activity需实现OnRequestPermissionsResultCallback通过 PermissionsUtil.onRequestPermissionsResult引用回调
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        if (requestCode == requestId){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults [i] != PackageManager.PERMISSION_GRANTED){
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[i]);
                    if (showRequestPermission) {
                        //权限重新申请
                        Toast.makeText(context,"权限被禁止了",Toast.LENGTH_LONG).show();
                        //requestPermissions(context,permissions);
                        break;
                    } else {
                        //权限被禁止，且不再询问
                        Toast.makeText(context,"权限被禁止，且不再提示",Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                else {
                    //当某个权限或者所有权限被允许
                    shouldShowPermissionRationale(permissions);

                    break;

                }

            }

        }
    }

//**********************************使用方法
//    public class MainActivity extends AppCompatActivity implements View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback {
//        private PermissionsUtil permissionsUtil;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//            permissionsUtil = new PermissionsUtil(this);
//            findViewById(R.id.permission).setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            switch (v.getId()){
//                case R.id.permission:
//                    permissionsUtil.shouldShowPermissionRationale(111,permissions);
//                    break;
//            }
//        }
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            permissionsUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
//        }
//
//    }

}
