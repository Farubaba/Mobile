package com.silence.rootfeature.paging;

import java.util.List;
import java.util.Map;

/**
 * @author violet
 * @date 2018/4/29 18:08
 */

public class Test {
    public void test(){
        Paging<String,List<Map<String,Object>>,String> paging = new DefaultPaging<String,List<Map<String,Object>>,String>(){
            @Override
            public String prepareRequestParams() {
                return super.prepareRequestParams();
            }

            @Override
            public List<Map<String, Object>> pageRequest(String s, String s2) {
                return super.pageRequest(s, s2);
            }

            @Override
            public List<Map<String, Object>> onRequest(List<Map<String, Object>> maps) {
                return super.onRequest(maps);
            }

            @Override
            public boolean hasMoreData() {
                return super.hasMoreData();
            }
        };
    }
}
