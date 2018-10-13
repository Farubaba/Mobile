package com.silence.rootfeature.base;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.silence.rootfeature.base.interf.ActivityDelegator;
import com.silence.rootfeature.base.interf.DefaultActivityDelegator;
import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.DebugUtil;

/**
 * @author violet
 * date :  2018/4/29 15:21
 */

public class BaseListActivity extends ListActivity{

    protected String TAG = this.getClass().getSimpleName();

    protected ActivityDelegator activityDelegator = new DefaultActivityDelegator(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDelegator.parseIntentParams(getIntent());


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        activityDelegator.parseIntentParams(intent);
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
        activityDelegator.startActivity(intent);
    }
}
