package com.loanuncle.gm.juke.base;

import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;

/**
 * Created by GM on 2018/8/25.
 * @description 底层view（主要用于全局请求）
 */

public interface LoanBaseView extends BaseView {

    void overallLogout(LogoutResponseBean logoutResponseBean);

    void overallGetNewInfo(GetNewUserResponseBean getNewUserResponseBean);
}
