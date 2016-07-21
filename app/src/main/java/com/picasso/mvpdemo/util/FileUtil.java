package com.picasso.mvpdemo.util;

import android.os.Environment;

import com.picasso.mvpdemo.App;

import java.io.File;

/**
 * 作者：Picasso on 2016/7/21 13:18
 * 详情：
 */
public class FileUtil {
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getAvailableCacheDir() {
        if (isExternalStorageWritable()) {
            return App.app.getExternalCacheDir();
        } else {
            return App.app.getCacheDir();
        }
    }
}
