package com.xxliang.yunc.controller;

import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.service.OOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xxliang
 * @date 2025/6/4  17:16
 * @description OSS服务接口
 */
@RestController
@RequestMapping("/oss")
public class OSSController {

    @Autowired
    public OOSService ossService;
    @PostMapping("/upLoadFile")
    public JSONResult upLoadFile(@RequestParam MultipartFile file){

        return JSONResult.success(ossService.upLoadFile(file));
    }

}
