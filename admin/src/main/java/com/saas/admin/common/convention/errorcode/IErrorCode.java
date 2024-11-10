package com.saas.admin.common.convention.errorcode;

/**
 * 平台错误码
 *
 * 构建一个错误码的规范，每一个错误码必须包含两个部分一个是code一个是message
 */
public interface IErrorCode {

    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}
