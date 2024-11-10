package com.saas.admin.common.convention.exception;

import com.saas.admin.common.convention.errorcode.BaseErrorCode;
import com.saas.admin.common.convention.errorcode.IErrorCode;

/**
 * 服务端异常
 */
public class ServiceException extends AbstractException {

    public ServiceException(String messge) {
        this(messge, BaseErrorCode.SERVICE_ERROR, null);
    }

    public ServiceException(IErrorCode errorCode) {
        this(null, errorCode);
    }

    public ServiceException(String messge, IErrorCode errorCode) {
        this(messge, errorCode, null);
    }

    // 最终一定会调用super这个接口
    public ServiceException(String messge, IErrorCode errorCode, Throwable throwable) {
        super(messge, errorCode, throwable);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
