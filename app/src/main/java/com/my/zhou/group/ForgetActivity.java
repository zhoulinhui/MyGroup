package com.my.zhou.group;

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

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.image_login_back)
    ImageView image_login_back;
    @Bind(R.id.drawablebtn_registok)
    DrawableCenterButton forgetOk;
    @Bind(R.id.edit_regist_phone)
    EditText edit_registPhone;
    @Bind(R.id.edit_regist_password1)
    EditText edit_newPassword1;
    @Bind(R.id.edit_regist_password2)
    EditText edit_newPassword2;
    @Bind(R.id.edit_regist_code)
    EditText edit_forget_code;
    @Bind(R.id.drawablebtn_code)
    DrawableCenterButton drawablebtnCode;

    private int time = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        initiView();
        setListener();
    }

    /**
     * 发送验证码
     *
     * @param msg
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (time <= 1) {
                        drawablebtnCode.setText("发送验证码");
                        drawablebtnCode.setClickable(true);
                        time = 60;
                    } else {
                        time--;
                        drawablebtnCode.setText(time + "s");
                        drawablebtnCode.setClickable(false);
                        //再次发送“0”触发操作
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
            }
        }
    };

    private void setListener() {
        image_login_back.setOnClickListener(this);
        drawablebtnCode.setOnClickListener(this);
        forgetOk.setOnClickListener(this);
    }

    private void initiView() {
        text_title.setText("忘记密码");
        forgetOk.setText("修改完成");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawablebtn_code:
                sendCode();
                break;
            case R.id.drawablebtn_registok:
                getBackOk();
                break;

        }
    }

    /**
     * sendCode
     */
    private void sendCode() {
        //发送验证码请求，携带手机号参数
        String phone = edit_registPhone.getText().toString();
        if (phone.equals("")) {
            Toast.makeText(ForgetActivity.this, "号码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //向服务器发送请求
            RequestParams params = new RequestParams(HttpUrl.CODE);
            //添加参数
            params.addBodyParameter("phonenum", phone);

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("TAG", "HHH+H" + result);
                    try {
                        //解析返回的字符串
                        JSONObject obj = new JSONObject(result);
                        //如果服务器发送验证码成功，则更新界面，倒计时开始
                        if (obj.getInt("retcode") == 2000) {
                            //向handler发送为"0"的操作，10为延迟的时间
                            handler.sendEmptyMessageDelayed(0, 10);
                            Log.i("ssfdss", obj.getJSONObject("data").getString("code"));
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

    private void getBackOk() {
        //获得用户输入的信息
        String mobile = edit_registPhone.getText().toString();
        String code = edit_forget_code.getText().toString();
        String password1 = edit_newPassword1.getText().toString();
        String password2 = edit_newPassword2.getText().toString();

        if (mobile.equals("")) {
            Toast.makeText(this, "号码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!(password1.equals(password2))) {
            Toast.makeText(this, "密码有误", Toast.LENGTH_SHORT).show();
        } else if (code.equals("")) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams params = new RequestParams(HttpUrl.FORGET);
            params.addBodyParameter("mobile", mobile);
            params.addBodyParameter("password", password1);
            params.addBodyParameter("repassword", password2);
            params.addBodyParameter("code", code);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        //获取返回值，注册成功则跳转到登录界面，并销毁当前界面
                        //否则提示用户
                        if (object.getInt("retcode") == 2000) {
                            Toast.makeText(ForgetActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ForgetActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
