package com.it.cast.client;

import com.aspire.commons.AjaxResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: WuZhiLong
 * @create: 2019-06-18 10:54
 **/
@FeignClient("server-common-kafka")
public interface KafkaClient {

    @PostMapping(value = "/v1/addMsgToKafka")
    AjaxResult<JsonNode> addMessageToKafka(@RequestBody String key);

}
