package com.aspire.commons.feign_request_interceptor;

import com.aspire.commons.HeaderInformation;
import com.aspire.commons.log.LogCommons;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * feign转发header参数
 * @author gaoqi 2019/04/16
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor{

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String,String> headers = getHeaders(getHttpServletRequest());
        for(String headerName : headers.keySet()){
            LogCommons.info("KEY:{},Value:{}",headerName,getHeaders(getHttpServletRequest()).get(headerName));
            requestTemplate.header(headerName, getHeaders(getHttpServletRequest()).get(headerName));
        }
    }




    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            LogCommons.error("feign请求头转发异常：{}",e);
            return null;
        }
    }


    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
