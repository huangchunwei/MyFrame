package com.mevv.myframe;

import android.os.Bundle;
import android.view.View;

import com.mevv.myframe.common.base.BaseActivity;
import com.mevv.myframe.common.base.CommonFragmentActivity;
import com.mevv.myframe.common.base.FragmentId;
import com.mevv.myframe.common.util.SceneManager;

public class MainActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
       /* Api.getObject(ApiFactory.INSTANCE.getObjectApi().getBannerList(ParamsHelper.getBasicMap())
                , this
                , true
                , new VCallback<List<BannerListBean>>() {
                    @Override
                    public void onSuccess(List<BannerListBean> result) {
                        TipUtil.showToast("数据请求成功");
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        TipUtil.showToast(e.getMessage());
                    }
                });*/
    }

    public void one(View view){
    }

    public void two(View view){
        Bundle bundle = new Bundle();
        bundle.putInt(CommonFragmentActivity.FRAG_ID, FragmentId.ONE_FRAGMENT);
        SceneManager.toScene(this, CommonFragmentActivity.class, bundle);
    }
}
