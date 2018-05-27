package com.silence.rootfeature.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 简易的Toast工具类，快速生成Toast，并防止重复Toast提示和长时间不消失的问题。
 */
public final class ToastUtil {
    private static Toast mToast;
    private static String mEmptyString = "";
    private static Context  applicationContext;
    /**
     * 初始化Toast
     * @return
     */
    public static Toast init(Context context){
        if(mToast == null){
            if(context != null){
                applicationContext = context.getApplicationContext();
                mToast = Toast.makeText(applicationContext, mEmptyString, Toast.LENGTH_LONG);
            }
        }
        return mToast;
    }

    /**
     * 长时间的toast
     * @param toastString
     */
    public static void show(String toastString){
        init(applicationContext);
        mToast.setText(toastString);
        mToast.show();
    }

    public static void show(int toastStringResId){
        init(applicationContext);
        mToast.setText(toastStringResId);
        mToast.show();
    }


    /**
     * 显示Short 或者Long duration的Toast
     * @param toastStringResId
     * @param duration Toast.LENGTH_SHORT  or Toast.LENGTH_LONG;
     */
    public static void show(int toastStringResId, int duration){
        init(applicationContext);
        mToast.setText(toastStringResId);
        mToast.setDuration(duration);
        mToast.show();
    }

    /**
     * 显示Short 或者Long duration的Toast
     * @param toastString
     * @param duration Toast.LENGTH_SHORT  or Toast.LENGTH_LONG;
     */
    public static void show(String toastString, int duration){
        init(applicationContext);
        mToast.setText(toastString);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void showLongToast(String toastString){
        show(toastString, Toast.LENGTH_LONG);
    }

    public static void showShortToast(String toastString){
        show(toastString, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(int toastStringResId){
        show(toastStringResId, Toast.LENGTH_LONG);
    }

    public static void showShortToast(int toastStringResId){
        show(toastStringResId, Toast.LENGTH_SHORT);
    }

}
