package com.my.zhou.group.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.my.zhou.group.R;


/**
 * Created by tarena on 2016/12/17.
 */

public class CommonProgressDialog extends Dialog {

    public CommonProgressDialog(Context context) {
        super(context, R.style.commonProgressDialog);
        setContentView(R.layout.commonprogressdialog);
        //显示在屏幕中心
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    /**
     * 设置加载消息的方法
     *
     * @param s
     */

    public void setMessage(String s) {
        TextView textView = (TextView) this.findViewById(R.id.tv_loading);
        textView.setText(s);
    }
}
