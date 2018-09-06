package app.songy.com.global_base.component.imageClip;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 *Description:
 *creator: song
 *Date: 2018/6/12 上午11:06
 */
public class IOUtils {
    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.delete();
    }
}
