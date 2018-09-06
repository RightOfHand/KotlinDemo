package app.songy.com.global_base.common.helper;

import android.os.AsyncTask;


import java.io.File;

import app.songy.com.global_base.common.base.BaseActivity;
import app.songy.com.global_base.common.file.FileUtils;
import app.songy.com.global_base.component.widget.progress.Progress;

/**
 * Created by Administrator on 2017/05/05.
 * Copyright © 2016年 AXD. All rights reserved.
 */

public class CompressImageUtils {
    private File file = null;

    public void CompressImage(BaseActivity activity, String oldPath, String newPath, CompressCallBack callBack) {
        CompressTask task = new CompressTask(activity, oldPath, newPath, callBack);
        task.execute();
    }

    class CompressTask extends AsyncTask<Void, Integer, Integer> {
        private BaseActivity activity;
        private String oldPath, newPath;
        private CompressCallBack callBack;

        CompressTask(BaseActivity activity, String oldPath, String newPath, CompressCallBack callBack) {
            this.activity = activity;
            this.oldPath = oldPath;
            this.newPath = newPath;
            this.callBack = callBack;
        }


        @Override
        protected void onPreExecute() {
            if (activity != null) {
                Progress.show(activity, "压缩中...");
            }
        }


        @Override
        protected Integer doInBackground(Void... params) {
            file = FileUtils.compressFile(oldPath, newPath);
            return null;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            if (activity != null) {
                Progress.dismiss();
            }
            callBack.success(file);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }


    public interface CompressCallBack {
        void success(File file);
    }
}
