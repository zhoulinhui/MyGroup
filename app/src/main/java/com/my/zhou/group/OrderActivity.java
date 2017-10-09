package com.my.zhou.group;

import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.my.zhou.group.adapter.OrderAdapter;
import com.my.zhou.group.bean.Order;
import com.my.zhou.group.bean.OrderItem;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.data.GetOrderData;
import com.my.zhou.group.data.IOrderInterface;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, IOrderInterface {

    @Bind(R.id.image_xiangqing_back)
    ImageView image_xiangqing_back;
    @Bind(R.id.btn_left)
    RadioButton btnLeft;
    @Bind(R.id.btn_right)
    RadioButton btnRight;
    @Bind(R.id.order_radioGroup)
    RadioGroup orderRadioGroup;
    @Bind(R.id.text_newwork_name)
    TextView textView_order_name;
    @Bind(R.id.text_newwork_take_address)
    TextView textView_order_address;
    @Bind(R.id.text_money)
    TextView text_money;
    @Bind(R.id.text_money2)
    TextView text_money2;
    @Bind(R.id.text_order_time)
    TextView text_order_time;
    @Bind(R.id.text_nickname)
    TextView text_nickname;
    @Bind(R.id.text_xiangqing_address)
    TextView text_xiangqing_address;
    @Bind(R.id.order_listview)
    ListView order_listview;

    private String eid;
    private String phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        //获得意图
        Intent intent = getIntent();
        //读取数据
        eid = intent.getStringExtra("eoid");
        phones = intent.getStringExtra("store_phone");
        Log.i("TAG", "PHONES_Store=" + phones);
        Log.i("TAG", "EID==" + eid);
        setListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        orderData(eid);
    }

    private GetOrderData orders;

    private void orderData(String id) {
        eid = id;
        orders = new GetOrderData(this);
        orders.setOrderLists(eid);

    }

    private void setListener() {
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        image_xiangqing_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        orderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == btnLeft.getImeActionId()) {
//                    Toast.makeText(OrderActivity.this, "确定取单", Toast.LENGTH_LONG).show();
//                } else if (checkedId == btnRight.getImeActionId()) {
//                    Toast.makeText(OrderActivity.this, "联系商家", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                btnLeft.setText("确定取货");
                sendTakeDatas();
                break;
            case R.id.btn_right:
                //联系商家
                if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                } else {
                    //拨打电话
                    call(phones);
                }
                break;
        }
    }

    private void call(String phones) {
        try {
            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phones));
            startActivity(phoneIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //同意则拨打电话
                    call(phones);
                } else {
                    //否则，提示用户
                    Toast.makeText(this, "请设置权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 请求接口
     * 确定取货
     */
    private String uid;

    private void sendTakeDatas() {
        //判断uid，如果存了，根据存的uid直接调接口获取用户资料
        SharedPreferences sharedPre = getSharedPreferences("group", MODE_PRIVATE);
        uid = sharedPre.getString("uid", "nouid");

        RequestParams params = new RequestParams(HttpUrl.CONFIRMPICK);
        params.addBodyParameter("e_id", uid);
        params.addBodyParameter("eoid", eid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TAG", "TAKE_result=" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("retcode") == 2000) {
                        //
                        btnLeft.setChecked(false);
                        btnRight.setChecked(true);
                        Toast.makeText(OrderActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
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

    private List<Order> listOrders = new ArrayList<>();

    @Override
    public void setOrder(List<Order> list, List<OrderItem> list1) {
        listOrders = list;
        Log.i("TAG", "ORDER_list===" + listOrders);
        if (listOrders == null) {
            Toast.makeText(OrderActivity.this, "数据请求有误", Toast.LENGTH_LONG).show();
        } else {
            String name = listOrders.get(0).getStore_name();
            textView_order_name.setText(name);
            String time = listOrders.get(0).getAdd_time();
            text_order_time.setText("建议" + time + "之前送达");
            textView_order_address.setText(listOrders.get(0).getStore_addr());
            text_money.setText("￥" + listOrders.get(0).getMoney());
            text_money2.setText("￥" + listOrders.get(0).getMoney_total());

            text_nickname.setText(listOrders.get(0).getNickname());
            text_xiangqing_address.setText(listOrders.get(0).getUser_addr());

            //适配器的数据
            OrderAdapter adapter = new OrderAdapter(OrderActivity.this, list1);
            order_listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }
}
