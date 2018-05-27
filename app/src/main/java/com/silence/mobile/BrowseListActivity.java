package com.silence.mobile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.app.C;
import com.silence.rootfeature.base.BaseListActivity;
import com.silence.rootfeature.base.interf.ActivityDelegator;
import com.silence.rootfeature.base.interf.DefaultActivityDelegator;
import com.silence.rootfeature.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author violet
 * @date 2018/4/29 15:43
 */

public class BrowseListActivity extends BaseListActivity {

    /**
     * 形如 com.silence.mobile.COMPONENT_PATH
     */
    public static final String COMPONENT_PATH = AppUtil.getPackagePrefixKey("COMPONENT_PATH");
    public String pathPrefix = null;
    public String lable = null;

    public ActivityDelegator activityDelegator = new DefaultActivityDelegator(this){
        @Override
        public Intent parseIntentParams(Intent intent) {
            super.parseIntentParams(intent);
            pathPrefix = intent.getStringExtra(COMPONENT_PATH);
            if(pathPrefix == null){
                pathPrefix = C.Strings.EMPTY;
            }
            return intent;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDelegator.parseIntentParams(getIntent());

        Intent queryIntent = new Intent();
        queryIntent.setAction(Intent.ACTION_VIEW);
        queryIntent.addCategory(Intent.CATEGORY_DEFAULT);

        PackageManager pm = AppUtil.getPackageManager();
        List<ResolveInfo> acitivtys = pm.queryIntentActivities(queryIntent, PackageManager.MATCH_DEFAULT_ONLY);




        if(CommonUtil.isEmpty(pathPrefix)){
            if(acitivtys == null || acitivtys.size() == 0){
               // setListAdapter(baseAdapter);
            }else{
                //setListAdapter(baseAdapter);
            }
        }else{

        }



    }
}
