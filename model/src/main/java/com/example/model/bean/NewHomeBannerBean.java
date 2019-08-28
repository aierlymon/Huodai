package com.example.model.bean;

import java.util.List;

public class NewHomeBannerBean {


    /**
     * banners : [{"id":3,"title":"对滴","icon":"/group1/default/20190725/14/48/8/云燕轮播.jpg","url":"www.baidu.com","open":1,"createTime":1561905152,"allowClient":"0,1,2"}]
     * info : 获取成功！
     */

    private String info;
    private List<BannersBean> banners;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class BannersBean {
        /**
         * id : 3
         * title : 对滴
         * icon : /group1/default/20190725/14/48/8/云燕轮播.jpg
         * url : www.baidu.com
         * open : 1
         * createTime : 1561905152
         * allowClient : 0,1,2
         */

        private int id;
        private String title;
        private String icon;
        private String url;
        private int open;
        private int createTime;
        private String allowClient;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
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
