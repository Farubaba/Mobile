package com.silence.mobile;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author violet
 * date :  2018/8/21 14:58
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UiAutomatorTest {

    @Test
    public void uiAutomator(){
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        findAndClick(mDevice,"同意并继续");
        findAndClick(mDevice,"连接");
        findAndClick(mDevice,"检测配置");
        findAndClick(mDevice,"继续刷新");
        findAndClick(mDevice,"一键查询可用WiFi");
        //findAndClick(mDevice,"发现");

    }



    public void findAndClick(UiDevice mDevice, String displayName){
        try{
            UiObject view = mDevice.findObject(new UiSelector().text(displayName).className(View.class));
            view.click();
        }catch (Exception  e){

        }finally {

        }


        try{
            UiObject view = mDevice.findObject(new UiSelector().text(displayName).className(TextView.class));
            view.click();
        }catch (Exception  e){

        }finally {

        }

        try{
            UiObject view = mDevice.findObject(new UiSelector().text(displayName).className(Button.class));
            view.click();
        }catch (Exception  e){

        }finally {

        }
    }
}
