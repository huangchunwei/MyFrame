package com.mevv.myframe.base;

import com.mevv.myframe.module.fragment.OneFragment;

/**
 * Created by VV on 2016/10/23.
 */

public class FragmentFractory {

    public static BaseFragment createFragment(int fragmentId){
        BaseFragment baseFragment = null;
        switch (fragmentId) {
            case FragmentId.ONE_FRAGMENT:
                baseFragment = OneFragment.newInstance();
            break;
        }
        return baseFragment;
    }
}
