package com.my.zhou.group.constant;


import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.my.zhou.group.bean.LoginConstant;
import com.my.zhou.group.bean.NewWork;
import com.my.zhou.group.bean.Person;

import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by dell on 2017/3/24.
 */

public class MyApplication extends Application {
    private MyApplication instance;
    public static List<LoginConstant> login;
    public static List<NewWork> newWorks;
    public static MyApplication mContext;
    public TextView mLocationResult, logMsg;
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SDKInitializer.initialize(this);
        x.Ext.init(this);//初始化xutils
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

    }

    /**
     * 单例模式
     *
     * @return
     */
    public MyApplication getInstance() {
        if (instance == null)
            return new MyApplication();
        return instance;
    }

    @Override
    public void onTerminate() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        mLocationClient = null;
        mMyLocationListener = null;
        super.onTerminate();
    }

    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.e("12345ffdssdf", "onReceiveLocation: " + location.getLongitude());
            String city = location.getCity();
            StringBuffer sb = new StringBuffer(256);
            SharedPreferences preferences = getSharedPreferences("ZuoBiao", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String uidx = "X";
            String uidy = "Y";
            String citys = "city";
            Log.i("sdsdsdsdsd", "city" + city + "X" + String.valueOf(location.getLongitude()) + "Y" + String.valueOf(location.getLatitude()));
            editor.putString(uidx, String.valueOf(location.getLongitude()));
            editor.putString(uidy, String.valueOf(location.getLatitude()));
            editor.putString(citys, String.valueOf(location.getCity()));
            editor.commit();
            Log.i("aaaaaaaa", "city" + city + "X" + String.valueOf(location.getLongitude()) + "Y" + String.valueOf(location.getLatitude()));
            sb.append("城市 : ");
            sb.append(location.getCity());
            sb.append("\ntime : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
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
            sb.append("\nlocationdescribe : ");// 位置语义化信息
            sb.append(location.getLocationDescribe());
            List<Poi> list = location.getPoiList();// POI信息
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            logMsg(city);
            Log.i("BaiduLocationApiDem", sb.toString());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }

    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        try {
            if (mLocationResult != null)
                mLocationResult.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
