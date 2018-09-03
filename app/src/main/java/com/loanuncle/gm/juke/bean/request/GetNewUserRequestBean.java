package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/8/25.
 * @description 验证用户信息请求实体类
 */

public class GetNewUserRequestBean extends BaseRequest{

    /**
     * mobile : string
     */

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
