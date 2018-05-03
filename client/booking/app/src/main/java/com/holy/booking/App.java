package com.holy.booking;

import com.holy.common.app.Application;
import com.holy.factory.Factory;
import com.igexin.sdk.PushManager;

/**
 * @author Hailin
 * @time 2018/4/23 19:34
 * @function
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        PushManager.getInstance().initialize(this);
    }
}
