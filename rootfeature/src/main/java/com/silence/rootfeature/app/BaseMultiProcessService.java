package com.silence.rootfeature.app;

import android.content.Context;

import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.ToastUtil;

/**
 * @author violet
 * date 2018/10/3 13:36
 */
public class BaseMultiProcessService implements MultiProcessManager.MultiProcessService {

    @Override
    public void onAllProcess(Context context, String processName) {
        /**
         * 日志管理类需要首先初始化，其他所有地方都有可能使用到它
         */
        LogManager.getInstance().init();
        /**
         * Toast提示是全局可以使用的提示，需要优先初始化，其他仍和地方都有可能用到它。
         */
        ToastUtil.init(context);
        /**
         * 将appContext保存到AppUtil中，供其他地方全局使用，此初始化必须有限初始化。
         */
        AppUtil.init(context);

        LogManager.getInstance().dt(processName, "processName = " + AppUtil.getCurrentProcessName() + " initMultiProcess");
    }

    @Override
    public void onMainProcess(Context context, String processName) {
        ToastUtil.init(AppUtil.getAppContext());
        //test
        LogManager.getInstance().dt(processName, "initMainProcess");
        ToastUtil.show("mainProcessInit");
    }

    @Override
    public void onChildProcess(Context context, String processName) {

    }
}
