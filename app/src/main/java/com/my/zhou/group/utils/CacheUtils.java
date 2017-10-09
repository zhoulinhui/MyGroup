package com.my.zhou.group.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/6/21.
 */
public class CacheUtils {

    /**
     * 保存软件参数
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("cunchu", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 得到软件保存的参数
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("cunchu", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /*
      保存软件参数（String类型的）
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("u_id", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("u_id", Context.MODE_PRIVATE);
        return sp.getString(key, "返回的uid");
    }

    //存储personpager的登录状态
    public static void putBooleanpp(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("pp", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBooleanpp(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("pp", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    //存储five的登录状态
    public static void putBooleanff(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("ff", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBooleanff(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("ff", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}

