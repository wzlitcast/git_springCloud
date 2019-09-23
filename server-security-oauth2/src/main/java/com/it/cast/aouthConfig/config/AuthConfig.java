package com.it.cast.aouthConfig.config;

import lombok.*;

/**
 * JustAuth配置类
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthConfig {

    /**
     * 客户端id：对应个平台的appKey
     */
    private String clientId;

    /**
     * 客户端Secret：对应个平台的appSecret
     */
    private String clientSecret;

    /**
     * 登录成功后的回调地址
     */
    private String redirectUri;

    /**
     * 支付宝公钥：当选择支付宝登录时，该值可用
     */
    private String alipayPublicKey;
}
