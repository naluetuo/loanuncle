package com.loanuncle.gm.loanuncle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.loanuncle.gm.loanuncle.R;

import java.util.ArrayList;
import java.util.List;

public class BillCompltedAdapter extends BaseAdapter<BillCompltedAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mList;

    public BillCompltedAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
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

    }


    @Override
    public int getItemCount() {
        return 5;
//        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout content;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
