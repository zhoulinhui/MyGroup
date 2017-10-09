package com.my.zhou.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.my.zhou.group.adapter.MyFragmentPagerAdapter;
import com.my.zhou.group.bean.NewWork;
import com.my.zhou.group.bean.Person;
import com.my.zhou.group.bean.Put;
import com.my.zhou.group.bean.Take;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.constant.MyApplication;
import com.my.zhou.group.data.INewWorkInterface;
import com.my.zhou.group.data.IPutInterface;
import com.my.zhou.group.data.ITakeInterface;
import com.my.zhou.group.fragment.NewWorkFragment;
import com.my.zhou.group.fragment.PutFragment;
import com.my.zhou.group.fragment.TakeFragment;
import com.my.zhou.group.fragment.WaitAgainFragment;
import com.my.zhou.group.utils.CircularImage;
import com.my.zhou.group.view.DrawableCenterButton;
import com.my.zhou.group.view.MyViewPager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ITakeInterface, INewWorkInterface, IPutInterface {
    FragmentPagerAdapter adapter;
    NewWorkFragment newWorkFragment = null;
    PutFragment putFragment = null;
    TakeFragment takeFragment = null;
    private CircularImage userPhoto;
    private TextView textUserName, textUserTitle, text_openTrue, text_openFalse;
    private ImageView imagSlidingmenu, image_map_icon;
    private RadioGroup radioGroup;
    private MyViewPager viewPager;
    private LinearLayout linear_loginId, shenqing_ok, linear_shenqing;
    private DrawableCenterButton drawableCenterButton_exit, drawablebtn_shenqing;
    private RadioButton radio_home, radio_put, radio_take;
    private RelativeLayout relativeLayout_money, relativeLayout_history, relativeLayout_notifi,
            relativeLayout_help, relativeLayout_jiesuan;
    SlidingMenu slidingMenu;
    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    //百度地图
    private TextView tv_locaiton_result;
    private LocationClient mLocationClient;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09";//gcj02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initiaView();
        initialFragment();
        setListener();
        initSlidingMenu();
        //islogin();
        //初始化默认头像
        userPhoto.setImageResource(R.mipmap.bangzhu);
        //百度地图
        mLocationClient = ((MyApplication) getApplication()).mLocationClient;
//        ((MyApplication)getApplication()).mLocationResult = (TextView) choosecity.getChildAt(1);
        initLocation();

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，
        int span = 60000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private String uid;
    private List<Person> contactList;
    private String isLock;
    private String isLock1;

    @Override
    protected void onStart() {
        super.onStart();
        //判断uid，如果存了，根据存的uid直接调接口获取用户资料
        SharedPreferences sharedPre = getSharedPreferences("group", MODE_PRIVATE);
        uid = sharedPre.getString("uid", "nouid");
        //登录完成之后返回的is_lock值
        isLock = sharedPre.getString("islock", "nolock");
        Log.i("TAG", "ISLOCK=" + isLock);
        //申请完成后返回的is_lock值
        SharedPreferences sharedPre1 = getSharedPreferences("lock1", MODE_PRIVATE);
        isLock1 = sharedPre1.getString("islock1", "nolock1");
        Log.i("TAG", "ISLOCK1=" + isLock1);

        if (!uid.equals("nouid")) {
            if (isLock.equals("0") && isLock1 == null) {
                shenqing_ok.setVisibility(View.GONE);
                linear_shenqing.setVisibility(View.VISIBLE);
                Toast.makeText(this, "请申请成为跑腿人", Toast.LENGTH_LONG).show();
                drawablebtn_shenqing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, PersonActivity.class));
                    }
                });
            } else if (isLock.equals("3") || isLock1.equals("3")) {
                //申请通过
                //开启开工 忙碌状态
                shenqing_ok.setVisibility(View.VISIBLE);
                linear_shenqing.setVisibility(View.GONE);

            } else if (isLock.equals("1") || isLock1.equals("1")) {
                Toast.makeText(this, "账号被冻结", Toast.LENGTH_LONG).show();
            } else if ((isLock.equals("0") && isLock1.equals("2")) || (isLock.equals("2") && isLock1.equals("2"))) {
                Toast.makeText(this, "审核中", Toast.LENGTH_LONG).show();
                drawablebtn_shenqing.setText("申请中");
                shenqing_ok.setVisibility(View.GONE);
                linear_shenqing.setVisibility(View.VISIBLE);
            }
            //开始判断状态
            RequestParams params1 = new RequestParams(HttpUrl.ISOPEN);
            params1.addBodyParameter("id", uid);
            params1.addBodyParameter("is_open", String.valueOf(open));
            x.http().post(params1, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        Log.i("TAG", "Start_Open==" + result);
                        if (object.getInt("retcode") == 2000) {
                            JSONObject data = object.optJSONObject("data");
                            isOpen = data.getString("is_open");
                            Log.i("TAG", "start_isOpen==" + isOpen);

                            Toast.makeText(MainActivity.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

            //进行网络请求，展示头像，昵称
            RequestParams params = new RequestParams(HttpUrl.GETINFO);
            params.addBodyParameter("uid", uid);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("TAG", "Result-person=" + result);
                    try {
                        JSONObject object = new JSONObject(result);

                        if (object.getInt("retcode") == 2000) {
                            JSONObject data = object.getJSONObject("data");
                            if (contactList == null) {
                                contactList = new ArrayList<Person>();
                            }
                            contactList.clear();
                            Person person = new Person();
                            person.setE_mobile(data.getString("e_mobile"));
                            person.setUser_name(data.getString("user_name"));
                            person.setE_head_photo(data.getString("e_head_photo"));
                            contactList.add(person);
                            Log.i("TAG", "Person-person=" + person);
                            textUserTitle.setText("姓名");
                            textUserName.setText(person.getUser_name());
                            Glide.with(MainActivity.this).load(HttpUrl.API + person.getE_head_photo()).placeholder(R.drawable.gg).into(userPhoto);
                            //状态的显示（是否忙碌）
                            //isOpen();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }

    }

    private String size, size1, size2;

    //控制侧滑的开关及初始化控件
    private WaitAgainFragment fragment;

    private void initiaView() {
        radio_home = (RadioButton) findViewById(R.id.radio_home);
        radio_take = (RadioButton) findViewById(R.id.radio_take);
        radio_put = (RadioButton) findViewById(R.id.radio_put);

        image_map_icon = (ImageView) findViewById(R.id.image_map_icon);
        imagSlidingmenu = (ImageView) findViewById(R.id.imag_slidingmenu);
        image_map_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        imagSlidingmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
    }

    /**
     * slidingMenu侧滑菜单
     */
    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMenu(R.layout.fragment_sliding);
        textUserName = (TextView) slidingMenu.findViewById(R.id.text_name);
        textUserTitle = (TextView) slidingMenu.findViewById(R.id.text_username);
        linear_loginId = (LinearLayout) slidingMenu.findViewById(R.id.linear_loginId);
        userPhoto = (CircularImage) slidingMenu.findViewById(R.id.circleImageView);

        linear_shenqing = (LinearLayout) slidingMenu.findViewById(R.id.linear_shenqing);
        drawablebtn_shenqing = (DrawableCenterButton) slidingMenu.findViewById(R.id.drawablebtn_shenqing);
        //申请完成之后显示（开工 忙碌）
        shenqing_ok = (LinearLayout) slidingMenu.findViewById(R.id.liear_shenqing_ok);
        text_openTrue = (TextView) slidingMenu.findViewById(R.id.text_open_true);
        text_openFalse = (TextView) slidingMenu.findViewById(R.id.text_open_false);

        drawableCenterButton_exit = (DrawableCenterButton) slidingMenu.findViewById(R.id.drawablebtn_exit);

        relativeLayout_money = (RelativeLayout) slidingMenu.findViewById(R.id.rela_sliding_money);
        relativeLayout_notifi = (RelativeLayout) slidingMenu.findViewById(R.id.rela_sliding_notifi);
        relativeLayout_jiesuan = (RelativeLayout) slidingMenu.findViewById(R.id.rela_sliding_jiesuan);
        relativeLayout_history = (RelativeLayout) slidingMenu.findViewById(R.id.rela_sliding_jiesuan);
        relativeLayout_help = (RelativeLayout) slidingMenu.findViewById(R.id.rela_sliding_help);
        // initViewData();
        // 设置侧滑的模式为左侧滑
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置策划菜单的阴影的宽度
        slidingMenu.setShadowWidth(10);
        // 设置侧滑菜单滑出后窗口的剩余宽度
        slidingMenu.setBehindOffset(200);
        // 设置侧滑菜单的淡入淡出效果
        slidingMenu.setFadeEnabled(true);
        // 设置侧滑菜单的渐变角度
        slidingMenu.setFadeDegree(0.35f);
        // 将侧滑菜单附加到活动窗口上
        slidingMenu.attachToActivity(this, SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置下方视图的在滚动时的缩放比例
        slidingMenu.setBehindScrollScale(0.0f);

        //侧滑控件监听
        linear_loginId.setOnClickListener(this);
        userPhoto.setOnClickListener(this);
        drawableCenterButton_exit.setOnClickListener(this);

        text_openTrue.setOnClickListener(this);
        text_openFalse.setOnClickListener(this);

        relativeLayout_money.setOnClickListener(this);
        relativeLayout_history.setOnClickListener(this);
        relativeLayout_jiesuan.setOnClickListener(this);
        relativeLayout_notifi.setOnClickListener(this);
        relativeLayout_help.setOnClickListener(this);
    }

    private static int time = 0;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time++;
            //延时5秒post
            handler.postDelayed(this, 1000 * 5);
        }
    };

    /**
     * 用户经纬度
     */
    private void getLngLat() {
        SharedPreferences preferences = getSharedPreferences("ZuoBiao", Context.MODE_PRIVATE);
        String log = preferences.getString("X", "mox");
        String lat = preferences.getString("Y", "noy");
        Log.i("TAG", "log======" + log + "lat======" + lat);
        RequestParams params = new RequestParams(HttpUrl.LONGLAT);
        params.addBodyParameter("id", uid);
        params.addBodyParameter("j_du", log);
        params.addBodyParameter("w_du", lat);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.i("TAG", "LOG_LAT=" + result);
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("retcode") == 2000) {

                        //延时5秒post
                        handler.postDelayed(runnable, 1000 * 5);

                        Toast.makeText(MainActivity.this, "" + obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        isOpen(ISOPEN);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 是否忙碌
     * is_open(开工传1  忙碌传0)
     */
    private int open;
    private String isOpen;
    private static final int ISOPEN = 1;
    private static final int ISCLOSE = 0;

    private void isOpen(int open) {
        if (!uid.equals("nouid")) {
            if (open == ISOPEN) {
                open = 1;
                text_openTrue.setBackgroundResource(R.drawable.btn_shape_02);
                text_openTrue.setTextColor(Color.WHITE);
                text_openFalse.setBackgroundResource(R.drawable.btn_shape_05);
                text_openFalse.setTextColor(Color.parseColor("#FF7300"));
            } else if (open == ISCLOSE) {
                open = 0;
                text_openTrue.setBackgroundResource(R.drawable.btn_shape_05);
                text_openTrue.setTextColor(Color.parseColor("#FF7300"));
                text_openFalse.setBackgroundResource(R.drawable.btn_shape_02);
                text_openFalse.setTextColor(Color.WHITE);
            }
        } else {
            text_openTrue.setClickable(false);
            text_openFalse.setClickable(false);
        }
        RequestParams params = new RequestParams(HttpUrl.ISOPEN);
        params.addBodyParameter("id", uid);
        params.addBodyParameter("is_open", String.valueOf(open));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Log.i("TAG", "OPEN==" + result);
                    if (object.getInt("retcode") == 2000) {
                        JSONObject data = object.optJSONObject("data");
                        isOpen = data.getString("is_open");
                        Log.i("TAG", "isOPEN==" + isOpen);

                        Toast.makeText(MainActivity.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void setListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.radio_home);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio_take);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio_put);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_home:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.radio_take:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.radio_put:
                        viewPager.setCurrentItem(2, false);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void initialFragment() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        viewPager = (MyViewPager) findViewById(R.id.viewpager_Id);
        adapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager());

        newWorkFragment = new NewWorkFragment();
        putFragment = new PutFragment();
        takeFragment = new TakeFragment();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    /**
     * 退出应用
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "再点一次退出", Toast.LENGTH_LONG).show();
                return true;
            } else {
                finish(); // 退出
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        mLocationClient = null;
        super.onDestroy();
    }

    private int state;
    private static final int BLANK = 0;
    private static final int NOBIANK = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_loginId:
                //跳转到登录界面
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.circleImageView:
                if (uid.equals("nouid")) {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.drawablebtn_exit:
                break;
            case R.id.text_open_true:
                if (state == BLANK) {
                    getLngLat();
                } else {
                    text_openTrue.setClickable(false);
                }
                break;
            case R.id.text_open_false:
                open = ISCLOSE;
                isOpen(open);
                handler.removeCallbacks(runnable);
                break;
            case R.id.rela_sliding_money:
                break;
            case R.id.rela_sliding_history:
                break;
            case R.id.rela_sliding_jiesuan:
                break;
            case R.id.rela_sliding_notifi:
                break;
            case R.id.rela_sliding_help:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                String url = data.getExtras().getString("url");
                Log.i("TAG", "TU图片====" + HttpUrl.API + url);
                //Glide.with(this).load(HttpUrl.API+url).placeholder(R.drawable.gg).into(userPhoto);
            }
        }
//        else if(requestCode == 2){
//            if(resultCode == RESULT_OK&&data != null){
//                uid = data.getExtras().getString("uid");
////                onStart();
//            }
//        }
    }


    @Override
    public void setTake(List<Take> list) {
        radio_take.setText("待取货(" + list.size() + ")");
    }

    @Override
    public void setNewWork(List<NewWork> list) {
        radio_home.setText("新任务(" + list.size() + ")");
    }

    @Override
    public void setPut(List<Put> list) {
        radio_put.setText("待送达(" + list.size() + ")");
    }
}
