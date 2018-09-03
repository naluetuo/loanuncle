package com.loanuncle.gm.juke.model;


import com.loanuncle.gm.juke.bean.response.AddBillResponseBean;
import com.loanuncle.gm.juke.bean.response.AgreementResponseBean;
import com.loanuncle.gm.juke.bean.response.BaseInfoResponseBean;
import com.loanuncle.gm.juke.bean.response.DeleteBillResponseBean;
import com.loanuncle.gm.juke.bean.response.GetBillDoneListResponseBean;
import com.loanuncle.gm.juke.bean.response.GetBillListResponseBean;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;
import com.loanuncle.gm.juke.bean.response.LoginResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;
import com.loanuncle.gm.juke.bean.response.RepayMentResponseBean;
import com.loanuncle.gm.juke.bean.response.VerCodeResponseBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * created by GM in 2018.8.20
 * @description 网络请求接口
 * */
public interface RetrofitService {


//    String BASE_URL = "http://172.16.3.247:6010/";

    String BASE_URL = "http://loans.jukmall.cn/";

    /**
     * 请求发送验证码
     * */
    @POST("account/getSmsCode")
    Observable<VerCodeResponseBean> sendVerCode(@Body RequestBody bean);

    /**
     * 登录请求
     * */
    @POST("account/login")
    Observable<LoginResponseBean> login(@Body RequestBody body);

    /**
     * 退出登录请求
     * */
    @POST("account/loginout")
    Observable<LogoutResponseBean> logout(@Body RequestBody body);

    /**
     * 协议请求
     * */
    @GET("common/getContractUrl")
    Observable<AgreementResponseBean> agreement(@QueryMap Map<String,Object> body);

    /**
     * 获取基本版本信息请求
     * */
    @GET("common/getBaseInfo")
    Observable<BaseInfoResponseBean> baseInfo(@QueryMap Map<String,Object> body);

    /**
     * 添加账单请求
     * */
    @POST("bill/addBill")
    Observable<AddBillResponseBean> addBill(@Body RequestBody body);

    /**
     * 获取账单列表
     * */
    @POST("bill/getBillList")
    Observable<GetBillListResponseBean> getBillList(@Body RequestBody body);

    /**
     * 删除账单
     * */
    @POST("bill/deleteBill")
    Observable<DeleteBillResponseBean> deleteBill(@Body RequestBody body);

    /**
     * 账单还款
     * */
    @POST("bill/billRefund")
    Observable<RepayMentResponseBean> repayMent(@Body RequestBody body);

    /**
     * 获取已完成账单
     * */
    @POST("bill/getFinishBillList")
    Observable<GetBillDoneListResponseBean> getBillDoneList(@Body RequestBody body);

    /**
     * 验证用户信息
     * */
    @POST("account/getNewInfo")
    Observable<GetNewUserResponseBean> getNewUserInfo(@Body RequestBody body);

}