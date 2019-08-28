package com.example.model.bean;

import java.util.List;

public class NewHomeBodyBean {


    /**
     * loanProduct : [{"id":2,"name":"花卡小贷","icon":"/group1/default/20190630/20/27/8/WX20190513-153520@2x.png","categoryId":1,"profile":"花卡小贷的就是好","tags":"null","limitL":5000,"limitH":10000,"period":"期限 3个月-12个月","interest":"日息 0.05%","applyNum":3500,"applyCond":"身份证就可以申请","applyProc":"","specDesc":"","sucProb":"50%","speed":"最快当天下款","todayUse":"","sortNum":1,"top":0,"online":1,"url":"https://api.lianlianhua.wang/h5/register.html?id=d7221f90-5ece-474a-b17f-51e10d240c64","allowClient":"2,9,1","createTime":1561904793},{"id":4,"name":"大额金融","icon":"/group1/default/20190701/18/10/8/2.png","categoryId":4,"profile":"慈善现金贷，还不起送给你","tags":null,"limitL":5000,"limitH":30000,"period":"期限1-3个月","interest":"日息0.07%","applyNum":56893,"applyCond":"身份证+户口簿+出生证明","applyProc":"验证身份证和手机号码","specDesc":"仅限中国","sucProb":"88%","speed":"3分钟","todayUse":"99","sortNum":0,"top":1,"online":1,"url":"https://tzwz2.2dubao.com/#/youxinFourtyEight?channelId=2190","allowClient":"1","createTime":1561947003},{"id":10,"name":"家电抵押贷款","icon":"/group1/default/20190709/22/07/8/家电维修.jpg","categoryId":3,"profile":"家电就可以抵押贷款","tags":null,"limitL":2000,"limitH":6000,"period":"期限1~3个月","interest":"日息0.01%","applyNum":0,"applyCond":"","applyProc":"","specDesc":"","sucProb":"","speed":"最快3天下款","todayUse":"","sortNum":0,"top":1,"online":0,"url":"https://www.vip.com/","allowClient":"1","createTime":1562681310},{"id":19,"name":"借钱花","icon":"/group1/default/20190725/14/50/8/1.png","categoryId":13,"profile":"","tags":null,"limitL":1000,"limitH":3000,"period":"期限1~3个月","interest":"日息0.03%","applyNum":0,"applyCond":"","applyProc":"","specDesc":"","sucProb":"","speed":"最快5分钟下款","todayUse":"","sortNum":0,"top":1,"online":1,"url":"http://www.baidu.com","allowClient":"1","createTime":1564129664},{"id":20,"name":"立借","icon":"/group1/default/20190725/14/50/8/2.png","categoryId":14,"profile":"可贷口子查询","tags":null,"limitL":2000,"limitH":4000,"period":"期限7~30天","interest":"日息0.02%","applyNum":0,"applyCond":"","applyProc":"","specDesc":"","sucProb":"","speed":"最快3分钟下款","todayUse":"","sortNum":0,"top":1,"online":1,"url":"https://ddwallet.dadixintong.com/land-wx/#/2019/wx20190612","allowClient":"1","createTime":1564129821},{"id":21,"name":"微众钱包","icon":"/group1/default/20190725/14/50/8/3.png","categoryId":15,"profile":"凭身份证即可借","tags":null,"limitL":500,"limitH":2000,"period":"期限7-30天","interest":"日息0.04%","applyNum":0,"applyCond":"","applyProc":"","specDesc":"","sucProb":"","speed":"最快当天下款","todayUse":"","sortNum":0,"top":1,"online":1,"url":"http://channel.1000xn.cn/channels/view/channel.html?id=19","allowClient":"1","createTime":1564130050},{"id":22,"name":"大众小贷","icon":"/group1/default/20190725/14/51/8/4.png","categoryId":16,"profile":"人民的钱包人民花","tags":null,"limitL":10000,"limitH":50000,"period":"期限3~6个月","interest":"日息0.01%","applyNum":0,"applyCond":"","applyProc":"","specDesc":"","sucProb":"","speed":"最快3天下款","todayUse":"","sortNum":0,"top":1,"online":1,"url":"https://channel.91zzgj.com/index.html?f=jhh_wz_gdt01","allowClient":"1","createTime":1564130125}]
     * info : 获取成功！
     */

    private String info;
    private List<LoanProductBean> loanProduct;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<LoanProductBean> getLoanProduct() {
        return loanProduct;
    }

    public void setLoanProduct(List<LoanProductBean> loanProduct) {
        this.loanProduct = loanProduct;
    }

    public static class LoanProductBean {
        /**
         * id : 2
         * name : 花卡小贷
         * icon : /group1/default/20190630/20/27/8/WX20190513-153520@2x.png
         * categoryId : 1
         * profile : 花卡小贷的就是好
         * tags : null
         * limitL : 5000
         * limitH : 10000
         * period : 期限 3个月-12个月
         * interest : 日息 0.05%
         * applyNum : 3500
         * applyCond : 身份证就可以申请
         * applyProc :
         * specDesc :
         * sucProb : 50%
         * speed : 最快当天下款
         * todayUse :
         * sortNum : 1
         * top : 0
         * online : 1
         * url : https://api.lianlianhua.wang/h5/register.html?id=d7221f90-5ece-474a-b17f-51e10d240c64
         * allowClient : 2,9,1
         * createTime : 1561904793
         */

        private int id;
        private String name;
        private String icon;
        private int categoryId;
        private String profile;
        private String tags;
        private int limitL;
        private int limitH;
        private String period;
        private String interest;
        private int applyNum;
        private String applyCond;
        private String applyProc;
        private String specDesc;
        private String sucProb;
        private String speed;
        private String todayUse;
        private int sortNum;
        private int top;
        private int online;
        private String url;
        private String allowClient;
        private int createTime;

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

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public int getLimitL() {
            return limitL;
        }

        public void setLimitL(int limitL) {
            this.limitL = limitL;
        }

        public int getLimitH() {
            return limitH;
        }

        public void setLimitH(int limitH) {
            this.limitH = limitH;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public int getApplyNum() {
            return applyNum;
        }

        public void setApplyNum(int applyNum) {
            this.applyNum = applyNum;
        }

        public String getApplyCond() {
            return applyCond;
        }

        public void setApplyCond(String applyCond) {
            this.applyCond = applyCond;
        }

        public String getApplyProc() {
            return applyProc;
        }

        public void setApplyProc(String applyProc) {
            this.applyProc = applyProc;
        }

        public String getSpecDesc() {
            return specDesc;
        }

        public void setSpecDesc(String specDesc) {
            this.specDesc = specDesc;
        }

        public String getSucProb() {
            return sucProb;
        }

        public void setSucProb(String sucProb) {
            this.sucProb = sucProb;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getTodayUse() {
            return todayUse;
        }

        public void setTodayUse(String todayUse) {
            this.todayUse = todayUse;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAllowClient() {
            return allowClient;
        }

        public void setAllowClient(String allowClient) {
            this.allowClient = allowClient;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }
    }
}
