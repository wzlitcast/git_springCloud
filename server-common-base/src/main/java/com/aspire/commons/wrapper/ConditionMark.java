package com.aspire.commons.wrapper;

public enum ConditionMark {
	//AbstractWrapper
	SET_ENTITY("设置实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）"),
	
	ALL_EQ("全部eq(或个别isNull)"), 
	EQ("等于 ="), 
	NE("不等于 <>"),
	GT("大于 >"),
	GE("大于等于 >="),
	LT("小于 <"),
	LE("小于等于 <="),
	BETWEEN("BETWEEN 值1 AND 值2"),
	NOT_BETWEEN("NOT BETWEEN 值1 AND 值2"),
	LIKE("LIKE '%值%'"),
	NOT_LIKE("NOT LIKE '%值%'"),
	LIKE_LEFT("LIKE '%值'"),
	LIKE_RIGHT("LIKE '值%'"),
	IS_NULL("字段 IS NULL"),
	IS_NOT_NULL("字段 IS NOT NULL"),
	IN("字段 IN (v0,v1, ...)"),
	NOT_IN("字段 NOT IN (v0,v1, ...)"),
	IN_SQL("字段 IN ( sql语句 )"),
	NOT_IN_SQL("字段 NOT IN ( sql语句 )"),
	GROUP_BY("分组：GROUP BY 字段, ..."),
	ORDER_BY_ASC("排序：ORDER BY 字段, ... ASC"),
	ORDER_BY_DESC("排序：ORDER BY 字段, ... DESC"),
	ORDER_BY("排序：ORDER BY 字段, ..."),
	HAVING("HAVING ( sql语句 )"),
	APPLY("拼接 sql"), 
	OR("拼接 OR"), 
	AND("AND 嵌套"), 
	LAST("无视优化规则直接拼接到 sql 的最后"), 
	EXISTS("拼接 EXISTS ( sql语句 )"), 
	NOT_EXISTS("拼接 OR"), 
	NESTED("正常嵌套 不带 AND 或者 OR"),
	
	//QueryWrapper
	SELECT("设置查询字段"),
	
	//UpdateWrapper
	SET("SQL SET 字段"),
	SET_SQL("设置 SET 部分 SQL"),
	LAMBDA("获取 LambdaWrapper")
	;

	public String getComment() {
		return comment;
	}


	private String comment;

	private ConditionMark(String comment) {
		this.comment = comment;
	}
}
