/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HeaderInformation
 * Author:   issuser
 * Date:     2019/3/3 10:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.aspire.commons;

import com.aspire.commons.entity.gateway.Param;
import com.aspire.commons.ip_convert.IpConvert;
import com.aspire.commons.log.LogCommons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 *
 *
 * @author wangpeng
 * @create 2019/3/3
 * @since 1.0.0
 */

public class HeaderInformation {
    private HeaderInformation(){}
    public static final String JSON_ERROR_MSG = "[1203],请求头json转换map错误";

    public static Map<String,String> getHeads() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String headjson = request.getHeader("param");
        Map param = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            param = mapper.readValue(headjson, Map.class);
        } catch (IOException e) {
            LogCommons.error("headson",JSON_ERROR_MSG);
        }
        return param;
    }

    public static Map getUserInfo() {
        return getHeads();
    }

    private static Param getHeadsInfo() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String headjson = request.getHeader("param");
        Param param = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            param = mapper.readValue(headjson, Param.class);
        } catch (IOException e) {
            LogCommons.error("headjson",JSON_ERROR_MSG);
        }
        return param;
    }

    //
    public static Param getUser(){
        return getHeadsInfo();
    }

    //获取ip,为long类型
    public static Long getUserIP() {
        String ip = getHeadsInfo().getIp();
        return IpConvert.ipToLong(ip);
    }

    //获取用户id
    public static String getUserId() {
        return getHeadsInfo().getUserId();
    }

    //获取企业id
    public static String getEnterpriseId(){
        return getHeadsInfo().getEnterpriseId();
    }

    //获取用户名
    public static String getLoginName(){
        return getHeadsInfo().getLoginName();
    }

    //获取用户手机号码
    public static String getMobile(){
        return getHeadsInfo().getMobile();
    }

    //获取员工id
    public static String getNumberId(){
        return getHeadsInfo().getNumberId();
    }



    //获取全部请求头信息
    public static String getInformation(String key){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader(key);
    }

    //获取系统id
    public static String getSystemId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader("SYSTEM-ID");
    }

}
