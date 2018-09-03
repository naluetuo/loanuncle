package com.loanuncle.gm.juke.model;


import com.loanuncle.gm.baselibrary.retrofit.BaseApiImpl;

/**
 * created by GM in 2018.8.20
 * @description 基础网络访问
 * */
public class Api extends BaseApiImpl {

    private static Api api = new Api(RetrofitService.BASE_URL);

    public Api(String baseUrl) {
        super(baseUrl);
    }

    public static RetrofitService getInstance() {
        return api.getRetrofit().create(RetrofitService.class);
    }
}
