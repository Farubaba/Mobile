package com.silence.rootfeature.paging_old;

/**
 * Created by violet on 15/9/6.
 */
public interface LoadingState {

    public void startLoading();

    public void stopLoading();

    public boolean isLoading();
}
