package app.songy.com.global_base.common.http

import app.songy.com.global_base.common.helper.AppHelper

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */


open  class KBaseManager : KBaseHttpRequest() {
    companion object {
        private val urlAddress = "http://172.16.12.95:8190/"
        /**
         * todo 获取URL
         */
        fun getHostUrl(resId: Int): String {
            return urlAddress + AppHelper.getResources().getString(resId)
        }
    }

}
