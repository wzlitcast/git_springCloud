package com.it.cast.client;


import com.aspire.commons.AjaxResult;
import com.aspire.commons.entity.excel.ImportBaseVo;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("server-service-user")
public interface UserClient {

    @PostMapping(value = "/v1/user/importUser")
    @ApiOperation(value = "新增", notes = "新增")
    AjaxResult<JsonNode> importUser(@RequestBody String param);

}
