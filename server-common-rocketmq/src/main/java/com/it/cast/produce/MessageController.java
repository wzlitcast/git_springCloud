package com.it.cast.produce;

import com.aspire.commons.AjaxResult;
import com.aspire.commons.log.LogCommons;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class MessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private Map<String,Object> map = null;
    private Message message = null;
    private SendResult sendResult = null;
    private static final String TOPIC = "topic";
    private static final String TAG = "tag";
    private static final String BODY = "body";
    private static final String QUEUEID = "queueId";
    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/v1/send/SendOrderly")
    public AjaxResult<JsonNode> sendOrderlyMsg(@RequestBody String msg) {
        try {
            map = mapper.readValue(msg, new TypeReference<Map>() {});
            message = new Message(
                    map.get(TOPIC).toString(),
                    map.get(TAG).toString(),
                    mapper.writeValueAsBytes(map.get(BODY)));
            sendResult = rocketMQTemplate.syncSendOrderly(this.topicAndTag(message), message,map.get(QUEUEID).toString());
        } catch (Exception e) {
            LogCommons.error("消息推送异常:{}",e);
            LogCommons.error("消息推送异常msg:{}",map);
        }
        LogCommons.info("result:{}",sendResult);
        return new AjaxResult<JsonNode>().success();
    }


    @PostMapping("/v1/send/SendCommon")
    public AjaxResult<JsonNode> sendMsg(@RequestBody String msg) {
        try {
            map = mapper.readValue(msg, new TypeReference<Map>() {});
            message = new Message(
                    map.get(TOPIC).toString(),
                    map.get(TAG).toString(),
                    mapper.writeValueAsBytes(map.get(BODY))
            );
            sendResult = rocketMQTemplate.syncSend(this.topicAndTag(message), message);
        } catch (Exception e) {
            LogCommons.error("消息推送异常:{}",e);
            LogCommons.error("消息推送异常msg:{}",map);
        }
        LogCommons.info("result :{}",sendResult);
        return new AjaxResult<JsonNode>().success();
    }


    @PostMapping("/v1/send/test")
    public AjaxResult<JsonNode> sendTest() {
        try {
            Message messageA = new Message("RMQ_SYS_TRANS_HALF_TOPIC", "tagA", mapper.writeValueAsBytes("A业务！"));
            Message messageB = new Message("RMQ_SYS_TRANS_HALF_TOPIC", "tagB", mapper.writeValueAsBytes("B业务！"));
            Message messageC = new Message("RMQ_SYS_TRANS_HALF_TOPIC", "tagC", mapper.writeValueAsBytes("C业务！"));
            sendResult = rocketMQTemplate.syncSend(this.topicAndTag(messageA),messageA);
            sendResult = rocketMQTemplate.syncSend(this.topicAndTag(messageB),messageB);
            sendResult = rocketMQTemplate.syncSend(this.topicAndTag(messageC),messageC);
        } catch (Exception e) {
            LogCommons.error("消息推送异常:{}",e);
            LogCommons.error("消息推送异常msg:{}",map);
        }
        LogCommons.info("result :{}",sendResult);
        return new AjaxResult<JsonNode>().success();
    }

    private String topicAndTag(Message message){
        return message.getTopic()+":"+message.getTags();
    }

}
