package com.it.cast.controller;

import com.aspire.commons.AjaxResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.it.cast.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 简单测试
 * @author: WuZhiLong
 * @create: 2019-06-05 11:17
 **/
@RestController
public class SimpleController {
    @Autowired
    private SimpleService simpleService;

    @PostMapping(value = "/v1/addMsgToKafka")
    AjaxResult<JsonNode> addMessageToKafka(@RequestBody String key) {
        return simpleService.addMessageToKafka(key);
    }
}