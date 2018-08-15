package com.silence.rootfeature.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.silence.rootfeature.logger.LogManager;

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

    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            LogManager.getInstance().d(TAG, "onActivityCreated() called with: activity = [" + activity + "], savedInstanceState = [" + savedInstanceState + "]");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            LogManager.getInstance().d(TAG, "onActivityStarted() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogManager.getInstance().d(TAG, "onActivityResumed() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogManager.getInstance().d(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            LogManager.getInstance().d(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogManager.getInstance().d(TAG, "onActivitySaveInstanceState() called with: activity = [" + activity + "], outState = [" + outState + "]");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogManager.getInstance().d(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        MultiProcessInitializer.initProcesses(this);
        registerActivityLifecycleCallbacks(mCallbacks);
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
