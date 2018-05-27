package com.silence.rootfeature.utils;

import android.view.View;

import com.silence.rootfeature.app.C;
import com.silence.rootfeature.logger.LogManager;

/**
 * View相关工具类，提供便捷，统一的操作
 *
 * @author violet
 * @date 2018/3/25 11:15
 */

public final class ViewUtil {

    /**
     * 判断View的Measure mode 是否为{@link android.view.View.MeasureSpec#UNSPECIFIED}
     * @param widthOrHeightMeasureSpec
     * @return
     */
    public static boolean isModeUnspecified(int widthOrHeightMeasureSpec){
        boolean result = false;
        int mode = View.MeasureSpec.getMode(widthOrHeightMeasureSpec);
        if(mode == View.MeasureSpec.UNSPECIFIED){
            result = true;
        }
        LogManager.getInstance().d("isModeUnspecified = "+ result);
        return result;
    }

    /**
     * 判断View的Measure mode 是否为{@link android.view.View.MeasureSpec#EXACTLY}
     * @param widthOrHeightMeasureSpec
     * @return
     */
    public static boolean isModeExactly(int widthOrHeightMeasureSpec){
        boolean result = false;
        int mode = View.MeasureSpec.getMode(widthOrHeightMeasureSpec);
        if(mode == View.MeasureSpec.EXACTLY){
            result = true;
        }
        LogManager.getInstance().d("isModeExactly = "+ result);
        return result;
    }

    /**
     * 判断View的Measure mode 是否为{@link android.view.View.MeasureSpec#AT_MOST}
     * @param widthOrHeightMeasureSpec
     * @return
     */
    public static boolean isModeAtMost(int widthOrHeightMeasureSpec){
        boolean result = false;
        int mode = View.MeasureSpec.getMode(widthOrHeightMeasureSpec);
        if(mode == View.MeasureSpec.AT_MOST){
            result = true;
        }
        LogManager.getInstance().d("isModeAtMost = "+ result);
        return result;
    }

    /**
     * 同时打印WidthMeasureSpec和HeightMeasureSpec的相关信息
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * @return
     */
    public static String displaySpecMode(int widthMeasureSpec, int heightMeasureSpec){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" width-mode = ");
        if(isModeAtMost(widthMeasureSpec)){
            stringBuilder.append("At_Most");
        }else if(isModeExactly(widthMeasureSpec)){
            stringBuilder.append("Exactly");
        }else if(isModeUnspecified(widthMeasureSpec)){
            stringBuilder.append("Unspecified");
        }
        stringBuilder.append("  width = " + View.MeasureSpec.getSize(widthMeasureSpec));

        stringBuilder.append(C.Strings.NEW_LINE_SEPERATOR);

        stringBuilder.append("height-mode = ");
        if(isModeAtMost(heightMeasureSpec)){
            stringBuilder.append("At_Most");
        }else if(isModeExactly(heightMeasureSpec)){
            stringBuilder.append("Exactly");
        }else if(isModeUnspecified(heightMeasureSpec)){
            stringBuilder.append("Unspecified");
        }
        stringBuilder.append("  height = " + View.MeasureSpec.getSize(heightMeasureSpec));

        String result = stringBuilder.toString();

        LogManager.getInstance().d(result);
        return result;
    }
}
