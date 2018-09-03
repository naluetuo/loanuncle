package com.loanuncle.gm.juke.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.util.AgreementWebClient;
import com.loanuncle.gm.juke.util.MyWebChromClient;

/**
 * Created by GM on 2018/8/27.
 * @description 协议网页
 */

public class AgreementWebActivity extends LoanBaseActivity implements AgreementWebClient.InterceptUrlCallBack {

    private WebView agreementWeb;
    private ProgressBar mPbProgress;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_agreement);
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    @Override
    public void initView() {
        agreementWeb = findViewById(R.id.agreement_web);
        mPbProgress = findViewById(R.id.pb_progress);

        url = getIntent().getStringExtra("url");
        initWeb();
    }

    private void initWeb() {
        WebSettings settings = agreementWeb.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        agreementWeb.setWebViewClient(new AgreementWebClient(this));
        agreementWeb.setWebChromeClient(new MyWebChromClient(mPbProgress));
//        mLoanWebView.loadUrl("file:///android_asset/javascript.html");
//        mLoanWebView.loadUrl("https://taishanopen.oss-cn-hangzhou.aliyuncs.com/H5/index.html");
        agreementWeb.loadUrl(url);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolBarRightImage.setVisibility(View.GONE);
        mToolBarTitle.setText(getResources().getString(R.string.agreement_title));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setWebTitle(String title) {
        mToolBarTitle.setText(title);
    }
}
