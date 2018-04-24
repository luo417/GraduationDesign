package com.holy.common.app;


import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

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

    public static Application getInstance(){return mInstance;}

    public static File getCacheDirFile() {
        return mInstance.getCacheDir();
    }

    /**
     * 获取头像的临时存储地址
     * @return
     */
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

    public static void showToast(final String msg){
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(mInstance, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToast(@StringRes int msgID){
        showToast(mInstance.getString(msgID));
    }
}
