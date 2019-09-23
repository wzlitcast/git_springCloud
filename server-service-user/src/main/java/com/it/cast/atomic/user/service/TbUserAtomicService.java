package com.it.cast.atomic.user.service;

import com.it.cast.atomic.user.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.aspire.commons.valid_list.ValidList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.aspire.commons.AjaxResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * <p>
    *  服务类
    * </p>
 *
 * @author 
 * @since 2019-06-11
 */
public interface TbUserAtomicService extends IService<TbUser> {

    String insertTbUser(TbUser tbuser);

    List<String> insertTbUsers(ValidList<TbUser> tbusers);

    TbUser getTbUser(String userId);

    List<Object> getTbUserWrapperByObject(Wrapper<TbUser> queryWrapper);

    AjaxResult<JsonNode> getTbUserByPage(Page page,Wrapper<TbUser> queryWrapper);
	
	IPage<TbUser> getPage(Page page,Wrapper<TbUser> queryWrapper);

    List<Map<String, Object>> getTbUserWrapperByMap(Wrapper<TbUser> queryWrapper);

    Integer getTbUserWrapperByCount(Wrapper<TbUser> queryWrapper);

    List<TbUser> getTbUserWrappers(Wrapper<TbUser> queryWrapper);

    Collection<TbUser> getTbUserIds(ValidList<String> ids);

    AjaxResult<JsonNode> getTbUserMapsByPage(Page page,Wrapper<TbUser> queryWrapper);
	
	IPage<Map<String, Object>> getMapsByPage(Page page,Wrapper<TbUser> queryWrapper);

    Boolean updateTbUser(TbUser tbuser);

    Boolean updateTbUsers(ValidList<TbUser> tbusers);

    Boolean updateTbUserByWrapper(TbUser tbuser,Wrapper<TbUser> updateWrapper);

    Boolean deleteTbUser(String userId);

    Boolean deleteTbUserBybackup(String userId);

    Boolean deleteTbUserByIds(ValidList<String> ids);

    Boolean deleteTbUserBybackups(ValidList<String> ids);

    Boolean deleteTbUserByWrapper(Wrapper<TbUser> queryWrapper);


}
