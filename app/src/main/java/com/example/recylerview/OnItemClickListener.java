package com.example.recylerview;

import android.view.View;

/**
 * RecyclerView item点击事件接口标准
 */

public interface OnItemClickListener {
    /**
     * 单击事件
     *
     * @param view
     * @param object
     * @param position
     */
    void onClick(View view, Object object, int position);

    /**
     * 长按事件
     *
     * @param view
     * @param object
     * @param position
     */
    void onLongClick(View view, Object object, int position);
}