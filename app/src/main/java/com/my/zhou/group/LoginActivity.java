package com.my.zhou.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.my.zhou.group.bean.Login;
import com.my.zhou.group.bean.LoginConstant;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.constant.MyApplication;
import com.my.zhou.group.view.DrawableCenterButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.image_login_back)
    ImageView imageLoginBack;
    @Bind(R.id.edit_login_username)
    EditText edit_login_username;
    @Bind(R.id.edit_login_password)
    EditText edit_login_password;
    @Bind(R.id.text_login_forget)
    TextView text_login_forget;
    @Bind(R.id.drawablebtn_login)
    DrawableCenterButton drawablebtn_login;
    @Bind(R.id.drawablebtn_regist)
    DrawableCenterButton drawablebtn_regist;
    //声明联网获取数据的集合
    private List<Login> constants;
    public static final String UID = "uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void setListener() {
        imageLoginBack.setOnClickListener(this);
        drawablebtn_login.setOnClickListener(this);
        drawablebtn_regist.setOnClickListener(this);
        text_login_forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_login_back:
                finish();
                break;
            case R.id.drawablebtn_login:
                login();
                break;
            case R.id.drawablebtn_regist:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                finish();
                break;
            case R.id.text_login_forget:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                finish();
                break;
        }
    }

    private void login() {
        final String username = edit_login_username.getText().toString();
        String password = edit_login_password.getText().toString();
        String device_token = JPushInterface.getRegistrationID(getApplicationContext());
        Log.i("TAG", username + "," + password);

        if (username.length() != 11) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else {

            RequestParams params = new RequestParams(HttpUrl.LOGIN);
            params.addBodyParameter("mobile", username);
            params.addBodyParameter("password", password);
            params.addBodyParameter("device_token", device_token);
            /**
             * 切记不要在这Log --params  {Log.i("TAG","ppppp"+params);}
             */
            x.http().post(params, new Callback.CommonCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        Log.i("TAG", "kkkkk" + object);
                        if (constants == null) {
                            constants = new ArrayList<Login>();
                        }
                        constants.clear();

                        //获取返回值，登录成功则跳转到首页界面，并销毁当前界面
                        //否则提示用户
                        if (object.getInt("retcode") == 2000) {

                            JSONObject data = object.optJSONObject("data");
                            Login login = new Login();
                            login.setE_id(data.getString("e_id"));
                            login.setE_mobile(data.getString("e_mobile"));
                            login.setIs_lock(data.getString("is_lock"));
                            login.setUser_name(data.getString("user_name"));
                            constants.add(login);


//                            LoginConstant cons = new LoginConstant();
//                            cons.setIs_lock(data.getString("is_lock"));
//                            Log.i("TAG","IS_lock=="+data.getString("is_lock"));
//                            String uid = data.getString("e_id");
//                            cons.setUid(uid);
//
//                            constants.add(cons);
//                            Log.i("TAG", "CONS+=" + cons);
                            // MyApplication.login = constants;

                            //获取SharedPreferences对象
                            SharedPreferences sharedPre = getSharedPreferences("group", MODE_PRIVATE);
                            //获取Editor对象
                            SharedPreferences.Editor editor = sharedPre.edit();
                            //设置参数
                            editor.putString("uid", data.getString("e_id"));
                            editor.putString("islock", data.getString("is_lock"));
                            //提交
                            editor.commit();
                            Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();

//                            startActivityForResult(new Intent(LoginActivity.this, MainActivity.class),101);
//                                Intent intent = new Intent();
//                                intent.putExtra("name",username);
                            //            //数据是使用Intent返回
//                            Intent intentResult = new Intent();
//                            //把返回数据存入Intent
//                            intentResult.putExtra("uid", uid);
//                            //设置返回数据
//                            setResult(RESULT_OK, intentResult);
//                            //关闭Activity
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
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
    protected void onStart() {
        super.onStart();
        setListener();
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//       if(requestCode==101&&resultCode==RESULT_OK){
//
//           String s=data.getStringExtra("name");
//           edit_login_username.setText(s);
//       }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
