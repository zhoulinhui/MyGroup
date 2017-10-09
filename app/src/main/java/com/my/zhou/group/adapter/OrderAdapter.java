package com.my.zhou.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.zhou.group.R;
import com.my.zhou.group.bean.Order;
import com.my.zhou.group.bean.OrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/20.
 */

public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderItem> mListOrder = new ArrayList<>();

    public OrderAdapter(Context context, List<OrderItem> listOrder) {
        this.mContext = context;
        this.mListOrder = listOrder;
    }

    @Override
    public int getCount() {
        return mListOrder.size();
    }

    @Override
    public OrderItem getItem(int position) {
        return mListOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inflate_order_item, null);
            vHolder = new ViewHolder(convertView);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();

        }
        OrderItem orderItem = getItem(position);
        vHolder.textView_name.setText(orderItem.getGoodName());
        vHolder.textView_number.setText("x" + orderItem.getGoodNum());
        vHolder.textView_money.setText(orderItem.getGoodPrice() + "元");

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.text_order_name)
        TextView textView_name;
        @Bind(R.id.text_order_number)
        TextView textView_number;
        @Bind(R.id.text_order_money)
        TextView textView_money;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
