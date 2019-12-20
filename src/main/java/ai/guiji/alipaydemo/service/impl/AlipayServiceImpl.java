package ai.guiji.alipaydemo.service.impl;

import ai.guiji.alipaydemo.config.AlipayProperties;
import ai.guiji.alipaydemo.service.AlipayService;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * author: zhouliliang
 * Date: 2019/12/20 15:02
 * Description:
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private AlipayProperties alipayProperties;
    @Autowired
    private AlipayClient alipayClient;

    @Override
    public String preCreateOrder(Map<String, String> bizMap) throws AlipayApiException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(JSON.toJSONString(bizMap));
        request.setReturnUrl(alipayProperties.getReturnUrl());
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        AlipayTradePrecreateResponse precreateResponse = alipayClient.execute(request);
        return precreateResponse.getBody();
    }

    @Override
    public String preCreateOrder(String orderNo, double amount, String subject, String storeId, String timeoutExpress) throws AlipayApiException {
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(orderNo);
        model.setTotalAmount(String.valueOf(amount));
        model.setSubject(subject);
        if (StringUtils.isNotBlank(storeId)) {
            model.setStoreId(storeId);
        }
        if (StringUtils.isNotBlank(timeoutExpress)) {
            model.setTimeoutExpress(timeoutExpress);
        }
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setReturnUrl(alipayProperties.getReturnUrl());
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        AlipayTradePrecreateResponse precreateResponse = alipayClient.execute(request);

        return precreateResponse.getBody();
    }

    @Override
    public String createOrder(String orderNo, double amount, String body) throws AlipayApiException {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(body);
        model.setOutTradeNo(orderNo);
        model.setTotalAmount(String.valueOf(amount));
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setPassbackParams("公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数");

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        alipayRequest.setBizModel(model);
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());// 回调地址
        alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        AlipayTradeAppPayResponse aliPayResponse = alipayClient.sdkExecute(alipayRequest);
        //就是orderString 可以直接给客户端请求，无需再做处理。
        return aliPayResponse.getBody();
    }

    @Override
    public boolean notify(String tradeStatus, String orderNo, String tradeNo) {
        if ("TRADE_FINISHED".equals(tradeStatus)
                || "TRADE_SUCCESS".equals(tradeStatus)) {
            // 支付成功，根据业务逻辑修改相应数据的状态
//              boolean state = orderPaymentService.updatePaymentState(orderNo, tradeNo);
//            if (state) {
//                return true;
//            }
        }
        return false;
    }
}
