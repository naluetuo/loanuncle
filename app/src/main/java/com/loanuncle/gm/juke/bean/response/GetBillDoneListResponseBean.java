package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

import java.util.List;

/**
 * Created by GM on 2018/8/25.
 * @description 获取已完成账单返回实体类
 */

public class GetBillDoneListResponseBean extends BaseResponse{


    /**
     * result : {"curPage":0,"nextPage":0,"object":[{"borrowChannel":"老司机的账单","finishRefundDate":"2018-01-01","totalRefundAmount":"500.00","username":"laoshiji"}],"perPageRecordNum":0,"prePage":0,"requestPage":0,"totalPage":0,"totalRecords":0}
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
         * object : [{"borrowChannel":"老司机的账单","finishRefundDate":"2018-01-01","totalRefundAmount":"500.00","username":"laoshiji"}]
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
        private List<BillCompletedBean> object;

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

        public List<BillCompletedBean> getBillCompletedList() {
            return object;
        }

        public void setBillCompletedList(List<BillCompletedBean> object) {
            this.object = object;
        }
    }
}
