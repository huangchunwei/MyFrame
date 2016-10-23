package com.mevv.myframe.widget.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Wayne on 2016/7/28.
 * Email: loveuu715@163.com
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int space;
    private boolean shouldPadding;
    private boolean justPaddingRight;


    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(Context context, int space) {
        this.space = space;
        if (context != null) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }
    }

    public void setLeftRight(boolean shouldPadding) {
        this.shouldPadding = shouldPadding;
    }

    public void setJustPaddingRight(boolean justPaddingRight) {
        this.justPaddingRight = justPaddingRight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 1; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (shouldPadding) {
            outRect.left = space;
            outRect.right = space;
        }
        if (justPaddingRight) {
            outRect.left = 0;
            outRect.right = space;
        }

//        outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());

        outRect.bottom = mDivider.getIntrinsicHeight();

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0
                || parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
            outRect.top = 0;
            outRect.bottom = 0;
        }

    }
}
