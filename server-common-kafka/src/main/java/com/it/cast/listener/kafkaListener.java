package com.it.cast.listener;

import com.aspire.commons.AjaxResult;
import com.aspire.commons.log.LogCommons;
import com.fasterxml.jackson.databind.JsonNode;
import com.it.cast.client.EmailClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description: 监听器
 * @author: WuZhiLong
 * @create: 2019-06-05 12:24
 **/
@Component
public class kafkaListener {

    @Autowired
    private EmailClient emailClient;

    @KafkaListener(topics = "t1")
    public void listenT1(ConsumerRecord<?, ?> cr) throws Exception {
        try {
            LogCommons.info("cr results:{}",cr);
            String msg = (String) cr.value();
            AjaxResult<JsonNode> ajaxResult = emailClient.commonMail2(msg);
            LogCommons.info("results:{}",ajaxResult);
        } catch (Exception e) {
            LogCommons.error("error:{}",e);
        }
    }

}