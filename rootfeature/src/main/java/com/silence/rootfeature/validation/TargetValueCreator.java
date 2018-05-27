package com.silence.rootfeature.validation;

/**
 * 用于生产TargetValue的工厂类
 * Created by violet on 16/2/29.
 */
public interface TargetValueCreator<T> {
    T createTargetValue();
}
