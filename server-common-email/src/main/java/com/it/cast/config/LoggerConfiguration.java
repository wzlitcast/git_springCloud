//package com.it.cast.config;
//
//import com.ctrip.framework.apollo.Config;
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.logging.LogLevel;
//import org.springframework.boot.logging.LoggingSystem;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.Set;
//
///**
// * Created by kl on 2018/6/25. Content :
// */
//@Service
//public class LoggerConfiguration {
//  private templates final Logger logger = LoggerFactory.getLogger(LoggerConfiguration.class);
//  private templates final String LOGGER_TAG = "logging.level.";
//
//  @Autowired
//  private LoggingSystem loggingSystem;
//
//  @ApolloConfig
//  private Config config;
//
//  @ApolloConfigChangeListener
//  private void onChange(ConfigChangeEvent changeEvent) {
//    refreshLoggingLevels();
//  }
//
//  @PostConstruct
//  private void refreshLoggingLevels() {
//    Set<String> keyNames = config.getPropertyNames();
//    for (String key : keyNames) {
//      if (containsIgnoreCase(key, LOGGER_TAG)) {
//        String strLevel = config.getProperty(key, "info");
//        LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
//        loggingSystem.setLogLevel(key.replace(LOGGER_TAG, ""), level);
//        logger.info("{}:{}", key, strLevel);
//      }
//    }
//  }
//
//  private templates boolean containsIgnoreCase(String str, String searchStr) {
//    if (str == null || searchStr == null) {
//      return false;
//    }
//    int len = searchStr.length();
//    int max = str.length() - len;
//    for (int i = 0; i <= max; i++) {
//      if (str.regionMatches(true, i, searchStr, 0, len)) {
//        return true;
//      }
//    }
//    return false;
//  }
//}
//
//
