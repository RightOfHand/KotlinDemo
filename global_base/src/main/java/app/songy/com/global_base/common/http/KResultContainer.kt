package app.songy.com.global_base.common.http

import java.io.Serializable

import app.songy.com.global_base.common.http.bean.ActionSheet

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */

class KResultContainer<T> : Serializable {
    var data: T? = null
    var code: Int = 0
    var isSuccess: Boolean = false
    //服务商端回提示结构
    var actionSheet: ActionSheet? = null
    //本地定义的  failureResponse msg
    var msg: String? = null

    constructor() {}

    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }
}
