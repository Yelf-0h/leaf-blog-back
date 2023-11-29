package com.yecheng.leafblogback.controller;

import cn.hutool.core.util.IdUtil;
import com.yecheng.leafblogback.interceptor.UserHolderContext;
import com.yecheng.leafblogback.utils.AppHttpCodeEnum;
import com.yecheng.leafblogback.utils.KodoOssUtil;
import com.yecheng.leafblogback.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.Multipart;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Yelf
 * @create 2023-02-26-22:02
 */
@RestController
@RequestMapping("upload")
@Slf4j
public class UploadController {
    @Resource
    private KodoOssUtil kodoOssUtil;


    @PostMapping("uploadUserImage")
    public ResponseResult<String> uploadUserImage(MultipartFile file){
        if (Objects.isNull(file)){
            System.out.println("System.out.println(animalMap + \"1121231231231\"); 变更2222");
            return ResponseResult.errorResult(500,"文件上传失败！空文件");
        }
        String fileName = IdUtil.simpleUUID()+"-"+file.getOriginalFilename();
        String url = "上传失败！！";
        try {
            byte[] fileBytes = file.getBytes();
            url = kodoOssUtil.upload(fileName,fileBytes);
        } catch (Exception e) {
            return ResponseResult.errorResult(500,"出现异常！头像上传失败！");
        }
        log.info("UploadController.uploadUserImage业务结束，结果为：{}",url);
        Long userId = UserHolderContext.getUserId();

        return ResponseResult.okResult(url);
    }

    @PostMapping("uploadUserImageBase64")
    public ResponseResult<String> uploadUserImageBase64(@RequestBody HashMap<String,String> file){
        if (Objects.isNull(file)){
            return ResponseResult.errorResult(500,"文件上传失败！空文件");
        }
        String files = file.get("file").split(";")[0];
        String suffix = files.split("/")[1];
        String fileName = IdUtil.simpleUUID()+"-"+ UUID.randomUUID()+"."+suffix;
        String url = "上传失败！！";
        try {
            String fileNew=file.get("file").substring(file.get("file").indexOf(",")+1, file.get("file").length());
            byte[] decodeBase64 = Base64.decodeBase64(fileNew);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodeBase64);
            url = kodoOssUtil.upload(fileName,byteArrayInputStream);
        } catch (Exception e) {
            return ResponseResult.errorResult(500,"出现异常！头像上传失败！");
        }
        log.info("UploadController.uploadUserImage业务结束，结果为：{}",url);
        Long userId = UserHolderContext.getUserId();

        return ResponseResult.okResult(url);
    }

}

