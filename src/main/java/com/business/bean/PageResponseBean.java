package com.business.bean;

import com.business.common.ResponseBean;
import com.github.pagehelper.Page;

/**
 * @Author: taoye
 * @Description:
 * @Date: 13:54 2018/8/18
 */
public class PageResponseBean<T> extends ResponseBean<T> {
    private PageData page;

    @Override
    public void setData(T data) {
        if (data instanceof Page) {
            page = new PageData((Page)data);
        }
        super.setData(data);
    }

    public void setData(T data, int total) {
        if (data instanceof Page) {
            Page tmpPage = (Page) data;
            tmpPage.setTotal(total);

            page = new PageData(tmpPage);
        }
        super.setData(data);
    }

    public PageData getPage() {
        return page;
    }

    public void setPage(PageData page) {
        this.page = page;
    }

    /**
     * com.github.pagehelper.Page 的代理类
     *
     * @author zhoujl
     */
    class PageData {
        private Page page;

        public PageData(Page page) {
            if (page == null) {
                throw new RuntimeException("page can not be null");
            }
            this.page = page;
        }

        /**
         * 页码，从1开始
         */
        public int getPageNum() {
            return page.getPageNum();
        }

        /**
         * 页面大小
         */
        public int getPageSize() {
            return page.getPageSize();
        }

        /**
         * 起始行
         */
        public int getStartRow() {
            return page.getStartRow();
        }

        /**
         * 末行
         */
        public int getEndRow() {
            return page.getEndRow();
        }

        /**
         * 总数
         */
        public long getTotal() {
            return page.getTotal();
        }

        /**
         * 总页数
         */
        public int getPages() {
            return page.getPages();
        }
    }
}
