package com.loanuncle.gm.juke.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GM on 2018/8/18.
 * @description 期数选择控件
 */

public class CustomCurrentPeriodsPicker {

    private Context context;
    private Dialog periodsPickerDialog;

    private ResultHandler handler;
    private TextView cancle;
    private TextView sure;
    private DatePickerView content_pv;
    private List<String> contents;
    private static String UNIT;
    private static int MAX_COUNT = 12;
    private String currentData;

    public CustomCurrentPeriodsPicker(Context context, ResultHandler handler) {
        this.context = context;
        this.handler = handler;
        initDialog();
        initView();
    }

    private void initDialog() {
        if (periodsPickerDialog == null) {
            periodsPickerDialog = new Dialog(context, R.style.TimePickerDialog);
            periodsPickerDialog.setCancelable(true);
            periodsPickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            periodsPickerDialog.setContentView(R.layout.custom_periods_picker);
            Window window = periodsPickerDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dm.widthPixels;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        content_pv = (DatePickerView) periodsPickerDialog.findViewById(R.id.content_pv);
        cancle = periodsPickerDialog.findViewById(R.id.tv_cancle);
        sure = periodsPickerDialog.findViewById(R.id.tv_select);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periodsPickerDialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(currentData);
                periodsPickerDialog.dismiss();
            }
        });
    }

    /**
     * 显示选择控件
     * */
    public void show(String data) {
        if(periodsPickerDialog != null){
            initData();
            addListener();
            content_pv.setSelected(data);
            periodsPickerDialog.show();
        }
    }

    /**
     * 初始化数据
     * */
    private void initData(){
        if(contents == null){
            contents = new ArrayList<>();
        }
        contents.clear();
        for (int i = 1; i <= MAX_COUNT; i++) {
            contents.add(i+UNIT);
        }
        content_pv.setData(contents);
        content_pv.setCanScroll(contents.size() > 1);
    }

    private void addListener() {

        content_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                currentData = text; //保存选择的数据
            }
        });
    }

    /**
     * 设置单位
     * */
    public void setUnit(String unit){
        if(unit != null && !unit.isEmpty()){
            UNIT = unit;
        }
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setIsLoop(boolean isLoop) {
        this.content_pv.setIsLoop(isLoop);
    }

    /**
     * 设置文字大小
     * */
    public void setPvTextSize(int sp){
        this.content_pv.setTextSize(sp);
    }

    /**
     * 设置显示的条目
     * */
    public void setMaxCount(int maxCount){
        MAX_COUNT = maxCount;
    }

    /**
     * 定义结果回调接口
     */
    public interface ResultHandler {
        void handle(String time);
    }
}
