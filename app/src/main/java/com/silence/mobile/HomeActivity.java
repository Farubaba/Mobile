package com.silence.mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.silence.mobile.intent_start.IntentMatchActivity2;
import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.app.C;
import com.silence.rootfeature.base.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HomeActivity extends BaseActivity {

    public int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = findViewById(R.id.btn_custom);
        StringBuilder sb = new StringBuilder();
        sb.append(AppUtil.getPackageName()).append(C.Strings.NEW_LINE_SEPERATOR)
                .append(AppUtil.getVersionCode()).append(C.Strings.NEW_LINE_SEPERATOR)
                .append(AppUtil.getVersionName()).append(C.Strings.NEW_LINE_SEPERATOR);
        button.setText(sb);
        String redirect = "";
        try {
            redirect = URLEncoder.encode("http://www.sohu.com/main?pp=1.1f&qq=2.32&mm=3&flagflag=true","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final Intent intent1 = new Intent();
        intent1.setData(Uri.parse("company://www.company.com/testpath1111?name=1chengwei&age=135&p=1.1f&q=2.32&m=3&flag=true&redirect="+ redirect));

        final Intent intent2 = new Intent();
        intent2.setData(Uri.parse("company://www.company.com/testpath1/path2?name=2chengwei&age=235&p=1.1f&q=2.32&m=3&flag=true&redirect="+ redirect));

        final Intent intent3 = new Intent();
        intent3.setData(Uri.parse("company://www.company.com/3/testpath3?name=3chengwei&age=335&p=1.1f&q=2.32&m=3&flag=true&redirect="+ redirect));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(index % 4 == 0){
                startActivity(intent1);
            }else if(index % 4 == 1){
                startActivity(intent2);
            }else if(index % 4 == 2){
                startActivity(intent3);
            }else if(index % 4 == 3){
                Intent intent = new Intent();
                intent.setClass(v.getContext(), IntentMatchActivity2.class);
                startActivity(intent);
            }
            index ++;
            }
        });
    }
}
