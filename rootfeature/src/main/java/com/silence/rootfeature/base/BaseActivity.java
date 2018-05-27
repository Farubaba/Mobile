package com.silence.rootfeature.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.silence.rootfeature.base.interf.AbsActivityDelegator;
import com.silence.rootfeature.base.interf.ActivityDelegator;
import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.DebugUtil;

/**
 * @author violet
 * @date 2018/3/23 11:59
 */

public class BaseActivity extends AppCompatActivity implements ActivityPressenter{

    protected String TAG = this.getClass().getSimpleName();

    protected ActivityDelegator activityDelegator = new AbsActivityDelegator(this) {
        @Override
        public void startActivity(Intent intent) {
            super.startActivity(intent);
        }

        @Override
        public Intent parseIntentParams(Intent intent) {
            return super.parseIntentParams(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDelegator.parseIntentParams(getIntent());

        getIntent().putExtra("key111","value111");
        LogManager.getInstance().d("key1111111111 ="+getIntent().getStringExtra("key111"));
        DebugUtil.displayIntentInfo(getIntent());
        DebugUtil.displayUri(getIntent().getData());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        activityDelegator.parseIntentParams(intent);
        //setIntent(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 避免出现 android.content.ActivityNotFoundException: No Activity found to handle Intent
     * 方案一：无论是否有Activity能处理这个Intent，都直接调用startActivity，并总是try catch异常。
     * {@link #startActivity(Intent)}内部最终也是调用了{@link #startActivityForResult(Intent, int)}
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        try{
            super.startActivity(intent);
        }catch (Exception e){
            //e.printStackTrace();
            LogManager.getInstance().e(e,e.getMessage());
        }
    }

    @Override
    public void showLoading(Activity activity) {

    }

    @Override
    public void parseRequestParameter(Intent intent) {

    }

    @Override
    public void init() {

    }

    @Override
    public void dispatchRequest(Intent intent) {

    }

    @Override
    public void sendRequest(Activity activity) {

    }

    @Override
    public void pageRequest(Activity activity) {

    }

    @Override
    public void parseResult(Activity activity) {

    }

    @Override
    public void showResultPage() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showEmptyPage() {

    }

    @Override
    public void dismissLoading(Activity activity) {

    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void clear() {

    }

    /**
     * 避免出现 android.content.ActivityNotFoundException: No Activity found to handle Intent。
     * 方案二：调用之前先查询是否有Activity能处理这个Intent，如果没有则不调用
     * @param intent
     */
//    @Override
//    public void startActivity(Intent intent) {
//        if(AppUtil.hasIntentActivity(intent)){
//            super.startActivity(intent);
//        }else{
//            LogManager.getInstance().e("android.content.ActivityNotFoundException: No Activity found to handle Intent");
//        }
//    }



}
