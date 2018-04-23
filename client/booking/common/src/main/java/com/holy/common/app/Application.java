package com.holy.common.app;


import android.os.SystemClock;

import java.io.File;

/**
 * @author Hailin
 * @time 2018/4/23 19:20
 * @function
 */

public class Application extends android.app.Application {
    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static File getCacheDirFile() {
        return mInstance.getCacheDir();
    }

    public static File getPortraitTmpFile() {
        File dir = new File(getCacheDirFile(), "portrait");
        dir.mkdirs();

        //删除旧的缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) f.delete();
        }

        File file = new File(dir, SystemClock.currentThreadTimeMillis() + ".jpg");
        return file.getAbsoluteFile();
    }
}
