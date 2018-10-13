package com.silence.rootfeature;
import android.support.test.runner.AndroidJUnit4;

import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Printer;
import com.silence.rootfeature.logger.ILogPrinter;
import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.validators.EmailAddressValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * @author violet
 * date :  2018/3/11 14:14
 */
@RunWith(AndroidJUnit4.class)
public class LogManagerInstrumentedTest {
    public final String TAG = getClass().getSimpleName();

    @Before
    public void init(){
        LogManager.getInstance().init();
    }

    @Test
    public void logWithDefaultTAG(){
        LogManager.getInstance().d("1. method %1$s", "d.(x,y)");
        LogManager.getInstance().d("2. method d(object)");
        LogManager.getInstance().e("3. method %1$s with %2$s","e()","2 parameters");
        LogManager.getInstance().w("4. method %1$s", "w(1param)");
        LogManager.getInstance().json("{a:'111',b:'222'}");
        LogManager.getInstance().v("5. method v() with %1$s", "1 param");
    }

    @Test
    public void testValidatorUtil(){
        EmailAddressValidator emailAddressValidator = new EmailAddressValidator(new TargetValueCreator<String>() {
            @Override
            public String createTargetValue() {
                return "xxx@xxx.com";
            }
        });

        //assertTrue(emailAddressValidator.validation());
        assertTrue(emailAddressValidator.getTargetValue() + "：不是合法的Email地址",emailAddressValidator.validation());
    }
}
