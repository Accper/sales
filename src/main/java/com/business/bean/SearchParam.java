package com.business.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @Author: taoye
 * @Description:
 * @Date: 17:50 2018/8/15
 */
public class SearchParam extends PageParam {
    /**
     * 查询关键字
     */
    private String keyWord;

    /**
     * 查询开始时间
     */
    private Date startDate;

    /**
     * 查询结束时间
     */
    private Date endDate;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Date getStartDate() {
        if (startDate == null) { return null; }
        DateTime dt = new DateTime(startDate);
        return dt.millisOfDay().withMinimumValue().toDate();
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        if (endDate == null) { return null; }
        DateTime dt = new DateTime(endDate);
        return dt.millisOfDay().withMaximumValue().toDate();
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

