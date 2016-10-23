package com.mevv.myframe.api.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by VV on 2016/10/14.
 * 返回结果为String
 */

public interface StringApi {

    @GET("{url}")
    Call<String> getStringInfoGet(@Path("url") String url, @QueryMap Map<String, String> maps);

    @POST("{url}")
    Call<String> getStringInfoPost(@Path("url") String url, @QueryMap Map<String, String> maps);
}
