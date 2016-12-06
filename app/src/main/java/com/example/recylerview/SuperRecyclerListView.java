package com.example.recylerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SuperRecyclerListView extends RecyclerView {

    private LoadListener mLoadListener;
    private LayoutManager mLayoutManager;
    private boolean mIsLoading;
    private boolean mIsLoadEnable = true;

    /**
     * 回调接口，用于数据加载
     */
    public interface LoadListener {
        void loadData();
    }

    public SuperRecyclerListView(Context context) {
        this(context, null);
    }

    public SuperRecyclerListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperRecyclerListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.addOnScrollListener(new SuperOnScrollListener());

        mLayoutManager = this.getLayoutManager();
    }

    /**
     * 设置load listener
     *
     * @param loadListener
     */
    public void setOnLoadListener(LoadListener loadListener) {
        mLoadListener = loadListener;
    }

    /**
     * 数据加载结束后调用此方法
     */
    public void onLoadComplete() {
        mIsLoading = false;
    }

    /**
     * 判断是否正在加载中
     *
     * @return
     */
    public boolean isLoading() {
        return mIsLoading;
    }

    /**
     * 数据加载结束后调用此方法
     */
    public boolean isLoadEnable() {
        return mIsLoadEnable;
    }

    /**
     * 设置是否启用自动加载功能
     *
     * @param isEnable
     */
    public void setLoadEnable(boolean isEnable) {
        mIsLoadEnable = isEnable;
    }

    private class SuperOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mLayoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mLayoutManager;
                int visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();

                if (totalItemCount <= visibleItemCount) return;
                if (mIsLoading || !mIsLoadEnable) return;
                int lastItemIndex = firstVisibleItem + visibleItemCount;
                if (lastItemIndex >= totalItemCount && mLoadListener != null) {
                    mIsLoading = true;
                    mLoadListener.loadData();
                }
            }
        }
    }

}
