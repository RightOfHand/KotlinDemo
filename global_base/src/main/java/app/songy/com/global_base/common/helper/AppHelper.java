package app.songy.com.global_base.common.helper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.DimenRes;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import app.songy.com.global_base.common.base.IApp;

/**
 * 系统级别的工具类，业务的一些公用功能
 * Created by yh on 2016/4/8.
 */
public class AppHelper {


    private static IApp app;

    public static SSLSocketFactory getSocketFactory(AssetManager assets) {

        List<InputStream> certificates = getStream(assets);
        if (certificates == null || certificates.isEmpty()) {
            return null;
        }
        SSLSocketFactory sslSocketFactory = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias,
                        certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
    private static List<InputStream> getStream(AssetManager assets) {
        List<InputStream> list = new ArrayList<>();
//        if (!Configs.RELEASE || switchCheck) {

        return list;
    }
    public static SSLSocketFactory getSocketFactory(InputStream... certificates) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias,
                        certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }


    public static void init(IApp app) {
        if (app == null) throw new NullPointerException("IApp can not null");
        AppHelper.app = app;
    }

    /**
     * 检测应用是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 检测应用是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isApkInstalled(String packageName) {
        return isApkInstalled(getContext(), packageName);
    }


    /**
     * 资源ID获取String
     */
    public static String getString(int stringId) {
        return getContext().getString(stringId);
    }

    public static String getString(int stringId, Object... formatArgs) {
        return getContext().getString(stringId, formatArgs);
    }

    public static int getDimen(@DimenRes int dimen) {
        return getContext().getResources().getDimensionPixelOffset(dimen);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Context getContext() {

        return app.getContext();
    }

    public static long getStartTime() {
        return app.getStartTime();
    }



}
