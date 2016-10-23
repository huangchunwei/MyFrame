package com.mevv.myframe.common.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mevv.myframe.BaseApplication;


/**
 * 配置信息保存工具类
 * Created by Wayne on 2016/7/21.
 */
public class SPUtil {

    private static final String DEF_STR_VALUE = "";
    private static final int DEF_INT_VALUE = -100;
    private static final long DEF_LONG_VALUE = -110l;
    private static final boolean DEF_BOOLEAN_VALUE = false;
    private static final float DEF_FLOAT_VALUE = -120f;


    public static final String FILE_NAME = "SPDatas";

    private final static int SP_MODE = Context.MODE_PRIVATE;

    private static Application sApplication;

    public static SharedPreferences getSP(Application application, String name) {
        if (application == null)
            application = BaseApplication.getApplication();
        return application.getSharedPreferences(name, SP_MODE);
    }

    //String
    public static void putString(String key, String value) {
        if (key == null)
            return;
        getSP(sApplication, FILE_NAME).edit().putString(key, value.trim()).commit();
    }

    public static String getString(String key) {
        if (key == null)
            return DEF_STR_VALUE;
        return getSP(sApplication, FILE_NAME).getString(key, "");
    }

    public static String getString(String key, String defValue) {
        if (key == null)
            return DEF_STR_VALUE;
        return getSP(sApplication, FILE_NAME).getString(key, defValue);
    }

    //int
    public static void putInt(String key, int value) {
        if (key == null)
            return;
        getSP(sApplication, FILE_NAME).edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        if (key == null)
            return DEF_INT_VALUE;
        return getSP(sApplication, FILE_NAME).getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        if (key == null)
            return DEF_INT_VALUE;
        return getSP(sApplication, FILE_NAME).getInt(key, defValue);
    }

    //boolean
    public static void putBoolean(String key, boolean value) {
        if (key == null)
            return;
        getSP(sApplication, FILE_NAME).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        if (key == null) {
            return DEF_BOOLEAN_VALUE;
        }
        return getSP(sApplication, FILE_NAME).getBoolean(key, DEF_BOOLEAN_VALUE);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (key == null) {
            return DEF_BOOLEAN_VALUE;
        }
        return getSP(sApplication, FILE_NAME).getBoolean(key, defValue);
    }

    //long
    public static void putLong(String key, long value) {
        if (key == null) {
            return;
        }
        getSP(sApplication, FILE_NAME).edit().putLong(key, value).commit();
    }

    public static long getLong(String key) {
        if (key == null) {
            return DEF_LONG_VALUE;
        }
        return getSP(sApplication, FILE_NAME).getLong(key, DEF_LONG_VALUE);
    }

    public static long getLong(String key, long defValue) {
        if (key == null) {
            return DEF_LONG_VALUE;
        }
        return getSP(sApplication, FILE_NAME).getLong(key, defValue);
    }

    public static void putFloat(String key, float value) {
        if (key == null) {
            return;
        }
        getSP(sApplication, FILE_NAME).edit().putFloat(key, value).commit();
    }

    public static float getFloat(String key) {
        if (key == null) {
            return DEF_FLOAT_VALUE;
        }
        return getSP(sApplication, FILE_NAME).getFloat(key, DEF_FLOAT_VALUE);
    }

    public static float getFloat(String key, float defValue) {
        if (key == null) {
            return DEF_FLOAT_VALUE;
        }
        return getSP(sApplication, FILE_NAME).getFloat(key, defValue);
    }

    public static Object getValue(String key, Object type) {
        if (key == null) {
            return null;
        }
        Object value = null;
        SharedPreferences sp = getSP(sApplication, FILE_NAME);
        if (type instanceof String) {
            value = sp.getString(key, DEF_STR_VALUE).trim();
        } else if (type instanceof Integer) {
            value = sp.getInt(key, DEF_INT_VALUE);
        } else if (type instanceof Float) {
            value = sp.getFloat(key, DEF_FLOAT_VALUE);
        } else if (type instanceof Long) {
            value = sp.getLong(key, DEF_LONG_VALUE);
        } else if (type instanceof Boolean) {
            value = sp.getBoolean(key, DEF_BOOLEAN_VALUE);
        }
        return value;
    }

    public static void putValue(String key, Object value) {
        SharedPreferences.Editor editor = getSP(sApplication, FILE_NAME).edit();
        if (value instanceof String) {
            editor.putString(key, ((String) value).trim());
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }

    public static void clearAll(){
        getSP(sApplication, FILE_NAME).edit().clear().commit();
    }

    public static void removeValue(String key) {
        if (key == null) {
            return;
        }
        getSP(sApplication, FILE_NAME).edit().remove(key).commit();
    }
}
