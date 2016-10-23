package com.mevv.myframe.common.base;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mevv.myframe.R;

/**
 * Created by VV on 2016/10/23.
 */

public class CommonFragmentActivity extends BaseActivity {

    public static final String FRAG_ID = "fragmentId";
    protected BaseFragment mFragment;

    @Override
    public int bindLayout() {
        return R.layout.activity_common_fragment;
    }

    @Override
    public void init() {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int fragId = getIntent().getIntExtra(FRAG_ID, -1);
                if (fragId == -1)
                    return;
                mFragment = FragmentFractory.createFragment(fragId);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fl_common_fragment_activity_container, mFragment);
                ft.commit();
            }
        });
    }
}
