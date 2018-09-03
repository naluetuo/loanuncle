package com.loanuncle.gm.juke.bean.response;

/**
 * Created by GM on 2018/8/25.
 * @description 完成账单实体类
 */

public class BillCompletedBean {

    private String borrowChannel;
    private String finishRefundDate;
    private String totalRefundAmount;
    private String username;

    public String getBorrowChannel() {
        return borrowChannel;
    }

    public void setBorrowChannel(String borrowChannel) {
        this.borrowChannel = borrowChannel;
    }

    public String getFinishRefundDate() {
        return finishRefundDate;
    }

    public void setFinishRefundDate(String finishRefundDate) {
        this.finishRefundDate = finishRefundDate;
    }

    public String getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(String totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
