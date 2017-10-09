package com.my.zhou.group.data;

import android.util.Log;

import com.my.zhou.group.bean.Put;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.fragment.WaitSendFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class GetPutData {
    private List<Put> putList;
    private WaitSendFragment fragment;

    public GetPutData(WaitSendFragment fragment) {
        this.fragment = fragment;
    }

    public void sendPutDatas(String id) {
        RequestParams params = new RequestParams(HttpUrl.WAITSEND);
        params.addBodyParameter("e_id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TAG", "Put_list_result=" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("retcode") == 2000) {
                        JSONArray data = object.getJSONArray("data");
                        if (putList == null) {
                            putList = new ArrayList<Put>();
                        }
                        putList.clear();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject job = data.getJSONObject(i);
                            Put put = new Put();
                            put.setAdd_time(job.getString("add_time"));
                            put.setUser_addr(job.getString("user_addr"));
                            put.setMoney(job.getString("money"));
                            put.setMoney_total(job.getString("money_total"));
                            put.setStore_addr(job.getString("store_addr"));
                            put.setStore_name(job.getString("store_name"));
                            put.setEoid(job.getString("eoid"));
                            put.setStore_id(job.getString("store_id"));
                            put.setRange(job.getString("range"));
                            //??????????????

                            put.setMobile(job.getString("mobile"));
                            put.setJ_du(job.getString("j_du"));
                            put.setW_du(job.getString("w_du"));
                            putList.add(put);
                            Log.i("TAG", "Put_list_putList=" + putList);
                        }
                        sendPutDatas();
                    } else {
                        Log.i("TAG", "" + object.getInt("retcode"));
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

    private void sendPutDatas() {
        IPutInterface putWork = (IPutInterface) fragment;
        putWork.setPut(putList);

    }

}
