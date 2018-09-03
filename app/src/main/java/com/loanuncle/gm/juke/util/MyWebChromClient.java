package com.loanuncle.gm.juke.util;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by GM on 2018/8/25.
 * @description 设置加载进度条
 */

public class MyWebChromClient extends WebChromeClient{

    private ProgressBar pbProgress;

    public MyWebChromClient(ProgressBar pbProgress) {
        this.pbProgress = pbProgress;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
            // 网页加载完成
            pbProgress.setVisibility(View.GONE);
        } else {
            // 加载中
            pbProgress.setProgress(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }

}
