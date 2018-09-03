package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

import java.util.List;

/**
 * Created by GM on 2018/8/24.
 * @description 获取账单列表返回类
 */

public class GetBillListResponseBean extends BaseResponse{

    /**
     * result : {"curPage":0,"nextPage":0,"object":[{"billId":"1000000000000000","borrowChannel":"老司机的账单","needRefund":true,"needRefundAmount":"100.00","refundDays":0,"refundPeriods":"4/5","username":"老司机"}],"perPageRecordNum":0,"prePage":0,"requestPage":0,"totalPage":0,"totalRecords":0}
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
         * curPage : 0
         * nextPage : 0
         * object : [{"billId":"1000000000000000","borrowChannel":"老司机的账单","needRefund":true,"needRefundAmount":"100.00","refundDays":0,"refundPeriods":"4/5","username":"老司机"}]
         * perPageRecordNum : 0
         * prePage : 0
         * requestPage : 0
         * totalPage : 0
         * totalRecords : 0
         */

        private int curPage;
        private int nextPage;
        private int perPageRecordNum;
        private int prePage;
        private int requestPage;
        private int totalPage;
        private int totalRecords;
        private List<BillBean> object;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getPerPageRecordNum() {
            return perPageRecordNum;
        }

        public void setPerPageRecordNum(int perPageRecordNum) {
            this.perPageRecordNum = perPageRecordNum;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getRequestPage() {
            return requestPage;
        }

        public void setRequestPage(int requestPage) {
            this.requestPage = requestPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public List<BillBean> getBillList() {
            return object;
        }

        public void setBillList(List<BillBean> object) {
            this.object = object;
        }
    }
}
