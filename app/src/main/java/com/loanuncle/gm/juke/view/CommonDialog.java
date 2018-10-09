package com.loanuncle.gm.juke.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.util.ScreenUtil;

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

    private TextView delete;
    private TextView sure;
    private TextView head;
    private TextView titleView;

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
        sure = findViewById(R.id.sure_btn);
        head = findViewById(R.id.dialog_head);
        titleView = findViewById(R.id.dialog_title);

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

    /**
     * 设置底部按钮的文字
     * */
    public void setBottomText(String deleteStr,String sureStr){
        if(delete != null){
            delete.setText(deleteStr);
        }
        sure.setText(sureStr);
    }

    /**
     * 去除取消按钮
     * */
    public void setDeleteGone(){
        delete.setVisibility(View.GONE);
    }

    /**
     * 设置头部是否显示
     * */
    public void setHeadVisiable(boolean isShow){
        if(isShow){
            head.setVisibility(View.VISIBLE);
        }else {
            head.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param title*/
    public void setTitleStr(Spanned title){
        titleView.setText(title);
    }

    /**
     * 设置头部显示字符
     * */
    public void setHead(String headStr){
        head.setText(headStr);
    }

    public interface ICommonDialogClickCallBack{
        void dialogDeleteCallBack();
        void dialogSureCallBack();
    }
}
