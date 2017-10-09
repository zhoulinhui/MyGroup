package com.my.zhou.group.constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/4/10.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
//        判断borad接受的类型
        if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            //获得message的内容
            Bundle bundle = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            //吐司自定义内容
            Toast.makeText(context, "message title" + title + "content:" + message, Toast.LENGTH_LONG).show();
        }
    }

}