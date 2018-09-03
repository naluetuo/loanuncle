package com.loanuncle.gm.juke.bean.request;

/**
 * Created by GM on 2018/8/25.
 * @description 存放H5推送过来的数据
 */

public class WebPullDataBean {

    private String pullData;

    public String getPullData() {
        return pullData == null ? "" : pullData;
    }

    public void setPullData(String pullData) {
        this.pullData = pullData;
    }
}
