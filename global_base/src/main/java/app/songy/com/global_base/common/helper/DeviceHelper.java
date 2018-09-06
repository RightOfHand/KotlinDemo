package app.songy.com.global_base.common.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.UUID;



import app.songy.com.global_base.common.constants.SpKeys;
import app.songy.com.global_base.common.lang.Strings;
import app.songy.com.global_base.common.util.DateUtil;
import app.songy.com.global_base.common.util.Util;

import static android.telephony.TelephonyManager.NETWORK_TYPE_EHRPD;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.SIM_STATE_READY;

/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:37
 */
public class DeviceHelper {

    private final static String DEFAULT_MAC = "00:90:4c:11:22:33";
    private final static String[] INTERCEPTS_MAC = {
            "00:90:4c:11:22:33", "00:20:00:00:00:00"
    };

    /**
     * 获取当前手机网络类型
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (connectMgr != null) {
            info = connectMgr.getActiveNetworkInfo();
        }

        String typeName;
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                typeName = "WIFI";
            } else {
                int subType = info.getSubtype();
                int type = getNetworkClass(subType);
                switch (type) {
                    case 1:
                        typeName = "2G";
                        break;
                    case 2:
                        typeName = "3G";
                        break;
                    case 3:
                        typeName = "4G";
                        break;
                    default:
                        typeName = "UNKNOW";
                        break;
                }
            }
        } else {
            typeName = "None";
        }

        return typeName;
    }

    /**
     * Return general class of network type, such as "3G" or "4G". In cases
     * where classification is contentious, this method is conservative.
     *
     * @hide
     */
    public static int getNetworkClass(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case 16://NETWORK_TYPE_GSM
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 1;//NETWORK_CLASS_2_G
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
            case 17://NETWORK_TYPE_TD_SCDMA
                return 2;//NETWORK_CLASS_3_G
            case NETWORK_TYPE_LTE:
            case 18://NETWORK_TYPE_IWLAN
                return 3;//NETWORK_CLASS_4_G
            default:
                return 0;//NETWORK_CLASS_UNKNOWN
        }
    }

    /**
     * 是否取到所有信息
     */
    private static boolean isGetSuccess() {
        return !TextUtils.isEmpty(getPhoneModel()) && !TextUtils.isEmpty(getFactory())
                && !TextUtils.isEmpty(getMaxCpuFreq()) && getRomMemory() > 0;
    }

    /**
     * 获取机身总存储(不包含SD卡)
     */
    public static long getRomMemory() {
        long[] romInfo = new long[1];
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        //Total rom memory
        romInfo[0] = blockSize * totalBlocks;
        return romInfo[0];
    }

    /**
     * 获取CPU最大频率（单位KHZ）
     */
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 设备型号，设备名称，CPU频率，磁盘大小，物理内存大小 取MD5值
     */
    public static String phoneTotalInfo() {
        String defaultInfo = "00000000-0000-0000-0000-000000000000";
        if (!isGetSuccess()) {
            return defaultInfo;
        }
        StringBuilder md5Str = new StringBuilder();
        md5Str.append(getPhoneModel())
                .append(getFactory())
                .append(getMaxCpuFreq())
                .append(getRomMemory());
        String phoneInfo = Util.getMD5(md5Str.toString());
        if (TextUtils.isEmpty(phoneInfo) || phoneInfo.length() < 32) {
            return defaultInfo;
        }

        phoneInfo = phoneInfo.substring(0, 8) + "-"
                + phoneInfo.substring(8, 12) + "-"
                + phoneInfo.substring(12, 16) + "-"
                + phoneInfo.substring(16, 20) + "-"
                + phoneInfo.substring(20, phoneInfo.length());

        return phoneInfo.toUpperCase();
    }

    /**
     * 获取手机开机时间
     */
    public static String getOpenTime() {
        long nowTime = System.currentTimeMillis();
        long openTime = SystemClock.elapsedRealtime();
        return DateUtil.millSecond2String(nowTime - openTime);
    }

    /**
     * 设备语言编码
     */
    public static String getLanguage() {
        String language = Strings.EMPTY;
        if (AppHelper.getContext() != null) {
            Locale locale = AppHelper.getContext().getResources().getConfiguration().locale;
            language = locale.getLanguage();
        }
        return language;
    }

    public static String getChannel() {
        return "Android";
    }

    /**
     * 获取运营商名称
     */
    public static String getSimOperatorName() {
        if (getTelManager() != null && SIM_STATE_READY == getTelManager().getSimState()) {
            return getTelManager().getSimOperatorName();
        }

        return Strings.EMPTY;
    }

    /**
     * 获取ISO国家码，相当于提供SIM卡的国家码
     */
    public static String getSimCountryIso() {
        if (getTelManager() != null) {
            return getTelManager().getSimCountryIso();
        }

        return Strings.EMPTY;
    }

    private static TelephonyManager getTelManager() {
        if (AppHelper.getContext() == null) {
            return null;
        }
        return (TelephonyManager) AppHelper.getContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取应用的版本号
     */
    public static String getAppVersion() {
        Context context = AppHelper.getContext();
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo;
            try {
                packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取应用的版本号
     */
    public static String getAppPackageName() {
        Context context = AppHelper.getContext();
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            return context.getPackageName();
        }
        return "";
    }

    /**
     * 获取版本信息 versioncode
     */
    public static int getVersionCode() {
        final Context context = AppHelper.getContext();
        int version = 1;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = null;
            try {
                packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (packInfo != null) {
                version = packInfo.versionCode;
            }
        }
        return version;
    }

    /**
     * 获取设备的制造商
     */
    public static String getFactory() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取系统版本号
     */
    public static String getPhoneOS() {
        return "Android " + getSysVersion() + " " + Build.VERSION.RELEASE;
    }

    /**
     * 获取Android API版本
     */
    public static String getSysVersion() {
        return Build.VERSION.SDK_INT + "";
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        String model = Build.BRAND + " " + Build.MODEL;
        if (!TextUtils.isEmpty(model) && model.length() > 50) {
            model = model.substring(0, 49);
        }
        return Validator.replaceHanzi(model);
    }

    /**
     * 判断IMEI是否为纯数字串
     */
    private static boolean isNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean isNumber = true;
        int i;
        char c;
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (!((c >= '0') && (c <= '9')) || "00000000000000".equals(str) || Strings.ZERO.equals(str)) {
                isNumber = false;
                break;
            }
        }
        return isNumber;
    }

    /**
     * 获取设备号(IMEI)
     */
    public static String getDeviceIMEI() {
        Context context = AppHelper.getContext();
        String deviceIMEI = SPHelper.getString(SpKeys.SP_KEY_IMEI);

        if (!TextUtils.isEmpty(deviceIMEI)) {
            return deviceIMEI;
        }
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager == null || TextUtils.isEmpty(telephonyManager.getDeviceId())) {
                    deviceIMEI = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                } else {
                    deviceIMEI = telephonyManager.getDeviceId();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!TextUtils.isEmpty(deviceIMEI) && isNumber(deviceIMEI)) {
            SPHelper.putString(SpKeys.SP_KEY_IMEI, deviceIMEI);
            return deviceIMEI;
        }

        if (!TextUtils.isEmpty(getMacAddress())) {
            return getMacAddress();
        }

        if (!TextUtils.isEmpty(getUUID())) {
            return getUUID();
        }

        return "";
    }

    /**
     * 获取MacAddress
     */
    public static String getMacAddress() {
        String macAddress;
        macAddress = SPHelper.getString(SpKeys.SP_KEY_MAC_ADDRESS);
        if (!TextUtils.isEmpty(macAddress)) {
            return macAddress;
        }

        final Context context = AppHelper.getContext();
        if (context != null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi == null) {
                return "";
            }
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                String address = info.getMacAddress();
                //联想系列手机获取MacAddress为 \u0000\u0000:\u0000\u0000:\u0000\u0000:\u0000\u0000:\u0000\u0000:\u0000\u0000
                //抛弃这类MacAddress
                List<String> macs = Validator.findMac(address);
                if (macs.isEmpty()) {
                    return "";
                } else {
                    macAddress = macs.get(0);
                    if (isCorrectMacAddress(macAddress)) {
                        SPHelper.putString(SpKeys.SP_KEY_MAC_ADDRESS, macAddress);
                        return macAddress;
                    }
                }
            }
        }
        return "";
    }

    /**
     * 判断mac地址是否合法
     */
    private static boolean isCorrectMacAddress(String address) {
        boolean flag = false;
        if (!TextUtils.isEmpty(address) && address.length() == 17) {
            address = address.replaceAll(":", "");
            flag = isHex(address) && !intercept(address);
        }

        return flag;
    }

    private static boolean intercept(String address) {
        for (String mac : INTERCEPTS_MAC) {
            if (mac.equals(address)) return true;
        }
        return false;
    }

    /**
     * 判断是否为纯16进制数字串
     */
    private static boolean isHex(String str) {
        boolean isHexFlg = true;
        int i;
        char c;
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (!(((c >= '0') && (c <= '9')) ||
                    ((c >= 'A') && (c <= 'F')) ||
                    (c >= 'a') && (c <= 'f'))) {
                isHexFlg = false;
                break;
            }
        }
        return isHexFlg;
    }

    /**
     * 获取UUID
     */
    public static String getUUID() {
        String Uuid;
        Uuid = SPHelper.getString(SpKeys.SP_KEY_UUID);
        if (!TextUtils.isEmpty(Uuid)) {
            return Uuid;
        } else {
            Uuid = UUID.randomUUID().toString();
            if (!TextUtils.isEmpty(Uuid)) {
                SPHelper.putString(SpKeys.SP_KEY_UUID, Uuid);
                return Uuid;
            }
        }

        return "";
    }

    /**
     * 获取设备唯一ID
     */
    public static String getDevNumber() {
        if (!TextUtils.isEmpty(getMacAddress())) {
            return getMacAddress();
        } else if (!TextUtils.isEmpty(getDeviceIMEI())) {
            return getDeviceIMEI();
        } else if (!TextUtils.isEmpty(getUUID())) {
            return getUUID();
        } else {
            return "";
        }
    }

    /**
     * 检测系统是否为MIUI
     */
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        try {
            BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
//
//    /**
//     * 获取渠道
//     */
//    public static String getChannel() {
//        Context context = App.getContext();
//        if (context != null) {
//            String channel = PackerNg.getMarket(context);
//            if (!TextUtils.isEmpty(channel)) {
//                return channel.toUpperCase();
//            }
//        }
//        return "AXD";
//    }

    /**
     * 获取手机宽高
     */
    public static String getPhonePixels(Activity activity) {
        if (activity != null) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int widthPixels = dm.widthPixels;
            int heightPixels = dm.heightPixels;
            return widthPixels + "-" + heightPixels;
        }
        return "unKnown";
    }

    /**
     * 屏幕宽度
     */
    public static int getDeviceWidth(Activity activity) {
        if (activity != null) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            return dm.widthPixels;
        }
        return 0;
    }

    /**
     * 屏幕高度
     */
    public static int getDeviceHeight(Activity activity) {
        if (activity != null) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            return dm.heightPixels;
        }
        return 0;
    }


    /**
     * 判断当前有没有网络连接
     */
    public static boolean getNetworkState() {
        Context context = AppHelper.getContext();
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            return !(networkinfo == null || !networkinfo.isAvailable());
        }
        return false;
    }

    /**
     * SD卡是否挂载
     */
    public static boolean mountedSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检测应用是否安装
     **/
    public static boolean isApkInstalled(String packageName) {
        Context context = AppHelper.getContext();
        if (context != null) {
            final PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            for (int i = 0; i < pinfo.size(); i++) {
                if (pinfo.get(i).packageName.equalsIgnoreCase(packageName)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}
