package com.example.model.bean;

public class LoginCallBackBean {

    /**
     * user : {"id":21,"phone":"15914855180","nick":"用户8629160","name":null,"card":null,"clientType":"android","channelId":"2","status":1,"activeTime":1563340488,"createTime":1563328362,"ip":"223.104.1.244"}
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjEsInBob25lIjoiMTU5MTQ4NTUxODAiLCJuYW1lIjpudWxsLCJpYXQiOjE1NjM1MjIxNzF9.JXimuW6kk4v6tGVpBpHevS37gOl_fgAiGm9Z62wfRLc
     */

    private UserBean user;
    private String token;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        /**
         * id : 21
         * phone : 15914855180
         * nick : 用户8629160
         * name : null
         * card : null
         * clientType : android
         * channelId : 2
         * status : 1
         * activeTime : 1563340488
         * createTime : 1563328362
         * ip : 223.104.1.244
         */

        private int id;
        private String phone;
        private String nick;
        private Object name;
        private Object card;
        private String clientType;
        private String channelId;
        private int status;
        private int activeTime;
        private int createTime;
        private String ip;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getCard() {
            return card;
        }

        public void setCard(Object card) {
            this.card = card;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(int activeTime) {
            this.activeTime = activeTime;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
