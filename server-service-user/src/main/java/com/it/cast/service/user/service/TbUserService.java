package com.it.cast.service.user.service;

import com.aspire.commons.analysis_excel.templateVo.ImportUser;
import com.aspire.commons.entity.excel.ImportBaseVo;
import com.it.cast.atomic.user.entity.TbUser;
import com.aspire.commons.AjaxResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2019-06-11
 */
public interface TbUserService {
	AjaxResult<JsonNode> createTbUser(TbUser tbuser);
	AjaxResult<JsonNode> updateTbUser(TbUser tbuser);
	AjaxResult<JsonNode> getTbUser(String userId);
	AjaxResult<JsonNode> deleteTbUser(String userId);
	AjaxResult<JsonNode> getTbUserByUserName(String userName);
    AjaxResult<JsonNode> queryAll();
}

