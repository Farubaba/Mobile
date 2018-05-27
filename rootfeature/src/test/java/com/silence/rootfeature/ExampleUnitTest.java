package com.silence.rootfeature;

import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.FormatUtil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void formatCurrency(){
        String value = FormatUtil.formatLong(Calendar.getInstance().getTimeInMillis());
        Matcher<String> matcher = new Matcher<String>() {
            @Override
            public boolean matches(Object item) {
                if(item.toString().length() == 8 ){
                    return true;
                }
                return false;
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        };
        Assert.assertThat(value, matcher);
        Logger.getGlobal().info(value);
        LogManager.getInstance().d(value);
    }
}