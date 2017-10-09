package com.my.zhou.group.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by tarena on 2016/10/10.
 */
public class DialogUtil {

    public interface OnPositiveListener {
        void onPositive();
    }

    public static void showUse4GWatchVideo(Context context, final OnPositiveListener onPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("系统提示")
                .setMessage("您确定要在无WIFI情况下使用吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPositiveListener.onPositive();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    public static void showSureLogOut(Context context, final OnPositiveListener onPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("消息提示")
                .setMessage("登出后无法收藏和评论 , 您确定要退出吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPositiveListener.onPositive();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null).show();
    }

}
