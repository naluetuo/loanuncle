package com.loanuncle.gm.juke.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by GM on 2018/8/25.
 * @description webview监听类
 */

public class AgreementWebClient extends WebViewClient {

    private InterceptUrlCallBack interceptUrlCallBack;

    public AgreementWebClient(InterceptUrlCallBack interceptUrlCallBack) {
        this.interceptUrlCallBack = interceptUrlCallBack;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //这里进行url拦截

        return super.shouldOverrideUrlLoading(view,url);
    }



    @Override
    public void onPageFinished(WebView view, String url) {
        String title = view.getTitle();
        view.getSettings().setJavaScriptEnabled(true);
        interceptUrlCallBack.setWebTitle(title);

        super.onPageFinished(view, url);
    }

    public interface InterceptUrlCallBack{
        /**
         * 获取标题
         * */
        void setWebTitle(String title);
    }
}
