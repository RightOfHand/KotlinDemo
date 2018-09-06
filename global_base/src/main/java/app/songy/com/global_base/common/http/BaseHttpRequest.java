package app.songy.com.global_base.common.http;

import com.alibaba.fastjson.JSON;


import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.songy.com.global_base.common.constants.Logs;
import app.songy.com.global_base.common.lang.Strings;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description:
 * Created by song on 2018/6/5.
 * email：bjay20080613@qq.com
 */

public abstract class BaseHttpRequest {
    private static OkHttpClient client=null;

    /**
     *Description:
     *creator: song
     *Date: 2018/6/7 下午4:26
     * @param url  地址
     * @param parameters  请求参数
     * @param callback   请求回调
     */
    protected static Call postNav(String url, Map<String, Object> parameters, Callback callback) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("request", parameters);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(params));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        Logs.network.d("request %s",JSON.toJSONString(request));
        Call call = getClient().newCall(request);
        call.enqueue(callback);
        return call;
    }
    /**
     *Description: 图片上传
     *creator: song
     *Date: 2018/6/7 下午4:21
     */
    protected static Call upload(String hostUrl,Map<String,Object> params,Callback callback) {

        Request.Builder requestBuilder = new Request.Builder();
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();

        for (Map.Entry<String, Object> pair : params.entrySet()) {
            Object value = pair.getValue();
            //以File上传
            if (value instanceof File) {
                File file = (File) value;
                MediaType PNG = MediaType.parse("image/*");
                requestBodyBuilder.setType(MultipartBody.FORM);
                // 第二个参数为  文件名称   可以定义为file的绝对路径 做为文件名称 ((File) value).getAbsolutePath()

                requestBodyBuilder.addFormDataPart(pair.getKey(),"upload.jpg",
                        RequestBody.create(PNG, file));
            } else if (value instanceof byte[]) {
                //字节流上传
                MediaType stream = MediaType.parse("application/octet-stream; charset=utf-8");
                requestBodyBuilder.addFormDataPart(pair.getKey(), pair.getKey(),
                        RequestBody.create(stream, (byte[]) value));
            } else {
                requestBodyBuilder.addFormDataPart(pair.getKey(), value == null ? Strings.EMPTY :
                        value.toString());
            }
        }
        requestBuilder.post(requestBodyBuilder.build());
        //添加request header
//        requestBuilder.addHeader("User-Agent",iRequest.getUA());

        Request request = requestBuilder
                .url(hostUrl)
                .build();



        Call call = getClient().newCall(request);
        call.enqueue(callback);
        return call;
    }
    /**
     * TODO 获取OkHttpClient
     *
     * @return OkHttpClient
     */
    private static synchronized OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS).build();
        }
        return client;
    }

    public static void cancelAll() {
         getClient().dispatcher().cancelAll();
    }
}
