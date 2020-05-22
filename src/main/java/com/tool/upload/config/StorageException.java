package com.tool.upload.config;

/**
 * @Description: OSS异常处理类
 * @author qdum-duty
 * @date 2020/5/14 17:19
 * @version: V1.0
 */
public class StorageException extends RuntimeException{

    /**
     * 错误描述信息
     */
    private String message;

    public StorageException(String message) {
        super(message);
        setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        return (message != null) ? (s + ": " + message) : s;
    }
}
