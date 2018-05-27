package com.silence.rootfeature.validation;



import com.silence.rootfeature.aop.Advice;

import java.util.List;

/**
 * 校验器，一般用于检查条件是否满足。
 * 为了能够实现可插拔的条件校验流程而设计。
 *
 * Created by violet on 16/2/24.
 */
public interface Validator<T> extends ActionCallback , Advice {
    /**
     * 获取TargetValue生成器
     * @return
     */
    TargetValueCreator getTargetValueCreator();

    /**
     * 设置TargetValueCreator
     * @param targetValueCreator
     * @return
     */
    Validator setTargetValueCreator(TargetValueCreator<T> targetValueCreator);

    /**
     * 获取当前Validator的前置验证器列表
     * @return
     */
    List<Validator<T>> getBeforeValidators();

    /**
     * 设置当前Validator的前置验证器列表
     * @param beforeValidators
     * @return
     */
    Validator setBeforeValidators(List<Validator<T>> beforeValidators);

    /**
     * 添加一个前置验证器
     * @param beforeValidator
     * @return
     */
    Validator addBeforeValidator(Validator<T> beforeValidator);

    /**
     * 执行所有的前置验证器
     * @return
     */
    boolean beforeValidate();

    /**
     * 获取待校验的值
     * @return
     */
    T getTargetValue();

    /**
     * 执行校验
     * @return
     */
    boolean validate();

    /**
     * 完整的校验逻辑，子类需要覆写该方法以实现自己的校验逻辑。
     * 不要覆写{@link #validate()}方法，因为在该方法中会执行AOP逻辑,及{@link #beforeAdvice()} 和{@link #afterAdvice()}
     * @return
     */
    boolean validation();

    /**
     * 获取当前Validator的类唯一标识
     * @return
     */
    String getValidatorIdentify();

    /**
     * 设置当前validator类的唯一标识
     * @param validatorIdentify
     * @return
     */
    Validator setValidatorIdentify(String validatorIdentify);

    /**
     * 判断是否在检测到某一条件校验失败后，终止校验流程。
     * @return
     */
    boolean isStopOnValidateFailed();

    /**
     * 设置，当某一条件校验失败后，是否需要终止后续校验流程。
     * @param stopOnValidateFailed
     * @return
     */
    Validator setStopOnValidateFailed(boolean stopOnValidateFailed);

    /**
     * 判断是否需要跳过当前validator对象的onPass回调
     * @return
     */
    boolean isIgnoreOnSuccess();

    /**
     * 设置是否跳过当前Validator的onPass回调方法
     * @param ignoreOnSuccess
     * @return
     */
    Validator setIgnoreOnSuccess(boolean ignoreOnSuccess);

    /**
     * 判断是否需要跳过当前Validator对象的onFailed回调方法
     * @return
     */
    boolean isIgnoreOnFailed();

    /**
     * 设置是否需要跳过当前Validator对象的onFailed
     * @param ignoreOnFailed
     * @return
     */
    Validator setIgnoreOnFailed(boolean ignoreOnFailed);
}
