package com.it.cast.atomic.user.mapper;

import com.it.cast.atomic.user.entity.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-06-11
 */
@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {
	int insertBackup(String userId);

}
