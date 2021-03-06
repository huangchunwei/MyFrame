package com.mevv.myframe.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VV on 2016/10/21.
 */

public class VRecyclerView extends RecyclerView {

    private static List<Integer> sHeaderTypes = new ArrayList<>();

    private static final float DRAG_RATE = 2;//下拉刷新阻率
    private static final int TYPE_REFRESH_HEADER = -5;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;
    private static final int HEADER_INIT_INDEX = 10000;

    private float mLastY = -1;


    private boolean mPullRefreshEnable = true;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFootViews = new ArrayList<>();
    private HeaderView mHeaderView;
    private boolean isLoadingData;
    private boolean isNoMore;
    private boolean loadingMoreEnabled;
    private View mEmptyView;
    private final AdapterDataObserver mDataObserver = new DataObserver();
    private Adapter mWrapAdapter;

    private LoadingListener mLoadingListener;


    public VRecyclerView(Context context) {
        this(context, null);
    }

    public VRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //下拉刷新头
        if (mPullRefreshEnable) {//是否可以下拉刷新
            HeaderView headerView = new HeaderView(getContext());
            mHeaderViews.add(headerView);
            mHeaderView = headerView;
        }
        //上拉加载尾
        final FooterView footerView = new FooterView(getContext());
        footerView.getText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE);
            }
        });
        addFootView(footerView);
        mFootViews.get(0).setVisibility(GONE);
        setOverScrollMode(OVER_SCROLL_NEVER);//设置没有边界过渡阴影效果
    }

    public void addHeaderView(View view) {
        if (mPullRefreshEnable && !(mHeaderViews.get(0) instanceof HeaderView)) {
            HeaderView refreshHeader = new HeaderView(getContext());
            mHeaderViews.add(0, refreshHeader);
            mHeaderView = refreshHeader;
        }
        mHeaderViews.add(view);
        sHeaderTypes.add(HEADER_INIT_INDEX + mHeaderViews.size());
    }

    public void addFootView(final View view) {
        mFootViews.clear();
        mFootViews.add(view);
    }

    public void loadMoreComplete() {
        isLoadingData = false;
        View footView = mFootViews.get(0);
        if (footView instanceof FooterView) {
            ((FooterView) footView).setState(FooterView.STATE_NOMORE);
        } else {
            footView.setVisibility(View.GONE);
        }
    }

    public void loadMoreComplete(String tip) {
        isLoadingData = false;
        View footView = mFootViews.get(0);
        if (footView instanceof FooterView) {
            ((FooterView) footView).setState(FooterView.STATE_NOMORE, tip);
        } else {
            footView.setVisibility(View.GONE);
        }
    }


    public void setNoMore(boolean noMore) {
        this.isNoMore = noMore;
        View footView = mFootViews.get(0);
        ((FooterView) footView).setState(this.isNoMore ? FooterView.STATE_NOMORE : FooterView.STATE_COMPLETE);
    }

    public void reset() {
        setNoMore(false);
        loadMoreComplete();
        refreshComplete();
    }

    public void noMoreLoading() {
        isLoadingData = false;
        View footView = mFootViews.get(0);
        isNoMore = true;
        if (footView instanceof FooterView) {
            ((FooterView) footView).setState(FooterView.STATE_NOMORE);
        } else {
            footView.setVisibility(View.GONE);
        }
    }

    public void setPullRefreshEnabled(boolean enabled) {
        mPullRefreshEnable = enabled;
        Log.i("hate", "是否可以下拉刷新"+mPullRefreshEnable);
    }

    public void setLoadingMoreEnabled(boolean enabled) {
        loadingMoreEnabled = enabled;
        if (!enabled && mFootViews.size() > 0) {
            mFootViews.get(0).setVisibility(GONE);
        }
    }

    public void setArrowImageView(int resId) {
        if (mHeaderView != null) {
            mHeaderView.setArrowImageView(resId);
        }
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        mDataObserver.onChanged();
    }


    public void refreshComplete() {
        mHeaderView.refreshComplete();
    }


    public View getEmptyView() {
        return mEmptyView;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                    && layoutManager.getItemCount() > layoutManager.getChildCount()
                    && !isNoMore
                    && mHeaderView.getState() < HeaderView.STATE_REFRESHING) {

                View footView = mFootViews.get(0);
                isLoadingData = true;
                if (footView instanceof FooterView) {
                    ((FooterView) footView).setState(FooterView.STATE_LOADING);
                    this.smoothScrollToPosition(lastVisibleItemPosition + 1);
                } else {
                    footView.setVisibility(View.VISIBLE);
                    this.smoothScrollToPosition(lastVisibleItemPosition + 1);
                }
                mLoadingListener.onLoadMore();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (isOnTop() && mPullRefreshEnable) {
                    mHeaderView.onMove(deltaY / DRAG_RATE);
                    if (mHeaderView.getVisibleHeight() > 0 && mHeaderView.getState() < HeaderView.STATE_REFRESHING) {
//                        Log.i("getVisibleHeight", "getVisibleHeight = " + mRefreshHeader.getVisibleHeight());
//                        Log.i("getVisibleHeight", " mRefreshHeader.getState() = " + mRefreshHeader.getState());
                        return false;
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (isOnTop() && mPullRefreshEnable) {
                    if (mHeaderView.releaseAction()) {
                        if (mLoadingListener != null) {
                            mLoadingListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private boolean isOnTop() {
        return !(mHeaderViews == null || mHeaderViews.isEmpty()) && mHeaderViews.get(0).getParent() != null;
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * Adapter数据绑定监听
     */
    private class DataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && mEmptyView != null) {
                int emptyCount = 0;
                if (mPullRefreshEnable) {
                    emptyCount++;
                }
                if (loadingMoreEnabled) {
                    emptyCount++;
                }
                if (adapter.getItemCount() == emptyCount) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    VRecyclerView.this.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    VRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }


    /**
     * Adapter装饰
     */
    private class WrapAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;

        private int headerPosition = 1;

        public WrapAdapter(Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position) || isFooter(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < mHeaderViews.size();
        }

        public boolean isContentHeader(int position) {
            return position >= 1 && position < mHeaderViews.size();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - mFootViews.size();
        }

        public boolean isRefreshHeader(int position) {
            return position == 0;
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFootViews.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                mCurrentPosition++;
                return new SimpleViewHolder(mHeaderViews.get(0));
            } else if (isContentHeader(mCurrentPosition)) {
                if (viewType == sHeaderTypes.get(mCurrentPosition - 1)) {
                    mCurrentPosition++;
                    return new SimpleViewHolder(mHeaderViews.get(headerPosition++));
                }
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootViews.get(0));
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        private int mCurrentPosition;

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isHeader(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER;
            }
            if (isHeader(position)) {
                position = position - 1;
                return sHeaderTypes.get(position);
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_NORMAL;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount()) {
                int adjPosition = position - getHeadersCount();
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        private class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }


}
