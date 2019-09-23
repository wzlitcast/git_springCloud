package com.it.cast.config;

import com.aspire.commons.custom_head_annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomAnnotationConfig {

    @Bean
    RequestAnnotationAspect requestAnnotationAspect(){return new RequestAnnotationAspect();}
}


