package app.songy.com.global_base.common.http

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

import java.io.IOException

import app.songy.com.global_base.common.constants.Logs
import app.songy.com.global_base.common.helper.AppHelper
import app.songy.com.global_base.common.helper.ToastHelper
import app.songy.com.global_base.common.http.bean.ActionSheet
import app.songy.com.global_base.common.http.bean.ResultCode
import app.songy.com.global_base.common.http.bean.ResultContainer
import app.songy.com.global_base.common.manager.ActivityManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */

open class KHttpCallBack(private val typeReference: TypeReference<*>) {
    private var showError = true
    var mCallback: Callback = object : Callback {
        override fun onFailure(call: Call?, e: IOException) {
            if (call == null)
                return
            if (call.isCanceled) {
                return
            }
            Logs.network.e("Response %s \nfail %s", call.request().url(), e.toString())
            val result = ResultContainer<Any>(ResultCode.CODE_ERROR_UN_KNOW, "请求异常")
            postResult(result)
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response?) {

            if (response?.body() == null) {
                val result = ResultContainer<Any>(ResultCode.CODE_ERROR_UN_KNOW, "请求失败")
                postResult(result)
                return
            }
            val responseString = response.body().string()
            var result: ResultContainer<*>? = null
            if (call.isCanceled) {
                return
            }
            showLog(responseString, call)
            try {
                if (!TextUtils.isEmpty(responseString)) {
                    Logs.network.d("Response   %s", responseString)
                    val jsonObject = JSON.parseObject(responseString)
                    if (jsonObject != null) {
                        result = jsonObject.getObject("result", ResultContainer::class.java)
                    }

                } else {
                    Logs.network.e("Response   %s", call.request().url().toString() + " fail")
                    result = ResultContainer<Any>(ResultCode.CODE_ERROR_UN_KNOW, "请求失败")
                    postResult(result)
                    return
                }

            } catch (e: Exception) {
                Logs.network.e(e, "Response %s", call.request().url().toString() + " fail")
                result = ResultContainer<Any>(ResultCode.CODE_ERROR_UN_KNOW, "请求异常")
            }

            postResult(result)
        }
    }


    fun postResult(result: ResultContainer<*>?) {
        HANDLER_MAIN.post { postResultRun(result) }

    }

    private fun postResultRun(result: ResultContainer<*>?) {
        if (result != null && result.isSuccess) {
            showActionSheet(result.actionSheet, result.code)
            this@KHttpCallBack.onResponse(result)
        } else {
            this@KHttpCallBack.onFailure(result)
        }
        this@KHttpCallBack.onFinish()
    }

    open fun onFailure(result: ResultContainer<*>?) {
        if (showActionSheet(result!!.actionSheet, result.code)) {
            return
        }
        val msg: String
        if (result.actionSheet != null) {
            msg = result.actionSheet.message
        } else {
            msg = result.msg
        }
        val context = AppHelper.getContext()
        if (!TextUtils.isEmpty(msg)) {
            if (context != null) {
                ToastHelper.makeImgToast(msg)
            }
        }
    }

    //新的提示结构,包括toast和dialog
    private fun showActionSheet(sheet: ActionSheet?, resultCode: Int): Boolean {
        if (sheet == null) {
            return false
        }
        val context = AppHelper.getContext()
        var isShow = false
        when (sheet.tip) {
            ActionSheet.TOAST_HINT -> {
                Logs.network.e(JSON.toJSONString(sheet))
                if (context != null) {
                    ToastHelper.makeToast(sheet.message)
                }
                isShow = true
            }
            ActionSheet.DIALOG_HINT -> {
                val activity = ActivityManager.last()
                if (activity != null) {
                    //dialog show
                }
                isShow = true
            }
        }
        return isShow


    }

   open fun onResponse(result: ResultContainer<*>) {

    }


   open fun onFinish() {}

    private fun showLog(responseStr: String, call: Call) {
        if (responseStr.length > 3000) {
            var i = 0
            while (i < responseStr.length) {
                val last = if (i + 3000 >= responseStr.length) responseStr.length else i + 3000
                val s1 = responseStr.substring(i, last)
                if (i == 0) {
                    Log.d("Response %s \nResult %s", call.request().url().toString() + s1)
                } else {
                    println(s1)
                }
                i = last
            }
        } else {
            Log.d("Response %s \nResult  %s", call.request().url().toString() + responseStr)
        }

    }

    companion object {

        private val HANDLER_MAIN = Handler(Looper.getMainLooper())
    }
}
