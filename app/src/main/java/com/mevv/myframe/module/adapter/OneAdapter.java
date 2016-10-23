package com.mevv.myframe.module.adapter;

import android.content.Context;
import android.widget.TextView;

import com.mevv.myframe.R;
import com.mevv.myframe.bean.OneBean;
import com.mevv.myframe.widget.superadapter.SuperAdapter;
import com.mevv.myframe.widget.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by VV on 2016/10/22.
 */

public class OneAdapter extends SuperAdapter<OneBean> {
    public OneAdapter(Context context, List<OneBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OneBean item) {
        TextView tvId = holder.findViewById(R.id.tv_one_id);
        tvId.setText(item.getId());
        TextView tvName = holder.findViewById(R.id.tv_one_name);
        tvName.setText(item.getName());
    }
}
