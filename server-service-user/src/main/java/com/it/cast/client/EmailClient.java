package com.it.cast.client;

import com.aspire.commons.AjaxResult;
import com.aspire.commons.VersionConstant;
import com.aspire.commons.entity.excel.MessageVo;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("server-common-email")
public interface EmailClient {

    @ApiOperation(value = "Excel缓冲式生成邮件发送", notes = "响应200表示成功,403表示参数为空,500表示执行SQL异常")
    @PostMapping("/" + VersionConstant.VERSION + "/email/bufferTypeMail")
    AjaxResult<JsonNode> bufferTypeMail(@RequestBody byte[] bytes, @RequestParam String email,@RequestParam String fileName);

    @ApiOperation(value = "导入导出数据邮件发送", notes = "响应200表示成功,403表示参数为空,500表示执行SQL异常")
    @PostMapping("/" + VersionConstant.VERSION + "/email/excelMail")
    AjaxResult<JsonNode> excelMail(@RequestBody MessageVo messageVo);
}
