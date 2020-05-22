package com.tool.upload.config;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectApiBuilder;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.obs.services.ObsClient;
import com.qdum.toolupload.entrance.UploadUtil;
import com.qdum.toolupload.obs.ObsUtil;
import com.qdum.toolupload.oss.OssUtil;
import com.qdum.toolupload.ucolud.UFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 华为OBS配置初始化
 * @author qdum-duty
 * @date 2020/5/14 14:41
 * @version: V1.0
 */
@Configuration
public class StorageConfig {

    @Value("${storage.way}")
    private String storageWay;

    /*=================================== 阿里云OSS相关配置 ==========================================*/
    /** OSS标识字段 */
    private static final String OSS = "oss";

    @Value("${storage.oss.endpoint}")
    private String ossEndpoint;

    @Value("${storage.oss.accessKeyId}")
    private String ossAccessKeyId;

    @Value("${storage.oss.accessKeySecret}")
    private String ossAccessKeySecret;

    /*=================================== 华为云OBS相关配置 ==========================================*/
    /** OBS标识字段 */
    private static final String OBS = "obs";

    @Value("${storage.obs.endpoint}")
    private String obsEndpoint;

    @Value("${storage.obs.accessKeyId}")
    private String obsAccessKeyId;

    @Value("${storage.obs.accessKeySecret}")
    private String obsAccessKeySecret;


    /*=================================== UCloud->uFile相关配置 ==========================================*/
    /** UFile标识字段 */
    private static final String UFILE = "ufile";

    @Value("${storage.ufile.customHost}")
    private String customHost;

    @Value("${storage.ufile.publicKey}")
    private String uCloudPublicKey;

    @Value("${storage.ufile.privateKey}")
    private String uCloudPrivateKey;

    @Bean
    public UploadUtil initStorageClient(){
        UploadUtil uploadUtil = null;
        if(OSS.equals(storageWay)){
            OSS ossClient = new OSSClientBuilder().build(ossEndpoint, ossAccessKeyId, ossAccessKeySecret);
            uploadUtil = new OssUtil(ossClient);
        }else if(OBS.equals(storageWay)){
            ObsClient obsClient = new ObsClient(obsAccessKeyId, obsAccessKeySecret, obsEndpoint);
            uploadUtil = new ObsUtil(obsClient);
        }else if(UFILE.equals(storageWay)){
            ObjectConfig config = new ObjectConfig(customHost);
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(
                    uCloudPublicKey,
                    uCloudPrivateKey);
            ObjectApiBuilder objectApiBuilder = UfileClient.object(objectAuthorization, config);
            uploadUtil = new UFileUtil(objectApiBuilder);
        }else {
            throw new StorageException("请配置上传方式,上传方式:【"+OSS+"】【"+OBS+"】【"+UFILE+"】");
        }
        return uploadUtil;
    }

}
