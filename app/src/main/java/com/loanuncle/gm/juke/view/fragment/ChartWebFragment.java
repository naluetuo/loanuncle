package com.loanuncle.gm.juke.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseFragment;
import com.loanuncle.gm.juke.bean.BaseRequest;
import com.loanuncle.gm.juke.bean.request.WebPullDataBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.eventbus.ChangeButtonEventBus;
import com.loanuncle.gm.juke.eventbus.NewAccountEventBus;
import com.loanuncle.gm.juke.util.ChartWebClient;
import com.loanuncle.gm.juke.util.MyWebChromClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GM on 2018/9/18.
 * @description 图表页面
 */

public class ChartWebFragment extends LoanBaseFragment implements ChartWebClient.InterceptUrlCallBack {

    private WebView mLoanWebView;
    private ProgressBar mPbProgress;
    private WebPullDataBean webPullDataBean;
    private Context mContext;

    public static ChartWebFragment newInstance(Context context){
        ChartWebFragment fragment = new ChartWebFragment();
        fragment.mContext = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_supermarket,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        EventBus.getDefault().post(new ChangeButtonEventBus(2));
        initToolBar(view);
    }

    @Override
    public void initView(View view) {
        mLoanWebView = view.findViewById(R.id.supermarket_web);
        mPbProgress = view.findViewById(R.id.pb_progress);
        initWeb();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initToolBar(View view) {
        super.initToolBar(view);
        mToolBarRightImage.setVisibility(View.GONE);
        mToolBarTitle.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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
        mLoanWebView.setWebViewClient(new ChartWebClient(this));
        mLoanWebView.setWebChromeClient(new MyWebChromClient(mPbProgress));
//        mLoanWebView.loadUrl("https://creditcardapp.bankcomm.com/applynew/front/apply/track/record.html?trackCode=A0717103825971");
//        mLoanWebView.loadUrl("https://taishanopen.oss-cn-hangzhou.aliyuncs.com/H5/index.html");
        mLoanWebView.loadUrl(OtherConstant.BILLCHART_URL);
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
        return false;
    }


    @Override
    public void showLog(String str) {

    }

    /**
     * 订阅发送userinfo
     *
     * @param newAccountEventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(NewAccountEventBus newAccountEventBus) {
        try {
            getUserInfo();
        } catch (Exception e) {

        }
    }
}
