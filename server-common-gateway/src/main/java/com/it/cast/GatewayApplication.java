package com.it.cast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * <p>
 * Gateway 启动类
 * </p>
 *
 * @author gq
 * @since 2019-05-10
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GatewayApplication {
	public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
