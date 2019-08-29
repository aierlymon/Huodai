package com.linglong.model.bean;

import java.util.List;

public class NewHomeMenuBean {


    /**
     * loanCategories : [{"id":1,"name":"新口子","icon":"/group1/default/20190701/18/09/8/1.png","createTime":1561904630,"allowClient":"1,2,3,4"},{"id":2,"name":"闪电下款","icon":"/group1/default/20190701/18/10/8/4.png","createTime":1561904644,"allowClient":"1,2,3,4"},{"id":3,"name":"高通过率","icon":"/group1/default/20190701/18/10/8/3.png","createTime":1561946855,"allowClient":"1,2,3,4"},{"id":4,"name":"品牌大额","icon":"/group1/default/20190701/18/10/8/2.png","createTime":1561947388,"allowClient":"1,2,3,4"},{"id":9,"name":"信息流广告","icon":"/group1/default/20190725/14/50/8/1.png","createTime":1564037431,"allowClient":"1,2,3,4"},{"id":10,"name":"搜索广告","icon":"/group1/default/20190725/14/50/8/2.png","createTime":1564037450,"allowClient":"1,2,3,4"},{"id":11,"name":"视频广告","icon":"/group1/default/20190725/14/50/8/3.png","createTime":1564037463,"allowClient":"1,2,3,4"},{"id":12,"name":"社交广告","icon":"/group1/default/20190725/14/51/8/4.png","createTime":1564037476,"allowClient":"1,2,3,4"},{"id":13,"name":"新口子","icon":"/group1/default/20190701/18/09/8/1.png","createTime":1564129436,"allowClient":"1,2,3,4"},{"id":14,"name":"闪电下款","icon":"/group1/default/20190701/18/10/8/4.png","createTime":1564129487,"allowClient":"1,2,3,4"}]
     * page : 1
     * pageCount : 10
     * info : 获取成功！
     */

    private int page;
    private int pageCount;
    private String info;
    private List<LoanCategoriesBean> loanCategories;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<LoanCategoriesBean> getLoanCategories() {
        return loanCategories;
    }

    public void setLoanCategories(List<LoanCategoriesBean> loanCategories) {
        this.loanCategories = loanCategories;
    }

    public static class LoanCategoriesBean {
        /**
         * id : 1
         * name : 新口子
         * icon : /group1/default/20190701/18/09/8/1.png
         * createTime : 1561904630
         * allowClient : 1,2,3,4
         */

        private int id;
        private String name;
        private String icon;
        private int createTime;
        private String allowClient;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public String getAllowClient() {
            return allowClient;
        }

        public void setAllowClient(String allowClient) {
            this.allowClient = allowClient;
        }
    }
}
