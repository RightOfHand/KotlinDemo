package app.songy.com.global_base.common.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */

public class Util {
    /**
     * @param ts 原有类型
     * @return List<T> 返回类型
     * TODO(对集合的非空验证，避免异常)
     */
    public static <T> List<T> cutNull(List<T> ts) {
        List<T> list = (ts != null ? ts : new ArrayList<T>());
        Iterator<T> t = list.iterator();
        while (t.hasNext()) {
            if (t.next() == null) {
                t.remove();
            }
        }
        return list;
    }

    /**
     * @param ts 数据源
     * @return List<T> 返回类型
     * TODO(对集合的非空验证,避免异常)
     */
    public static <T> Set<T> cutNull(Set<T> ts) {
        Set<T> set = (ts != null ? ts : new HashSet<T>());
        Iterator<T> t = set.iterator();
        while (t.hasNext()) {
            if (t.next() == null) {
                t.remove();
            }
        }
        return set;
    }

    public static String cutNullToZero(String str) {
        return TextUtils.isEmpty(str) ? "0" : str;
    }

    public static <T> List<T> addAll(List<T> ta, List ts) {
        for (Object a : ts) {
            ta.add((T) a);
        }
        return ta;
    }

    public static String cutNull(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    public static String cutEmpty(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replace(" ", "");
    }
    /**
     * MD5 32位小写
     */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
