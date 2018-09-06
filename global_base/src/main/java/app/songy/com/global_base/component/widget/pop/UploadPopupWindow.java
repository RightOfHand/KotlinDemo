package app.songy.com.global_base.component.widget.pop;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;


import com.alibaba.fastjson.TypeReference;

import java.io.File;

import app.songy.com.global_base.R;
import app.songy.com.global_base.common.base.BaseActivity;
import app.songy.com.global_base.common.file.FileUtils;
import app.songy.com.global_base.common.helper.CompressImageUtils;
import app.songy.com.global_base.common.helper.ToastHelper;
import app.songy.com.global_base.common.helper.UIHelper;
import app.songy.com.global_base.common.http.HttpCallBack;
import app.songy.com.global_base.common.http.bean.ResultContainer;
import app.songy.com.global_base.common.upload.UploadFile;
import app.songy.com.global_base.common.upload.UploadFileManager;
import app.songy.com.global_base.common.util.CaptureCropUtil;
import app.songy.com.global_base.common.util.CheckPermissionUtils;
import app.songy.com.global_base.component.widget.progress.Progress;

/**
 *Description: 图片上传 pop
 *creator: song
 *Date: 2018/6/12 上午10:12
 */

public class UploadPopupWindow extends PopupWindow implements OnClickListener, BaseActivity.ActivityResultCallback {

    private static final int REQ_CAMERA = 1;
    private static final int REQ_ALBUM = 2;
    private Button  mCamera, mCancel, mAlbum;
    private String imgUrl = "";
    private File mImagePath;
    private BaseActivity activity;
    private UploadCallBack callBack;
    private String newPath = "";
    private boolean cropAble = false;
    private int aspectX = 1;
    private int aspectY = 1;
    private CaptureCropUtil cropUtil;

    public UploadPopupWindow(BaseActivity activity) {
        this.activity = activity;
        cropUtil = new CaptureCropUtil(activity);
        showUploadWindow();
        newPath = FileUtils.getCachePath(activity) + FileUtils.getImgName(".jpg");
    }

    public void setCallBack(UploadCallBack callBack) {
        this.callBack = callBack;
    }

    public void setCameraEnable(boolean cameraEnable) {
        if (mCamera != null) {
            mCamera.setVisibility(cameraEnable ? View.VISIBLE : View.GONE);
        }
    }

    public void setCropAble(boolean cropAble, int aspectX, int aspectY) {
        this.cropAble = cropAble;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
    }

    public void showUploadWindow() {
        View view = LayoutInflater.from(activity).inflate(R.layout.pop_upload_image, null);
        mCancel = (Button) view.findViewById(R.id.btn_cancel);
        mAlbum = (Button) view.findViewById(R.id.btn_album);
        mCamera = (Button) view.findViewById(R.id.btn_camera);
        setOutsideTouchable(false);

        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupBottomAnim);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.transparent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.8f;
        activity.getWindow().setAttributes(params);

        mCamera.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mAlbum.setOnClickListener(this);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 1f;
        activity.getWindow().setAttributes(params);
    }



    public void show(View parent) {
        showAtLocation(parent,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        activity.addActivityResultCallback(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
            if (id == R.id.btn_camera) {
            CheckPermissionUtils.checkCameraPermission(activity, new CheckPermissionUtils.PermissionRequestCallback() {
                @Override
                public void hasPermission() {
                    openCamera();
                }

                @Override
                public void noPermission() {

                }
            });

            dismiss();
        } else if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id == R.id.btn_album) {
            openAlbum();
            dismiss();
        }
    }

    private void openAlbum() {
        if (CheckPermissionUtils.hasStoragePermission(activity)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
            activity.startActivityForResult(intent, REQ_ALBUM);
        } else {
            CheckPermissionUtils.checkStoragePermission(activity, new CheckPermissionUtils.PermissionRequestCallback() {
                @Override
                public void hasPermission() {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");//相片类型
                    activity.startActivityForResult(intent, REQ_ALBUM);
                }

                @Override
                public void noPermission() {
                    ToastHelper.makeToast("请到系统设置里打开读取手机存储权限");
                }
            });
        }
    }

    /**
     * todo 打开照相机
     */
    private void openCamera() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mImagePath = new File(newPath);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(activity, "app.songy.com.kotlindemo.fileProvider", mImagePath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImagePath));
            }
            activity.startActivityForResult(intent, REQ_CAMERA);
        } else {
            ToastHelper.makeImgToast("缺少SD卡");
            destroy();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (callBack == null) {
                return;
            }
            switch (requestCode) {
                case REQ_CAMERA:
                    if (cropAble) {
                        toCropImage(newPath);
                    } else {
                        new CompressImageUtils().CompressImage(activity, newPath, newPath, new CompressImageUtils.CompressCallBack() {
                            @Override
                            public void success(File file) {
                                if (file != null) {
                                    processUpload(new UploadFile(file));
                                } else {
                                    callBack.onFail("图片获取异常");
                                }
                            }
                        });
                    }
                    break;

                case REQ_ALBUM:
                    if (data == null) {
                        return;
                    }
                    if (cropAble) {
                        toCropImage(findPath(data.getData()));
                    } else {
                        findPic(data.getData());
                    }
                    break;
                case CaptureCropUtil.REQUEST_CODE_RESIZE:
                    final File file = cropUtil.getTempCropImageFile();
                    new CompressImageUtils().CompressImage(activity, file.getAbsolutePath(), file.getAbsolutePath(), new CompressImageUtils.CompressCallBack() {
                        @Override
                        public void success(File file) {
                            if (file != null) {
                                processUpload(new UploadFile(file));
                            } else {
                                callBack.onFail("图片获取异常");
                            }
                        }
                    });
                    break;

                default:
            }
        }
    }
    private void toCropImage(String uri){
        int width = UIHelper.getDeviceWidth(activity);
        cropUtil.resizeImageNew(uri, aspectX, aspectY, width);
    }


    private String findPath(Uri originalUri){
        String path = originalUri.getPath();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(originalUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }

        return path;
    }

    private void findPic(Uri originalUri) {
        if (originalUri != null) {
            String path = findPath(originalUri);
            if (!TextUtils.isEmpty(path)) {
                new CompressImageUtils().CompressImage(activity, path, newPath, new CompressImageUtils.CompressCallBack() {
                    @Override
                    public void success(File file) {
                        if (file != null) {
                            processUpload(new UploadFile(file));
                        }
                    }
                });
            } else {
                callBack.onFail("图片获取异常");
            }
        }
    }

    /**
     * TODO 上传图片
     *
     * @param uploadFile upload
     */
    private void processUpload(final UploadFile uploadFile) {
        Progress.show(activity,"上传中...");
        final File file = uploadFile.getFile();
        UploadFileManager.uploadFile(file,new HttpCallBack(new TypeReference<String>(){}){
            @Override
            public void onResponse(ResultContainer result) {
                super.onResponse(result);
                if (callBack!=null) callBack.onSuccess(result.getData().toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Progress.dismiss();
            }
        });
    }

    public interface UploadCallBack {
        void onSuccess(String imgUrl);

        void onClear();

        void onFail(String error);
    }

    public void destroy() {
        activity.removeActivityResultCallback(this);
    }


}
