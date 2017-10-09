package com.my.zhou.group.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.my.zhou.group.R;
import com.my.zhou.group.adapter.TakeAdapter;
import com.my.zhou.group.bean.Take;
import com.my.zhou.group.data.GetTakeData;
import com.my.zhou.group.data.ITakeInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TakeFragment extends Fragment {
    private ImageView image_join;
    @Bind(R.id.listView_take)
    ListView listView_take;
    @Bind(R.id.swipe_take)
    SwipeRefreshLayout swipeRefreshLayout;

    public TakeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_put, container, false);
        ButterKnife.bind(this, view);
        //setListener();

        return view;
    }

//    private void setListener() {
//        //设置下拉刷新的颜色
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light);
//        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
//        //设置下拉刷新监听
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Message msg = new Message();
//                msg.what = 1;
//                mHandler.sendMessageDelayed(msg, 2000);
//            }
//        });
//
//    }
//
//    private String uid;
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        //判断uid，如果存了，根据存的uid直接调接口获取用户资料
//        SharedPreferences sharedPre = getActivity().getSharedPreferences("group", MODE_PRIVATE);
//        uid = sharedPre.getString("uid", "nouid");
//        Log.i("TAG", "UID=" + uid);
//        if (!uid.equals("nouid")) {//uid存在
//            takeDatas(uid);
//        } else {
//            Toast.makeText(getActivity(), "数据请求错误", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private GetTakeData takes;
//
//    private void takeDatas(String i) {
//        uid = i;
//        takes = new GetTakeData(TakeFragment.this);
//        takes.setTakeLists(uid);
//    }
//
//private List<Take> listTakes=new ArrayList<>();
//    @Override
//    public void setTake(List<Take> list) {
//        listTakes=list;
//        Log.i("TAG", "TakeList=" + listTakes);
//        if (listTakes == null) {
//            Toast.makeText(getActivity(), "数据请求错误", Toast.LENGTH_SHORT).show();
//        } else {
//            TakeAdapter adapter=new TakeAdapter(getContext(),listTakes);
//            listView_take.setAdapter(adapter);
//
//        }
//    }
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    takeDatas(uid);
//                    swipeRefreshLayout.setRefreshing(false);
//                    break;
//            }
//        }
//    };
}
