package app.songy.com.global_base.common.http;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;

import app.songy.com.global_base.common.constants.Logs;
import app.songy.com.global_base.common.helper.AppHelper;
import app.songy.com.global_base.common.helper.ToastHelper;
import app.songy.com.global_base.common.manager.ActivityManager;
import app.songy.com.global_base.common.http.bean.ActionSheet;
import app.songy.com.global_base.common.http.bean.ResultCode;
import app.songy.com.global_base.common.http.bean.ResultContainer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Description:
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

public class HttpCallBack {

    final private TypeReference typeReference;
    private boolean showError = true;

    private static Handler HANDLER_MAIN = new Handler(Looper.getMainLooper());

    public Callback mCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            if (call == null)
                return;
            if (call != null && call.isCanceled()) {
                return;
            }
            Logs.network.e("Response %s \nfail %s", call.request().url(), e.toString());
            ResultContainer result = new ResultContainer(ResultCode.CODE_ERROR_UN_KNOW, "请求异常");
            postResult(result);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            if (response == null || response.body() == null) {
                ResultContainer result = new ResultContainer(ResultCode.CODE_ERROR_UN_KNOW, "请求失败");
                postResult(result);
                return;
            }
            String responseString = response.body().string();
            ResultContainer result = null;
            if (call.isCanceled()) {
                return;
            }
            showLog(responseString, call);
            try {
                if (!TextUtils.isEmpty(responseString)) {
                    Logs.network.d("Response   %s", responseString);
                    JSONObject jsonObject = JSON.parseObject(responseString);
                    if (jsonObject != null) {
                        result = jsonObject.getObject("result",ResultContainer.class);
                    }

                } else {
                    Logs.network.e("Response   %s", call.request().url() + " fail");
                    result = new ResultContainer(ResultCode.CODE_ERROR_UN_KNOW, "请求失败");
                    postResult(result);
                    return;
                }

            } catch (Exception e) {
                Logs.network.e(e, "Response %s", call.request().url() + " fail");
                result = new ResultContainer(ResultCode.CODE_ERROR_UN_KNOW, "请求异常");
            }
            postResult(result);
        }
    };

    public HttpCallBack(TypeReference typeReference) {
        this.typeReference = typeReference;
    }


    public void postResult(final ResultContainer result) {
        HANDLER_MAIN.post(new Runnable() {
            @Override
            public void run() {
                postResultRun(result);
            }
        });

    }

    private void postResultRun(ResultContainer result) {
        if ((result != null && result.isSuccess())) {
            showActionSheet(result.getActionSheet(), result.getCode());
            HttpCallBack.this.onResponse(result);
        } else {
            HttpCallBack.this.onFailure(result);
        }
        HttpCallBack.this.onFinish();
    }

    public void onFailure(ResultContainer result) {
        if (showActionSheet(result.getActionSheet(), result.getCode())) {
            return;
        }
        String msg;
        if (result.getActionSheet() != null) {
            msg = result.getActionSheet().getMessage();
        } else {
            msg = result.getMsg();
        }
        Context context = AppHelper.getContext();
        if (!TextUtils.isEmpty(msg)) {
            if (context != null) {
                ToastHelper.makeImgToast(msg);
            }
        }
    }

    //新的提示结构,包括toast和dialog
    private boolean showActionSheet(ActionSheet sheet, int resultCode) {
        if (sheet == null) {
            return false;
        }
        Context context = AppHelper.getContext();
        boolean isShow = false;
        switch (sheet.getTip()) {
            case ActionSheet.TOAST_HINT:
                Logs.network.e(JSON.toJSONString(sheet));
                if (context != null) {
                    ToastHelper.makeToast(sheet.getMessage());
                }
                isShow = true;
                break;
            case ActionSheet.DIALOG_HINT:
                Activity activity = ActivityManager.last();
                if (activity != null) {
                    //dialog show
                }
                isShow = true;
                break;
        }
        return isShow;


    }


    public void onResponse(ResultContainer result) {

    }


    public void onFinish() {
    }

    private void showLog(String responseStr, Call call) {
        if (responseStr.length() > 3000) {
            for (int i = 0; i < responseStr.length(); ) {
                int last = i + 3000 >= responseStr.length() ? responseStr.length() : i + 3000;
                String s1 = responseStr.substring(i, last);
                if (i == 0) {
                    Log.d("Response %s \nResult %s", call.request().url()+s1);
                } else {
                    System.out.println(s1);
                }
                i = last;
            }
        } else {
            Log.d("Response %s \nResult  %s", call.request().url()+responseStr);
        }

    }
}
