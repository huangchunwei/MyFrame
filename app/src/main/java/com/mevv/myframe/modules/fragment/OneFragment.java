package com.mevv.myframe.modules.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.mevv.myframe.R;
import com.mevv.myframe.common.base.BaseRefreshListFragment;
import com.mevv.myframe.bean.OneBean;
import com.mevv.myframe.common.util.TipUtil;
import com.mevv.myframe.modules.adapter.OneAdapter;
import com.mevv.myframe.widget.recyclerview.VRecyclerView;
import com.mevv.myframe.widget.superadapter.OnItemClickListener;
import com.mevv.myframe.widget.superadapter.SuperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VV on 2016/10/23.
 */

public class OneFragment extends BaseRefreshListFragment {

    private List<OneBean> mBeanList;
    private OneAdapter mOneAdapter;

    public static OneFragment newInstance() {
        Bundle args = new Bundle();
        OneFragment fragment = new OneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected SuperAdapter getAdapter() {
        mOneAdapter = new OneAdapter(mContext, mBeanList, R.layout.item_one);
        mOneAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                TipUtil.showToast("位置:" + (position - 1) + ",name:"
                        + mOneAdapter.getData().get(position - 1).getName());
            }
        });
        return mOneAdapter;
    }

    @Override
    protected void refreshData(SwipeRefreshLayout refreshLayout) {
        mBeanList = new ArrayList<>();
        OneBean oneBean;
        for (int i = 0; i < 20; i++) {
            oneBean = new OneBean();
            oneBean.setId(i + "");
            oneBean.setName("数据" + i);
            mBeanList.add(oneBean);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefresh();
                mOneAdapter.getData().clear();
                mOneAdapter.addAll(mBeanList);
                mOneAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(0);
            }
        }, 2000);
    }

    @Override
    protected void loadData(VRecyclerView recycleView) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopLoadMore();
                List<OneBean> data = new ArrayList<OneBean>();
                data.add(new OneBean("aa", "新增数据aa", "'"));
                data.add(new OneBean("bb", "新增数据bb", "'"));
                data.add(new OneBean("cc", "新增数据cc", "'"));
                mOneAdapter.addAll(data);
            }
        }, 3000);
    }
}
