package com.example.recylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private static final int VIEW_TYPE_COMMON = 1;
    private static final int VIEW_TYPE_LOAD = 2;

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    private Context mContext;
    private ArrayList<String> mData;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public MyRecyclerAdapter(Context context, ArrayList<String> data) {
        this.mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case VIEW_TYPE_COMMON:
                onBindViewHolderCommon(holder, position);
                break;
            case VIEW_TYPE_LOAD:
                onBindViewHolderLoad(holder, position);
                break;
        }
    }

    private void onBindViewHolderCommon(MyViewHolder holder, final int position) {
        holder.tv.setText(mData.get(position));
        if (mOnItemClickListener != null) {
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    private void onBindViewHolderLoad(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return VIEW_TYPE_LOAD;
        }
        return VIEW_TYPE_COMMON;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.list_item_tv);
        }
    }

}
