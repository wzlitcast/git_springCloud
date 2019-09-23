package com.it.cast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * <p>
 * Zipkin 启动类
 * </p>
 *
 * @author gq
 * @since 2019-05-10
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableZipkinServer
public class ZipkinApplication {
	public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
