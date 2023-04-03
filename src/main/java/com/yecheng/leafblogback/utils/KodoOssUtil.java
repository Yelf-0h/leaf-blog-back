package com.yecheng.leafblogback.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Setter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 七牛云oss工具类
 *
 * @author Yelf
 * @create 2023-02-27-1:11
 * @date 2023/02/27
 */
@Component
@ConfigurationProperties(prefix = "kodo.oss.file")
@Setter
public class KodoOssUtil {

    /**
     * ...生成上传凭证，然后准备上传
     */
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String baseUrl;

    private String key;

    public String upload(String fileName, byte[] file) throws Exception {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration();
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        key = SystemConstant.OSS_FILE_CLASS+fileName;
        byte[] uploadBytes = file;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        Response response = uploadManager.put(uploadBytes, key, upToken);

        return baseUrl + key;
    }
    public String upload(String fileName, InputStream file) throws Exception {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration();
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        key = SystemConstant.OSS_FILE_CLASS+fileName;
        InputStream byteInputStream = file;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        Response response = uploadManager.put(byteInputStream,key,upToken,null, null);

        return baseUrl + key;
    }

}
