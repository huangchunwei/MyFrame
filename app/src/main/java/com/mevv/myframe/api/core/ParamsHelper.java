package com.mevv.myframe.api.core;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by VV on 2016/10/14.
 */

public class ParamsHelper {
    public static final String TAG = "ParamsHelper";
    public static Gson sGson;
    private static LinkedHashMap<String, String> sMap;

    static {
        sGson = new Gson();
    }

    public static Map getBasicMap() {
        sMap = new LinkedHashMap<>();
        //TODO 添加自己的必须参数
        sMap.put("token", "5d01321caea90573a25cd075ba440d6a");
        sMap.put("secret_key", "rA21VeE8347bScsuIDNq");
        sMap.put("adcode", "440300");
        sMap.put("user_type", "1");
        return sMap;
    }

    public static String generateUrlForGet(@NonNull String baseUrl, @NonNull Map<String, String> params) {
        if (TextUtils.isEmpty(baseUrl) || params == null)
            new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl + "?");
        for (String key : params.keySet())
            sb.append(key + "=" + params.get(key) + "&");
        return sb.substring(0, sb.length() - 1);
    }


    public static class Builder {
        private LinkedHashMap<String, String> mMap;
        //是否添加用户类型参数
        private boolean isAddUserType = false;

        public Builder(boolean isAddUserType) {
            this.isAddUserType = isAddUserType;
            mMap = new LinkedHashMap<>();
            initBaseMap();
        }

        public Builder() {
            mMap = new LinkedHashMap<>();
            initBaseMap();
        }

        public Builder put(String key, String value) {
            mMap.put(key, value);
            return this;
        }

        public Map<String, String> build() {
            return mMap;
        }

        private void initBaseMap() {

            mMap.put("token", "5d01321caea90573a25cd075ba440d6a");
            mMap.put("secret_key", "rA21VeE8347bScsuIDNq");
            /*if (isAddUserType) {
                if (isAddUserType) {
                    // 工号进入
                    mMap.put("user_type", "2");
                } else {
                    // 非工号进入
                    mMap.put("user_type", "1");
                }
            }*/
        }
    }
}
