package com.my.zhou.group.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.my.zhou.group.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public class CameraAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> contacts;
    private View view;
    private ImageView image_camera_show;
    private ImageView image_camera_delete;

    public CameraAdapter(Context context, List<Bitmap> contacts) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //根据position确定需要显示的数据
        Bitmap bitmap = contacts.get(position);
        if (bitmap == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.inflate_camera_moren, null);
        } else {
            //将XML设计为模板，变成JAVA中的对象（实例化）
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.inflate_image_camera, null);
            //从整个的模板对象中找到具体显示数据的控件
            image_camera_show = (ImageView) view.findViewById(R.id.image_camera_show);
            image_camera_delete = (ImageView) view.findViewById(R.id.image_camera_delete);
            //将第position个数据显示到模板中
            image_camera_show.setImageBitmap(bitmap);
            //给控件添加监听
        }
        return view;
    }
}
