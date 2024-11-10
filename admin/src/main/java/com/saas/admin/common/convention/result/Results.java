package com.saas.admin.common.convention.result;

import com.saas.admin.common.convention.errorcode.BaseErrorCode;
import com.saas.admin.common.convention.exception.AbstractException;

/**
 * 全局返回对象构造器
 */
public class Results {
    /**
     * 构造成功响应 - 返回的对象是Result对象，code = “0”
     */
    public static Result<Void> success() {
        return new Result<Void>()
                .setCode(Result.SUCCESS_CODE);
    }

    /**
     * 构造服务端失效响应
     */
    public static Result<Void> failure() {
        return new Result<Void>()
                .setCode(BaseErrorCode.SERVICE_ERROR.code())
                .setMessage(BaseErrorCode.SERVICE_ERROR.message());
    }

    /**
     * 通过 {AbstractException} 构建失败响应
     */
    public static Result<Void> failure(AbstractException abstractException) {

    }


}
