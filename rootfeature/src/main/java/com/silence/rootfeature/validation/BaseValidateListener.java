package com.silence.rootfeature.validation;

/**
 * 通用的验证回调接口，如果需要有通用的回调处理逻辑，应该继承自该类。
 * Created by violet on 16/2/24.
 */
public abstract class BaseValidateListener implements ValidateListener {
    public String TAG = getClass().getSimpleName();

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailed() {

    }
}
