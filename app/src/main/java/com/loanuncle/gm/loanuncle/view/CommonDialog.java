package com.loanuncle.gm.loanuncle.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.loanuncle.gm.loanuncle.R;
import com.loanuncle.gm.loanuncle.util.ScreenUtil;

/**
 * Created by GM on 2018/8/18.
 * @description 通用对话框
 */

public class CommonDialog extends Dialog{

    private static final int WIDTH_PERCENT = 2; //宽度比
    private static final int HEIGHT_PERCENT = 3; //高度比
    private String tag; //自定义标识
    private String title;
    private ICommonDialogClickCallBack callBack;

    //固定大小Dialog模板
    public CommonDialog(Context context,String title,ICommonDialogClickCallBack callBack) {
        super(context, R.style.Theme_dialog);
        this.callBack = callBack;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        initWindow();
        initCallback();
    }

    private void initWindow() {
        int deviceWidth = ScreenUtil.getScreenWidth();
        int deviceHeight = ScreenUtil.getScreenHeight();
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = deviceWidth / HEIGHT_PERCENT * WIDTH_PERCENT;
        layoutParams.height = deviceHeight / HEIGHT_PERCENT;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }

    /**
     * 添加回调
     * */
    private void initCallback() {
        TextView delete = findViewById(R.id.cancle_btn);
        TextView sure = findViewById(R.id.sure_btn);
        TextView titleView = findViewById(R.id.dialog_title);

        titleView.setText(title);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.dialogDeleteCallBack();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.dialogSureCallBack();
            }
        });
    }

    //自定义Dialog模板
    public CommonDialog(Context context, int width, int height, int layout, int gravity){
        this(context,width,height,layout, R.style.Theme_dialog,gravity,R.style.pop_anim_style);
    }

    //自定义大小Dialog属性
    public CommonDialog(Context context,int width,int height,int layout,int style,int gravity,int animation){
        super(context,style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(animation);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public interface ICommonDialogClickCallBack{
        void dialogDeleteCallBack();
        void dialogSureCallBack();
    }
}
