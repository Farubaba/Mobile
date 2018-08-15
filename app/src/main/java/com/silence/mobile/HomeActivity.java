package com.silence.mobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.silence.mobile.intent_start.IntentMatchActivity2;
import com.silence.mobile.trainning.MainActivity;
import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.app.C;
import com.silence.rootfeature.app.PermissionUtil;
import com.silence.rootfeature.base.BaseActivity;
import com.silence.rootfeature.utils.StorageUtilTest;
import com.silence.rootfeature.utils.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Permission;

public class HomeActivity extends BaseActivity {

    public int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View container = LayoutInflater.from(this).inflate(R.layout.activity_home, null);
        setContentView(container);
        container.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        Button button = findViewById(R.id.btn_custom);
        Button button1 = findViewById(R.id.btn_custom1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

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


//        StorageUtilTest.getInternalFilesDir();
//        StorageUtilTest.getInternalCacheDir();
//        StorageUtilTest.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//        StorageUtilTest.getExternalStoragePublicDir(Environment.DIRECTORY_PICTURES);
        StorageUtilTest.testPath();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ToastUtil.show("no permission to write external storage.");
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                //显示一个异步提示信息，告诉用户为什么需要这个权限
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 101:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    ToastUtil.show("write external storage PERMISSION_GRANTED, do your work now.");
                }else{
                    ToastUtil.show("user cancel , PERMISSION_GRANTED failed.");
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 101:
                break;
        }
    }
}
