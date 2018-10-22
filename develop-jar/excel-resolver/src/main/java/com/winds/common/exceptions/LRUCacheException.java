package com.winds.common.exceptions;

/**
 * Creater: cnblogs-WindsJune
 * Date: 2018/9/21
 * Time: 10:04
 * Description: No Description
 */
public class LRUCacheException extends  Exception{
    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMessage;

    public LRUCacheException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public LRUCacheException(String message) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
