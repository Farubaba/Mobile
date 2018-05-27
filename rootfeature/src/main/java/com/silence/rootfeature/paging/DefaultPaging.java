package com.silence.rootfeature.paging;

import java.util.List;

/**
 * @author violet
 * @date 2018/4/29 17:53
 */

public class DefaultPaging<PARAM, RESULT, CALLBACK> implements Paging<PARAM, RESULT, CALLBACK> {

    @Override
    public PARAM prepareRequestParams() {
        return null;
    }

    @Override
    public RESULT pageRequest(PARAM param, CALLBACK callback) {
        return null;
    }

    @Override
    public RESULT onRequest(RESULT result) {
        return null;
    }

    @Override
    public boolean hasMoreData() {
        return false;
    }
}
