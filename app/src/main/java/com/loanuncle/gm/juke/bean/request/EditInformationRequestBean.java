package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/9/16.
 * @description 编辑个人资料请求类
 */

public class EditInformationRequestBean extends BaseRequest{

    public EditInformationRequestBean() {
        super();
    }

    /**
     * birthday : 19900101
     * nickname : 老司机
     * sex : MAN
     */

    private String birthday;
    private String nickname;
    private String sex;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
