package app.songy.com.login.manager

import app.songy.com.global_base.common.http.*
import app.songy.com.login.R
import java.util.*

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */
class KLoginManager : KBaseManager(){

    fun login(account: String, pwd: String, callBack: KHttpCallBack) {
        val param = HashMap<String,Any>()
        param["userAccount"] = account
        param["userPassword"] = pwd
        postNav(getHostUrl(R.string.login_post),param,callBack.mCallback)

    }

    fun register(account: String, pwd: String, callBack: KHttpCallBack){
        val param = HashMap<String,Any>()
        param["userAccount"] = account
        param["userPassword"] = pwd
        postNav(getHostUrl(R.string.login_post),param,callBack.mCallback)
    }

}