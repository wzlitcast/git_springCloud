package com.it.cast.consumer;

import com.aspire.commons.AjaxResult;
import com.aspire.commons.log.LogCommons;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.cast.client.UserClient;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;


@Service
@RocketMQMessageListener(topic = "RMQ_SYS_TRANS_HALF_TOPIC",consumerGroup = "${rocketmq.consumer.group2}")
public class CommonMessageConsumerService implements RocketMQListener<String> {

    @Resource
    UserClient userClient;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * @param s 根据tag进行业务分化
     */
    @Override
    public void onMessage(String s) {
        Message message = null;
        String msg = null;
        try {
            message = mapper.readValue(s, Message.class);
            msg = mapper.readValue(message.getBody(), new TypeReference<String>() {});
            String tags = message.getTags();
            if ("tagA".equals(tags)){
                this.tagA(msg);
            }
            if ("tagB".equals(tags)){
                this.tagB(msg);
            }
            if ("tagC".equals(tags)){
                this.tagC(msg);
            }
            if("RMQ_SYS_TRANS_HALF_TOPIC".equals(tags)){
                this.test(tags);
            }
        } catch (IOException e) {
            LogCommons.error("JSON解析异常：{}",e);
        }

    }

    private AjaxResult<JsonNode> test(String tags) {
        LogCommons.info("处理RMQ_SYS_TRANS_HALF_TOPIC业务:{}",tags);
        return new AjaxResult<JsonNode>().success();
    }

    private AjaxResult<JsonNode> tagA(String msg){
        LogCommons.info("处理tagA业务:{}",msg);
        return new AjaxResult<JsonNode>().success();
    }
    private AjaxResult<JsonNode> tagB(String msg){
        LogCommons.info("处理tagB业务:{}",msg);
        return new AjaxResult<JsonNode>().success();
    }
    private AjaxResult<JsonNode> tagC(String msg){
        LogCommons.info("处理tagC业务:{}",msg);
        return new AjaxResult<JsonNode>().success();
    }
}
