package com.mevv.myframe.bean;

/**
 * Created by VV on 2016/9/9.
 * 首页轮播列表bean
 */
public class BannerListBean {
    public String img;//图片地址
    public String url;//跳转地址
    public String name;//名称


    @Override
    public String toString() {
        return "BannerListBean{" +
                "img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
