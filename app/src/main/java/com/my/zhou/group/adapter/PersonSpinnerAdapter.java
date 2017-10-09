package com.my.zhou.group.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.zhou.group.R;
import com.my.zhou.group.bean.Region;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public class PersonSpinnerAdapter extends BaseAdapter {
    Context context;
    List<Region> list;

    public PersonSpinnerAdapter(Context context,
                                List<Region> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.spinner_item_person, null);
            v = new ViewHolder();
            v.textView = (TextView) convertView.findViewById(R.id.tv_spinner_person);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.textView.setText(list.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
    }
}
