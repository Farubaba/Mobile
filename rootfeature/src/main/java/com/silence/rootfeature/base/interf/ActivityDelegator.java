package com.silence.rootfeature.base.interf;

import android.app.Activity;
import android.content.Intent;

/**
 * @author violet
 * @date 2018/4/29 15:24
 */

public interface ActivityDelegator {

    public void startActivity(Intent intent);
    /**
     * 解析出Intent中的请求参数，通常参数在以下几个位置存放：
     * 1、存在Bundle对象中{@link Intent#getExtras()}，或者直接使用{@link Intent#getStringExtra(String)}类似方法获取
     * 2、存在Uri对象中{@link Intent#getData()}
     * 3、存储在{@link android.os.Parcelable}对象中,使用类似{@link Intent#getParcelableExtra(String)}方法获取
     * @param intent
     * @return
     */
    public Intent parseIntentParams(Intent intent);
}
