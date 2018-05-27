package com.silence.rootfeature.paging_old;

/**
 * 请求成功后，必须回调{@link #onLoadSuccess(int)}方法更新请求参数对象，以便正确处理翻页。<br>
 * 请求失败后，可以回调{@link #onLoadFailure()}方法更新请求参数对象.<br>
 *
 * @author violet
 */
public interface PageLoadCallback {

    /**
     * 请求成功后，必须回调该方法更新请求参数对象，以便正确处理翻页
     *
     * @param totalSizeOrCurrentPageSize
     */
    public void onLoadSuccess(int totalSizeOrCurrentPageSize);

    /**
     * 请求失败后，可以回调方法更新请求参数对象
     */
    public void onLoadFailure();

}