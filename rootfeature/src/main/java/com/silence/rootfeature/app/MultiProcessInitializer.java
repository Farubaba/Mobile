package com.silence.rootfeature.app;

import android.content.Context;

import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.CompatUtil;
import com.silence.rootfeature.utils.ToastUtil;

/**
 * app进程初始化工具类，集中处理application中对各个进程的的初始化工作
 *
 *
 * @author violet
 * @date 2018/3/5 10:36
 */

public class MultiProcessInitializer {

    public static final String TAG = MultiProcessInitializer.class.getSimpleName();
    /**
     * 进程名称，和AndroidManifest.xml中定义的进程名称一致
     * 例如：app私有进程形如：com.android.training:private_process
     *      全局进程形如：com.test.global_process
     */
    public static final String TEST_PRIVATE_PROCESS_NAME = "com.android.training:private_process";
    public static final String TEST_GLOBAL_PROCESS_NAME = "com.test.global_process";

    /**
     * 根据不同的进程，完成不同的逻辑。
     */
    public static void initProcesses(Context context) {
        initMultiProcessFirst(context);
        if (AppUtil.isMainProcess()) {
            initMainProcess();
        } else if (AppUtil.isThatProcess(TEST_PRIVATE_PROCESS_NAME)) {
            LogManager.getInstance().dt(TAG, TEST_PRIVATE_PROCESS_NAME);
        } else if (AppUtil.isThatProcess(TEST_GLOBAL_PROCESS_NAME)) {
            LogManager.getInstance().dt(TAG, TEST_GLOBAL_PROCESS_NAME);
        }
        //TODO add other process handle if need.
    }

    /**
     * 首先初始化多个进程中都需要初始化的对象，例如：applicationContext
     *
     * @param appContext
     */
    public static void initMultiProcessFirst(Context appContext) {
        /**
         * 日志管理类需要首先初始化，其他所有地方都有可能使用到它
         */
        LogManager.getInstance().init();
        /**
         * Toast提示是全局可以使用的提示，需要优先初始化，其他仍和地方都有可能用到它。
         */
        ToastUtil.init(appContext);
        /**
         * 将appContext保存到AppUtil中，供其他地方全局使用，此初始化必须有限初始化。
         */
        AppUtil.init(appContext);

        LogManager.getInstance().dt(TAG, "processName = " + AppUtil.getCurrentProcessName() + " initMultiProcessFirst");
    }


    /**
     * 完成主进程初始化：<br>
     * 1.系统中自定义的模块初始化，例如初始化我们扩展的LogManager对象<br>
     * 2.初始化第三方库<br>
     * 3.初始化数据库<br>
     * 4.初始化多进程业务逻辑处理<br>
     * 5.其他初始化<br>
     */
    public static void initMainProcess() {
        ToastUtil.init(AppUtil.getAppContext());
        //test
        LogManager.getInstance().dt(TAG, "initMainProcess");
        ToastUtil.show("mainProcessInit");
    }
}

