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
 * @description 编辑姓名
 */

public class ChangeNameDialog extends Dialog{
    private static final int WIDTH_PERCENT = 2; //宽度比
    private static final int HEIGHT_PERCENT = 3; //高度比
    private String tag; //自定义标识
    private String title;
    private INameDialogClickCallBack callBack;

    private TextView delete;
    private TextView sure;
    private EditText nameEdit;
    private Context mContext;


    //固定大小Dialog模板
    public ChangeNameDialog(Context context, String title, INameDialogClickCallBack callBack) {
        super(context, R.style.Theme_dialog);
        this.mContext = context;
        this.callBack = callBack;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_name);
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
        nameEdit = findViewById(R.id.name_edt);

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
                if(TextUtils.isEmpty(nameEdit.getText())){
                    ToastUtils.showShort(mContext,"填写内容为空，请填写合理意见后提交");
                }else {
                    callBack.dialogSureNameCallBack(String.valueOf(nameEdit.getText()));
                }
            }
        });
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public interface INameDialogClickCallBack{
        void dialogDeleteCallBack();
        void dialogSureNameCallBack(String name);
    }
}
