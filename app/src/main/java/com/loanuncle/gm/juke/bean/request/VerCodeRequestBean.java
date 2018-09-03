package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/8/20.
 * @description 请求发送验证码请求类
 */

public class VerCodeRequestBean extends BaseRequest{

    public VerCodeRequestBean() {
        super();
    }

    private String mobile;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
