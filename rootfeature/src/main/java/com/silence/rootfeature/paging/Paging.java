package com.silence.rootfeature.paging;

import java.util.List;

/**
 * 分页业务规则接口
 * 1.准备请求参数，要能区分当前是第一页，中间页，还是最后一页。
 * 2.异步请求第一页数据
 * 3.传递并展示请求到的数据，要知道当前是否是第一页，中间页，还是最后一页
 * 4.判断是否还有更多数据
 * 5.请求下一页数据
 * @author violet
 * @date 2018/4/29 17:43
 */

public interface Paging<PARAM, RESULT, CALLBACK> {
    PARAM prepareRequestParams();
    RESULT pageRequest(PARAM param , CALLBACK callback);
    RESULT onRequest(RESULT result);
    boolean hasMoreData();
}
