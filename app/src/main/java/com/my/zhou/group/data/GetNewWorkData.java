package com.my.zhou.group.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.my.zhou.group.bean.NewWork;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.constant.MyApplication;
import com.my.zhou.group.fragment.NewWorkFragment;
import com.my.zhou.group.utils.ACache;
import com.my.zhou.group.utils.SharedPreferencesUtil;

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
 * Created by Administrator on 2017/4/17.
 */

public class GetNewWorkData {

    private List<NewWork> newWorkList = new ArrayList<>();
    private NewWorkFragment fragment;

    public GetNewWorkData(NewWorkFragment fragment) {
        this.fragment = fragment;
    }

    public void setNewWorkList(String id) {
        RequestParams params = new RequestParams(HttpUrl.MISSIONLIST);

        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TAG", "NewWork_result=" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("retcode") == 2000) {
                        JSONArray data = object.getJSONArray("data");
                        if (newWorkList == null) {
                            newWorkList = new ArrayList<NewWork>();
                        }
                        newWorkList.clear();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject job = data.getJSONObject(i);
                            NewWork newwork = new NewWork();
                            newwork.setAdd_time(job.getString("add_time"));
                            newwork.setEoid(job.getString("eoid"));
                            newwork.setMoney(job.getString("money"));
                            newwork.setRange(job.getString("range"));
                            newwork.setMoney_total(job.getString("money_total"));
                            newwork.setStore_addr(job.getString("store_addr"));
                            newwork.setStore_id(job.getString("store_id"));
                            newwork.setUser_addr(job.getString("user_addr"));
                            newwork.setStore_name(job.getString("store_name"));
                            newWorkList.add(newwork);
                            Log.i("TAG", "NEWWORK=" + newWorkList);
                            MyApplication.newWorks = newWorkList;

                        }
                        sendNewWorkData();
                        Log.i("TAG", "11联网获取数据成功11");
                    } else {
                        Log.i("TAG", "11111" + object.getInt("retcode"));
                        sendNewWorkData();
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

    //定义接口包含的成员变量
    public void sendNewWorkData() {
        INewWorkInterface newDatas = (INewWorkInterface) fragment;
        newDatas.setNewWork(newWorkList);
        // newDatas.setNewWorkEoid(lists);
    }

}
