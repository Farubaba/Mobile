package com.silence.rootfeature;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.silence.rootfeature.logger.LogManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.silence.rootfeature.test", appContext.getPackageName());
    }

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
}
