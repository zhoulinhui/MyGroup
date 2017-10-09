package com.my.zhou.group.data;

import android.app.Activity;
import android.util.Log;

import com.my.zhou.group.bean.Region;
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
 * Created by Administrator on 2017/4/14.
 */

public class GetRegionData {
    private List<Region> regions;

    private Activity activity;

    public GetRegionData(Activity activity) {
        this.activity = activity;
    }

    /**
     * 地区
     *
     * @param id
     */
    public void setRegionsNet(final String id, final int number) {

        RequestParams params = new RequestParams(HttpUrl.REGION);
        if (!id.equals("-1")) {
            params.addBodyParameter("id", id);
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TAG", result);
                try {
                    JSONObject object = new JSONObject(result);
                    //JSONObject jobs = object.getJSONObject("data");
                    if (object.getInt("retcode") == 2000) {
                        //JSONArray data =jobs.getJSONArray("data");
                        JSONArray data = object.getJSONArray("data");
                        if (regions == null) {
                            regions = new ArrayList<Region>();
                        }
                        regions.clear();

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject job = data.getJSONObject(i);
                            Region region = new Region();
                            region.setId(job.getString("id"));
                            region.setName(job.getString("name"));
                            regions.add(region);
                            Log.i("TAG", "Region=" + regions);
                        }
                        sendRegionManager(number);
                        Log.i("TAG", "联网获取数据成功");
                    } else {
                        Log.i("TAG", "" + object.getInt("retcode"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("qiao", "开始获取数据");
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
    public void sendRegionManager(int number) {
        IRegionInterface rightNet = (IRegionInterface) activity;
        rightNet.setRegion(regions, number);
    }
}
