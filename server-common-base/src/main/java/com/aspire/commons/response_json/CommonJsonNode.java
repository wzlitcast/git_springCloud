package com.aspire.commons.response_json;

import com.aspire.commons.utils.BeanHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * 通用返回JSON
 */
public  class CommonJsonNode {

    private static final String RESULTS = "results";
    private static final String TOTAL_COUNT = "totalCount";
    private static final String PAGE_NO = "pageNo";
    private static final String PAGE_SIZE = "pageSize";

    public static <T> JsonNode result(T t){
        return new ObjectMapper().createObjectNode().putPOJO(RESULTS,t);
    }

    public static <T> JsonNode results(T t){
        return new ObjectMapper().createObjectNode().putPOJO(RESULTS,t);
    }
    public static <T> JsonNode resultsPage(IPage<T> page){
        return new ObjectMapper().createObjectNode().put(TOTAL_COUNT,page.getTotal()).put(PAGE_NO,page.getCurrent()).put(PAGE_SIZE,page.getSize()).putPOJO(RESULTS, page.getRecords());
    }
    public static <T> JsonNode resultsMapsPage(IPage<T> page){
        return new ObjectMapper().createObjectNode().put(TOTAL_COUNT,page.getTotal()).put(PAGE_NO,page.getCurrent()).put(PAGE_SIZE,page.getSize()).putPOJO(RESULTS, BeanHelper.formatColNameForList((List<Map<String, Object>>) page.getRecords()));
    }
}
