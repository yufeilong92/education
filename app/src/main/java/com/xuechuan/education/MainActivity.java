package com.xuechuan.education;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xuechuan.education.ui.LocationActivity;
import com.xuechuan.education.ui.VideoPlayActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //定位
    public LocationClient mLocationClient = null;
    //链接
    public LocationClientOption mOption = null;
    //回调监听
    private MyLocationListener myListener = null;
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBaiDuInfom();
        getPersimmions();

    }

    private void setBaiDuInfom() {
        //声明LocationClient类
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getApplicationContext());
        }
        if (mOption == null) {
            mOption = new LocationClientOption();
        }
        if (myListener == null) {
            myListener = new MyLocationListener();
        }
        mOption.setIsNeedLocationDescribe(true);
        //当前地图定位信息
        mOption.setIsNeedAddress(true);
        mLocationClient.setLocOption(mOption);
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
    }

    public void getLocation(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }

    }
    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    public void play(View view) {
        Intent intent = new Intent(this, VideoPlayActivity.class);
        startActivity(intent);
    }
}
