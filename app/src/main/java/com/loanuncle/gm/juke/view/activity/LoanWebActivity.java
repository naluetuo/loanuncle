package com.loanuncle.gm.juke.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.bean.BaseRequest;
import com.loanuncle.gm.juke.bean.request.WebPullDataBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.contact.GetNewUserContact;
import com.loanuncle.gm.juke.contact.PersonCenterContact;
import com.loanuncle.gm.juke.util.MyWebChromClient;
import com.loanuncle.gm.juke.util.MyWebClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GM on 2018/8/25.
 * @description 贷款H5界面
 */

public class LoanWebActivity extends LoanBaseActivity implements MyWebClient.InterceptUrlCallBack,
        PersonCenterContact.view, GetNewUserContact.view {

    private WebView mLoanWebView;
    private ProgressBar mPbProgress;
    private WebPullDataBean webPullDataBean;
    private boolean isRootView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_supermarket);
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        mLoanWebView = findViewById(R.id.supermarket_web);
        mPbProgress = findViewById(R.id.pb_progress);
        initWeb();
    }

    /**
     * 初始化web
     * */
    @SuppressLint("JavascriptInterface")
    private void initWeb() {
        WebSettings settings = mLoanWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mLoanWebView.setWebViewClient(new MyWebClient(this));
        mLoanWebView.setWebChromeClient(new MyWebChromClient(mPbProgress));
//        mLoanWebView.loadUrl("file:///android_asset/javascript.html");
//        mLoanWebView.loadUrl("https://taishanopen.oss-cn-hangzhou.aliyuncs.com/H5/index.html");
        mLoanWebView.loadUrl(OtherConstant.LOAN_URL);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolBarRightImage.setVisibility(View.GONE);
        mToolBarTitle.setText(getResources().getString(R.string.loan_supermarket_title));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mLoanWebView.canGoBack()){
                    finish();
                }else {
                    mLoanWebView.goBack();
                }
            }
        });
        mWebClosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getUserInfo() {
        BaseRequest baseRequest = new BaseRequest();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String request = gson.toJson(baseRequest);
//        String htmlUrl = "https://taishanopen.oss-cn-hangzhou.aliyuncs.com/H5/index.html";
//        String url = htmlUrl+":callJS()";

        String url = "javascript:userInfo('"+request+"')";
//        mLoanWebView.loadUrl("callJS()");
        mLoanWebView.evaluateJavascript(url, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                Log.e("webView",value);
            }
        });
    }

    @Override
    public void setUserRelogin() {
        overallLogout();
    }

    @Override
    public void getNewAccountInfo() {
        overallGetNewUserInfo();
    }

    @Override
    public void pullPlatformInfo() {
        if(webPullDataBean != null){
            String url = "javascript:platformInfo('"+webPullDataBean.getPullData()+"')";
            mLoanWebView.evaluateJavascript(url, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    Log.e("webView",value);
                }
            });
        }
    }

    @Override
    public void pushPlatformInfo(String body) {
        String[] params = body.split("&");
        Map<String,Object> map = new HashMap<>();
        Gson gson = new Gson();
        if(params != null && params.length > 0){
            for (int i = 0; i < params.length; i++) {
                int middle = params[i].indexOf("=");
                map.put(params[i].substring(0,middle),params[i].substring(middle+1,params[i].length()));
            }
            if(map.size() > 0){
                String data = gson.toJson(map);
                webPullDataBean = new WebPullDataBean();
                webPullDataBean.setPullData(data);
            }
        }
    }

    @Override
    public void setWebTitle(String title) {
        mToolBarTitle.setText(title);
    }

    @Override
    public boolean isRootWeb(String url) {
//        if(OtherConstant.LOAN_URL.equals(url.substring(0,url.length()-2))){
        if(!mLoanWebView.canGoBack()){
            isRootView = true;
        }else {
            isRootView = false;
        }
        changeRightBtn();
        return isRootView;
    }

    /**
     * 改变右上角按钮
     * */
    private void changeRightBtn(){
        if(!isRootView){
            mWebClosebtn.setVisibility(View.VISIBLE);
        }else {
            mWebClosebtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressedSupport() {
//        super.onBackPressedSupport();
        if(!mLoanWebView.canGoBack()){
            finish();
        }else {
            mLoanWebView.goBack();
        }
    }
}
