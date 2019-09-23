package com.it.cast.common.config;

import com.alipay.demo.trade.config.Configs;
import com.it.cast.modules.unionpay.util.SDKConfig;
import com.it.cast.modules.weixinpay.util.ConfigUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动加载支付宝、微信以及银联相关参数配置
 * 创建者 张志朋
 * 创建时间 2018年5月15日
 */
@Component
public class InitPay implements ApplicationRunner{
	
	@Override
    public void run(ApplicationArguments var) throws Exception{
		//初始化 支付宝-微信-银联相关参数,涉及机密,此文件不会提交,请自行配置相关参数并加载
		Configs.init("zfbinfo.properties");//支付宝
		ConfigUtil.init("wxinfo.properties");//微信
		SDKConfig.getConfig().loadPropertiesFromSrc();//银联
    }
}