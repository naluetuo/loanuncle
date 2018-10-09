package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/9/16.
 * @description 意见反馈请求类
 */

public class FeedBackRequestBean extends BaseRequest{


    public FeedBackRequestBean() {
        super();
    }

    /**
     * describe : 老司机很强大
     */

    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
