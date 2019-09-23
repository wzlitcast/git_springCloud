package com.it.cast.controller;

import com.aspire.commons.entity.email.BuffTypeExcelVo;
import com.aspire.commons.entity.email.MailVo;
import com.aspire.commons.log.LogCommons;
import com.it.cast.listener.SendEmailService;
import com.aspire.commons.AjaxResult;
import com.aspire.commons.VersionConstant;
import com.aspire.commons.entity.excel.MessageVo;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.FileInputStream;

@RestController
public class SendEmailController {

    @Resource
    SendEmailService sendExcelEmailService;

    @ApiOperation(value = "导入导出数据邮件发送", notes = "响应200表示成功,403表示参数为空,500表示执行SQL异常")
    @PostMapping("/" + VersionConstant.VERSION + "/email/excelMail")
    AjaxResult<JsonNode> excelMail(@RequestBody MessageVo messageVo) {
        long startTime=System.currentTimeMillis();
        sendExcelEmailService.excelMail(messageVo);
        long endTime=System.currentTimeMillis();
        LogCommons.info("生成文件+发送邮件耗时",()->endTime-startTime+"MS");
        return new AjaxResult<JsonNode>().success();
    }

    @ApiOperation(value = "通用邮件发送", notes = "响应200表示成功,403表示参数为空,500表示执行SQL异常")
    @PostMapping("/" + VersionConstant.VERSION + "/email/commonMail")
    AjaxResult<JsonNode> commonMail(@RequestBody MailVo mailVo) throws Exception {
        return  sendExcelEmailService.commonMail(mailVo);
    }

    @ApiOperation(value = "kafka推送邮件发送", notes = "响应200表示成功,403表示参数为空,500表示执行SQL异常")
    @PostMapping("/" + VersionConstant.VERSION + "/email/commonMail2")
    AjaxResult<JsonNode> commonMail2(@RequestBody String param) throws Exception {
        return  sendExcelEmailService.commonMail2(param);
    }

    @ApiOperation(value = "Excel缓冲式生成邮件发送", notes = "响应200表示成功,403表示参数为空,500表示执行SQL异常")
    @PostMapping("/" + VersionConstant.VERSION + "/email/bufferTypeMail")
    AjaxResult<JsonNode> bufferTypeMail(@RequestBody byte[] bytes, @RequestParam String email,@RequestParam String fileName) throws Exception {
        return  sendExcelEmailService.bufferTypeMail(bytes,email,fileName);
    }
}
