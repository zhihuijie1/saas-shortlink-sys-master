package com.saas.admin.common.convention.exception;

import com.saas.admin.common.convention.errorcode.BaseErrorCode;
import com.saas.admin.common.convention.errorcode.IErrorCode;

/**
 * 客户端异常
 */
public class ClientException extends AbstractException {
    public ClientException(IErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public ClientException(String message) {
        this(BaseErrorCode.CLIENT_ERROR, message, null);
    }

    public ClientException(IErrorCode errorCode, String message) {
        this(errorCode,message,  null);
    }

    public ClientException(IErrorCode errorCode, String message, Throwable throwable) {
        super(message, errorCode, throwable);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }


}
