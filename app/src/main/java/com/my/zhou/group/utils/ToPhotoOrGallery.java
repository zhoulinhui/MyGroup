package com.my.zhou.group.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

/**
 * 调用系统相机，读取图库，判断SD卡是否可用
 * Created by wangjunqiang on 2016/12/9.
 */
public class ToPhotoOrGallery {

    /**
     * 调用相册
     */
    public static Intent gallery(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }


    /**
     * 调用摄像头拍照
     * callback 接口类型的数据
     */
    public static File takePhotos(Activity context, Callback callback) {
        long l = SystemClock.currentThreadTimeMillis();
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            //拼接文件名
            File file = new File(context.getExternalFilesDir("image"), "liushao_image" + l + ".jpg");
            //检查是否有相同文件名存在，有则删除
            if (file.exists()) {
                file.delete();
            }

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            /**
             * MediaStore媒体提供者包含所有可用的媒体内部的元数据和外部存储设备
             * 用来显示内容的名称解析器 Uri用于存储请求的图像或视频
             * 第2参数：从一个文件中创建一个Uri    URI的形式文件为绝对路径
             */
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            //把数据通过接口传递出去
            callback.callBack(intent);
            return file;

        } else {
            Toast.makeText(context, "sd卡异常", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 判断SD卡是否可用
     * getExternalStorageState()当前状态的主要共享/外部存储媒体。
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 定义内部接口，用于回调传递数据
     */
    public interface Callback {
        void callBack(Intent in);
    }
}
