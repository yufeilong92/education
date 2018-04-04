package com.xuechuan.education.application;


import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;


import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xuechuan.education.service.LocationService;

/**
 * 主Application，所有百度定位SDK的接口说明请参考线上文档：http://developer.baidu.com/map/loc_refer/index.html
 *
 * 百度定位SDK官方网站：http://developer.baidu.com/map/index.php?title=android-locsdk
 * 
 * 直接拷贝com.baidu.location.service包到自己的工程下，简单配置即可获取定位结果，也可以根据demo内容自行封装
 */

public class EdcationApplication extends Application {
	public LocationService locationService;
    public Vibrator mVibrator;
    /** 加密秘钥 */
    private String aeskey = "VXtlHmwfS2oYm0CZ";
    /** 加密向量 */
    private String iv = "2u9gDPKdX6GyQJKU";
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());

        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        mContext = getApplicationContext();
        initPolyvCilent();
    }

    /**
     * 初始化数据
     */
    private void initPolyvCilent() {
        PolyvSDKClient client = PolyvSDKClient.getInstance();
        //配置加密
        client.setConfig("CMWht3MlpVkgpFzrLNAebYi4RdQDY/Nhvk3Kc+qWcck6chwHYKfl9o2aOVBvXVTRZD/14XFzVP7U5un43caq1FXwl0cYmTfimjTmNUYa1sZC1pkHE8gEsRpwpweQtEIiTGVEWrYVNo4/o5jI2/efzA=="
                ,aeskey,iv,mContext);
        //初始化sdk
        client.initSetting(mContext);
        //启动Bugly
        client.initCrashReport(mContext);
        //获取SD卡信息
        PolyvDevMountInfo.getInstance().init(mContext, new PolyvDevMountInfo.OnLoadCallback() {
            @Override
            public void callback() {

            }
        });


    }
}
