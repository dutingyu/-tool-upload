package com.tool.upload.entrance;

import java.io.File;
import java.io.InputStream;

/**
 * @Description: 上传工具类入口
 * @author qdum-duty
 * @date 2020/5/14 10:31
 * @version: V1.0
 */
public interface UploadUtil {

    /**
     * @Description: 简单上传
     * @author qdum-duty
     * @date 2020/5/14 14:33
     * @param file 文件
     * @param nameAs 文件名
     * @return: 文件链接
     * @version: V1.0
     */
    public String upload(File file, String nameAs);

    /**
     * @Description: 简单上传文件
     * @author qdum-duty
     * @date 2020/5/14 14:33
     * @param inputStream  文件流
     * @param nameAs  文件名
     * @return: 件链接
     * @version: V1.0
     */
    public String upload(InputStream inputStream, String nameAs);
}
