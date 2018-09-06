package app.songy.com.global_base.common.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;

/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:45
 */
public interface IParse {
    String toJson(@NonNull Object bean);

    @Nullable
    <T> T fromJson(@NonNull String json, @NonNull Class<T> clazz);

    @Nullable
    <T> T fromJson(@NonNull String json, @NonNull Type type);
}
