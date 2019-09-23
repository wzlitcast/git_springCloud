package com.aspire.commons.analysis_json;

import com.aspire.commons.AjaxResult;
import com.aspire.commons.log.LogCommons;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalysisJson {

    static ObjectMapper mapper = new ObjectMapper();

    static String data = "data";
    static String results = "results";

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 解析AjaxResult<JsonNode>中data中的key,一般用作解析原子层单个数据新增返回ID
     *
     * @param json 需要解析Json
     * @param key  Json数据体中的data下面的key
     * @return key对应的value 返回类型是String
     * @Json解析异常，比如给的格式不合法，等等...·
     */
    public static String getString(AjaxResult<JsonNode> json, String key)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            return mapper.readTree(mapper.writeValueAsString(json)).path(data).path(key).textValue();
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getString()解析异常：{}", e);
            throw new Exception(e);
        }
    }


    public static Integer getInteger(AjaxResult<JsonNode> json, String key)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,key);
            if(json==null){
                return null;
            }
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(key).toString(), Integer.class);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getInteger()解析异常：{}", e);
            throw new Exception(e);
        }
    }

    /**
     * 解析data/results下的Integer(原子层查询返回的统计数)
     *
     * @param json
     * @return
     * @throws Exception
     */
    public static Integer getCountFromAtomic(AjaxResult<JsonNode> json)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results).toString(), Integer.class);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getCountFromAtomic()解析异常：{}", e);
            throw new Exception(e);
        }
    }

    public static Boolean getBoolean(AjaxResult<JsonNode> json, String key)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,key);
            if(json==null){
                return null;
            }
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(key).toString(), Boolean.class);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getBoolean()解析异常：{}", e);
            throw new Exception(e);
        }
    }

    /**
     * 解析AjaxResult<JsonNode>中data中的key，一般用作原子层批量新增返回的ID
     *
     * @param json 需要解析Json
     * @param key  Json数据体中的data下面的key
     * @return key对应的value 返回类型是List<String>
     * @Json解析异常，比如给的格式不合法，等等...
     */
    public static List<String> getListString(AjaxResult<JsonNode> json, String key)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,key);
            if(json==null){
                return null;
            }
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(key).toString(), new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListString()解析异常：{}", e);
            throw new Exception(e);
        }
    }


    /**
     * 解析AjaxResult<JsonNode>中data中的key，一般用作原子层查询返回的List<对象>
     *
     * @param json 需要解析Json
     * @param key  Json数据体中的data下面的key
     * @return key对应的value 返回类型是List<T>
     * @Json解析异常，比如给的格式不合法，等等...
     */
    public static <T> List<T> getListObject(AjaxResult<JsonNode> json, String key, TypeReference typeReference)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,key);
            if(json==null){
                return null;
            }
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(key).toString(), typeReference);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListObject()解析异常：{}", e);
            throw new Exception(e);
        }
    }

    public static <T> List<T> getListObject(AjaxResult<JsonNode> json, String... key)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results);
            for (int i = 0; i < key.length; i++) {
                jsonNode = jsonNode.path(key[i]);
            }
            return mapper.readValue(jsonNode.toString(), new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListObject(...)解析异常：{}", e);
            throw new Exception(e);
        }
    }

    public static <T> List<T> getListObject(AjaxResult<JsonNode> json, TypeReference typeReference, String... key)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results);
            for (int i = 0; i < key.length; i++) {
                jsonNode = jsonNode.path(key[i]);
            }
            return mapper.readValue(jsonNode.toString(), typeReference);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListObject(....)解析异常：{}", e);
            throw new Exception(e);
        }
    }


    /**
     * 解析AjaxResult<JsonNode>中data中的key，一般用作原子层查询返回的List<对象>
     * @param json 需要解析Json
     * @param key Json数据体中的data下面的key
     * @return key对应的value 返回类型是List<T>
     * @Json解析异常，比如给的格式不合法，等等...
     */
    /**
     * @param json      需要解析Json
     * @param valueType 类的类类型
     * @param <T>       泛型
     * @return List<T>
     * @Json解析异常，比如给的格式不合法，等等...
     */
    public static <T> List<T> getListObject(AjaxResult<JsonNode> json, Class<T> valueType)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, valueType);
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results).toString(), javaType);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListObject(T)解析异常：{}", e);
            throw new Exception(e);
        }
    }

    /**
     * 解析AjaxResult<JsonNode>中data中的key，分页pageKey，一般用作原子层查询返回的List<对象>
     *
     * @param json 需要解析Json
     * @param key  Json数据体中的data下面的key
     * @return key对应的value 返回类型是List<T>
     * @Json解析异常，比如给的格式不合法，等等...
     */
    public static <T> List<T> getListObject(AjaxResult<JsonNode> json, String key, String pageKey, TypeReference typeReference)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,key);
            if(json==null){
                return null;
            }
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(key).path(pageKey).toString(), typeReference);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListObject(key,T)解析异常：{}", e);
            throw new Exception(e);
        }
    }

    /**
     * 解析data/results下的List<Map>为Map(原子层自定义查询返回结构为List，取第一个)
     *
     * @param json
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getMapFromAotmicListMap(AjaxResult<JsonNode> json)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            List<Map<String, Object>> list = mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results).toString(), new TypeReference<List<Map<String, Object>>>() {
            });
            if (CollectionUtils.isNotEmpty(list)) {
                return list.get(0);
            }
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getMapFromAotmicListMap()解析异常：{}", e);
            throw new Exception(e);
        }
        return null;
    }

    public static List<Map<String, Object>> getListMapFromAotmicListMap(AjaxResult<JsonNode> json)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            List<Map<String, Object>> list = mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results).toString(), new TypeReference<List<Map<String, Object>>>() {
            });
            return list;
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListMapFromAotmicListMap()解析异常：{}", e);
            throw new Exception(e);
        }
    }


    /**
     * 解析AjaxResult<JsonNode>中data中的key，一般用作原子层查询返回的Map
     *
     * @param json 需要解析Json
     * @return key对应的value 返回类型是Map<String, Object>
     * @Json解析异常，比如给的格式不合法，等等...
     */
    public static Map<String, Object> getMapObject(AjaxResult<JsonNode> json)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            Map<String, Object> map = mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results).toString(), new TypeReference<Map<String, Object>>() {
            });
            return map;
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getMapObject()解析异常：{}", e);
            throw new Exception(e);
        }
    }


    /**
     * 解析AjaxResult<JsonNode>中data中的key，一般用作原子层查询返回的单个对象
     *
     * @param json 需要解析Json
     * @param
     * @return key对应的value 返回类型是T
     * @Json解析异常，比如给的格式不合法，等等...
     */
    public static <T> T getObject(AjaxResult<JsonNode> json, Class<T> valueType)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,null);
            if(json==null){
                return null;
            }
            return mapper.readValue(mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results).toString(), valueType);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getObject(T)解析异常：{}", e);
            throw new Exception(e);
        }
    }

    /**
     * 解析AjaxResult<JsonNode>中data中的key，一般用作原子层查询返回的单个对象
     *
     * @param json 需要解析Json
     * @param key  results下对应的字段
     * @return key对应的value 返回类型是JsonNode
     * @Json解析异常，比如给的格式不合法，等等...
     */
    public static JsonNode getJsonNode(AjaxResult<JsonNode> json, String key)  throws Exception {
        try {
            json = AnalysisJson.checkJsonEmpty(json,key);
            if(json==null){
                return null;
            }
            return mapper.readTree(mapper.writeValueAsString(json)).path(data).path(results).path(key);
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getJsonNode(key)解析异常：{}", e);
            throw new Exception(e);
        }
    }


    /**
     * 解析String，返回List
     *
     * @param json
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getListObjectByString(String json)  throws Exception {
        try {
            return mapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            LogCommons.error(json.toString()+"getListObjectByString()解析异常：{}", e);
            throw new Exception(e);
        }
    }

    /**
     * 校验json数据里面的results是否为空，防止解析出现空指针
     * @param json
     * @return
     */
    private static AjaxResult<JsonNode> checkJsonEmpty(AjaxResult<JsonNode> json,String key){
        if(json==null){
            return null;
        }
        if(json.getData()==null){
            return null;
        }
        if(StringUtils.isEmpty(key)&&json.getData().get(results)==null){
            return null;
        }
        if(!StringUtils.isEmpty(key)&&json.getData().get(key)==null){
            return null;
        }
        return json;
    }
}
