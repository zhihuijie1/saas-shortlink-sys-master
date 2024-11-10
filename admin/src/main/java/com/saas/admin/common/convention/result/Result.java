package com.saas.admin.common.convention.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
@Accessors(chain = true)
// @Accessors(chain = true) 用于开启链式调用
// 当设置 chain = true 时，setter 方法会返回当前对象 this，而不是默认的 void
// 这样可以实现链式调用，比如: new Result().setCode("0").setMessage("success").setData(data)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 5679018624309023727L;

    /**
     * 正确返回码
     */
    public static final String SUCCESS_CODE = "0";

    /**
     * 返回码
     */
    public String code;

    /**
     * 返回消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    /**
     * 读取id
     */
    private String requestId;

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }
}
