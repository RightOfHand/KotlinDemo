package app.songy.com.global_base.common.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;

import app.songy.com.global_base.common.constants.Logs;
import app.songy.com.global_base.common.constants.SpKeys;
import app.songy.com.global_base.common.lang.Strings;

/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:43
 */
public class SPHelper {
    private static SharedPreferences sharedPreferences;
    /**
     * @serialField 保存在手机里面的文件名
     */
    private static final String FILE_NAME = SpKeys.FILE_NAME;

    public static String getString(String key) {
        return getPrefs().getString(key, Strings.EMPTY);
    }

    public static String getString(String key, String value) {
        return getPrefs().getString(key, value);
    }

    public static void putString(String key, String value) {
        getPrefs().edit().putString(key, value).apply();
    }

    public static int getInt(String key) {
        return getPrefs().getInt(key, 0);
    }

    public static long getLong(String key) {
        return getPrefs().getLong(key, 0);
    }

    public static void putLong(String key, long value) {
        getPrefs().edit().putLong(key, value).apply();
    }

    public static void putInt(String key, int value) {
        getPrefs().edit().putInt(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getPrefs().getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return getPrefs().getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).apply();
    }

    public static void putBean(String key, Object bean) {
        getPrefs().edit().putString(key, JsonHelper.toJson(bean)).apply();
    }

    public static <T> T getBean(String key, Class<T> clazz) {
        String json = getPrefs().getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JsonHelper.fromJson(json, clazz);
    }

    public static <T> T getBean(String key, Type type) {
        try {
            String json = getPrefs().getString(key, null);
            Logs.defaults.d("SPHelper-getBean:%s", json);
            if (TextUtils.isEmpty(json)) {
                return null;
            }
            return JsonHelper.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> T getBean(String key, TypeReference<T> type) {
        try {
            String json = getPrefs().getString(key, null);
            Logs.defaults.d("SPHelper-getBean:%s", json);
            if (TextUtils.isEmpty(json)) {
                return null;
            }
            return JsonHelper.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void remove(String key) {
        getPrefs().edit().remove(key).apply();
    }

    private static SharedPreferences getPrefs() {
        if (sharedPreferences == null) {
            sharedPreferences = AppHelper.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
