package com.silence.rootfeature.intents;

import android.content.Intent;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author violet
 * date :  2018/4/25 16:06
 */

public class UriIntentDispatcher {
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    public static final String SCHEME_CONTENT = "content";
    public static final String SCHEME_SMS = "sms";
    public static final String SCHEME_TEL = "tel";
    public static final String SCHEME_ITMS_APPS = "itms-apps";
    public static final String SCHEME_FILE = "file";
    /**
     * app 自定义scheme
     */
    public static final String SCHEME_MOBILE_APP = "fengjr";

    @StringDef({SCHEME_HTTP,//
            SCHEME_HTTPS, //
            SCHEME_CONTENT,//
            SCHEME_SMS,//
            SCHEME_TEL,//
            SCHEME_ITMS_APPS,//
            SCHEME_FILE, //
            SCHEME_MOBILE_APP//
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface Scheme {

    }

    @Scheme
    private String scheme;

    @Scheme
    public String getScheme(){
        return this.scheme;
    }

    private UriIntentDispatcher setScheme(@Scheme String scheme){
        this.scheme = scheme;
        return this;
    }
    /**************StringDef end***************/

    public UriIntentDispatcher dispatch(Intent resultIntent){
        //FIXME 如果遇到不支持的scheme如何处理
        setScheme(resultIntent.getScheme());

        switch (getScheme()){
            case SCHEME_HTTP:
                break;
            case SCHEME_HTTPS:
                break;
            case SCHEME_CONTENT:
                break;
            case SCHEME_SMS:
                break;
            case SCHEME_TEL:
                break;
            case SCHEME_ITMS_APPS:
                break;
            case SCHEME_FILE:
                break;
            default://不支持的scheme
                break;
        }
        return this;
    }

}
