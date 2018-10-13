package com.silence.rootfeature.paging_old;

import android.widget.ListView;

import com.silence.rootfeature.logger.LogManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author violet
 * @version V1.0
 * date :  2016-05-24 10:40
 * @tips
 */
public class CountUnknownPageLoader extends PageLoader {

    protected AtomicBoolean isNoMoreData = new AtomicBoolean(true);
    public static final int ERROR__CURRENT_REQUEST_PAGE_COUNT = -1;
    public static final int COUNT_REQUEST_NO_DATA = 0;
    /**
     * 当前回来的分页数据数量，
     * 默认值是ERROR__CURRENT_REQUEST_PAGE_COUNT，不合法，或者调用者忘记了设置请求到的数量
     */
    protected int currentRequestPageCount = ERROR__CURRENT_REQUEST_PAGE_COUNT;


    public static CountUnknownPageLoader newInstance() {
        return new CountUnknownPageLoader();
    }

    @Override
    public CountUnknownPageLoader initListView(ListView listView, PageLoadAdapter adapter, PageLoadParam param, LoadingView loadingView) {
        super.initListView(listView, adapter, param, loadingView);
        return this;
    }

    @Override
    public synchronized CountUnknownPageLoader setPageLoaderRequest(PageLoadRequest pageLoaderRequest) {
        this.pageLoaderRequest = pageLoaderRequest;
        return this;
    }

    @Override
    public synchronized PageLoadParam getParam() {
        return super.getParam();
    }

    public int getPageSize() {
        if (getParam() != null) {
            return getParam().getPageSize();
        }
        // 该情况说明PagerSize并没有被正确设置
        return COUNT_REQUEST_NO_DATA;
    }

    @Override
    public boolean noMoreData() {
        //忘记设置requestPageCount,调试环境中弹出Toast提示
        if (getCurrentRequestPageCount() == ERROR__CURRENT_REQUEST_PAGE_COUNT) {
            LogManager.getInstance().e(TAG, "是否忘记设置requestPageCount了");
            return false;
        } else {
            if (getCurrentRequestPageCount() == COUNT_REQUEST_NO_DATA || getCurrentRequestPageCount() < getPageSize()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public synchronized CountUnknownPageLoader requestMoreData() {
        //和Super中的逻辑完全不同，不能调用supper
        //super.requestMoreData();
        if (pageLoaderRequest != null && !noMoreData()) {
            if (!requesting.get()) {
                requesting.set(true);
                pageLoaderRequest.sendRequest();
            } else {
                LogManager.getInstance().e(TAG, "is requesting, will not send new request, please wait for last request come back.");
            }
        }
        return this;
    }

    @Override
    public synchronized void onLoadSuccess(int currentRequestPageCount) {
        //这里不在需要totalSize的分页逻辑处理，所以不调用supper
        //super.onLoadSuccess(currentRequestPageCount);
        updateLoadingState(false);
        this.currentRequestPageCount = currentRequestPageCount;
        if (currentRequestPageCount > 0) {
            updateParam(true);
        } else {
            updateParam(false);
        }
    }

    @Override
    public synchronized void onLoadFailure() {
        super.onLoadFailure();
    }

    public synchronized int getCurrentRequestPageCount() {
        return this.currentRequestPageCount;
    }

    protected synchronized CountUnknownPageLoader updateParam(boolean increasePageIndex) {
        LogManager.getInstance().d(TAG, "updateParam, increasePageIndex = " + increasePageIndex);
        if (mParam != null) {
            int nextPage = this.mParam.getNextPage();
            if (this.mParam != null && increasePageIndex) {
                this.mParam.setPage(nextPage);
                //this.mParam.setTotalSize(totalSize);
            }
            LogManager.getInstance().d(TAG, "updateParam, page = " + this.mParam.getPage() + " totalSize = " + this.mParam.getTotalSize());
        }
        return this;
    }

    /**
     * 重置
     *
     * @return
     */
    public CountUnknownPageLoader reset() {
        if (mParam != null) {
            mParam.reset();
        }
        currentRequestPageCount = ERROR__CURRENT_REQUEST_PAGE_COUNT;
        if (requesting != null) {
            requesting.set(false);
        }
        return this;
    }
}
