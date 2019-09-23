package com.it.cast.controller;

import com.aliyuncs.CommonResponse;
import com.aspire.commons.log.LogCommons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.cast.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: WuZhiLong
 * @create: 2019-06-19 17:13
 **/
@RestController
public class SmsController {

    @Value("${singName}")
    private String singName;

    @Value("${templateCode}")
    private String templateCode;

    @Autowired
    private SmsUtil smsUtil;

    @PostMapping("sendSms")
    private CommonResponse sendSms(@RequestBody Map<String, String> map) {
        try {
            CommonResponse res = smsUtil.sendSms(
                    map.get("mobile"),
                    //模板ID
                    map.get("templateCode"),
                    //短信签名
                    map.get("singName"),
                    //要替换的字符Json
                    map.get("param")
            );
            LogCommons.info("data1: " + res.getData());
            return res;
        } catch (Exception e) {
            LogCommons.error("error1:{}",e);
        }
        return null;
    }

    @PostMapping("SendBatchSms")
    private CommonResponse SendBatchSms(@RequestBody Map<String, String> map) {
        try {
            CommonResponse res = smsUtil.SendBatchSms(
                    map.get("mobile"),
                    //模板ID
                    map.get("templateCode"),
                    //短信签名JSon
                    map.get("singName"),
                    //替换字符[{“name”:”TemplateParamJson”},{“name”:”TemplateParamJson”}]
                    map.get("param")
            );
            LogCommons.info("Code     : " + res.getData());
            return res;
        } catch (Exception e) {
            LogCommons.error("error2:{}",e);
        }
        return null;
    }

    @PostMapping("querySendDetails")
    private CommonResponse querySendDetails(@RequestBody Map<String, String> map) {
        try {
            CommonResponse response = smsUtil.querySendDetails(
                    map.get("mobile"),
                    map.get("date"),
                    map.get("pageSize"),
                    map.get("currentPage"),
                    //bizId 发送回执ID，即发送流水号。调用发送接口SendSms或SendBatchSms发送短信时，返回值中的BizId字段。
                    map.get("bizId")
            );
            LogCommons.info("data3: " + response.getData());
            return response;
        } catch (Exception e) {
            LogCommons.error("error3:{}",e);
        }
        return null;
    }

    public static void main(String[] args) throws JsonProcessingException {
        Map map = new HashMap();
        map.put("code","9527");
        String s = new ObjectMapper().writeValueAsString(map);
        System.out.println(s);
    }
}
