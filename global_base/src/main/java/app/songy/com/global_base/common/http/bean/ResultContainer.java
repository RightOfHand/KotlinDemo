package app.songy.com.global_base.common.http.bean;

import java.io.Serializable;

/**
 * Description: response 数据结构
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

public class ResultContainer<T> implements Serializable {
    private T data;
    private int code;
    private boolean success;
    //服务商端回提示结构
    private ActionSheet actionSheet;
    //本地定义的  failureResponse msg
    private String msg;

    public ResultContainer() {
    }

    public ResultContainer(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ActionSheet getActionSheet() {
        return actionSheet;
    }

    public void setActionSheet(ActionSheet actionSheet) {
        this.actionSheet = actionSheet;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
