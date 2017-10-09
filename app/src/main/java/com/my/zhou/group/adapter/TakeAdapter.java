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
import com.my.zhou.group.OrderActivity;
import com.my.zhou.group.R;
import com.my.zhou.group.bean.Take;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/20.
 */

public class TakeAdapter extends BaseAdapter {
    private Context mContext;
    List<Take> mListwork = new ArrayList<Take>();

    private PhoneOnClickListener listener;

    //监听器类接口
    public interface PhoneOnClickListener {
        void onClick(String phone); //单击事件处理接口
    }

    //实现这个View的监听器
    public void setOnClickListener(PhoneOnClickListener listener) {
        this.listener = listener;   //引用监听器类对象,在这里可以使用监听器类的对象
    }

    public TakeAdapter(Context context, List<Take> listwork) {
        this.mContext = context;
        this.mListwork = listwork;
    }

    @Override
    public int getCount() {
        return mListwork.size();
    }

    @Override
    public Take getItem(int position) {
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
        final Take take = getItem(position);
        vHolder.text_take_add_time.setText("立即(建议" + take.getAdd_time() + "之前到达)");
        vHolder.text_newwork_store_name.setText(take.getStore_name());
        vHolder.text_newwork_store_addr.setText(take.getStore_addr());
        vHolder.text_newwork_user_addr.setText(take.getUser_addr());
        //应付金额
        vHolder.text_newwork_money.setText("应付￥" + take.getMoney());
        //实付金额
        vHolder.text_newwork_money_total.setText("实付￥" + take.getMoney_total());
        // 距离商家的距离
        vHolder.text_newwork_range.setText("取货" + take.getRange() + "km");
        //进入详情单
        vHolder.image_join_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderActivity.class);
                intent.putExtra("eoid", take.getEoid());
                intent.putExtra("store_phone", take.getStore_phone());
                Log.i("TAG", "STORE_phone=" + take.getStore_phone());
                mContext.startActivity(intent);

            }
        });
        vHolder.linear_take_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //手机ING
                Log.i("TAG", "PHONE==" + take.getStore_phone());
                listener.onClick(take.getStore_phone());
            }
        });
        vHolder.linear_zuobiao.setOnClickListener(new View.OnClickListener() {
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
        @Bind(R.id.image_join_order)
        ImageView image_join_order;
        @Bind(R.id.linear_take_telephone)
        LinearLayout linear_take_telephone;
        @Bind(R.id.linear_zuobiao)
        LinearLayout linear_zuobiao;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
