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

public class ItemRemoveAdapter extends BaseAdapter<ItemRemoveAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mList;

    public ItemRemoveAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public void notifyDataSetChanged(List<String> dataList) {

    }

    @Override
    public ItemRemoveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_repayment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRemoveAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 5;
//        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout content;
        public LinearLayout delete;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            delete = (LinearLayout) itemView.findViewById(R.id.item_delete);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
