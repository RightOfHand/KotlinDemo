package app.songy.com.global_base.common.http

import com.alibaba.fastjson.JSON

import java.io.File
import java.util.LinkedHashMap
import java.util.concurrent.TimeUnit

import app.songy.com.global_base.common.constants.Logs
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */

open class KBaseHttpRequest {
    private var client: OkHttpClient? = null

    init {

    }


    /**
     * Description:
     * creator: song
     * Date: 2018/6/7 下午4:26
     * @param url  地址
     * @param parameters  请求参数
     * @param callback   请求回调
     */
    fun postNav(url: String, parameters: Map<String, Any>, callback: Callback): Call {
        val params = LinkedHashMap<String, Any>()
        params.put("request", parameters)
        val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(params))
        val request = Request.Builder()
                .post(requestBody)
                .url(url)
                .build()
        Logs.network.d("request %s", JSON.toJSONString(request))
        val call = getClient().newCall(request)
        call.enqueue(callback)
        return call
    }

    /**
     * Description: 图片上传
     * creator: song
     * Date: 2018/6/7 下午4:21
     */
     fun upload(hostUrl: String, params: Map<String, Any>, callback: Callback): Call {

        val requestBuilder = Request.Builder()
        val requestBodyBuilder = MultipartBody.Builder()

        for ((key, value) in params) {

            when (value) {
                is File -> {
                    //以File上传
                    val fileType = MediaType.parse("image/*")
                    requestBodyBuilder.setType(MultipartBody.FORM)
                    // 第二个参数为  文件名称   可以定义为file的绝对路径 做为文件名称 ((File) value).getAbsolutePath()
                    requestBodyBuilder.addFormDataPart(key, "upload.jpg",
                            RequestBody.create(fileType, value))
                }
                is ByteArray -> {
                    //字节流上传
                    val stream = MediaType.parse("application/octet-stream; charset=utf-8")
                    requestBodyBuilder.addFormDataPart(key, key,
                            RequestBody.create(stream, value))
                }
                else -> requestBodyBuilder.addFormDataPart(key, value?.toString())
            }
        }
        requestBuilder.post(requestBodyBuilder.build())
        //添加request header
        //        requestBuilder.addHeader("User-Agent",iRequest.getUA());

        val request = requestBuilder
                .url(hostUrl)
                .build()


        val call = getClient().newCall(request)
        call.enqueue(callback)
        return call
    }

    /**
     * TODO 获取OkHttpClient
     *
     * @return OkHttpClient
     */
    @Synchronized private fun getClient(): OkHttpClient {
        if (client == null) {
            client = OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS).build()
        }
        return this.client!!
    }

    fun cancelAll() {
        getClient().dispatcher().cancelAll()
    }
}
