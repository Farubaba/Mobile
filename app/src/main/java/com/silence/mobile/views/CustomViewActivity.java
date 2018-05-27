package com.silence.mobile.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.silence.mobile.R;
import com.silence.rootfeature.base.BaseActivity;
import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.CompatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author violet
 * @date 2018/3/23 12:03
 */

public class CustomViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        CompatUtil.verifyStoragePermissions(this);
        LogManager.getInstance().d("CustomViewActivity %1$s ","onCreate");
        TextView textView = findViewById(R.id.float_text);
//        floatAnim(textView,1000);


    }

    private void floatAnim(View view, int delay){
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -6.0f,6.0f,-6.0f);
        translationXAnim.setDuration(1500);
        translationXAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        translationXAnim.setRepeatMode(ValueAnimator.REVERSE);//
        translationXAnim.start();
        animators.add(translationXAnim);
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY", -3.0f,3.0f,-3.0f);
        translationYAnim.setDuration(1000);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.setRepeatMode(ValueAnimator.REVERSE);
        translationYAnim.start();
        animators.add(translationYAnim);

        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playTogether(animators);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }
}
