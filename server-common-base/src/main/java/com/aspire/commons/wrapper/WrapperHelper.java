package com.aspire.commons.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspire.commons.log.LogCommons;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WrapperHelper
 * @author chenwei_b
 */
public class WrapperHelper {
	
	public static <T> void parseCommonWrapper(CommonWrapper cw, AbstractWrapper<T, String, ?> w, Class<T> entityType) {
		if (null == cw || null == w || null == entityType) return ;
		// 解析Update实体
		if (w instanceof UpdateWrapper) {
			cw.setUpdateEntity(parseObject(cw.getUpdateEntity(), entityType));
		} else {
			cw.setUpdateEntity(null);
		}
		// 解析Wrapper实体
		cw.getDataList().forEach(i -> matchConditions(w, i, entityType));
	}
	
	@SuppressWarnings("unchecked")
	private static <T> void matchConditions(AbstractWrapper<T, String, ?> w, WrapperData wd, Class<T> entityType) {
		Object[] params = wd.getParams();
		if (wd.getConditionMark() == ConditionMark.SET_ENTITY) {
			w.setEntity((T) parseObject(params[0], entityType));
		}
		if (wd.getConditionMark() == ConditionMark.ALL_EQ) {
			w.allEq((Map<String, Object>) params[0]);
		}
		if (wd.getConditionMark() == ConditionMark.EQ) {
			w.eq((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.NE) {
			w.ne((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.GT) {
			w.gt((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.GE) {
			w.ge((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.LT) {
			w.lt((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.LE) {
			w.le((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.BETWEEN) {
			w.between((String) params[0], params[1], params[2]);
		}
		if (wd.getConditionMark() == ConditionMark.NOT_BETWEEN) {
			w.notBetween((String) params[0], params[1], params[2]);
		}
		if (wd.getConditionMark() == ConditionMark.LIKE) {
			w.like((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.NOT_LIKE) {
			w.notLike((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.LIKE_LEFT) {
			w.likeLeft((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.LIKE_RIGHT) {
			w.likeRight((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.IS_NULL) {
			w.isNull((String) params[0]);
		}
		if (wd.getConditionMark() == ConditionMark.IS_NOT_NULL) {
			w.isNotNull((String) params[0]);
		}
		if (wd.getConditionMark() == ConditionMark.IN) {
			w.in((String) params[0], ((ArrayList<?>) params[1]).toArray());
		}
		if (wd.getConditionMark() == ConditionMark.NOT_IN) {
			w.notIn((String) params[0], ((ArrayList<?>) params[1]).toArray());
		}
		if (wd.getConditionMark() == ConditionMark.IN_SQL) {
			w.inSql((String) params[0], (String) params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.NOT_IN_SQL) {
			w.notInSql((String) params[0], (String) params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.GROUP_BY) {
			List<?> list = (List<?>) params[0];
			w.groupBy(list.toArray(new String[list.size()]));
		}
		if (wd.getConditionMark() == ConditionMark.ORDER_BY_ASC) {
			List<?> list = (List<?>) params[0];
			w.orderByAsc(list.toArray(new String[list.size()]));
		}
		if (wd.getConditionMark() == ConditionMark.ORDER_BY_DESC) {
			List<?> list = (List<?>) params[0];
			w.orderByDesc(list.toArray(new String[list.size()]));
		}
		/*if (wd.getConditionMark() == ConditionMark.ORDER_BY) {
			w.orderBy((String[]) params[0]);
		}*/
		if (wd.getConditionMark() == ConditionMark.HAVING) {
			w.having((String) params[0], ((ArrayList<?>) params[1]).toArray());
		}

		if (wd.getConditionMark() == ConditionMark.OR) {
			if (params == null) {
				w.or();
			} else {
				w.or(q -> {
					parseObject(params[0], CommonWrapper.class).getDataList().forEach(i -> matchConditions(q, i, entityType));
					return q;
				});
			}
		}
		if (wd.getConditionMark() == ConditionMark.AND) {
			w.and(q -> {
				parseObject(params[0], CommonWrapper.class).getDataList().forEach(i -> matchConditions(q, i, entityType));
				return q;
			});
		}
		if (wd.getConditionMark() == ConditionMark.APPLY) {
			w.apply((String) params[0], ((ArrayList<?>) params[1]).toArray());
		}
		if (wd.getConditionMark() == ConditionMark.LAST) {
			w.last((String) params[0]);
		}
		if (wd.getConditionMark() == ConditionMark.EXISTS) {
			w.exists((String) params[0]);
		}
		if (wd.getConditionMark() == ConditionMark.NOT_EXISTS) {
			w.notExists((String) params[0]);
		}
		if (wd.getConditionMark() == ConditionMark.NESTED) {
			w.nested(q -> {
				parseObject(params[0], CommonWrapper.class).getDataList().forEach(i -> matchConditions(q, i, entityType));
				return q;
			});
		}
		// 以下是QueryWrapper私有的API
		if (wd.getConditionMark() == ConditionMark.SELECT && w instanceof QueryWrapper) {
			QueryWrapper<T> qw = (QueryWrapper<T>) w;
			List<?> list = (List<?>) params[0];
			qw.select(list.toArray(new String[list.size()]));
		}
		// 以下是UpdateWrapper私有的API
		if (wd.getConditionMark() == ConditionMark.SET && w instanceof UpdateWrapper) {
			UpdateWrapper<T> uw = (UpdateWrapper<T>) w;
			uw.set((String) params[0], params[1]);
		}
		if (wd.getConditionMark() == ConditionMark.SET_SQL && w instanceof UpdateWrapper) {
			UpdateWrapper<T> uw = (UpdateWrapper<T>) w;
			uw.setSql((String) params[0]);
		}
		/*if (wd.getConditionMark() == ConditionMark.LAMBDA && w instanceof UpdateWrapper) {
			UpdateWrapper<T> uw = (UpdateWrapper<T>) w;
			uw.lambda();
		}*/
	}

	private static <T> T parseObject(Object o, Class<T> entityType) {
		ObjectMapper mapper = new ObjectMapper();
		T t = null;
		try {
			if (null != o) t = mapper.readValue(mapper.writeValueAsString(o), entityType);
		} catch (Exception e) {
			//日志 解析实体异常
			LogCommons.error("parseObject",e);
		}
		try {
		if (null == t) t = entityType.newInstance();
		} catch (Exception e) {
			//如果没有默认构造函数就抛出InstantiationException, 如果没有访问默认构造函数的权限就抛出IllegalAccessException
			//日志 newInstance
			LogCommons.error("newObject",e);
		}
		return t;
	}
}
