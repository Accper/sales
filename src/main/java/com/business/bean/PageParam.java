package com.business.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Transient;

/**
 * @Author: taoye
 * @Description:
 * @Date: 17:50 2018/8/15
 */
public class PageParam {

    /**
     * 第几页
     */
    @Transient
    private Integer pageNum;

    /**
     * 每页显示条数
     */
    @Transient
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}