package com.silence.rootfeature.paging_old;

/**
 * @author violet
 * @version V1.0
 * date :  2016-05-24 10:50
 * tips
 */
public interface Pagination extends PageLoadCallback {
    /**
     * 获取当前分页参数
     * @return
     */
    PageLoadParam getParam();
    /**
     * 请求数据
     * @param request
     * @param clearData
     * @return
     */
    PageLoader load(PageLoadRequest request, boolean clearData);

    /**
     * 请求下一页数据
     * @return
     */
    PageLoader requestMoreData();

    /**
     * 是否没有更多数据
     * @return
     */
    boolean noMoreData();

    /**
     * 设置Loading状态
     * @return
     */
    PageLoader updateLoadingState(boolean isLoading);
}
