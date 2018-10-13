package com.silence.basefeature;

import com.silence.rootfeature.aop.Advice;
import com.silence.rootfeature.app.C;

/**
 * @author violet
 * date 2018/9/30 14:25
 */
public class TestTransitiveDependencies {

    public void testTransitive(){
        String testString = C.Strings.EMPTY;
        System.out.println(testString);
        Advice advice = new Advice() {
            @Override
            public void beforeAdvice() {

            }

            @Override
            public void afterAdvice() {

            }
        };

        advice.beforeAdvice();
        advice.afterAdvice();
    }
}
