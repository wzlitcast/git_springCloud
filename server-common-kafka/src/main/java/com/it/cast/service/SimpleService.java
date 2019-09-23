package com.it.cast.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.aspire.commons.AjaxResult;
import com.aspire.commons.log.LogCommons;
import com.aspire.commons.response_json.CommonJsonNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.it.cast.config.MyProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @description: 测试
 * @author: WuZhiLong
 * @create: 2019-06-05 19:05
 **/
@Service
public class SimpleService {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private MyProps props;

    public AjaxResult<JsonNode> addMessageToKafka(String key) {
        try {
            LogCommons.info("addMessageToKafka star，key:{}",()->key);
            JSONObject reqJson = JSON.parseObject(key);
            if (!reqJson.containsKey("bizId") || !reqJson.containsKey("msg")){
                return new AjaxResult<JsonNode>().fail("-1","传参不完整");
            }
            String bizId = reqJson.getString("bizId");
            String msg = reqJson.getString("msg");
            String topic = props.getMapProps().get(bizId);
            if (StringUtils.isEmpty(topic)){
                return new AjaxResult<JsonNode>().fail("-1","业务id不存在");
            }
            template.send(topic,msg);
            return new AjaxResult<JsonNode>().success(CommonJsonNode.results("消息已推送，对应topic："+topic));
        } catch (JSONException e) {
            return new AjaxResult<JsonNode>().fail("-1","Json解析失败");
        }catch (Exception e){
            return new AjaxResult<JsonNode>().fail("-1","调用异常");
        }
    }
}
