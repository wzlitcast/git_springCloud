package com.it.cast.consumer;

import com.aspire.commons.AjaxResult;
import com.aspire.commons.analysis_json.AnalysisJson;
import com.aspire.commons.log.LogCommons;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.cast.client.UserClient;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;


@Service
@RocketMQMessageListener(topic = "${rocketmq.topic1}",consumerGroup = "${rocketmq.consumer.group}",selectorExpression = "tagB",consumeMode = ConsumeMode.ORDERLY)
public class OrderlyMessageConsumerService implements RocketMQListener<String> {

    @Resource
    UserClient userClient;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(String s) {
        Message message = null;
        String str = null;
        try {
            message = mapper.readValue(s, Message.class);
        } catch (IOException e) {
           LogCommons.error("IO异常：{}",e);
        }
        try {
            str = mapper.writeValueAsString(message.getBody());
            LogCommons.info("顺序消费信息：{}",str);
            AjaxResult<JsonNode> jsonNodeAjaxResult = userClient.importUser(str);
            LogCommons.info("顺序消费结果：{}",jsonNodeAjaxResult);
        } catch (IOException e) {
            LogCommons.error("JSON解析异常：{}",e);
        }

    }
}
