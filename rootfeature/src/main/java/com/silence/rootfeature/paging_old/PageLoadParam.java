package com.silence.rootfeature.paging_old;


import com.silence.rootfeature.logger.LogManager;

import java.util.HashMap;
import java.util.Map;

/**
 * FIXME 考虑使用Builder模式重构
 */
public class PageLoadParam implements PageLoadCallback {

    public static final String TAG = PageLoadParam.class.getSimpleName();
    public static final int UNKNOWN_TOTAL_SIZE = -1;
    public static final int UNKNOWN_TOTAL_PAGE = -1;
    /**
     * 通用常量
     */
    public static final String EMPTY_QUERY = "";
    public static final String ALL_QUERY = "ALL";
    /**
     * 默认起始页码为1
     */
    public static final String KEY_PAGE = "page";
    public static final String KEY_PAGE_SIZE = "pageSize";
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    private boolean firstLoad = true;

    /**
     * 可配置的页码参数名称。默认参数名为{@link #KEY_PAGE}
     */
    private String paramKeyPage = KEY_PAGE;

    /**
     * 可配置的每页显现记录数参数名称，默认为{@link #KEY_PAGE_SIZE}
     */
    private String paramKeyPageSize = KEY_PAGE_SIZE;

    /**
     * 分页信息：当前页的页码
     */
    public int page = DEFAULT_PAGE;
    /**
     * 分页信息：每页显示条数
     */
    public int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 分页信息：总记录数
     */
    public int totalSize = UNKNOWN_TOTAL_SIZE;
    /**
     * 分页信息：总页数
     */
    public int totalPage = UNKNOWN_TOTAL_PAGE;
    /**
     * 请求参数
     */
    private Map<String, String> paramMap = new HashMap<String, String>();

    public PageLoadParam() {
        init();
    }

    public static PageLoadParam newInstance() {
        return new PageLoadParam();
    }

    public PageLoadParam init() {
        //分页数据都是从第一页开始加载的
        page = DEFAULT_PAGE;
        pageSize = getPageSize();
        totalSize = UNKNOWN_TOTAL_SIZE;
        firstLoad = true;
        //参数
        paramMap.put(getParamKeyPage(), String.valueOf(getPage()));
        paramMap.put(getParamKeyPageSize(), String.valueOf(getPageSize()));
        return this;
    }

    public String getParamKeyPage() {
        return paramKeyPage;
    }

    public PageLoadParam setParamKeyPage(String paramKeyPage) {
        //当使用到自定义的KEY_PAGE时，需要移除默认的KEY_PAGE参数
        if (!KEY_PAGE.equals(paramKeyPage)) {
            paramMap.remove(KEY_PAGE);
            paramMap.put(paramKeyPage, String.valueOf(getPage()));
        }
        this.paramKeyPage = paramKeyPage;
        return this;
    }

    public String getParamKeyPageSize() {
        return paramKeyPageSize;
    }

    public PageLoadParam setParamKeyPageSize(String paramKeyPageSize) {
        //当使用到自定义的KEY_PAGE时，需要移除默认的KEY_PAGE参数
        if (!KEY_PAGE_SIZE.equals(paramKeyPageSize)) {
            paramMap.remove(KEY_PAGE_SIZE);
            paramMap.put(paramKeyPageSize, String.valueOf(getPageSize()));
        }
        this.paramKeyPageSize = paramKeyPageSize;
        return this;
    }

    /**
     * 获取当前需要请求的数据页码位置index
     *
     * @return
     */
    public int getPage() {
        return page;
    }

    public PageLoadParam setPage(int page) {
        this.page = page;
        paramMap.put(getParamKeyPage(), String.valueOf(page));
        return this;
    }

    /**
     * 获取当前的pageSize,每页显示条数
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    public PageLoadParam setPageSize(int pageSize) {
        this.pageSize = pageSize;
        paramMap.put(getParamKeyPageSize(), String.valueOf(pageSize));
        return this;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public Map<String, String> getParamMap() {
        return paramMap == null ? (paramMap = new HashMap<String, String>()) : paramMap;
    }

    public PageLoadParam setParamMap(Map<String, String> queryMap) {
        if (queryMap != null) {
            this.paramMap = queryMap;
        }
        return this;
    }

    public PageLoadParam addParam(String key, String value) {
        paramMap.put(key, value);
        return this;
    }

    public PageLoadParam removeParam(String key) {
        paramMap.remove(key);
        return this;
    }

    public String getParam(String key) {
        return paramMap.get(key);
    }

    /**
     * 重置PageLoadParam到默认状态
     *
     * @return
     */
    public PageLoadParam reset() {
        paramMap.clear();
        init();
        return this;
    }

    /**
     * 充值Page参数，不清楚搜索条件参数
     *
     * @return
     */
    public PageLoadParam resetPageInfoNoClear() {
        init();
        return this;
    }

    /**
     * 获取下一页数据的页码
     *
     * @return
     */
    public int getNextPage() {
        return getPage() + 1;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotalPage() {
        if (getTotalSize() == UNKNOWN_TOTAL_SIZE) {
            return UNKNOWN_TOTAL_PAGE;
        }
        return (getTotalSize() + getPageSize() - 1) / getPageSize();
    }

    /**
     * 该方法用于请求之前判断当前也是否已经是最后一页
     *
     * @return
     */
    public boolean noMoreData() {

        int totalSize = getTotalSize();
        int fetchedPageCount = getPage() - 1;
        int totalGet = getPageSize() * fetchedPageCount;
        LogManager.getInstance().d(TAG, "totalSize = " + totalSize + " fetchedPageCount = " + fetchedPageCount + " totalGet = " + totalGet);
        return !(totalSize > totalGet);
    }

    @Override
    public void onLoadSuccess(int totalSize) {
        firstLoad = false;
    }

    @Override
    public void onLoadFailure() {

    }

    public boolean isFirstLoad() {
        return firstLoad;
    }

    public void setFirstLoad(boolean firstLoad) {
        this.firstLoad = firstLoad;
    }
}
