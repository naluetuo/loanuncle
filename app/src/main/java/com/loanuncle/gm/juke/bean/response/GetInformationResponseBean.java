package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

/**
 * Created by GM on 2018/9/16.
 * @description 获取个人信息返回类
 */

public class GetInformationResponseBean extends BaseResponse{

    /**
     * result : {"birthday":"string","mobile":"string","nickname":"string","sex":"string"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * birthday : string
         * mobile : string
         * nickname : string
         * sex : string
         */

        private String birthday;
        private String mobile;
        private String nickname;
        private String sex;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
