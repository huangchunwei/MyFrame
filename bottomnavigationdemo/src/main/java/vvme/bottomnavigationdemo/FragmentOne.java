package vvme.bottomnavigationdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by VV on 2016/11/14.
 */

public class FragmentOne extends Fragment implements View.OnClickListener {


    private static Context mContext;


    public static FragmentOne newInstance() {

        Bundle args = new Bundle();

        FragmentOne fragment = new FragmentOne();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        View view = inflater.inflate(R.layout.fragment_one, null);
        view.findViewById(R.id.btn_jump).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        mContext.startActivity(new Intent(mContext, TestActivity.class));
    }
}
