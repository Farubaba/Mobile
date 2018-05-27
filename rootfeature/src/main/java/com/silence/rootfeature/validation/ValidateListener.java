package com.silence.rootfeature.validation;

/**
 * 验证器回调接口
 * Created by violet on 16/2/24.
 */
public interface ValidateListener extends ActionCallback{
    /**
     * 校验通过，并且{@link #isIgnoreOnSuccess()} 返回false时，回调该方法
     * @return
     */
    void onSuccess();

    /**
     * 校验失败，并且{@link #isIgnoreOnFailed()} 返回false时,回调该方法。
     * @return
     */
    void onFailed();
}
