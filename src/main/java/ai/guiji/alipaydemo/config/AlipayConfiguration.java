package ai.guiji.alipaydemo.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: zhouliliang
 * Date: 2019/12/20 14:48
 * Description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class AlipayConfiguration {

    @Autowired
    private AlipayProperties properties;

    @Bean
    public AlipayClient alipayClient() {

        return new DefaultAlipayClient(properties.getGatewayUrl(),
                properties.getAppid(),
                properties.getAppPrivateKey(),
                properties.getFormat(),
                properties.getCharset(),
                properties.getAlipayPublicKey(),
                properties.getSignType());
    }
}
