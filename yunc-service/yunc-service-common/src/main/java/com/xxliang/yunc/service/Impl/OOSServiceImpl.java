package com.xxliang.yunc.service.Impl;

import com.aliyun.oss.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectResult;
import com.xxliang.yunc.service.OOSService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xxliang
 * @date 2025/6/4  17:39
 * @description
 */

@Service
public class OOSServiceImpl implements OOSService {

    @Override
    public String upLoadFile(MultipartFile file) {
        // Endpoint以华东1（BEIJING）为例，填写为https://oss-cn-hangzhou.aliyuncs.com，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String bucketName = "xxliang-yunc";
        // 创建OSSClient实例。
        // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用 V4 签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = new OSSClientBuilder().build(endpoint,
                "LTAI5tSo4cBgRS8rPhb2VPE1",
                "cygNJ0oT7xB3XaOPiWQi9IYSUvqr3g");
        String newFileName = "";
        try {
            // 2. 上传文件
            String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //获取文件后缀
            String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            newFileName = nowDate + "/" + System.currentTimeMillis() + file.getOriginalFilename();
            // 获取文件输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, newFileName, inputStream);
            System.out.println("2. 文件 " + newFileName + " 上传成功。");
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException e) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "https://" + bucketName + "." + endpoint + "/" + newFileName;
    }
}

