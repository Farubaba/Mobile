package com.silence.rootfeature.aop;

/**
 * Created by violet on 16/2/25.
 */
public interface Advice {
    /**
     * 前置AOP
     * @return 是否执行成功，默认true
     */
    void beforeAdvice();

    /**
     * 后置AOP
     * @return  是否执行成功，默认true
     */
    void afterAdvice();
}
