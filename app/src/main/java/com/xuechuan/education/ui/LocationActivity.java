package com.xuechuan.education.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.xuechuan.education.R;
import com.xuechuan.education.application.EdcationApplication;
import com.xuechuan.education.service.LocationService;
/**
 * @Title:  LocationActivity
 * @Package com.xuechuan.education.ui
 * @Description: 地图定位界面
 * @author: L-BackPacker
 * @date:   2018/4/4 9:14
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/4
 */
public class LocationActivity extends AppCompatActivity {
    private LocationService locationService;
    private TextView LocationResult;
    private Button startLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du_location);
        LocationResult = (TextView) findViewById(R.id.textView1);
        LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        startLocation = (Button) findViewById(R.id.addfence);
    }
    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        final String s = str;
        try {
            if (LocationResult != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LocationResult.post(new Runnable() {
                            @Override
                            public void run() {
                                LocationResult.setText(s);
                            }
                        });

                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((EdcationApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        startLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startLocation.getText().toString().equals(getString(R.string.startlocation))) {
                    locationService.start();// 定位SDK
                    // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
                    startLocation.setText(getString(R.string.stoplocation));
                } else {
                    locationService.stop();
                    startLocation.setText(getString(R.string.startlocation));
                }
            }
        });
    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\n定位类型 : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\n对应的定位类型说明 description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\n纬度 : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\n经度 : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\n半径 : ");// 半径
                sb.append(location.getRadius());
                sb.append("\n国家码 : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\n国家名称 : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\n城市编码 : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\n城市 : ");// 城市
                sb.append(location.getCity());
                sb.append("\n区 : ");// 区
                sb.append(location.getDistrict());
                sb.append("\n街道 : ");// 街道
                sb.append(location.getStreet());
                sb.append("\n地址信息 : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\n内外判断结: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\n方向(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\n位置语义化信息: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPOI信息: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\n速度 : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\n卫星数目 : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nhe海拔高度 单位：米ight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps 质量 : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\n单 : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\n运营商信 : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                logMsg(sb.toString());
            }
        }

    };
}
