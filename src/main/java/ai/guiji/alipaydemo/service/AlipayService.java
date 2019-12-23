package ai.guiji.alipaydemo.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import java.util.Map;

public interface AlipayService {

    AlipayTradePrecreateResponse preCreateOrder(Map<String, String> bizMap) throws AlipayApiException;

    /**
     * @param orderNo: 订单编号
     * @param amount:  实际支付金额
     * @param subject: 订单描述
     * @Description: 交易预下单接口 alipay.trade.precreate
     */
    AlipayTradePrecreateResponse preCreateOrder(String orderNo, double amount, String subject, String storeId, String timeoutExpress) throws AlipayApiException;

    /**
     * @param orderNo: 订单编号
     * @param amount:  实际支付金额
     * @param body:    订单描述
     * @Description: 创建支付宝订单
     */
    String createOrder(String orderNo, double amount, String body) throws AlipayApiException;

    /**
     * @param tradeStatus: 支付宝交易状态
     * @param orderNo:     订单编号
     * @param tradeNo:     支付宝订单号
     * @Description:
     */
    boolean notify(String tradeStatus, String orderNo, String tradeNo);
}