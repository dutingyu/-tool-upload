package com.tool.upload.oss;


import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.qdum.toolupload.config.StorageException;
import com.qdum.toolupload.entrance.UploadUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.InputStream;

/**
 * @Description: 阿里云OSS上传工具类
 * @author qdum-duty
 * @date 2020/5/15 17:04
 * @version: V1.0
 */
public class OssUtil implements UploadUtil {

    /**
     * oss客户端
     */
    private final OSS ossClient;

    /**
     * 文件访问域名
     */
    @Value("${storage.oss.domain}")
    private String domain;

    /**
     * 储存空间名称
     */
    @Value("${storage.oss.bucketName}")
    private String bucketName;

    public OssUtil(OSS ossClient) {
        this.ossClient = ossClient;
    }

    /**
     * @Description: 简单上传
     * @author qdum-duty
     * @date 2020/5/15 17:05
     * @param file 文件
     * @param nameAs 文件名
     * @return: 文件链接
     * @version: V1.0
     */
    @Override
    public String upload(File file,String nameAs) {
        if(!file.exists()){
            throw new StorageException(String.format("文件不存在,path:【%s】",file.getPath()));
        }
        ossClient.putObject(new PutObjectRequest(bucketName, nameAs, file));
        return domain + "/" + nameAs;
    }

    /**
     * @Description: 简单上传
     * @author qdum-duty
     * @date 2020/5/20 15:57
     * @param inputStream   文件流
     * @param nameAs    文件名
     * @return: 文件链接
     * @version: V1.0
     */
    @Override
    public String upload(InputStream inputStream, String nameAs) {
        ossClient.putObject(new PutObjectRequest(bucketName, nameAs, inputStream));
        return domain + "/" + nameAs;
    }

}
