package com.silence.mobile.intent_start;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.silence.mobile.R;
import com.silence.rootfeature.base.BaseActivity;

/**
 * @author violet
 * @date 2018/4/25 20:40
 */

public class IntentMatchActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_activity);
        Button name = findViewById(R.id.btn_custom);
        Button age = findViewById(R.id.tv_custom);
        Intent intent = getIntent();
        Uri uri  = intent.getData();
        if(uri != null){
            name.setText(uri.getQueryParameter("name"));
            age.setText(uri.getQueryParameter("age"));
        }
    }
}
