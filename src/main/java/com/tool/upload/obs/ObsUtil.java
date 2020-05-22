package com.tool.upload.obs;


import com.obs.services.ObsClient;
import com.tool.upload.config.StorageException;
import com.tool.upload.entrance.UploadUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.InputStream;

/**
 * @Description: 华为云OBS上传工具类
 * @author qdum-duty
 * @date 2020/5/20 11:58
 * @version: V1.0
 */
public class ObsUtil implements UploadUtil {

    private final ObsClient obsClient;

    public ObsUtil(ObsClient obsClient) {
        this.obsClient = obsClient;
    }

    /**
     * 文件访问域名
     */
    @Value("${storage.obs.domain}")
    private String domain;

    /**
     * 储存空间名称
     */
    @Value("${storage.obs.bucketName}")
    private String bucketName;

    /**
     * @Description: 简单上传
     * @author qdum-duty
     * @date 2020/5/20 11:57
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
        obsClient.putObject(bucketName, nameAs, file);
        return domain + "/" + nameAs;
    }

    /**
     * @Description: 简单上传
     * @author qdum-duty
     * @date 2020/5/20 15:46
     * @param inputStream   文件流
     * @param nameAs    文件名
     * @return: 文件链接
     * @version: V1.0
     */
    @Override
    public String upload(InputStream inputStream,String nameAs) {
        obsClient.putObject(bucketName, nameAs, inputStream);
        return domain + "/" + nameAs;
    }

}
