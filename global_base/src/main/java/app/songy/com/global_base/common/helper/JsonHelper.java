package app.songy.com.global_base.common.helper;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;

import app.songy.com.global_base.common.listener.IParse;

/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:45
 */
public class JsonHelper {
    private static IParse iParse;

    public static void init(IParse parse) {
        iParse = parse;
    }

    public static String toJson(@NonNull Object bean) {
        return iParse.toJson(bean);
    }

    @Nullable
    public static <T> T fromJson(@NonNull String json, @NonNull Class<T> clazz) {
        return iParse.fromJson(json, clazz);
    }

    @Nullable
    public static <T> T fromJson(@NonNull String json, @NonNull Type type) {
        return iParse.fromJson(json, type);
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference){
        return JSON.parseObject(json,typeReference);
    }

}
