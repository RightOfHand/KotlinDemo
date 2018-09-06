package app.songy.com.global_base.common.upload;

import java.io.File;

/**
 *Description:
 *creator: song
 *Date: 2018/6/12 上午10:41
 */
public class UploadFile {

    private static final int ADD_FLAG_VALUE = -2;
    private static final int FAILED_VALUE = -1;


    public final static UploadFile ADD_FLAG = new UploadFile(null,ADD_FLAG_VALUE);

    private File file;
    private String remoteUrl;
    private String objectId; //oss objectId
    private int uploadProgress;  //-1失败 100成功

    public UploadFile() {
    }

    public static UploadFile obtainFailed(File file){
        return new UploadFile(file,FAILED_VALUE);
    }

    public UploadFile(File file) {
        this.file = file;
    }

    public UploadFile(File file, int uploadProgress) {
        this.file = file;
        this.uploadProgress = uploadProgress;
    }

    public boolean isFailed(){
        return uploadProgress == FAILED_VALUE;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getUploadProgress() {
        return uploadProgress;
    }

    public void setUploadProgress(int uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadFile)) {
            return false;
        }

        UploadFile that = (UploadFile) o;

        return file.equals(that.file);

    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}
