package com.my.zhou.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.zhou.group.MapActivity;
import com.my.zhou.group.Order1Activity;
import com.my.zhou.group.OrderActivity;
import com.my.zhou.group.R;
import com.my.zhou.group.bean.Put;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * song
 * Created by Administrator on 2017/4/20.
 */

public class PutAdapter extends BaseAdapter {
    private Context mContext;
    List<Put> mListwork = new ArrayList<Put>();

    private PhoneOnClickListener1 listener1;

    //监听器类接口
    public interface PhoneOnClickListener1 {
        void onClick1(String phone); //单击事件处理接口
    }

    public void setOnClickListener1(PhoneOnClickListener1 listener1) {
        this.listener1 = listener1;
    }

    public PutAdapter(Context context, List<Put> listwork) {
        this.mContext = context;
        this.mListwork = listwork;
    }

    @Override
    public int getCount() {
        return mListwork.size();
    }

    @Override
    public Put getItem(int position) {
        return mListwork.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.include_put, null);

            vHolder = new ViewHolder(convertView);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        final Put put = getItem(position);
        vHolder.image_newwork_take.setImageResource(R.mipmap.qu1);
        vHolder.image_newwork_put.setImageResource(R.mipmap.song2);

        vHolder.text_take_add_time.setText("立即(建议" + put.getAdd_time() + "之前到达)");
        vHolder.text_newwork_store_name.setText(put.getStore_name());
        vHolder.text_newwork_store_addr.setText(put.getStore_addr());
        vHolder.text_newwork_user_addr.setText(put.getUser_addr());
        //应付金额
        vHolder.text_newwork_money.setText("应付￥" + put.getMoney());
        //实付金额
        vHolder.text_newwork_money_total.setText("实付￥" + put.getMoney_total());
        // 距离商家的距离
        vHolder.text_newwork_range.setText("送货" + put.getRange() + "km");

        vHolder.image_put_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Order1Activity.class);
                intent.putExtra("eoid", put.getEoid());
                intent.putExtra("mobile", put.getMobile());
                Log.i("TAG", "PUT_EOID=" + put.getEoid());
                mContext.startActivity(intent);
            }
        });
        vHolder.linear_put_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener1.onClick1(put.getMobile());
            }
        });
        vHolder.linearLayout_zuobiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //导航ING
                Intent intent = new Intent(mContext, MapActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {

        @Bind(R.id.text_take_add_time)
        TextView text_take_add_time;
        @Bind(R.id.text_newwork_store_name)
        TextView text_newwork_store_name;
        @Bind(R.id.text_newwork_store_addr)
        TextView text_newwork_store_addr;
        @Bind(R.id.text_newwork_user_addr)
        TextView text_newwork_user_addr;
        @Bind(R.id.text_newwork_money)
        TextView text_newwork_money;
        @Bind(R.id.text_newwork_money_total)
        TextView text_newwork_money_total;
        @Bind(R.id.text_newwork_range)
        TextView text_newwork_range;
        @Bind(R.id.image_newwork_take)
        ImageView image_newwork_take;
        @Bind(R.id.image_newwork_put)
        ImageView image_newwork_put;
        @Bind(R.id.image_join_order)
        ImageView image_put_join;
        @Bind(R.id.linear_take_telephone)
        LinearLayout linear_put_telephone;
        @Bind(R.id.linear_zuobiao)
        LinearLayout linearLayout_zuobiao;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
