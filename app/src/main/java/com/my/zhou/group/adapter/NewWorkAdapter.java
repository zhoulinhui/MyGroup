package com.my.zhou.group.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zhou.group.PersonActivity;
import com.my.zhou.group.R;
import com.my.zhou.group.bean.NewWork;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.view.DrawableCenterButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/3/7.
 */

public class NewWorkAdapter extends BaseAdapter {
    private Context mContext;
    private INewWork ggInterface;
    List<NewWork> mListwork;

    public void Igg(INewWork ggInterface) {
        this.ggInterface = ggInterface;
    }

    public NewWorkAdapter(Context mContext, List<NewWork> listwork) {
        this.mContext = mContext;
        this.mListwork = listwork;
    }

    public void addData(List<NewWork> lists) {
        if (lists != null) {
            mListwork.addAll(lists);
        }
        notifyDataSetChanged();
    }

    ViewHolder viewHolder;

    @Override
    public int getCount() {
        return mListwork.size();
    }

    @Override
    public Object getItem(int position) {
        return mListwork.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inflate_newwork_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text_newwork_store_name = (TextView) convertView.findViewById(R.id.text_newwork_store_name);
            viewHolder.text_newwork_store_addr = (TextView) convertView.findViewById(R.id.text_newwork_store_addr);
            viewHolder.text_newwork_user_addr = (TextView) convertView.findViewById(R.id.text_newwork_user_addr);
            viewHolder.text_newwork_yingfu = (TextView) convertView.findViewById(R.id.text_newwork_money);
            viewHolder.text_newwork_shifu = (TextView) convertView.findViewById(R.id.text_newwork_money_total);
            viewHolder.text_newwork_distance = (TextView) convertView.findViewById(R.id.text_newwork_range);
            viewHolder.text_add_time = (TextView) convertView.findViewById(R.id.text_add_time);
            viewHolder.drawablebtn_qiangdan = (DrawableCenterButton) convertView.findViewById(R.id.drawablebtn_qiangdan);
            viewHolder.image_newwork_put = (ImageView) convertView.findViewById(R.id.image_newwork_put);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final NewWork newwork = (NewWork) getItem(position);
        viewHolder.image_newwork_put.setImageResource(R.mipmap.song2);
        // 商家名字
        viewHolder.text_newwork_store_name.setText(newwork.getStore_name());
        //商店地址
        viewHolder.text_newwork_store_addr.setText(newwork.getStore_addr());
        viewHolder.text_newwork_user_addr.setText(newwork.getUser_addr());
        //应付金额
        viewHolder.text_newwork_yingfu.setText("应付￥" + newwork.getMoney());
        //实付金额
        viewHolder.text_newwork_shifu.setText("实付￥" + newwork.getMoney_total());
        // 距离商家的距离
        viewHolder.text_newwork_distance.setText("取/送货" + newwork.getRange() + "km");
        //建议送达时间
        viewHolder.text_add_time.setText("立即(建议" + newwork.getAdd_time() + "之前到达)");

        //点击抢单的监听事件
        viewHolder.drawablebtn_qiangdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "LISTENER=");
                sendFrontRunner(newwork, position);
            }
        });

        return convertView;
    }

    private String uid;

    private void sendFrontRunner(NewWork n, final int position) {
        Log.i("TAG", "NNNNNNNN=" + n.getEoid());
        SharedPreferences sharedPre1 = mContext.getSharedPreferences("group", MODE_PRIVATE);
        uid = sharedPre1.getString("uid", "nouid");
        Log.i("TAG", "WWWWWWWW++" + uid);
        if (!uid.equals("nouid")) {//id存在
            Log.i("TAG", "YYYYYYYYY");
            RequestParams params = new RequestParams(HttpUrl.FRONTRUNNER);
            params.addBodyParameter("e_id", uid);
            params.addBodyParameter("eoid", n.getEoid());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("TAG", "NewRESULt=" + result);
                    try {
                        JSONObject object = new JSONObject(result);

                        if (object.getInt("retcode") == 2000) {
//                            ggInterface.setNew(mListwork.get(position));

                            mListwork.remove(position);
                            notifyDataSetChanged();

                            Toast.makeText(mContext, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
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

    class ViewHolder {

        TextView text_newwork_store_name, text_newwork_store_addr, text_newwork_user_addr,
                text_newwork_yingfu, text_newwork_shifu, text_newwork_distance, text_add_time;
        DrawableCenterButton drawablebtn_qiangdan;
        ImageView image_newwork_put;
    }

    public interface INewWork {
        void setNew(NewWork newWorks);
    }
}
