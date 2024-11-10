package com.saas.admin.common.convention.exception;


import com.saas.admin.common.convention.errorcode.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 抽象项目中三类异常体系，客户端异常、服务端异常以及远程服务调用异常
 *
 * RemoteException
 * ClientException
 * ServiceException
 */

@Getter
public abstract class AbstractException extends RuntimeException {

    public final String errorCode;

    public final String errorMessage;

    // message: 异常的具体信息
    // errorCode：错误码和错误信息
    // throwable：引起这个异常的根本异常（cause），允许追踪异常的来源
    public AbstractException(String message, IErrorCode errorCode, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ?  message : null).orElse(errorCode.message());
    }
}
