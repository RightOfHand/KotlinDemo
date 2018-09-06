package app.songy.com.global_base.common.http.bean;

/**
 * Description: 返回码
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

public interface ResultCode {
    /**
     * 登录异常
     */
    int TOKEN_ERROR = -1999;
    /**
     * 接口提示APP升级
     */
    int ERROR_1998 = -1998;
    /**
     * 清除缓存的登录信息
     */
    int TOKEN_CLEAR = -1997;

    /**
     *  请求成功code
     * */
    int RESULT_OLD_SUCCESS_CODE=100;
    /**
     * 未知
     * */
    int CODE_ERROR_UN_KNOW = -1;

    int CODE_IO= 500;//网络请求异常
}
