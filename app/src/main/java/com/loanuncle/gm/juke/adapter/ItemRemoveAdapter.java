package com.loanuncle.gm.juke.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.bean.response.BillBean;

import java.util.List;

public class ItemRemoveAdapter extends BaseAdapter<ItemRemoveAdapter.ViewHolder> {
    private Context mContext;
    private List<BillBean> mList;
    private BillCallBack mBillCallBack;

    public ItemRemoveAdapter(Context mContext,List<BillBean> mList,BillCallBack billCallBack) {
        super(mContext);
        this.mContext = mContext;
        this.mList = mList;
        this.mBillCallBack = billCallBack;
    }

    @Override
    public void notifyDataSetChanged(List<String> dataList) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ItemRemoveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_repayment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRemoveAdapter.ViewHolder holder, int position) {
        final BillBean billBean = mList.get(position);
        if(billBean != null){
            holder.platformName.setText(billBean.getBorrowChannel());
            holder.userName.setText(billBean.getUsername());
            holder.moneyShoudpay.setText(billBean.getNeedRefundAmount());
            int payTime = billBean.getRefundDays();
            if(payTime > 0){
                holder.overdueTimeTxt.setVisibility(View.GONE);
                holder.payTime.setText(payTime+"");
            }else if(payTime == 0){
                holder.overdueTimeTxt.setVisibility(View.GONE);
                holder.payTime.setText("今");
            }else {
                holder.overdueTimeTxt.setVisibility(View.VISIBLE);
                holder.payTimeEndtxt.setText("天");
                holder.payTime.setText(Math.abs(payTime)+"");
            }
            holder.payCount.setText(billBean.getRefundPeriods());
            if(billBean.isNeedRefund()){
                holder.makesurePay.setBackgroundResource(R.drawable.round_paysure);
                holder.makesurePay.setClickable(true);
            }else {
                holder.makesurePay.setBackgroundResource(R.drawable.round_unpaysure);
                holder.makesurePay.setClickable(false);
            }
            holder.makesurePay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBillCallBack.makeSureRepayMent(billBean);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView platformName;
        public TextView userName;
        public TextView moneyShoudpay;
        public TextView payTime;
        public TextView payCount;
        public TextView makesurePay;
        public TextView overdueTimeTxt;
        public TextView payTimeEndtxt;

        public LinearLayout layout;
        public LinearLayout contentLayout;
        public LinearLayout deleteLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            platformName = itemView.findViewById(R.id.platform_name);
            userName = itemView.findViewById(R.id.user_name);
            moneyShoudpay = itemView.findViewById(R.id.money_shoudpay);
            payTime = itemView.findViewById(R.id.pay_time);
            payCount = itemView.findViewById(R.id.pay_count);
            makesurePay = itemView.findViewById(R.id.makesure_pay);
            overdueTimeTxt = itemView.findViewById(R.id.overdue_time_txt);
            payTimeEndtxt = itemView.findViewById(R.id.pay_time_endtxt);

            layout = itemView.findViewById(R.id.item_layout);
            contentLayout = itemView.findViewById(R.id.item_content);
            deleteLayout = itemView.findViewById(R.id.item_delete);
        }
    }

    public interface BillCallBack{

        void makeSureRepayMent(BillBean bean);
    }
}
