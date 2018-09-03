package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/8/24.
 * @description 添加账单请求类
 */

public class AddBillRequestBean extends BaseRequest{

    public AddBillRequestBean() {
        super();
    }

    private String borrowChannel;
    private int currentPeriods;
    private int dueDate;
    private int monthDueAmount;
    private int totalPeriods;
    private String username;


    public String getBorrowChannel() {
        return borrowChannel;
    }

    public void setBorrowChannel(String borrowChannel) {
        this.borrowChannel = borrowChannel;
    }


    public int getCurrentPeriods() {
        return currentPeriods;
    }

    public void setCurrentPeriods(int currentPeriods) {
        this.currentPeriods = currentPeriods;
    }

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }


    public int getMonthDueAmount() {
        return monthDueAmount;
    }

    public void setMonthDueAmount(int monthDueAmount) {
        this.monthDueAmount = monthDueAmount;
    }


    public int getTotalPeriods() {
        return totalPeriods;
    }

    public void setTotalPeriods(int totalPeriods) {
        this.totalPeriods = totalPeriods;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
