package com.silence.mobile.trainning.okhttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.ConcurrentUtil;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author violet
 * date :  2018/8/16 15:18
 */
public class OkHttpDemo {

    String host = "http://localhost:8080/";
    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    String json;
    public void getJson(){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Request request = new Request.Builder()
                .url(host +"mobile-server/api/sys/config/v1")
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String result = response.body().string();
            LogManager.getInstance().d(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    ConcurrentUtil.countDown(countDownLatch);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                json = body.string();
                LogManager.getInstance().d(json);
                System.out.println(json);
                ConcurrentUtil.countDown(countDownLatch);
            }
        });
        ConcurrentUtil.await(countDownLatch);
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public void postJson() throws IOException {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(new Data("test",10,null));
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(host +"mobile-server/api/sys/config/v1")
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        System.out.println(result);
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    /**
     * post什么数据给服务器，服务器就返回什么数据
     *
     * @throws IOException
     */
    public void postAtoServerReturnA(String url) throws IOException {
        Gson gson = new GsonBuilder().create();
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,postBody);
        Request request = new Request.Builder()
                .url(host + url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        System.out.println(result);
    }
}

