package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

/**
 * Created by GM on 2018/8/22.
 * @description 登录请求返回实体类
 */

public class LoginResponseBean extends BaseResponse{


    /**
     * result : {"accountId":"kmWnq/zMgK6MwBcO+hEQqKAnwLcZev0VCchlRUrsY0w=","token":"evq8xlstngmpb0zxr0hwq4fpfwxqmypb"}
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
         * accountId : kmWnq/zMgK6MwBcO+hEQqKAnwLcZev0VCchlRUrsY0w=
         * token : evq8xlstngmpb0zxr0hwq4fpfwxqmypb
         */

        private String accountId;
        private String token;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
