package com.example.recylerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class AppCommonRecyclerView extends LinearLayout {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private View mLoadView;
    private View mEmptyView;

    /**
     * 回调监听，用于加载更多
     */
    protected RecyclerLoadListener mRecyclerLoadListener;
    /**
     * 加载更多功能是否启用
     */
    protected boolean mIsLoadEnable = false;

    /**
     * 是否正在加载
     */
    protected boolean mIsLoading = false;

    private AppCommonOnScrollListener mAppCommonOnScrollListener;

    /**
     * 监听数据变化，显示或隐藏emptyView
     */
    final private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateViewStatus();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateViewStatus();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateViewStatus();
        }
    };

    private class AppCommonOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if (mIsLoading || !mIsLoadEnable) return;
                if (totalItemCount <= visibleItemCount) return;
                int lastItemIndex = firstVisibleItem + visibleItemCount;
                if (lastItemIndex >= totalItemCount && mRecyclerLoadListener != null) {
                    mIsLoading = true;
                    updateLoadViewVisibility(true);
                    mRecyclerLoadListener.loadData();
                }
            }
        }
    }

    public AppCommonRecyclerView(Context context) {
        this(context, null);
    }

    public AppCommonRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppCommonRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_layout_recycler_view, this, true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.common_recyclerview);
        mLoadView = view.findViewById(R.id.common_load_view);

        if (mAppCommonOnScrollListener == null) {
            mAppCommonOnScrollListener = new AppCommonOnScrollListener();
        }
        mRecyclerView.addOnScrollListener(mAppCommonOnScrollListener);
        updateLoadViewVisibility(false);
    }

    private void updateLoadViewVisibility(boolean visible) {
        if (mLoadView == null) return;
        if (mIsLoadEnable) {
            if (visible) {
                mLoadView.setVisibility(View.VISIBLE);
            } else {
                mLoadView.setVisibility(View.GONE);
            }
        } else {
            mLoadView.setVisibility(GONE);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        mRecyclerView.addItemDecoration(decoration);
    }

    /**
     * 数据加载结束后调用此方法
     */
    public void onLoadComplete() {
        mIsLoading = false;
        updateLoadViewVisibility(false);
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
     * 判断是否可以自动加载
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

    public void setRecyclerLoadListener(RecyclerLoadListener recyclerLoadListener) {
        this.mRecyclerLoadListener = recyclerLoadListener;
    }

    /**
     * 设置emptyView
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        updateViewStatus();
    }

    /**
     * 设置adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        RecyclerView.Adapter oldAdapter = mRecyclerView.getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }
        mRecyclerView.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
        }
        updateViewStatus();
    }

    private void updateViewStatus() {
        if (mEmptyView != null && mRecyclerView.getAdapter() != null) {
            final boolean emptyViewVisible = mRecyclerView.getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }
}