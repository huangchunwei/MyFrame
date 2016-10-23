package com.mevv.myframe.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by VV on 2016/9/21.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.mContext = getContext();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(bindLayout(), null);
            init();
        }
        return mView;
    }

    protected abstract int bindLayout();

    protected abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
