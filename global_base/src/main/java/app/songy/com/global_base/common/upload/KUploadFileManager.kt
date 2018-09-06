package app.songy.com.global_base.common.upload

import app.songy.com.global_base.R
import app.songy.com.global_base.common.http.KBaseManager
import app.songy.com.global_base.common.http.KHttpCallBack
import java.io.File

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */
class KUploadFileManager : KBaseManager() {

    fun uploadFile(file:File,callBack: KHttpCallBack){
        var param=HashMap<String,Any>()
        param.put("fileData",file)
        upload(getHostUrl(R.string.login_post),param,callBack.mCallback)
    }
}