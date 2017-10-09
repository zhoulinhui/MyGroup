package com.my.zhou.group.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.my.zhou.group.MainActivity;
import com.my.zhou.group.bean.NewWork;
import com.my.zhou.group.bean.Take;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.fragment.TakeFragment;
import com.my.zhou.group.fragment.WaitAgainFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/4/21.
 */

public class GetTakeData {
    private List<Take> takeLists = new ArrayList<>();
    private WaitAgainFragment fragment;
    private Context context;

    public GetTakeData(WaitAgainFragment fragment) {
        this.fragment = fragment;
    }

    public void setTakeLists(String id) {
        RequestParams params = new RequestParams(HttpUrl.WAITGAIN);
        params.addBodyParameter("e_id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TAG", "Take_result=" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("retcode") == 2000) {
                        JSONArray data = object.getJSONArray("data");
                        if (takeLists == null) {
                            takeLists = new ArrayList<Take>();
                        }
                        takeLists.clear();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject job = data.getJSONObject(i);
                            Take take = new Take();
                            take.setAdd_time(job.getString("add_time"));
                            take.setUser_addr(job.getString("user_addr"));
                            take.setMoney(job.getString("money"));
                            take.setMoney_total(job.getString("money_total"));
                            take.setStore_name(job.getString("store_name"));
                            take.setStore_addr(job.getString("store_addr"));
                            take.setStore_id(job.getString("store_id"));
                            take.setEoid(job.getString("eoid"));
                            take.setRange(job.getString("range"));
                            take.setStore_phone(job.getString("store_phone"));
                            take.setJ_du(job.getString("j_du"));
                            take.setW_du(job.getString("w_du"));
                            take.setLat(job.getString("lat"));
                            take.setLng(job.getString("lng"));
                            takeLists.add(take);
                            Log.i("TAG", "TAKE_Lists=" + takeLists);

                        }
                        sendTakeData(takeLists);
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

    public void sendTakeData(List<Take> list) {
        ITakeInterface takeWork = (ITakeInterface) fragment;
        takeWork.setTake(takeLists);
    }


}
