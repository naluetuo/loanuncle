package com.loanuncle.gm.juke.bean.response;

/**
 * Created by GM on 2018/8/24.
 * @description 账单实体类
 */

public class BillBean {
    /**
     * billId : 1000000000000000
     * borrowChannel : 老司机的账单
     * needRefund : true
     * needRefundAmount : 100.00
     * refundDays : 0
     * refundPeriods : 4/5
     * username : 老司机
     */

    private String billId;
    private String borrowChannel;
    private boolean needRefund;
    private String needRefundAmount;
    private int refundDays;
    private String refundPeriods;
    private String username;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBorrowChannel() {
        return borrowChannel;
    }

    public void setBorrowChannel(String borrowChannel) {
        this.borrowChannel = borrowChannel;
    }

    public boolean isNeedRefund() {
        return needRefund;
    }

    public void setNeedRefund(boolean needRefund) {
        this.needRefund = needRefund;
    }

    public String getNeedRefundAmount() {
        return needRefundAmount;
    }

    public void setNeedRefundAmount(String needRefundAmount) {
        this.needRefundAmount = needRefundAmount;
    }

    public int getRefundDays() {
        return refundDays;
    }

    public void setRefundDays(int refundDays) {
        this.refundDays = refundDays;
    }

    public String getRefundPeriods() {
        return refundPeriods;
    }

    public void setRefundPeriods(String refundPeriods) {
        this.refundPeriods = refundPeriods;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
