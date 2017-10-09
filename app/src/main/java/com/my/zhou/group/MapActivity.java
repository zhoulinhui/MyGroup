package com.my.zhou.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

public class MapActivity extends AppCompatActivity {
    MapView mMapView = null;

    private static final String APP_FOLDER_NAME = "BNSDKDemo";
    private String mSDCardPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.activity_map);
        initView();
//        //初始化导航相关
//        if (initDirs()) {
//            initNavi();
//        }
    }

//    private void initNavi() {
//        // 申请权限
//        if (android.os.Build.VERSION.SDK_INT >= 23) {
//
//            if (!hasBasePhoneAuth()) {
//
//                this.requestPermissions(authBaseArr, authBaseRequestCode);
//                return;
//
//            }
//        }
//        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME,
//                new NaviInitListener() {
//                    @Override
//                    public void onAuthResult(int status, String msg) {
//                        if (0 == status) {
//                            authinfo = "key校验成功!";
//                        } else {
//                            authinfo = "key校验失败, " + msg;
//                        }
//                        MapActivity.this.runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                Toast.makeText(MapActivity.this, authinfo, Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//
//                    public void initSuccess() {
//                        Toast.makeText(MapActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
//                    }
//
//                    public void initStart() {
//                        Toast.makeText(MapActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
//                    }
//
//                    public void initFailed() {
//                        Toast.makeText(MapActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
//                    }
//                }, null /*mTTSCallback*/);
//    }

    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
