package com.loanuncle.gm.juke.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.bean.response.BillCompletedBean;

import java.util.List;

public class BillCompltedAdapter extends BaseAdapter<BillCompltedAdapter.ViewHolder> {
    private Context mContext;
    private List<BillCompletedBean> mList;

    public BillCompltedAdapter(Context mContext,List<BillCompletedBean> billCompletedBeans) {
        super(mContext);
        this.mContext = mContext;
        this.mList = billCompletedBeans;
    }

    @Override
    public void notifyDataSetChanged(List<String> dataList) {

    }

    @Override
    public BillCompltedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_alreadypay, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillCompltedAdapter.ViewHolder holder, int position) {
        BillCompletedBean billCompletedBean = mList.get(position);
        if(billCompletedBean != null){
            holder.platformName.setText(billCompletedBean.getBorrowChannel());
            holder.userName.setText(billCompletedBean.getUsername());
            holder.moneyShoudpay.setText(billCompletedBean.getTotalRefundAmount());
            holder.payCount.setText(billCompletedBean.getFinishRefundDate());
        }
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout content;
        public LinearLayout layout;

        public TextView platformName;
        public TextView userName;
        public TextView moneyShoudpay;
        public TextView payCount;

        public ViewHolder(View itemView) {
            super(itemView);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);

            platformName = itemView.findViewById(R.id.platform_name);
            userName = itemView.findViewById(R.id.user_name);
            moneyShoudpay = itemView.findViewById(R.id.money_shoudpay);
            payCount = itemView.findViewById(R.id.pay_count);

        }
    }
}
