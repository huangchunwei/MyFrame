package com.mevv.myframe.common.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mevv.myframe.R;
import com.mevv.myframe.common.util.UIUtils;
import com.mevv.myframe.widget.recyclerview.SpacesItemDecoration;
import com.mevv.myframe.widget.recyclerview.VRecyclerView;
import com.mevv.myframe.widget.superadapter.SuperAdapter;


/**
 * Created by Wayne on 2016/7/28.
 * Email: loveuu715@163.com
 */
public abstract class BaseRefreshListFragment extends BaseFragment {

    protected VRecyclerView mRecyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    private SpacesItemDecoration itemDecoration;
    protected int pageNo = 1;
    protected SuperAdapter mAdapter;
    protected Context mContext;

    @Override
    protected int bindLayout() {
        return R.layout.base_refresh_fragment;
    }

    @Override
    protected void init() {
        mContext = getActivity();
        mRecyclerView = (VRecyclerView) mView.findViewById(R.id.recycle_view_base_fragment);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        itemDecoration = new SpacesItemDecoration(UIUtils.dip2px(mContext, 10));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light,
                android.R.color.holo_green_light, R.color.colorAccent);
        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout != null) {
                    refreshData(swipeRefreshLayout);
                }
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefreshListener.onRefresh(); // 第一次必须手动调用，直接调用setRefreshing不会触发onRefresh方法
            }
        }, 50);

        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getContext(), 2));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingListener(new VRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (mRecyclerView != null) {
                    loadData(mRecyclerView);
                }
            }
        });
        SuperAdapter adapter = getAdapter();
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
            mAdapter = adapter;
        }
        View headView = getHeaderView();
        if (headView != null) {
            mRecyclerView.addHeaderView(headView);
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        mRecyclerView.setPadding(left, top, right, bottom);
    }

    public void setItemDecoration(int space) {
        mRecyclerView.removeItemDecoration(itemDecoration);
        itemDecoration = new SpacesItemDecoration(space);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    protected abstract SuperAdapter getAdapter();

    protected abstract void refreshData(SwipeRefreshLayout refreshLayout);

    protected abstract void loadData(VRecyclerView recycleView);


    protected void stopRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 300);
        }
    }

    protected void stopLoadMore() {
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
        }
    }

    protected View getHeaderView() {
        return null;
    }

}
