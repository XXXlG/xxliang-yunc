package com.xxliang.yunc.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.google.gson.Gson;
import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.pojo.VerifyCode;
import com.xxliang.yunc.service.VerifyService;
import com.xxliang.yunc.util.AssertUtil;
import com.xxliang.yunc.util.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xxliang
 * @date 2025/5/30  23:25
 * @description
 */
@Service
@SuppressWarnings("all")
@Slf4j
public class VerifyServiceImpl implements VerifyService {

    //    Redis模板
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer sendSmsCode(String mobile) {
//        接口JR303已经校验完成格式
//        1. 判读redis中是否有code?
        String key = String.format(ValidateConstant.REDIS_KEY_SMS, mobile);
        log.info("key:"+key);
        VerifyCode pre_verifyCode = (VerifyCode) redisTemplate.opsForValue().get(key);
        //       2.  如果没有
        //        代表是第一次生成验证码。并设置过期时间为3min，
        //
        //        验证码的(k-v)设计：key_phone : 6831 ;
        //        sms:18561144539 = {
        //                               val: 6831
        //                               time: 2025-3-26- 10:30:00
        //                           }
        if(ObjectUtils.isEmpty(pre_verifyCode)){
            String code = StrUtils.getRandomString(ValidateConstant.REDIS_KEY_SMS_CODE_LENGTH);
            long time = new Date().getTime();
            VerifyCode verifyCode = new VerifyCode(code, time);
            redisTemplate.opsForValue().set(
                    key,//k
                    verifyCode,//v
                    ValidateConstant.REDIS_KEY_SMS_TIMEOUT,//过期时间
                    TimeUnit.MILLISECONDS//时间单位
            );
            sendSmsService( code,mobile);
        }else{
//          3. 如果有的话，代表3min 之内是发送过的了
//            a. 判断是否在一分钟之内获取，则提示用户一分钟只允许获取一次。
            Long timer = pre_verifyCode.getTimer();
            log.info("当前时间"+new Date().getTime());
            log.info("上一次时间"+timer);
            log.info("距离上一次的时间差"+(new Date().getTime()-timer));
            log.info("重试时间间隔" +  ValidateConstant.REDIS_KEY_SMS_RETRY_TIMEOUT);
            AssertUtil.isTrue(new Date().getTime()-timer < ValidateConstant.REDIS_KEY_SMS_RETRY_TIMEOUT, "一分钟之内不允许重复获取！");
//            b. 如果不是1min 之内 ，则重新发送之前的验证码。重新刷新redis中的过期时间。
            redisTemplate.opsForValue().set(
                    key,//k
                    pre_verifyCode,//v
                    ValidateConstant.REDIS_KEY_SMS_TIMEOUT,//过期时间
                    TimeUnit.MILLISECONDS//时间单位
            );
            sendSmsService( pre_verifyCode.getCode(),mobile);
        }

        return 1;
    }

    public Integer sendSmsService(String code , String mobile){
        log.info( "发送验证码{}到{},请在{}分钟内尽快使用！",code,mobile,ValidateConstant.REDIS_KEY_SMS_TIMEOUT/60/1000);
        //下面导包看着点，千万别导错了，对着上面我的导包
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId("LTAI5tSo4cBgRS8rPhb2VPE1")
                // 您的AccessKey Secret	（这两个还不知道的去我前两次关于阿里云的有教程哪里找）
                .setAccessKeySecret("cygNJ0oT7xB3XaOPiWQi9IYSUvqr3g");
        // 访问的域名（这个不用变都是这个）
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = null;
        try {
            client = new Client(config);
            SendSmsRequest request = new SendSmsRequest();
            request.setSignName("阿里云短信测试");//签名名称
            request.setTemplateCode("SMS_154950909");//模版Code
            request.setPhoneNumbers(mobile);//电话号码
            //Map<String, String> param = new HashMap<>("code",code);
            //这里的参数是json格式的字符串
            Map map = new HashMap();
            map.put("code",code);
            request.setTemplateParam(JSONObject.toJSONString(map));
            SendSmsResponse response = client.sendSms(request);
            System.out.println("发送成功："+new Gson().toJson(response));
        } catch (Exception e) {
            throw new GlobalBussnessException("Alibab-API调用失败！");
        }
        return 1;
    }
}
