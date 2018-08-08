package com.business.common;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @Author: taoye
 * @Description: 交换数据格式
 * @Date: 17:51 2018/8/7
 */
public class ResponseBean<T> {
    private Integer status;
    private String message;
    private T data;

    public ResponseBean() {
        this.status = 1;
    }

    public ResponseBean(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
