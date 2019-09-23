package com.it.cast.service.user.controller;

import com.aspire.commons.analysis_excel.templateVo.ImportUser;
import com.aspire.commons.entity.excel.ImportBaseVo;
import com.aspire.commons.response_json.CommonJsonNode;
import com.it.cast.service.user.service.TbUserService;
import com.it.cast.atomic.user.entity.TbUser;
import com.aspire.commons.AjaxResult;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 *  应用实现类
 *
 * @since 2019-06-11
 */
@Slf4j
@RestController
public class TbUserController{

    @Autowired
    private TbUserService service;

    @PostMapping(value = "/v1/user/tbUser")
    @ApiOperation(value = "新增", notes = "新增")
    public AjaxResult<JsonNode> createTbUser(@RequestBody TbUser tbuser) {
        return service.createTbUser(tbuser);
    }

    @PostMapping(value = "/v1/user/queryAll")
    @ApiOperation(value = "查询所有", notes = "查询所有")
    public AjaxResult<JsonNode> queryAll() {
        return service.queryAll();
    }

    @PutMapping(value = "/v1/user/tbUser")
    @ApiOperation(value = "编辑", notes = "编辑")
    public AjaxResult<JsonNode> updateTbUser(@RequestBody TbUser tbuser) {
        return service.updateTbUser(tbuser);
    }

    @GetMapping(value = "/v1/user/tbUser/{userId}")
    @ApiOperation(value = "查询", notes = "查询")
    public AjaxResult<JsonNode> getTbUser(@PathVariable("userId") String userId) {
        return service.getTbUser(userId);
    }

    @GetMapping(value = "/v1/user/tbUser/findByName")
    @ApiOperation(value = "查询", notes = "查询")
    public AjaxResult<JsonNode> getTbUserByUserName(@RequestParam(value = "userName") String userName) {
        return service.getTbUserByUserName(userName);
    }

    @DeleteMapping(value = "/v1/user/tbUser/{userId}")
    @ApiOperation(value = "删除", notes = "删除")
    public AjaxResult<JsonNode> deleteTbUser(@PathVariable("userId") String userId) {
        return service.deleteTbUser(userId);
    }
}


