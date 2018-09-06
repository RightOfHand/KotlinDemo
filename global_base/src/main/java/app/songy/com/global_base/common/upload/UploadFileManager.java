package app.songy.com.global_base.common.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import app.songy.com.global_base.R;
import app.songy.com.global_base.common.http.BaseManager;
import app.songy.com.global_base.common.http.HttpCallBack;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public class UploadFileManager extends BaseManager {
    public static void uploadFile(File file, HttpCallBack callBack){
        Map<String,Object> param=new HashMap<>();
        param.put("fileData",file);
        upload(getHostUrl(R.string.login_post),param,callBack.mCallback);
    }
}
