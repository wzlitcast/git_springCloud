package com.it.cast.listener;


import com.aspire.commons.AjaxResult;
import com.aspire.commons.entity.email.MailVo;
import com.aspire.commons.entity.excel.MessageVo;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;


public interface SendEmailService {

    void excelMail(MessageVo messageVo) ;

    AjaxResult<JsonNode> commonMail(MailVo mailVo) throws Exception;

    AjaxResult<JsonNode> bufferTypeMail(byte[] bytes,String email,String fileName) throws IOException, Exception;

    AjaxResult<JsonNode> commonMail2(String param) throws IOException, Exception;
}
