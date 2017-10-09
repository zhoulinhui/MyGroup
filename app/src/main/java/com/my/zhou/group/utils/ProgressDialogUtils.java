package com.my.zhou.group.utils;

import android.app.Activity;
import android.util.Log;

import com.my.zhou.group.view.CommonProgressDialog;


/**
 * Created by tarena on 2016/12/17.
 * 弹窗工具类
 */

public class ProgressDialogUtils {
    private Activity activity;
    private CommonProgressDialog dialog;
    String TAG = "TAG";

    //显示方法
    public void showProgressDialog(Activity activity, String msg) {
        this.activity = activity;
        if (dialog == null) {
            dialog = new CommonProgressDialog(activity);
        }
        if (msg == null) {
            msg = "正在加载...";
        }


        if (!activity.isFinishing() && !dialog.isShowing()) {
            dialog.show();
        }
        dialog.setMessage(msg);
    }

    //关闭方法
    public void closeProgressDialog() {
        Log.i(TAG, "closeProgressDialog: -----");
        if (dialog != null && !activity.isFinishing()) {
            Log.i(TAG, "closeProgressDialog: ");
            dialog.dismiss();
            dialog = null;
        }
    }

}
