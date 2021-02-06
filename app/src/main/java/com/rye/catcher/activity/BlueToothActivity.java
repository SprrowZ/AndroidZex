package com.rye.catcher.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rye.base.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.BlueToothAdapter;
import com.rye.catcher.agocode.beans.BTBean;
import com.rye.catcher.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlueToothActivity extends BaseActivity {
    private static final String TAG = "BlueToothActivity";
    private BluetoothAdapter adapter;
    private int REQUEST_BLUE_TOOTH = 1;
    @BindView(R.id.btn_paired_devices)
    Button btn_paired_devices;
    @BindView(R.id.btn_scan)
    Button btn_scan;
    @BindView(R.id.paired)
    TextView paired;
    @BindView(R.id.none_paired)
    ListView none_paired;
    private StringBuilder bond = new StringBuilder();
    //    private StringBuilder bond_none=new StringBuilder();
    //ListView
    private ArrayList<BTBean> noneList;
    private BlueToothAdapter noneAdapter;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    bond.append("deviceName:" + device.getName() + "---deviceAddress" + device.getAddress() + "\n");
                    paired.setText(bond);
                } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.i(TAG, "BOND_NONE: " + device.getName() + "," + device.getAddress() + "/n" + noneList.toString());
                    BTBean bean = new BTBean();
                    bean.setName(device.getName());
                    bean.setAddress(device.getAddress());
                    noneList.add(bean);
                    //刷新界面
                    noneAdapter.notifyDataSetChanged();
                }
                Log.d(TAG, "device:Name--> " + device.getName());
                Log.d(TAG, "device:Address--> " + device.getAddress());
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Log.d(TAG, "Discovery Finished! ");
                btn_scan.setText("扫描完毕！");
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_blue_tooth;
    }

    @Override
    public void initEvent() {
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            ToastUtils.shortMsg("该设备支持蓝牙！");
        } else {
            Log.i(TAG, "init: 设备不支持蓝牙");
        }
        //通过系统广播发现蓝牙设备
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, intentFilter);
        //listView
        none_paired.setOnItemClickListener((parent, view, position, id) -> {

        });
        noneList = new ArrayList<>();

        noneAdapter = new BlueToothAdapter(this, noneList);
        none_paired.setAdapter(noneAdapter);
    }

    @OnClick({R.id.btn_paired_devices, R.id.btn_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_paired_devices:
                getPairedDevices();
                break;
            case R.id.btn_scan:
                scanDevices();
                break;
        }
    }

    private void getPairedDevices() {
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        for (BluetoothDevice device : devices
        ) {
            Log.d(TAG, "getPairedDevices:Name-> " + device.getName());
            Log.d(TAG, "getPairedDevices:Address-----> " + device.getAddress());
        }
    }

    private void scanDevices() {
        if (adapter.isDiscovering()) {//如果在扫描再
            adapter.cancelDiscovery();
        }
        adapter.startDiscovery();//发现完发广播！
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!adapter.isEnabled()) {//开启蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_BLUE_TOOTH);
        } else {
            ToastUtils.shortMsg("蓝牙设备已开启");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLUE_TOOTH) {
            ToastUtils.shortMsg("蓝牙设备已开启");
        }
    }
}
