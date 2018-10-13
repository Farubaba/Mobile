package com.silence.mobile;

import com.google.gson.GsonBuilder;
import com.silence.mobile.trainning.okhttp.OkHttpDemo;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author violet
 * date :  2018/8/16 21:30
 */
public class OkHttpTest {

    OkHttpDemo okHttpDemo;
    @Before
    public void setUp(){
        okHttpDemo = new OkHttpDemo();
    }

    @Test
    public void gsonTest(){
       String json = new GsonBuilder().create().toJson("简单的字符串，怎么toJson");
       System.out.println(json);
    }

    @Test
    public void getJsonTest(){
        okHttpDemo.getJson();
    }

    @Test
    public void postJson(){
        try {
            okHttpDemo.postJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPostAndReceive(){
        try {
            okHttpDemo.postAtoServerReturnA("mobile-server/api/sys/config/vn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveToFile(){
        try {
            okHttpDemo.postAtoServerReturnA("mobile-server/api/sys/config/doSveToFile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
