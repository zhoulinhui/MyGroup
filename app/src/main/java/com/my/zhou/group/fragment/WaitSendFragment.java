package com.my.zhou.group.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.my.zhou.group.R;
import com.my.zhou.group.adapter.PutAdapter;
import com.my.zhou.group.bean.Put;
import com.my.zhou.group.data.GetPutData;
import com.my.zhou.group.data.IPutInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitSendFragment extends Fragment implements IPutInterface, PutAdapter.PhoneOnClickListener1 {
    @Bind(R.id.listView_again_send)
    ListView listView_again_send;
    @Bind(R.id.swipe_again_send)
    SwipeRefreshLayout swipeRefreshLayout;

    public WaitSendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wait_send, container, false);
        ButterKnife.bind(this, view);
        setListener();
        return view;
    }

    private String uid;

    @Override
    public void onStart() {
        super.onStart();
        //判断uid，如果存了，根据存的uid直接调接口获取用户资料
        SharedPreferences sharedPre = getActivity().getSharedPreferences("group", MODE_PRIVATE);
        uid = sharedPre.getString("uid", "nouid");
        Log.i("TAG", "UID=" + uid);
        if (!uid.equals("nouid")) {//uid存在
            putDatas(uid);
        } else {
            Toast.makeText(getActivity(), "数据请求错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void setListener() {
        //设置下拉刷新的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);
        //swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setProgressViewEndTarget(false, 100);
        //设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessageDelayed(msg, 2000);
            }
        });

    }

    private GetPutData puts;

    private void putDatas(String i) {
        uid = i;
        puts = new GetPutData(WaitSendFragment.this);
        puts.sendPutDatas(uid);
    }

    private List<Put> listPuts = new ArrayList<>();

    @Override
    public void setPut(List<Put> list) {
        listPuts = list;
        //将fragment的listPuts放到接口中
        ((IPutInterface) getActivity()).setPut(listPuts);
        Log.i("TAG", "ListPut=" + listPuts);
        if (listPuts == null) {
            //Toast.makeText(getActivity(), "数据请求错误", Toast.LENGTH_SHORT).show();
        } else {
            PutAdapter adapter = new PutAdapter(getContext(), listPuts);
            adapter.setOnClickListener1(this);
            listView_again_send.setAdapter(adapter);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    putDatas(uid);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    private String phone1;

    @Override
    public void onClick1(String phone) {
        phone1 = phone;
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            //拨打电话
            call(phone1);
        }
    }

    /**
     * 拨打电话功能
     */
    private void call(String phone1) {
        try {
            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone1));
            startActivity(phoneIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //同意则拨打电话
                    call(phone1);
                } else {
                    //否则，提示用户
                    Toast.makeText(getActivity(), "请设置权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
