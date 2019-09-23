package com.it.cast.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.cast.atomic.user.entity.TbUser;
import com.it.cast.atomic.user.service.TbUserAtomicService;
import com.it.cast.client.EmailClient;
import com.it.cast.client.KafkaClient;
import com.it.cast.service.user.service.TbUserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.aspire.commons.AjaxResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.aspire.commons.response_json.CommonJsonNode;
import org.springframework.util.CollectionUtils;
import java.util.*;

/**
 * 
 *  服务实现类
 *
 * @since 2019-06-11
 */
@Service
public class TbUserServiceImpl implements TbUserService {

	@Autowired
	private TbUserAtomicService client;

    @Autowired
    private KafkaClient kafkaClient;

    @Autowired
    private EmailClient emailClient;

	
	@Override
    public AjaxResult<JsonNode> createTbUser(TbUser tbuser) {
        return new AjaxResult<JsonNode>().success(CommonJsonNode.results(client.insertTbUser(tbuser)));
    }

    @Override
    public AjaxResult<JsonNode> updateTbUser(TbUser tbuser) {
        return new AjaxResult<JsonNode>().success(CommonJsonNode.results(client.updateTbUser(tbuser)));
    }

    @Override
    public AjaxResult<JsonNode> getTbUser(String userId) {
        return new AjaxResult<JsonNode>().success(CommonJsonNode.results(client.getTbUser(userId)));
    }

    @Override
    public AjaxResult<JsonNode> deleteTbUser(String userId) {
        return new AjaxResult<JsonNode>().success(CommonJsonNode.results(client.deleteTbUser(userId)));
    }

    @Override
    public AjaxResult<JsonNode> getTbUserByUserName(String userName) {
        LambdaQueryWrapper<TbUser> tbUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tbUserLambdaQueryWrapper.eq(TbUser::getUserName,userName);
        List<TbUser> tbUserWrappers = client.getTbUserWrappers(tbUserLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(tbUserWrappers)){
            return new AjaxResult<JsonNode>().success(CommonJsonNode.results(tbUserWrappers.get(0)));
        }
        return new AjaxResult<JsonNode>().fail("-1","用户不存在！");
    }

    @Override
    public AjaxResult<JsonNode> queryAll() {
        LambdaQueryWrapper<TbUser> tbUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<TbUser> tbUserWrappers = client.getTbUserWrappers(tbUserLambdaQueryWrapper);
        return new AjaxResult<JsonNode>().success(CommonJsonNode.results(tbUserWrappers));
    }

}

