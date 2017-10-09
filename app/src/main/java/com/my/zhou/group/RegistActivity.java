package com.my.zhou.group;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.view.DrawableCenterButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.image_login_back)
    ImageView image_login_back;
    @Bind(R.id.drawablebtn_registok)
    DrawableCenterButton drawablebtn_registok;
    @Bind(R.id.edit_regist_phone)
    EditText edit_regist_phone;
    @Bind(R.id.edit_regist_password1)
    EditText edit_regist_password1;
    @Bind(R.id.edit_regist_password2)
    EditText edit_regist_password2;
    @Bind(R.id.drawablebtn_code)
    DrawableCenterButton drawablebtn_code;
    @Bind(R.id.edit_regist_code)
    EditText edit_regist_code;
    private int time = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        initiView();
        setListener();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (time <= 1) {
                        drawablebtn_code.setText("获取验证码");
                        drawablebtn_code.setClickable(true);
                        time = 60;
                    } else {
                        time--;
                        drawablebtn_code.setText(time + "s");
                        drawablebtn_code.setClickable(false);
                        //再次发送“0”触发操作
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
            }
        }
    };

    private void setListener() {
        image_login_back.setOnClickListener(this);
        drawablebtn_registok.setOnClickListener(this);
        drawablebtn_code.setOnClickListener(this);
    }

    private void initiView() {
        text_title.setText("注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawablebtn_registok:
                registPhone();
                break;
            case R.id.image_login_back:
                finish();
            case R.id.drawablebtn_code:
                sendCode();
                break;
        }
    }

    private void registPhone() {
        //获得用户信息
        String mobile = edit_regist_phone.getText().toString();
        String password1 = edit_regist_password1.getText().toString();
        String password2 = edit_regist_password2.getText().toString();
        String code = edit_regist_code.getText().toString();

        String device_token = JPushInterface.getRegistrationID(getApplicationContext());

        Log.i("TAG", "TOKEN" + device_token);

        if (mobile.length() != 11) {
            Toast.makeText(this, "号码输入有误", Toast.LENGTH_SHORT).show();
        } else if (!(password1.equals(password2))) {
            Toast.makeText(this, "密码输入有误", Toast.LENGTH_SHORT).show();
        } else if (code.equals("")) {
            Toast.makeText(this, "验证码输入有误", Toast.LENGTH_SHORT).show();
        } else {
            //向服务器发送请求
            RequestParams params = new RequestParams(HttpUrl.REGIST);
            params.addBodyParameter("mobile", mobile);
            params.addBodyParameter("password", password1);
            params.addBodyParameter("password2", password2);
            params.addBodyParameter("code", code);
            //需要集成激光获取设备号
            params.addBodyParameter("device_token", device_token);

            x.http().post(params, new Callback.CommonCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        //获取返回值，注册成功则跳转到登录界面，并销毁当前界面
                        //否则提示用户
                        if (object.getInt("retcode") == 2000) {
                            Toast.makeText(RegistActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegistActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
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

    private void sendCode() {

        String mobile = edit_regist_phone.getText().toString();
        Log.i("TAG", "ggg" + mobile);
        //向服务器发送请求
        RequestParams params = new RequestParams(HttpUrl.CODE);
        //添加参数
        params.addBodyParameter("phonenum", mobile);
//            Log.i("TAG","hhhh"+params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TAG", "AAAAAAAAAAAA" + result);
                try {
                    //解析返回的字符串
                    JSONObject obj = new JSONObject(result);
                    int status = obj.getInt("retcode");
                    Log.i("TAG", "status" + status);
                    //如果服务器发送验证码成功，则更新界面，倒计时开始
                    if (status == 2000) {
                        //向handler发送为"0"的操作，10为延迟的时间
                        handler.sendEmptyMessageDelayed(0, 10);
                    } else if (status == 4003) {
                        Toast.makeText(RegistActivity.this, "手机格式有误", Toast.LENGTH_SHORT).show();
                    } else if (status == 4000) {
                        Toast.makeText(RegistActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    } else if (status == 4006) {
                        Toast.makeText(RegistActivity.this, " 该手机号码已被注册", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("TAG", "错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("TAG", "取消");
            }

            @Override
            public void onFinished() {
                Log.i("TAG", "完成");
            }
        });
    }

    @Override
    protected void onDestroy() {
        //handler在子线程关闭activity时 hanler还在运行此时需要关闭handler
        handler.removeMessages(0);
        handler = null;
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
