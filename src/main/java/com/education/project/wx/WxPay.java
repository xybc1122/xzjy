package com.education.project.wx;

import lombok.Data;

@Data
public class WxPay {

    private String wxPackage;

    private String signType;

    private String paySign;

    private String timeStamp;

    private String nonceStr;

    private String appId;
}
