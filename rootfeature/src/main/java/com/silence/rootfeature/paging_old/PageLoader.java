package com.silence.rootfeature.paging_old;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.silence.rootfeature.logger.LogManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 分页数据加载管理器,使用参考：AmountCalendar.java
 *
 * @author violet
 */
public class PageLoader implements Pagination, LoadingState {

    public final String TAG = PageLoader.this.getClass().getSimpleName();
    protected ListView mListView;
    protected PageLoadAdapter mAdapter;
    protected LoadingView mLoadingView;
    protected AtomicBoolean mLoading = new AtomicBoolean(false);
    protected PageLoadParam mParam;
    protected PageLoadRequest pageLoaderRequest;
    protected AtomicBoolean requesting = new AtomicBoolean(false);
    protected boolean forceSetListAdapter = false;
    /**
     * 是否根据ListView的Scroll事件来自动加载下一页数据<br>
     * true : 在ListView滑动过程中自动去加载下一页数据，无需外界触发条件<br>
     * false: 关闭滑动ListView时，自动加载下一页的功能，加载下一页需要外界条件来触发<br>
     * 例如，在{@link OnRefreshListener2#onPullUpToRefresh}中调用{@link #requestMoreData()}来触发请求下一页数据，
     */
    protected boolean autoLoadMoreData = true;

    /**
     * 外部调用者传入的ScrollListener，用于对内部onScrollListener的扩展
     */

    protected OnScrollListener onScrollListener;

    private OnScrollListener innerOnScrollListener = new OnScrollListener() {
        private boolean isLastRow = false;
        private int totalItemCount;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (getParam() != null) {
                LogManager.getInstance().d(TAG, "onScrollStateChanged , scrollState=" + scrollState + " totalSize = " + getParam().getTotalSize());
                //处理滑动分页加载逻辑
                if (isAutoLoadMoreData()) {
                    int headerViewsCount = mListView.getHeaderViewsCount();
                    int footerViewsCount = mListView.getFooterViewsCount();
                    if (isLastRow && (this.totalItemCount - headerViewsCount - footerViewsCount) < getParam().getTotalSize()) {
                        //				requestMoreData();
                        isLastRow = false;
                    }
                }
            }
            //回调外部逻辑
            invokeOuterOnScrollStateChanged(view, scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (getParam() != null) {
                LogManager.getInstance().d(TAG, " totalSize = " + getParam().getTotalSize());
                LogManager.getInstance().d(TAG, "onScroll , firstVisibleItem = " + firstVisibleItem + " visibleItemCount=" + visibleItemCount + " totalItemCount=" + totalItemCount);
                //处理滑动分页加载逻辑
                if (isAutoLoadMoreData()) {
                    this.totalItemCount = totalItemCount;
                    int headerViewsCount = mListView.getHeaderViewsCount();
                    int footerViewsCount = mListView.getFooterViewsCount();
                    LogManager.getInstance().d(TAG, "headerCount = " + headerViewsCount + " footerCount = " + footerViewsCount);
                    if (firstVisibleItem + visibleItemCount >= (totalItemCount - headerViewsCount - footerViewsCount) && totalItemCount > 0) {
                        isLastRow = true;
                    }
                }
            }
            //回调外部逻辑
            invokeOuterOnScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    };

    public PageLoader() {

    }

    public static PageLoader newInstance() {
        return new PageLoader();
    }

    /**
     * 该方法执行在UI线程，真正发送请求是在{@link PageLoadRequest#sendRequest()}方法中实现.<br>
     * 需要在{@link PageLoadRequest#sendRequest()}中实现一个异步的请求。<br>
     *
     * @param request
     * @param clearData true，清除已有数据，完全重新请求
     * @return
     */
    public PageLoader load(PageLoadRequest request, boolean clearData) {
        //		clearAndNotifyChange(clearData);
        setPageLoaderRequest(request);
        if (request != null) {
            request.sendRequest();
            bindOnScrollListener(getListView(), innerOnScrollListener);
        }
        // 第一次请求，不显示loadingIndicatorView，只有请求更多时，才显示。
        // startLoading();
        return this;
    }

    /**
     * 该方法只有在{@link #isAutoLoadMoreData()} == false的时候，才允许被外界调用。
     * 用于请求下一页数据。
     *
     * @return
     */
    public synchronized PageLoader requestMoreData() {
        if (pageLoaderRequest != null && mParam != null) {
            int totalSize = this.mParam.getTotalSize();
            int totalGet = this.mParam.getPageSize() * (this.mParam.getPage() - 1);
            LogManager.getInstance().d(TAG, "totalSize = " + totalSize + " totalGet = " + totalGet);
            if (totalSize > totalGet) {
                LogManager.getInstance().d(TAG, "request more Data page = " + this.getParam().getPage());
                if (!requesting.get()) {
                    requesting.set(true);
                    pageLoaderRequest.sendRequest();
                } else {
                    LogManager.getInstance().e(TAG, "is requesting, will not send new request, please wait for last request come back.");
                }
            } else {
                LogManager.getInstance().e(TAG, "totalSize = " + totalSize + " totalGet = " + totalGet + " no more data.");
            }
        }
        return this;
    }

    /**
     * 判断当前是否还有更多数据
     *
     * @return
     */
    public boolean noMoreData() {
        if (mParam != null) {
            return mParam.noMoreData();
        }
        return true;
    }

    protected PageLoader clearAndNotifyChange(boolean clearData) {
        if (clearData) {
            if (mAdapter != null) {
                mAdapter.clearData();
            }
        }
        return this;
    }

    public ListView getListView() {
        return mListView;
    }

    public PageLoader initListView(ListView listView, PageLoadAdapter adapter, PageLoadParam param, LoadingView loadingView) {
        if (mListView == null) {
            this.mListView = listView;
        }
        if (mAdapter == null || isForceSetListAdapter()) {
            this.mAdapter = adapter;
        }
        this.mAdapter.setPageLoadParam(param);
        this.mAdapter.setListView(mListView);
        this.mLoadingView = loadingView;
        //bindOnScrollListener(listView, onScrollListener);
        this.mParam = param;
        initPageLoader();
        return this;
    }

    private PageLoader initPageLoader() {
        if (this.mListView != null) {
            if (this.mAdapter != null) {
                if (mListView.getAdapter() == null || isForceSetListAdapter()) {
                    this.mListView.setAdapter(mAdapter);
                }
            }
            if (this.mLoadingView != null && this.mLoadingView.getParent() == null) {
                try {
                    this.mListView.addFooterView(mLoadingView);
                } catch (Exception e) {
                    LogManager.getInstance().e(TAG, "Exception" + e.getMessage());
                }
            }
        }
        return this;
    }

    public synchronized PageLoadParam getParam() {
        return mParam;
    }

    public PageLoadRequest getPageLoaderRequest() {
        return pageLoaderRequest;
    }

    public synchronized PageLoader setPageLoaderRequest(PageLoadRequest pageLoaderRequest) {
        this.pageLoaderRequest = pageLoaderRequest;
        return this;
    }

    @Override
    public synchronized void onLoadSuccess(int totalSize) {
        updateParam(totalSize);
        stopLoading();
        requesting.set(false);
    }

    @Override
    public synchronized void onLoadFailure() {
        stopLoading();
        requesting.set(false);
    }

    private synchronized void updateParam(int totalSize) {
        LogManager.getInstance().d(TAG, "updateParam, totalSize = " + totalSize);
        if (mParam != null) {
            int nextPage = this.mParam.getNextPage();
            if (this.mParam != null) {
                this.mParam.setPage(nextPage);
                this.mParam.setTotalSize(totalSize);
            }
            LogManager.getInstance().d(TAG, "updateParam, page = " + this.mParam.getPage() + " totalSize = " + this.mParam.getTotalSize());
        }
    }

    private void bindOnScrollListener(ListView listView, OnScrollListener innerOnScrollListener) {
        if (listView != null && innerOnScrollListener != null) {
            listView.setOnScrollListener(innerOnScrollListener);
        }
    }

    @Override
    public void startLoading() {
        mLoading.set(true);
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopLoading() {
        mLoading.set(false);
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isLoading() {
        return mLoading.get();
    }

    public boolean isAutoLoadMoreData() {
        return autoLoadMoreData;
    }

    public PageLoader setAutoLoadMoreData(boolean autoLoadMoreData) {
        this.autoLoadMoreData = autoLoadMoreData;
        return this;
    }

    public AtomicBoolean getRequesting() {
        return requesting;
    }

    public PageLoader setRequesting(AtomicBoolean requesting) {
        this.requesting = requesting;
        return this;
    }

    @Override
    public PageLoader updateLoadingState(boolean isLoading) {
        this.requesting.set(isLoading);
        if (isLoading) {
            startLoading();
        } else {
            stopLoading();
        }
        return this;
    }

    public PageLoadParam getmParam() {
        return mParam;
    }

    public PageLoader setmParam(PageLoadParam mParam) {
        this.mParam = mParam;
        return this;
    }

    public boolean isForceSetListAdapter() {
        return forceSetListAdapter;
    }

    public PageLoader setForceSetListAdapter(boolean forceSetListAdapter) {
        this.forceSetListAdapter = forceSetListAdapter;
        return this;
    }

    public OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    public PageLoader setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
        return this;
    }

    private PageLoader invokeOuterOnScrollStateChanged(AbsListView view, int scrollState) {
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(view, scrollState);
        }
        return this;
    }

    private PageLoader invokeOuterOnScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (onScrollListener != null) {
            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        return this;
    }
}
