
package com.anju.artinstituteofchicago;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    private final int mItemOffset;

    public RecyclerViewItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}
