package com.loanuncle.gm.juke.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.util.ScreenUtil;
import com.loanuncle.gm.juke.util.ToastUtils;

/**
 * Created by GM on 2018/9/16.
 * @description 意见反馈对话框
 */

public class FeedBackDialog extends Dialog {

    private static final int WIDTH_PERCENT = 2; //宽度比
    private static final int HEIGHT_PERCENT = 3; //高度比
    private String tag; //自定义标识
    private String title;
    private IFeedBackDialogClickCallBack callBack;

    private TextView delete;
    private TextView submit;
    private EditText suggestionEdit;
    private Context mContext;

    //固定大小Dialog模板
    public FeedBackDialog(Context context, String title, IFeedBackDialogClickCallBack callBack) {
        super(context, R.style.Theme_dialog);
        this.mContext = context;
        this.callBack = callBack;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_feedback);
        initWindow();
        initCallback();
    }

    private void initWindow() {
        int deviceWidth = ScreenUtil.getScreenWidth();
        int deviceHeight = ScreenUtil.getScreenHeight();
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        layoutParams.width = deviceWidth / 10 * 8;
        layoutParams.height = deviceHeight / HEIGHT_PERCENT;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }

    /**
     * 添加回调
     * */
    private void initCallback() {
        delete = findViewById(R.id.cancle_btn);
        submit = findViewById(R.id.submit_btn);
        suggestionEdit = findViewById(R.id.suggestion_edt);

        TextView titleView = findViewById(R.id.dialog_title);

        titleView.setText(title);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.dialogDeleteCallBack();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(suggestionEdit.getText())){
                    ToastUtils.showShort(mContext,"填写内容为空，请填写合理意见后提交");
                }else {
                    callBack.dialogSubmitCallBack(String.valueOf(suggestionEdit.getText()));
                }
            }
        });
    }

    //自定义Dialog模板
    public FeedBackDialog(Context context, int width, int height, int layout, int gravity){
        this(context,width,height,layout, R.style.Theme_dialog,gravity,R.style.pop_anim_style);
    }

    //自定义大小Dialog属性
    public FeedBackDialog(Context context,int width,int height,int layout,int style,int gravity,int animation){
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

    public interface IFeedBackDialogClickCallBack{
        void dialogDeleteCallBack();
        void dialogSubmitCallBack(String feedBackStr);
    }
}
