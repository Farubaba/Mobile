package com.silence.rootfeature.intents;

import android.content.Intent;
import android.net.Uri;

import com.silence.rootfeature.utils.ConvenientUtil;

/**
 * @author violet
 * date :  2018/4/25 17:47
 */

public class ActionIntentParser {
    /**
     * 从URI中解析出intent
     * @param url
     * @return
     */
    public ActionIntent parse(String url) {
        if(isEmpty(url)){
            return null;
        }
        ActionIntent actionIntent = new ActionIntent();

        actionIntent.setData(Uri.parse(url));

        return actionIntent;
    }


    /**
     * 判断uri是否为空
     * @param uri
     * @return
     */
    public boolean isEmpty(String uri){
        if(ConvenientUtil.isEmpty(uri)){
            return true;
        }
        return false;
    }

    public boolean isHttp(String uri){
        Uri.parse(uri);
        return true;
    }


    public static class ActionIntent extends Intent{

        private String url;
        private Uri uri;

        public ActionIntent(){

        }

        public ActionIntent(String url){
            setUrl(url);
        }

        /**
         * 判断当前ActionIntent是否是从Url解析出来的
         * @return
         */
        public boolean isUrlIntent(){
            if(ConvenientUtil.isEmpty(getUrl())){
                return false;
            }
            return true;
        }

        public String getUrl() {
            return url;
        }

        public ActionIntent setUrl(String url) {
            this.url = url;
            if(!ConvenientUtil.isEmpty(url)){
                Uri uri = Uri.parse(url);
                String scheme = uri.getScheme();
                String host = uri.getHost();
                int port = uri.getPort();
                String authority = uri.getAuthority();
                String path = uri.getPath();
                String lastPathSegment = uri.getLastPathSegment();
            }
            return this;
        }
    }
}
