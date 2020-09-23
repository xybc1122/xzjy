package com.education.project.wx;

import com.education.project.base.HttpResult;
import com.education.project.config.WeChatConfig;
import com.education.project.order.entity.CourseOrder;
import com.education.project.utils.CommonUtils;
import com.education.project.utils.HttpUtils;
import com.education.project.utils.UUidUtil;
import com.education.project.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    private WeChatConfig weChatConfig;


    @Override
    public HttpResult<WxPay> WxPayService(CourseOrder order) {

        return HttpResult.success(unifiedOrder(order));
    }

    /**
     * 统一下单方法
     *
     * @return
     */
    private WxPay unifiedOrder(CourseOrder order) {

        //int i = 1/0;   //模拟异常
        //生成签名
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid", weChatConfig.getOpenAppid());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str", UUidUtil.getUUid());
        params.put("body", order.getTitle());
        params.put("out_trade_no", order.getOrderNumber());
        params.put("total_fee", CommonUtils.covPrice(order.getPrice().toString()));
        params.put("spbill_create_ip", order.getIp());
        params.put("notify_url", weChatConfig.getPayCallbackUrl());
        params.put("trade_type", "JSAPI");
        //sign签名
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign", sign);

        //map转xml
        String payXml = WXPayUtil.mapToXml(params);

        System.out.println(payXml);
        //统一下单
        String prepayId = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(), payXml, 4000);
        if (null == prepayId) {
            return null;
        }

        WxPay wxPay = new WxPay();
        wxPay.setPaySign(sign);
        wxPay.setAppId(weChatConfig.getOpenAppid());
        wxPay.setTimeStamp(new Date().getTime() + "");
        wxPay.setSignType("MD5");
        wxPay.setNonceStr(UUidUtil.getUUid());
        wxPay.setWxPackage(prepayId);
        return wxPay;
    }


}
