package com.it.cast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 配置类
 * @author: WuZhiLong
 * @create: 2019-06-05 19:47
 **/
@Component
@ConfigurationProperties(prefix="my-props")
public class MyProps {

    private Map<String, String> mapProps = new HashMap<>();

    public Map<String, String> getMapProps() {
        return mapProps;
    }

    public void setMapProps(Map<String, String> mapProps) {
        this.mapProps = mapProps;
    }
}


