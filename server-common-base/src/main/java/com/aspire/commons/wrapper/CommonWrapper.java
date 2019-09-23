package com.aspire.commons.wrapper;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonWrapper implements Serializable {

	private static final long serialVersionUID = 5230118347087194603L;

	private List<WrapperData> dataList = new ArrayList<>();
	
	private transient Object updateEntity;

	private Page page;

	@Override
	public String toString() {
		return "CommonWrapper [dataList=" + dataList + ", updateEntity=" + updateEntity + "]";
	}

	/**
	 * 设置实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
	 * @param entity
	 * @return
	 */
	public <T> CommonWrapper setEntity(T entity) {
		dataList.add(new WrapperData(ConditionMark.SET_ENTITY, new Object[] { entity }));
		return this;
	}

	/**
	 * 全部eq
	 * 例1: allEq({id:1,name:"老王",age:null})--->id = 1 and name = '老王' and age is null
	 * @param params
	 * @return
	 */
	public CommonWrapper allEq(Map<String, Object> params) {
		dataList.add(new WrapperData(ConditionMark.ALL_EQ, new Object[] { params }));
		return this;
	}

	/**
	 * 等于 =
	 * 例: eq(User::getName, "老王")--->name = '老王'
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper eq(SFunction<T, ?> column, Object val) {
		return eq(columnToString(column), val);
	}

	/**
	 * 等于 =
	 * 例: eq("name", "老王")--->name = '老王'
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper eq(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.EQ, new Object[] { column, val }));
		return this;
	}

	/**
	 * 不等于 <>
	 * 例: ne(User::getName, "老王")--->name <> '老王'
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper ne(SFunction<T, ?> column, Object val) {
		return ne(columnToString(column), val);
	}

	/**
	 * 不等于 <>
	 * 例: ne("name", "老王")--->name <> '老王'
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper ne(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.NE, new Object[] { column, val }));
		return this;
	}

	/**
	 * 大于 >
	 * 例: gt(User::getAge, 18)--->age > 18
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper gt(SFunction<T, ?> column, Object val) {
		return gt(columnToString(column), val);
	}

	/**
	 * 大于 >
	 * 例: gt("age", 18)--->age > 18
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper gt(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.GT, new Object[] { column, val }));
		return this;
	}

	/**
	 * 大于等于 >=
	 * 例: ge(User::getAge, 18)--->age >= 18
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper ge(SFunction<T, ?> column, Object val) {
		return ge(columnToString(column), val);
	}

	/**
	 * 大于等于 >=
	 * 例: ge("age", 18)--->age >= 18
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper ge(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.GE, new Object[] { column, val }));
		return this;
	}

	/**
	 * 小于 <
	 * 例: lt(User::getAge, 18)--->age < 18
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper lt(SFunction<T, ?> column, Object val) {
		return lt(columnToString(column), val);
	}

	/**
	 * 小于 <
	 * 例: lt("age", 18)--->age < 18
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper lt(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.LT, new Object[] { column, val }));
		return this;
	}

	/**
	 * 小于等于 <=
	 * 例: le(User::getAge, 18)--->age <= 18
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper le(SFunction<T, ?> column, Object val) {
		return le(columnToString(column), val);
	}

	/**
	 * 小于等于 <=
	 * 例: le("age", 18)--->age <= 18
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper le(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.LE, new Object[] { column, val }));
		return this;
	}

	/**
	 * BETWEEN 值1 AND 值2
	 * 例: between(User:getAge, 18, 30)--->age between 18 and 30
	 * @param <T>
	 * @param column
	 * @param val1
	 * @param val2
	 * @return
	 */
	public <T> CommonWrapper between(SFunction<T, ?> column, Object val1, Object val2) {
		return between(columnToString(column), val1, val2);
	}

	/**
	 * BETWEEN 值1 AND 值2
	 * 例: between("age", 18, 30)--->age between 18 and 30
	 * @param column
	 * @param val1
	 * @param val2
	 * @return
	 */
	public CommonWrapper between(String column, Object val1, Object val2) {
		dataList.add(new WrapperData(ConditionMark.BETWEEN, new Object[] { column, val1, val2 }));
		return this;
	}

	/**
	 * NOT BETWEEN 值1 AND 值2
	 * notBetween(User:getAge, 18, 30)--->age not between 18 and 30
	 * @param <T>
	 * @param column
	 * @param val1
	 * @param val2
	 * @return
	 */
	public <T> CommonWrapper notBetween(SFunction<T, ?> column, Object val1, Object val2) {
		return notBetween(columnToString(column), val1, val2);
	}

	/**
	 * NOT BETWEEN 值1 AND 值2
	 * notBetween("age", 18, 30)--->age not between 18 and 30
	 * @param column
	 * @param val1
	 * @param val2
	 * @return
	 */
	public CommonWrapper notBetween(String column, Object val1, Object val2) {
		dataList.add(new WrapperData(ConditionMark.NOT_BETWEEN, new Object[] { column, val1, val2 }));
		return this;
	}

	/**
	 * LIKE '%值%'
	 * 例: like(User::getName, "王")--->name like '%王%'
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper like(SFunction<T, ?> column, Object val) {
		return like(columnToString(column), val);
	}

	/**
	 * LIKE '%值%'
	 * 例: like("name", "王")--->name like '%王%'
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper like(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.LIKE, new Object[] { column, val }));
		return this;
	}

	/**
	 * NOT LIKE '%值%'
	 * 例: notLike(User::getName, "王")--->name not like '%王%'
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper notLike(SFunction<T, ?> column, Object val) {
		return notLike(columnToString(column), val);
	}

	/**
	 * NOT LIKE '%值%'
	 * 例: notLike("name", "王")--->name not like '%王%'
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper notLike(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.NOT_LIKE, new Object[] { column, val }));
		return this;
	}

	/**
	 * LIKE '%值'
	 * 例: likeLeft(User::getName, "王")--->name like '%王'
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper likeLeft(SFunction<T, ?> column, Object val) {
		return likeLeft(columnToString(column), val);
	}

	/**
	 * LIKE '%值'
	 * 例: likeLeft("name", "王")--->name like '%王'
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper likeLeft(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.LIKE_LEFT, new Object[] { column, val }));
		return this;
	}

	/**
	 * LIKE '值%'
	 * 例: likeRight(User::getName, "王")--->name like '王%'
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper likeRight(SFunction<T, ?> column, Object val) {
		return likeRight(columnToString(column), val);
	}

	/**
	 * LIKE '值%'
	 * 例: likeRight("name", "王")--->name like '王%'
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper likeRight(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.LIKE_RIGHT, new Object[] { column, val }));
		return this;
	}

	/**
	 * 字段 IS NULL
	 * isNull(User::getName)--->name is null
	 * @param <T>
	 * @param column
	 * @return
	 */
	public <T> CommonWrapper isNull(SFunction<T, ?> column) {
		return isNull(columnToString(column));
	}

	/**
	 * 字段 IS NULL
	 * isNull("name")--->name is null
	 * @param column
	 * @return
	 */
	public CommonWrapper isNull(String column) {
		dataList.add(new WrapperData(ConditionMark.IS_NULL, new Object[] { column }));
		return this;
	}

	/**
	 * 字段 IS NOT NULL
	 * 例: isNotNull(User::getName)--->name is not null
	 * @param <T>
	 * @param column
	 * @return
	 */
	public <T> CommonWrapper isNotNull(SFunction<T, ?> column) {
		return isNotNull(columnToString(column));
	}

	/**
	 * 字段 IS NOT NULL
	 * 例: isNotNull("name")--->name is not null
	 * @param column
	 * @return
	 */
	public CommonWrapper isNotNull(String column) {
		dataList.add(new WrapperData(ConditionMark.IS_NOT_NULL, new Object[] { column }));
		return this;
	}

	/**
	 * 字段 IN (v0,v1, ...)
	 * 例: in(User::getAge,{1,2,3})--->age in (1,2,3)
	 * @param <T>
	 * @param column
	 * @param values
	 * @return
	 */
	public <T> CommonWrapper in(SFunction<T, ?> column, Object... values) {
		return in(columnToString(column), values);
	}

	/**
	 * 字段 IN (v0,v1, ...)
	 * 例: in("age",{1,2,3})--->age in (1,2,3)
	 * @param column
	 * @param values
	 * @return
	 */
	public CommonWrapper in(String column, Object... values) {
		dataList.add(new WrapperData(ConditionMark.IN, new Object[] { column, values }));
		return this;
	}

	/**
	 * 字段 NOT IN (v0,v1, ...)
	 * 例: notIn(User::getAge,{1,2,3})--->age not in (1,2,3)
	 * @param <T>
	 * @param column
	 * @param values
	 * @return
	 */
	public <T> CommonWrapper notIn(SFunction<T, ?> column, Object... values) {
		return notIn(columnToString(column), values);
	}

	/**
	 * 字段 NOT IN (v0,v1, ...)
	 * 例: notIn("age",{1,2,3})--->age not in (1,2,3)
	 * @param column
	 * @param values
	 * @return
	 */
	public CommonWrapper notIn(String column, Object... values) {
		dataList.add(new WrapperData(ConditionMark.NOT_IN, new Object[] { column, values }));
		return this;
	}

	/**
	 * 字段 IN ( sql语句 )
	 * 例: inSql(User::getAge, "1,2,3,4,5,6")--->age in (1,2,3,4,5,6)
	 * @param <T>
	 * @param column
	 * @param inValue
	 * @return
	 */
	public <T> CommonWrapper inSql(SFunction<T, ?> column, String inValue) {
		return inSql(columnToString(column), inValue);
	}

	/**
	 * 字段 IN ( sql语句 )
	 * 例: inSql("age", "1,2,3,4,5,6")--->age in (1,2,3,4,5,6)
	 * @param column
	 * @param inValue
	 * @return
	 */
	public CommonWrapper inSql(String column, String inValue) {
		dataList.add(new WrapperData(ConditionMark.IN_SQL, new Object[] { column, inValue }));
		return this;
	}

	/**
	 * 字段 NOT IN ( sql语句 )
	 * 例: notInSql(User::getAge, "1,2,3,4,5,6")--->age not in (1,2,3,4,5,6)
	 * 例: notInSql("id", "select id from table where id < 3")--->id not in (select id from table where id < 3)
	 * @param <T>
	 * @param column
	 * @param inValue
	 * @return
	 */
	public <T> CommonWrapper notInSql(SFunction<T, ?> column, String inValue) {
		return notInSql(columnToString(column), inValue);
	}

	/**
	 * 字段 NOT IN ( sql语句 )
	 * 例: notInSql("age", "1,2,3,4,5,6")--->age not in (1,2,3,4,5,6)
	 * 例: notInSql("id", "select id from table where id < 3")--->id not in (select id from table where id < 3)
	 * @param column
	 * @param inValue
	 * @return
	 */
	public CommonWrapper notInSql(String column, String inValue) {
		dataList.add(new WrapperData(ConditionMark.NOT_IN_SQL, new Object[] { column, inValue }));
		return this;
	}

	/**
	 * 分组：GROUP BY 字段, ...
	 * 例: groupBy(User::getId, User::getName)--->group by id,name
	 * @param <T>
	 * @param columns
	 * @return
	 */
	@SafeVarargs
	public final <T> CommonWrapper groupBy(SFunction<T, ?>... columns) {
		if (ArrayUtils.isEmpty(columns)) {
			return this;
		}
		return groupBy(columnsToStringArray(columns));
	}

	/**
	 * 分组：GROUP BY 字段, ...
	 * 例: groupBy("id", "name")--->group by id,name
	 * @param columns
	 * @return
	 */
	public CommonWrapper groupBy(String... columns) {
		dataList.add(new WrapperData(ConditionMark.GROUP_BY, new Object[] { columns }));
		return this;
	}

	/**
	 * 排序：ORDER BY 字段, ... ASC
	 * 例: orderByAsc(User::getId, User::getName)--->order by id ASC,name ASC
	 * @param <T>
	 * @param columns
	 * @return
	 */
	@SafeVarargs
	public final <T> CommonWrapper orderByAsc(SFunction<T, ?>... columns) {
		if (ArrayUtils.isEmpty(columns)) {
			return this;
		}
		return orderByAsc(columnsToStringArray(columns));
	}

	/**
	 * 排序：ORDER BY 字段, ... ASC
	 * 例: orderByAsc("id", "name")--->order by id ASC,name ASC
	 * @param columns
	 * @return
	 */
	public CommonWrapper orderByAsc(String... columns) {
		dataList.add(new WrapperData(ConditionMark.ORDER_BY_ASC, new Object[] { columns }));
		return this;
	}

	/**
	 * 排序：ORDER BY 字段, ... DESC
	 * 例: orderByDesc(User::getId, User::getName)--->order by id DESC,name DESC
	 * @param <T>
	 * @param columns
	 * @return
	 */
	@SafeVarargs
	public final <T> CommonWrapper orderByDesc(SFunction<T, ?>... columns) {
		if (ArrayUtils.isEmpty(columns)) {
			return this;
		}
		return orderByDesc(columnsToStringArray(columns));
	}

	/**
	 * 排序：ORDER BY 字段, ... DESC
	 * 例: orderByDesc("id", "name")--->order by id DESC,name DESC
	 * @param columns
	 * @return
	 */
	public CommonWrapper orderByDesc(String... columns) {
		dataList.add(new WrapperData(ConditionMark.ORDER_BY_DESC, new Object[] { columns }));
		return this;
	}

	/**
	 * 排序：ORDER BY 字段, ...
	 * 例: orderBy(true, true, "id", "name")--->order by id ASC,name ASC
	 * @param condition
	 * @param isAsc
	 * @param columns
	 * @return
	 */
	/*public CommonWrapper orderBy(boolean condition, boolean isAsc, String... columns) {
		dataList.add(new WrapperData(ConditionMark.ORDER_BY, new Object[] { columns }));
		return this;
	}*/

	/**
	 * HAVING ( sql语句 )
	 * 例: having("sum(age) > 10")--->having sum(age) > 10
	 * 例: having("sum(age) > {0}", 11)--->having sum(age) > 11
	 * @param column
	 * @param params
	 * @return
	 */
	public CommonWrapper having(String column, Object... params) {
		dataList.add(new WrapperData(ConditionMark.HAVING, new Object[] { column, params }));
		return this;
	}

	/**
	 * 拼接 OR
	 * 注意：主动调用or表示紧接着下一个方法不是用and连接!(不调用or则默认为使用and连接)
	 * 例: eq("id",1).or().eq("name","老王")--->id = 1 or name = '老王'
	 * @return
	 */
	public CommonWrapper or() {
		dataList.add(new WrapperData(ConditionMark.OR, null));
		return this;
	}

	/**
	 * OR 嵌套
	 * 例: or(i -> i.eq("name", "李白").ne("status", "活着"))--->or (name = '李白' and status <> '活着')
	 * @param func
	 * @return
	 */
	public CommonWrapper or(Function<CommonWrapper, CommonWrapper> func) {
		dataList.add(new WrapperData(ConditionMark.OR, new Object[] { func.apply(new CommonWrapper()) }));
		return this;
	}

	/**
	 * AND 嵌套
	 * 例: and(i -> i.eq("name", "李白").ne("status", "活着"))--->and (name = '李白' and status <> '活着')
	 * @param func
	 * @return
	 */
	public CommonWrapper and(Function<CommonWrapper, CommonWrapper> func) {
		dataList.add(new WrapperData(ConditionMark.AND, new Object[] { func.apply(new CommonWrapper()) }));
		return this;
	}

	/**
	 * 拼接 sql
	 * 注意：该方法可用于数据库函数 动态入参的params对应前面sqlApply内部的{index}部分.这样是不会有sql注入风险的,反之会有!
	 * 例: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
	 * 例: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
	 * @param sqlApply
	 * @param params
	 * @return
	 */
	public CommonWrapper apply(String sqlApply, Object... params) {
		dataList.add(new WrapperData(ConditionMark.APPLY, new Object[] { sqlApply, params }));
		return this;
	}

	/**
	 * 无视优化规则直接拼接到 sql 的最后
	 * 注意：只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用
	 * 例: last("limit 1")
	 * @param lastSql
	 * @return
	 */
	public CommonWrapper last(String lastSql) {
		dataList.add(new WrapperData(ConditionMark.LAST, new Object[] { lastSql }));
		return this;
	}

	/**
	 * 拼接 EXISTS ( sql语句 )
	 * 例: exists("select id from table where age = 1")--->exists (select id from table where age = 1
	 * @param existsSql
	 * @return
	 */
	public CommonWrapper exists(String existsSql) {
		dataList.add(new WrapperData(ConditionMark.EXISTS, new Object[] { existsSql }));
		return this;
	}

	/**
	 * 拼接 NOT EXISTS ( sql语句 )
	 * 例: notExists("select id from table where age = 1")--->not exists (select id from table where age = 1)
	 * @param existsSql
	 * @return
	 */
	public CommonWrapper notExists(String existsSql) {
		dataList.add(new WrapperData(ConditionMark.NOT_EXISTS, new Object[] { existsSql }));
		return this;
	}

	/**
	 * 正常嵌套 不带 AND 或者 OR
	 * 例: nested(i -> i.eq("name", "李白").ne("status", "活着"))--->(name = '李白' and status <> '活着')
	 * @param func
	 * @return
	 */
	public CommonWrapper nested(Function<CommonWrapper, CommonWrapper> func) {
		dataList.add(new WrapperData(ConditionMark.NESTED, new Object[] { func.apply(new CommonWrapper()) }));
		return this;
	}

	// 以下queryWrapper
	/**
	 * 设置查询字段
	 * 例: select(User::getId, User::getName, User::getAge)--->select id, name, age
	 * @param <T>
	 * @param columns
	 * @return
	 */
	@SafeVarargs
	public final <T> CommonWrapper select(SFunction<T, ?>... columns) {
		if (ArrayUtils.isEmpty(columns)) {
			return this;
		}
		return select(columnsToStringArray(columns));
	}

	/**
	 * 设置查询字段
	 * 例: select("id", "name", "age")--->select id, name, age
	 * @param columns
	 * @return
	 */
	public final CommonWrapper select(String... columns) {
		dataList.add(new WrapperData(ConditionMark.SELECT, new Object[] { columns }));
		return this;
	}

	/**
	 * 设置查询字段
	 * 例: select(i -> i.getProperty().startsWith("test"))
	 * @param predicate
	 * @return
	 */
	/*public CommonWrapper select(Predicate<TableFieldInfo> predicate) {
		dataList.add(new WrapperData(ConditionMark.SELECT, new Object[] { predicate }));
		return this;
	}*/


	// 以下updateWrapper
	/**
	 * SQL SET 字段
	 * 例: set(User::getName, "老李头")--->set name = '老李头'
	 * 例: set(User::getName, "")--->set name = ''
	 * 例: set(User::getName, null)--->set name = null
	 * @param <T>
	 * @param column
	 * @param val
	 * @return
	 */
	public <T> CommonWrapper set(SFunction<T, ?> column, Object val) {
		return set(columnToString(column), val);
	}

	/**
	 * SQL SET 字段
	 * 例: set("name", "老李头")--->set name = '老李头'
	 * 例: set("name", "")--->set name = ''
	 * 例: set("name", null)--->set name = null
	 * @param column
	 * @param val
	 * @return
	 */
	public CommonWrapper set(String column, Object val) {
		dataList.add(new WrapperData(ConditionMark.SET, new Object[] { column, val }));
		return this;
	}

	/**
	 * 设置 SET 部分 SQL
	 * 例: set("name = '老李头', age = 18")--->set name = '老李头', age = 18
	 * @param sql
	 * @return
	 */
	public CommonWrapper setSql(String sql) {
		dataList.add(new WrapperData(ConditionMark.SET_SQL, new Object[] { sql }));
		return this;
	}

	/**
	 * 通过lambda将类的属性名转成下划线的字段名
	 * @param column
	 * @return
	 */
	public <T> String columnToString(SFunction<T, ?> column) {
		return StringUtils.camelToUnderline(StringUtils.resolveFieldName((LambdaUtils.resolve(column).getImplMethodName())));
	}

	/**
	 * 多字段转换为逗号 "," 分割字符串
	 * @param <T>
	 * @param columns 多字段
	 */
	@SafeVarargs
	public final <T> String columnsToString(SFunction<T, ?>... columns) {
		return Arrays.stream(columns).map(this::columnToString).collect(joining(StringPool.COMMA));
	}

	@SafeVarargs
	public final <T> String[] columnsToStringArray(SFunction<T, ?>... columns) {
		String[] columnsTo = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			columnsTo[i] = columnToString(columns[i]);
		}
		return columnsTo;
	}
}
