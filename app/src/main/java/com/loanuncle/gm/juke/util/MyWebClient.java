package com.loanuncle.gm.juke.util;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.loanuncle.gm.juke.constant.WebConstance;

/**
 * Created by GM on 2018/8/25.
 * @description webview监听类
 */

public class MyWebClient extends WebViewClient {

    private InterceptUrlCallBack interceptUrlCallBack;

    public MyWebClient(InterceptUrlCallBack interceptUrlCallBack) {
        this.interceptUrlCallBack = interceptUrlCallBack;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //这里进行url拦截

        if(url == null){
            return false;
        }

        if(url.startsWith(WebConstance.WEB_URLHEAD)){
            String action = "";
            if(url.contains("&")){
                action = url.substring(url.indexOf("=",0)+1,url.indexOf("&",0));
            }else {
                action = url.substring(url.indexOf("=")+1,url.length());
            }
            if(WebConstance.GET_UERINFO.equals(action)){
                interceptUrlCallBack.getUserInfo();
            }else if(WebConstance.SET_USERRELOGIN.equals(action)){
                interceptUrlCallBack.setUserRelogin();
                return true;
            }else if(WebConstance.GET_NEWACCOUNTINFO.equals(action)){
                interceptUrlCallBack.getNewAccountInfo();
            }else if(WebConstance.WEBPULL.equals(action)){
                interceptUrlCallBack.pullPlatformInfo();
                return true;
            }else if(WebConstance.WEBPUSH.equals(action)){
                String body = url.substring(url.indexOf("&",0)+1);
                interceptUrlCallBack.pushPlatformInfo(body);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        String title = view.getTitle();
        view.getSettings().setJavaScriptEnabled(true);
        interceptUrlCallBack.setWebTitle(title);
        interceptUrlCallBack.getUserInfo();
        interceptUrlCallBack.isRootWeb(url);
    }

    public interface InterceptUrlCallBack{

        /**
         * 调用h5将用户数据传过去
         * */
        void getUserInfo();
        /**
         * 退出登录操作
         * */
        void setUserRelogin();
        /**
         * 获取新的账户信息
         * */
        void getNewAccountInfo();
        /**
         * 将数据推送给H5
         * */
        void pullPlatformInfo();
        /**
         * 从H5获取数据
         * */
        void pushPlatformInfo(String body);
        /**
         * 获取标题
         * */
        void setWebTitle(String title);
        /**
         * 判断是否是底层网页
         * */
        boolean isRootWeb(String url);
    }
}
