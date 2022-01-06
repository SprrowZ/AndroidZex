package com.rye.catcher.activity;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.RequiresApi;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.rye.base.BaseActivity;

import com.rye.catcher.R;
import com.rye.base.utils.PermissionsUtil;
import com.rye.catcher.utils.ToastUtils;
import com.rye.catcher.utils.permission.Permissions;

import java.lang.ref.WeakReference;


public class BLEActivity extends BaseActivity {
    private static final String TAG = "BLEActivity";
    private static final int BLE_DISCONNECTED = 0;
    private static final int BLE_CONNECTING = 1;
    private static final int BLE_CONNECTED = 2;

    private BluetoothAdapter mBluetoothAdapter;
    private Button startScan;
    //
    private Button connect;
    private TextView tv_ble;

    //蓝牙已开启扫描
    private boolean mIsScanStart = false;
    //扫描器
    private BluetoothLeScanner mLeScanner;
    //扫描的相关设置
    private ScanSettings mScanSettings;
    //记录连接地址
    private String mBleAddress = "";
    //连接蓝牙设备所用
    private BluetoothGatt mGatt;
    //维护连接状态
    private boolean isConnected = false;
    private final zHandler handler = new zHandler(this);
    //连接状态回调
    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.i(TAG, "onConnectionStateChange: " + status);
            handler.sendEmptyMessage(newState);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_ble;
    }

    private void requestPermissions() {
        PermissionsUtil.INSTANCE.checkPermissions(this, new PermissionsUtil.IPermissionsResult() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void passPermissons() {
                initEvent();
            }

            @Override
            public void forbitPermissons() {
                Log.i(TAG, "forbitPermissons: 申请权限失败...");
            }
        }, Permissions.BLUE_TOOTH);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void initEvent() {
        requestPermissions();

        startScan = findViewById(R.id.startScan);
        connect = findViewById(R.id.connect);
        tv_ble = findViewById(R.id.tv_ble);

        final BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();
        if (mBluetoothAdapter != null) {
            ToastUtils.shortMsg("该设备支持蓝牙！");
        } else {
            ToastUtils.shortMsg("该设备不支持蓝牙！");
        }
        //检查手机是否支持BLE功能
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.shortMsg("该设备不支持BLE功能!");
        }
        //获取扫描器
        mLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        //扫描设置
        mScanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(3000).build();


        startScan.setOnClickListener(v -> {
            if (!mIsScanStart) {
                startScan.setText("停止扫描");
                mIsScanStart = true;
                scan(true);
            } else {
                startScan.setText("开始扫描");
                mIsScanStart = false;
                scan(false);
            }
        });

        connect.setOnClickListener(v -> {
            if (!isConnected) {
                connect();
            } else {
                disconnect();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scan(boolean enable) {
        ScanCallback scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BluetoothDevice device = result.getDevice();
                Log.d(TAG, "onScanResult: name=" + device.getName() + ",address=" +
                        device.getAddress());
                tv_ble.setText(device.getName() + "--" + device.getAddress());
                mBleAddress = device.getAddress();
            }
        };
        if (enable) {
            mLeScanner.startScan(scanCallback);
        } else {
            mLeScanner.stopScan(scanCallback);
        }
    }

    /**
     * 连接蓝牙设备
     *
     * @param
     * @return 是否连接成功
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean connect() {
        //根据地址获取远端蓝牙设备
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mBleAddress);
        Log.i(TAG, "connect: deviceName" + device.getName() + "deviceAddress:" + device.getAddress());
        mGatt = device.connectGatt(this, false, gattCallback);
        if (mGatt != null) {
            isConnected = true;
            return true;
        } else {
            return false;
        }

    }

    /**
     * 断开设备
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void disconnect() {
        if (mGatt != null) {
            isConnected = false;
            mGatt.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            //申请打开蓝牙功能
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }
    }


    /**
     * 弱引用，防止内存泄露
     */
    private static class zHandler extends Handler {
        WeakReference<BLEActivity> mActivity;

        public zHandler(BLEActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BLEActivity zActivity = mActivity.get();
            switch (msg.what) {
                case BluetoothProfile.STATE_CONNECTED:
                    zActivity.isConnected = true;
                    zActivity.connect.setText("断开...");
                    ToastUtils.shortMsg("匹配成功！");
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    zActivity.isConnected = false;
                    zActivity.connect.setText("连接...");
                    ToastUtils.shortMsg("匹配失败！");
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
