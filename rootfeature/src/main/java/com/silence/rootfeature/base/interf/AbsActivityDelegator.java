package com.silence.rootfeature.base.interf;

import android.app.Activity;
import android.content.Intent;

import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.logger.LogManager;

/**
 * @author violet
 * @date 2018/4/29 15:45
 */

public abstract class AbsActivityDelegator implements ActivityDelegator {

    protected Activity activity;

    public AbsActivityDelegator(Activity activity){
        this.activity = activity;
    }

    @Override
    public void startActivity(Intent intent) {
        if(AppUtil.hasIntentActivity(intent)){
            activity.startActivity(intent);
        }else{
            LogManager.getInstance().e("android.content.ActivityNotFoundException: No Activity found to handle Intent");
        }
    }

    @Override
    public Intent parseIntentParams(Intent intent) {
        activity.setIntent(intent);
        return intent;
    }
}
