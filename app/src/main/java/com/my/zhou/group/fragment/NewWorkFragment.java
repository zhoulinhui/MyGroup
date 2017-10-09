package com.my.zhou.group.fragment;


import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zhou.group.OrderActivity;
import com.my.zhou.group.R;
import com.my.zhou.group.adapter.NewWorkAdapter;
import com.my.zhou.group.bean.NewWork;
import com.my.zhou.group.bean.Order;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.data.GetNewWorkData;
import com.my.zhou.group.data.INewWorkInterface;
import com.my.zhou.group.data.ITakeInterface;
import com.my.zhou.group.utils.ACache;
import com.my.zhou.group.utils.DialogUtil;
import com.my.zhou.group.utils.NetWorkUtil;
import com.my.zhou.group.utils.ProgressDialogUtils;
import com.my.zhou.group.view.DrawableCenterButton;


import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewWorkFragment extends Fragment implements INewWorkInterface {
    ListView listView;
    NewWorkAdapter adapter;
    List<String> lists;
    @Bind(R.id.listView_newwork)
    ListView listView_newWork;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.text_no_list)
    TextView text_no_list;

    private ACache mCache;

    public NewWorkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_work, container, false);
        ButterKnife.bind(this, view);
        setListener();
        // mCache = ACache.get(getActivity());
        return view;
    }

    /**
     * 下拉刷新控件监听
     */
    private void setListener() {
        //设置下拉刷新的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
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

    private String uid;

    @Override
    public void onStart() {
        super.onStart();
        //判断uid，如果存了，根据存的uid直接调接口获取用户资料
        SharedPreferences sharedPre = getActivity().getSharedPreferences("group", MODE_PRIVATE);
        uid = sharedPre.getString("uid", "nouid");
        Log.i("TAG", "UID=" + uid);
        if (!uid.equals("nouid")) {//uid存在

            newWorksDatas(uid);

        } else {
            Toast.makeText(getActivity(), "数据请求错误", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 联网获取数据
     */
    private GetNewWorkData newWorks;
    private ProgressDialogUtils pro;

    private void newWorksDatas(String i) {
//        pro = new ProgressDialogUtils();
//        pro.showProgressDialog(getActivity(), "加载中···");
        uid = i;
//        newWorks = new GetNewWorkData(NewWorkFragment.this);
//        newWorks.setNewWorkList(uid);
        if (!NetWorkUtil.isNetworkConnected()) {
            Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_LONG).show();
            return;
        }
        if (!NetWorkUtil.isWifiConnected()) {
            DialogUtil.showUse4GWatchVideo(getActivity(), new DialogUtil.OnPositiveListener() {
                @Override
                public void onPositive() {
                    newWorks = new GetNewWorkData(NewWorkFragment.this);
                    newWorks.setNewWorkList(uid);
                }
            });

        } else {
            newWorks = new GetNewWorkData(NewWorkFragment.this);
            newWorks.setNewWorkList(uid);
        }
    }

    /**
     * 实现接口方法，接收数据
     *
     * @param list
     */
    private List<NewWork> listNewWorks = new ArrayList<NewWork>();

    @Override
    public void setNewWork(List<NewWork> list) {
        Log.i("network", "PPPPPPPPf=" + list);
        if (listNewWorks == null) {
            listNewWorks = new ArrayList<>();
        }
        listNewWorks.clear();
        listNewWorks.addAll(list);

        ((INewWorkInterface) getActivity()).setNewWork(listNewWorks);
        Log.i("TAG", "listNewWork=" + listNewWorks);

        if (listNewWorks.size() == 0 || listNewWorks == null) {
            text_no_list.setVisibility(View.VISIBLE);
            //Toast.makeText(getActivity(), "数据请求错误", Toast.LENGTH_SHORT).show();
        } else {
//            //使用getAsObject()，直接进行强转
            //ArrayList<NewWork> carLocations = (ArrayList<NewWork>) mCache.getAsObject("car");
            text_no_list.setVisibility(View.GONE);
            if (adapter == null) {
                adapter = new NewWorkAdapter(getContext(), listNewWorks);
                listView_newWork.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
        // pro.closeProgressDialog();

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    newWorksDatas(uid);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    public void sendNewWorkSize(List<NewWork> nSizes) {


    }
}
