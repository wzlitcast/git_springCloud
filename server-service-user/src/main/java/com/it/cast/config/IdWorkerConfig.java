package com.it.cast.config;

import com.aspire.commons.autoid.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *  生成的分布式ID
 * </p>
 *
 * @author 
 * @since 2019-06-11
 */
@Configuration
public class IdWorkerConfig {

    @Value("${worker_id}")
    private Long workerId;

    @Value("${datacenter_id}")
    private Long datacenterId;

    @Bean
    public IdWorker getIdWorker() {
        return new IdWorker(workerId, datacenterId);
    }

}


