package com.education.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 微信配置类
 */
@Configuration
@PropertySource(value="classpath:application.yml")
public class WeChatConfig {



    /**
     * 统一下单url
     */
    private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    /**
     * 开放平台appid
     */
    @Value("${wxopen.appid}")
    private String openAppid;






    /**
     * 商户号id
     */
    @Value("${wxpay.mer_id}")
    private String mchId;


    public String getOpenAppid() {
        return openAppid;
    }

    public void setOpenAppid(String openAppid) {
        this.openAppid = openAppid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
}
