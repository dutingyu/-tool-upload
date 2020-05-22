package com.tool.upload.ucolud;

import cn.ucloud.ufile.api.object.ObjectApiBuilder;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.util.MimeTypeUtil;
import cn.ucloud.ufile.util.StorageType;
import com.tool.upload.config.StorageException;
import com.tool.upload.entrance.UploadUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.InputStream;

/**
 * @Description: UCloud->UFile上传工具类
 * @author qdum-duty
 * @date 2020/5/20 11:59
 * @version: V1.0
 */
public class UFileUtil implements UploadUtil {

    private final ObjectApiBuilder objectApiBuilder;

    public UFileUtil(ObjectApiBuilder objectApiBuilder) {
        this.objectApiBuilder = objectApiBuilder;
    }

    /**
     * 文件访问域名
     */
    @Value("${storage.ufile.domain}")
    private String domain;

    /**
     * 储存空间名称
     */
    @Value("${storage.ufile.bucketName}")
    private String bucketName;

    /**
     * @Description: 简单上传
     * @author qdum-duty
     * @date 2020/5/20 12:46
     * @param file  文件
     * @param nameAs  文件名
     * @return: 文件链接
     * @version: V1.0
     */
    @Override
    public String upload(File file,String nameAs) {
        if(!file.exists()){
            throw new StorageException(String.format("文件不存在,path:【%s】",file.getPath()));
        }
        try {
            PutObjectResultBean response = objectApiBuilder.putObject(file,MimeTypeUtil.getMimeType(nameAs))
                    .nameAs(nameAs)
                    .toBucket(bucketName)
                    .withStorageType(StorageType.STANDARD)
                    .execute();
            if(response.getRetCode() != 0){
                throw new StorageException("uFile上传文件失败,错误描述信息:" + response.getMessage());
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            e.printStackTrace();
            return null;
        }
        return domain + "/" + nameAs;
    }

    /**
     * @Description: 简单上传
     * @author qdum-duty
     * @date 2020/5/20 15:55
     * @param inputStream   文件流
     * @param nameAs    文件名
     * @return: 文件链接
     * @version: V1.0
     */
    @Override
    public String upload(InputStream inputStream, String nameAs) {
        try {
            PutObjectResultBean response = objectApiBuilder.putObject(inputStream,MimeTypeUtil.getMimeType(nameAs))
                    .nameAs(nameAs)
                    .toBucket(bucketName)
                    .withStorageType(StorageType.STANDARD)
                    .execute();
            if(response.getRetCode() != 0){
                throw new StorageException("uFile上传文件失败,错误描述信息:" + response.getMessage());
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            e.printStackTrace();
            return null;
        }
        return domain + "/" + nameAs;
    }
}
