package com.silence.rootfeature.base;

import android.app.Activity;
import android.content.Intent;

/**
 * @author violet
 * @date 2018/4/28 14:13
 */

public interface ActivityPressenter {

    /**
     * showLoading和dismissLoading都应该通过发送事件消息来控制，避免处处直接调用。
     * @param activity
     */
    void showLoading(Activity activity);
    void parseRequestParameter(Intent intent);
    void init();
    /**
     * 根据请求参数分发请求，例如：是否onNewIntent请求
     * @param intent
     */
    void dispatchRequest(Intent intent);
    void sendRequest(Activity activity);
    void pageRequest(Activity activity);

    void parseResult(Activity activity);
    void showResultPage();
    void showErrorPage();
    void showEmptyPage();

    void dismissLoading(Activity activity);

    void closeActivity();
    void clear();
}
