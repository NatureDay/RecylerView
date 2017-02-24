package com.example.recylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 抽象类，用于封装Adapter一些基本操作
 */
public abstract class AppCommonAdapter<T> extends RecyclerView.Adapter {
    protected Context mContext;
    protected ArrayList<T> mData;

    /**
     * 回调监听，RecyclerView item点击事件
     */
    protected OnItemClickListener mOnItemClickListener;

    public AppCommonAdapter(Context mContext, ArrayList<T> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolderImpl(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindViewHolderImpl(holder, position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    protected abstract RecyclerView.ViewHolder createViewHolderImpl(ViewGroup parent, int viewType);

    protected abstract void bindViewHolderImpl(RecyclerView.ViewHolder holder, int position);

}
