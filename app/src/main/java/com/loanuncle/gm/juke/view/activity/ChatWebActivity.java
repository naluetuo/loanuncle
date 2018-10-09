package com.loanuncle.gm.juke.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.bean.request.WebPullDataBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.util.MyWebChromClient;
import com.loanuncle.gm.juke.util.MyWebClient;

/**
 * Created by GM on 2018/8/25.
 * @description 贷款图表H5界面
 */

public class ChatWebActivity extends LoanBaseActivity implements MyWebClient.InterceptUrlCallBack {

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
        settings.setDomStorageEnabled(true);
        mLoanWebView.setWebViewClient(new MyWebClient(this));
        mLoanWebView.setWebChromeClient(new MyWebChromClient(mPbProgress));
//        mLoanWebView.loadUrl("https://creditcardapp.bankcomm.com/applynew/front/apply/track/record.html?trackCode=A0717103825971");
//        mLoanWebView.loadUrl("https://taishanopen.oss-cn-hangzhou.aliyuncs.com/H5/index.html");
        mLoanWebView.loadUrl(OtherConstant.BILLCHART_URL);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolBarRightImage.setVisibility(View.GONE);
        mToolBarTitle.setText("");
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
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void getUserInfo() {

    }

    @Override
    public void setUserRelogin() {

    }

    @Override
    public void getNewAccountInfo() {

    }

    @Override
    public void pullPlatformInfo() {

    }

    @Override
    public void pushPlatformInfo(String body) {

    }

    @Override
    public void setWebTitle(String title) {
        mToolBarTitle.setText(title);
    }

    @Override
    public boolean isRootWeb(String url) {
        return false;
    }

    @Override
    public void showLog(String str) {

    }
}
