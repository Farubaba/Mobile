package com.silence.rootfeature.validation;

/**
 * 动作执行回调，一个动作(Action):<br>
 * 要么执行成功回调{@link #onSuccess()};<br>
 * 要么执行失败回调{@link #onFailed()};<br>
 *
 * Created by violet on 16/2/26.
 */
public interface ActionCallback {
    /**
     * 动作执行成功，回调该方法
     * @return
     */
    void onSuccess();

    /**
     * 动作执行失败,回调该方法。
     * @return
     */
    void onFailed();
}
