package com.it.cast.atomic.user.service.impl;

import com.it.cast.atomic.user.entity.TbUser;
import com.it.cast.atomic.user.mapper.TbUserMapper;
import com.it.cast.atomic.user.service.TbUserAtomicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aspire.commons.utils.BeanHelper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import java.util.regex.Pattern;
import com.aspire.commons.custom_judgment.Precondition;
import com.aspire.commons.AjaxResult;
import static com.aspire.commons.DebugCommon.*;
import com.aspire.commons.autoid.IdWorker;
import com.aspire.commons.valid_list.ValidList;
import com.aspire.commons.response_json.CommonJsonNode;


/**
 * 
 *  服务实现类
 *
 * @since 2019-06-11
 */
@Service
public class TbUserAtomicServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserAtomicService {
	@Autowired
    private TbUserMapper mapper;
	
	@Autowired
	private IdWorker idWorker;

	static final String KEY = "result";

	static final String USER = "user";

	static final String USERID = "userId";

	static final String WRAPPER = "queryWrapper";

    @Override
    public String insertTbUser(TbUser tbuser){
		if(StringUtils.isEmpty(tbuser.getUserId())){
                tbuser.setUserId(String.valueOf(idWorker.nextId()));
		}
		super.save(tbuser);
        return tbuser.getUserId();
    }


    @Override
    public List<String> insertTbUsers(@Validated({TbUser.Save.class}) ValidList<TbUser> tbusers) {
        List<String> list= new ArrayList<>();
		tbusers.forEach(tbuser -> {
			if(StringUtils.isEmpty(tbuser.getUserId())){
                tbuser.setUserId(String.valueOf(idWorker.nextId()));
			}
            list.add(String.valueOf(tbuser.getUserId()));
        });
        super.saveBatch(tbusers);
        return list;
    }


    @Override
    public TbUser getTbUser(String userId){
        Precondition.checkArgument(Pattern.compile(INTEGER).matcher(userId).matches());
        return super.getById(userId);
    }


    @Override
    public List<Object> getTbUserWrapperByObject(Wrapper<TbUser> queryWrapper){
        return super.listObjs(queryWrapper);
    }


    @Override
    public AjaxResult<JsonNode> getTbUserByPage(Page page,Wrapper<TbUser> queryWrapper){
        IPage<TbUser> result = super.page(page, queryWrapper);
        return new AjaxResult<JsonNode>().success(CommonJsonNode.resultsPage(result));
    }
	
	
	@Override
    public IPage<TbUser> getPage(Page page,Wrapper<TbUser> queryWrapper){
        return super.page(page, queryWrapper);
    }


    @Override
    public List<Map<String, Object>> getTbUserWrapperByMap(Wrapper<TbUser> queryWrapper){
        return BeanHelper.formatColNameForList(super.listMaps(queryWrapper));
    }


    @Override
    public Integer getTbUserWrapperByCount(Wrapper<TbUser> queryWrapper){
        return super.count(queryWrapper);
    }


    @Override
    public List<TbUser> getTbUserWrappers(Wrapper<TbUser> queryWrapper){
        return super.list(queryWrapper);
    }


    @Override
    public Collection<TbUser> getTbUserIds(ValidList<String> ids){
        return super.listByIds(ids);
    }


    @Override
    public AjaxResult<JsonNode> getTbUserMapsByPage(Page page, Wrapper<TbUser> queryWrapper){
        IPage<Map<String, Object>> result = super.pageMaps(page, queryWrapper);
        return new AjaxResult<JsonNode>().success(CommonJsonNode.resultsMapsPage(result));
    }
	
	
	@Override
    public IPage<Map<String, Object>> getMapsByPage(Page page, Wrapper<TbUser> queryWrapper){
        return super.pageMaps(page, queryWrapper);
    }


    @Override
    public Boolean updateTbUser(TbUser tbuser){
        String userId = tbuser.getUserId();
		Precondition.checkArgument(Pattern.compile(INTEGER).matcher(userId).matches());
        return super.updateById(tbuser);
    }


    @Override
    public Boolean updateTbUsers(ValidList<TbUser> tbusers){
        return super.updateBatchById(tbusers);
    }


    @Override
    public Boolean updateTbUserByWrapper(TbUser tbuser, Wrapper<TbUser> updateWrapper){
        return super.update(tbuser,updateWrapper);
    }


    @Override
    public Boolean deleteTbUser(String userId){
        Precondition.checkArgument(Pattern.compile(INTEGER).matcher(userId).matches());
        return super.removeById(userId);
    }


    @Override
    public Boolean deleteTbUserBybackup(String userId){
		Precondition.checkArgument(Pattern.compile(INTEGER).matcher(userId).matches());
        insertBackup(userId);
        return super.removeById(userId);
    }


    @Override
    public Boolean deleteTbUserByIds(@NonNull ValidList<String> ids){
		Precondition.parametercheckIsEmpty(ids.isEmpty());
        return super.removeByIds(ids);
    }


    @Override
    public Boolean deleteTbUserBybackups(@NonNull ValidList<String> ids){
		Precondition.parametercheckIsEmpty(ids.isEmpty());
		ids.forEach(item->insertBackup(item));
        return super.removeByIds(ids);
    }


    @Override
    public Boolean deleteTbUserByWrapper(Wrapper<TbUser> queryWrapper){
		Precondition.parametercheckIsEmpty(null==queryWrapper.getSqlSegment());
        return super.remove(queryWrapper);
    }


	/**
     * 新增到备份表数据
     * @param
     */
    private void insertBackup(String userId){
        mapper.insertBackup(userId);
    }

}

