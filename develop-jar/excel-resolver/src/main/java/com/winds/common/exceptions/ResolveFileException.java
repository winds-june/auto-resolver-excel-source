package com.winds.common.exceptions;
/**
 * Creater: cnblogs-WindsJune
 * Date: 2018/9/20
 * Time: 19:44
 * Description: 解析Excel的公共异常类
 */

public class ResolveFileException extends RuntimeException{

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMessage;

    public ResolveFileException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ResolveFileException(String message) {
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
