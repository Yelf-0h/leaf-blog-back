package com.yecheng.leafblogback.controller;

import cn.hutool.core.util.IdUtil;
import com.yecheng.leafblogback.interceptor.UserHolderContext;
import com.yecheng.leafblogback.utils.AppHttpCodeEnum;
import com.yecheng.leafblogback.utils.KodoOssUtil;
import com.yecheng.leafblogback.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.Multipart;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

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

}

