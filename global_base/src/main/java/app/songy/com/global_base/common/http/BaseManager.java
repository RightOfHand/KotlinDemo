package app.songy.com.global_base.common.http;

import java.util.HashMap;

import app.songy.com.global_base.common.helper.AppHelper;

/**
 * Description:
 * Created by song on 2018/6/6.
 * email：bjay20080613@qq.com
 */

public class BaseManager extends BaseHttpRequest{
    private static final String urlAddress="http://172.16.12.95:8190/";
    /**
     * todo 获取URL
     */
    protected static String getHostUrl(int resId) {

        return urlAddress+ AppHelper.getResources().getString(resId);
    }
    protected static HashMap<String, Object> getHashMap() {
        return new HashMap<>();
    }
}
