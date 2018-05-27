package com.silence.rootfeature.app;

import android.app.Application;

/**
 * 自定义Application类，处理以下任务：<br>
 *     <code>
 *         1、多进程
 *         2、系统初始化
 *         3、全局数据保存
 *         4、
 *     </code>
 * @author violet
 * @date 2018/3/5 10:13
 */

public class MobileApplication extends Application {
    public static final String TAG = MobileApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        MultiProcessInitializer.initProcesses(this);
    }

    /**
     * 可以在不同组件的该方法中实现一些内存优化。
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
