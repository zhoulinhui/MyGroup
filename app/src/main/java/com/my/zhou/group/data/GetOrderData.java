package com.my.zhou.group.data;

import android.app.Activity;
import android.util.Log;

import com.my.zhou.group.OrderActivity;
import com.my.zhou.group.bean.Order;
import com.my.zhou.group.bean.OrderItem;
import com.my.zhou.group.constant.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laobai on 2017/4/26.
 */
public class GetOrderData {
    private List<Order> orderLists = new ArrayList<>();
    private List<OrderItem> orderListItem = new ArrayList<>();
    private Activity activity;

    public GetOrderData(Activity activity) {
        this.activity = activity;
    }

    public void setOrderLists(String id) {
        RequestParams params = new RequestParams(HttpUrl.ORDERDETAIL);
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TAG", "ORDER_result=" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("retcode") == 2000) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray data1 = data.getJSONArray("detail");
                        if (orderLists == null || orderListItem == null) {
                            orderLists = new ArrayList<Order>();
                            orderListItem = new ArrayList<OrderItem>();
                        }
                        orderLists.clear();
                        orderListItem.clear();
                        for (int i = 0; i < data1.length(); i++) {
                            JSONObject job = data1.getJSONObject(i);
                            OrderItem orderItem = new OrderItem();
                            orderItem.setGoodName(job.getString("goods_name"));
                            orderItem.setGoodNum(job.getString("goods_num"));
                            orderItem.setGoodPrice(job.getString("goods_price"));
                            orderListItem.add(orderItem);
                            Log.i("TAG", "OrderListItem=" + orderListItem);
                        }
                        Order order = new Order();
                        order.setAdd_time(data.getString("add_time"));
                        order.setJ_du(data.getString("j_du"));
                        order.setW_du(data.getString("w_du"));
                        order.setLat(data.getString("lat"));
                        order.setLng(data.getString("lng"));
                        order.setMoney(data.getString("money"));
                        order.setMoney_total(data.getString("money_total"));
                        order.setStore_name(data.getString("store_name"));
                        order.setStore_id(data.getString("store_id"));
                        order.setStore_addr(data.getString("store_addr"));
                        order.setUser_addr(data.getString("user_addr"));
                        order.setNickname(data.getString("nickname"));
                        orderLists.add(order);
                        Log.i("TAG", "order_list==" + orderLists);

                    }
                    sendOrderData();
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

    private void sendOrderData() {
        IOrderInterface orderWork = (IOrderInterface) activity;
        orderWork.setOrder(orderLists, orderListItem);
    }
}
