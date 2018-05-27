package com.silence.rootfeature.paging_old;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.concurrent.atomic.AtomicBoolean;

/**
     * Created by violet on 15/9/6.
     */
public abstract class LoadingView extends RelativeLayout implements LoadingState{

    protected AtomicBoolean mLoading = new AtomicBoolean(false);
    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void startLoading() {
        mLoading.set(true);
        this.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        mLoading.set(false);
        this.setVisibility(View.GONE);
    }

    @Override
    public boolean isLoading() {
        return mLoading.get();
    }
}