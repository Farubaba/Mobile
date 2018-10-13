package com.silence.rootfeature.app;

import android.content.Context;

/**
 * app进程初始化工具类，集中处理application中对各个进程的的初始化工作
 *
 *
 * @author violet
 * date :  2018/3/5 10:36
 */

public class MultiProcessManager {

    public static final String TAG = MultiProcessManager.class.getSimpleName();
    /**
     * 进程名称，和AndroidManifest.xml中定义的进程名称一致
     * 例如：app私有进程形如：com.android.training:private_process
     *      全局进程形如：com.test.global_process
     */
    private static final String TEST_PRIVATE_PROCESS_NAME = "com.android.training:private_process";
    private static final String TEST_GLOBAL_PROCESS_NAME = "com.test.global_process";

    /**
     * 根据不同的进程，完成不同的逻辑。
     */
    public static void initProcesses(Context context, MultiProcessService processService) {
        String currentProcessName = AppUtil.getCurrentProcessName();
        if(processService != null){
            processService.onAllProcess(context, currentProcessName);
            if(AppUtil.isMainProcess()){
                processService.onMainProcess(context, currentProcessName);
            }else{
                processService.onChildProcess(context, currentProcessName);
            }
        }
    }


    public interface MultiProcessService {
        void onAllProcess(Context context, String processName);
        void onMainProcess(Context context, String processName);
        void onChildProcess(Context context, String processName);
    }

}

