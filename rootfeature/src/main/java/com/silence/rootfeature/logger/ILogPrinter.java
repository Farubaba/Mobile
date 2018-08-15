package com.silence.rootfeature.logger;

import com.orhanobut.logger.Printer;

import java.io.File;

/**
 * @author violet
 * @date 2018/3/10 18:31
 */

public interface ILogPrinter extends Printer {
    //#################自定义one-time TAG####################
    void dt(String TAG, String message, Object... args);

    void dt(String TAG, Object object);

    void dt(String TAG, File file);

    void et(String TAG, String message, Object... args);

    void et(String TAG, Throwable throwable, String message, Object... args);

    void wt(String TAG, String message, Object... args);

    void it(String TAG, String message, Object... args);

    void vt(String TAG, String message, Object... args);

    void wtft(String TAG, String message, Object... args);

    /**
     * Formats the given json content and print it
     */
    void jsont(String TAG, String json);

    /**
     * Formats the given xml content and print it
     */
    void xmlt(String TAG, String xml);
}
