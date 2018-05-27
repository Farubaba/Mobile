package com.silence.rootfeature;

import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.validators.EmailAddressValidator;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author violet
 * @date 2018/3/11 17:26
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class RobolectricTest {
    @Test
    public void testValidator(){
        EmailAddressValidator emailAddressValidator = new EmailAddressValidator(new TargetValueCreator<String>() {
            @Override
            public String createTargetValue() {
                return "xxx@xxx.com";
            }
        });
        Assert.assertTrue(emailAddressValidator.getTargetValue()+ " 不是合法email地址。",emailAddressValidator.validation());
    }
}
