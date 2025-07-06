package com.xxliang.yunc;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xxliang
 * @date 2025/6/22  19:01
 * @description
 */
@SpringBootTest(classes = PayApp.class)
@RunWith(SpringRunner.class)
@Slf4j
public class PayTest {
    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";//协议
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";//网关地址
        config.signType = "RSA2";//签名方式
        config.appId = "9021000149683306";//""<-- 请填写您的AppId，例如：2019091767145019 -->";
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCxlkuNFH0FspyE02thHhsoudTQnAKZ0FSoHkqv6YfziPHdDXi55NNJmr930iJsPOF1yYP0CD6+3UYyynD5WsGNobmTt7L1agqFB7fb8IGm66lLL25+HIfl0zlLA/mAAySARzopkIP6LTlDOWGwdq94dVbjHIBsLTge2nJjLsGt2j51pFoTqs4zAb5Cori2Gf75RG/6bYrowxhF4CObQ9LPXpVGLhRB7RUHq3N8TRLuVR3QL1bc7Eaco8h1C+M9wj1kMhTbEEhGdUcRZawKDTgpYiMJvyb/+bBEQ5tAc2W1ApofSWZeSwS+33q/UpZe6Ox/cx/9cvQ83irkDFJGutLbAgMBAAECggEBAKkueM1vvmeGMGyuCbdc+hzDZQZUCnpEqdH8AcUy4uzVAy5HnkLix5NwlIXvMBITsesHzujWTgiBP1Y13IkBuDFY6DXdJOV+X4U6xCMJLks6aW8SQd5tCd6eaw+XOsOEuupmcPeWENVu7gWoDfQSLbrBdsQorAPi1Hs4Ow9YRPV8wV4N6y0MT2Nvw/f5lM0CyrEvajNIGfzAj0SiEPsZOLYHYqMfGoh7xgk191l/o8e9FzzNBslrlQOp5EG1fh6lrn0d0mCCtmEV3PhRzTQDTe9xK0JW94XJGnhPPsRcxs8AvzUEqXSZkf0Nu3VfVjdxVB+svh+/HhQM6umWfozzl4ECgYEA+DB4W9Cbv/BqZ0IyWkYvi0SgxvLcmUn9MSsrOy8f4ccwOlgHUWXpmMSPUGF7nI96QdLOqXgY3NbyjCHbRvGlRWzDHkEz8wrPmt+WKY5iFXfGFec9uBOtCf+LQHQTiI+IWfZw4vJAhXVcxz+WM3uSsKDMyG+6FVUXDYi3bu5B6hsCgYEAty0FJoS7EoJQEDQZmqfDWmL3N81P/6bjq6uSXWCwAeEArfuv/HMHeQsjHyZzYXQaX13khvCS2T/Mb+dg6Y/nVR8Lef8pVAICPW7yS1CVvqHJRjuktVvADv7l3PqvShQ6w+9PttzlhdD56gxnjKViO6cDirXpdaah/83YoQWhRkECgYBBwP2yPZN2B6j5EHWsqxmfiSAbdW5ygkK9SxWaCYMl2xLw8i+GJjz8R188yYbpVK3sUICGL5ZOs/fwnmus4QqLWSRcmKO9yWh1PLVY7ysXSvNFDE2yvivDEVwASICAVp6mTv9bcRkdApoBA9y0aRHVD9URaWBglHmWEVmf3ynBbwKBgBEiIuMxcxYOHlMFrIdsvbcMnrcXey4cfBsoly4oxw3F7/xWGGXylHZb9lMJk60/4N+etqtwgSvU7dhhugzeqvKpTreAiGsVLQR2Bi/JP9hu5ZauKX/VLye36+Q5hn/VWJHPJpOiIc3ceCPOOTlMlt2hh5FH5qhch7aXmHWnQyiBAoGAdTFc5QRXrDI9iXfnHth8/T1mvjR/BEcL2K/pvxh9ihDMrJm4x62Zv3UrMxCLEXjqr0C3nH9DyJ1E//CzpkPasn67q1oPxkD94U+IZWG3S95+YHH3I16x71jL+eVS2AxjnG1PCIiuCiHOkb8E+66/yfLIlMXnodjTcOorAEL0fhs=";//"<-- 请填写您的应用私钥，例如：MIIEvQIBADANB ... ... -->";
        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        //config.merchantCertPath = "";//"<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
        //config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
        //config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";
        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsZZLjRR9BbKchNNrYR4bKLnU0JwCmdBUqB5Kr+mH84jx3Q14ueTTSZq/d9IibDzhdcmD9Ag+vt1GMspw+VrBjaG5k7ey9WoKhQe32/CBpuupSy9ufhyH5dM5SwP5gAMkgEc6KZCD+i05QzlhsHaveHVW4xyAbC04HtpyYy7Brdo+daRaE6rOMwG+QqK4thn++URv+m2K6MMYReAjm0PSz16VRi4UQe0VB6tzfE0S7lUd0C9W3OxGnKPIdQvjPcI9ZDIU2xBIRnVHEWWsCg04KWIjCb8m//mwREObQHNltQKaH0lmXksEvt96v1KWXujsf3Mf/XL0PN4q5AxSRrrS2wIDAQAB";//"<-- 请填写您的支付宝公钥，例如：MIIBIjANBg... -->";
        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = "异步通知地址，再无。。后期内网穿透";//<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";
        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        //config.encryptKey ="";
        return config;
    }

    @Test
    public void Test() {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePagePayResponse response = Factory.Payment.Page().pay("测试使用", "2234567890", "88.00", "www.baidu.com");
            // 3. 处理响应或异常
            System.out.println(response.getBody());
            if (ResponseChecker.success(response)) {
                log.info(response.toString());
                System.out.println("调用成功");
            } else System.err.println("调用失败，原因：" + response.getBody() + "，");
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
