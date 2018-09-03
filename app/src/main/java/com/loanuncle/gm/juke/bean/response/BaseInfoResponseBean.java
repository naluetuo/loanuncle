package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

/**
 * Created by GM on 2018/8/23.
 * @description 版本基本信息实体类
 */

public class BaseInfoResponseBean extends BaseResponse{

    /**
     * result : {"appUpdateResultVo":{"message":"ios版本：本次升级内容：\n\t1、优化换卡机制，换卡更安全 \n\t2、修复电子合同无法查看问题 \n\t3、优化运行速度，体验更流畅","serviceVersion":100,"updateType":0,"updateUrl":"https://itunes.apple.com/cn/app/%E7%82%B9%E7%82%B9%E6%90%9C%E8%B4%A2/id953002926?mt=8"},"showLoans":true}
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
         * appUpdateResultVo : {"message":"ios版本：本次升级内容：\n\t1、优化换卡机制，换卡更安全 \n\t2、修复电子合同无法查看问题 \n\t3、优化运行速度，体验更流畅","serviceVersion":100,"updateType":0,"updateUrl":"https://itunes.apple.com/cn/app/%E7%82%B9%E7%82%B9%E6%90%9C%E8%B4%A2/id953002926?mt=8"}
         * showLoans : true
         */

        private AppUpdateResultVoBean appUpdateResultVo;
        private boolean showLoans;
        private String loanUrl;

        public AppUpdateResultVoBean getAppUpdateResultVo() {
            return appUpdateResultVo;
        }

        public void setAppUpdateResultVo(AppUpdateResultVoBean appUpdateResultVo) {
            this.appUpdateResultVo = appUpdateResultVo;
        }

        public boolean isShowLoans() {
            return showLoans;
        }

        public void setShowLoans(boolean showLoans) {
            this.showLoans = showLoans;
        }

        public String getLoanUrl() {
            return loanUrl == null ? "" : loanUrl;
        }

        public void setLoanUrl(String loanUrl) {
            this.loanUrl = loanUrl;
        }

        public static class AppUpdateResultVoBean {
            /**
             * message : ios版本：本次升级内容：
             1、优化换卡机制，换卡更安全
             2、修复电子合同无法查看问题
             3、优化运行速度，体验更流畅
             * serviceVersion : 100
             * updateType : 0
             * updateUrl : https://itunes.apple.com/cn/app/%E7%82%B9%E7%82%B9%E6%90%9C%E8%B4%A2/id953002926?mt=8
             */

            private String message;
            private int serviceVersion;
            private int updateType;
            private String updateUrl;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getServiceVersion() {
                return serviceVersion;
            }

            public void setServiceVersion(int serviceVersion) {
                this.serviceVersion = serviceVersion;
            }

            public int getUpdateType() {
                return updateType;
            }

            public void setUpdateType(int updateType) {
                this.updateType = updateType;
            }

            public String getUpdateUrl() {
                return updateUrl;
            }

            public void setUpdateUrl(String updateUrl) {
                this.updateUrl = updateUrl;
            }
        }
    }
}
