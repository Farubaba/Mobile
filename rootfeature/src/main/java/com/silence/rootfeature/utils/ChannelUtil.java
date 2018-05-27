package com.silence.rootfeature.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.silence.rootfeature.logger.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 渠道工具
 *
 */
public final class ChannelUtil {

    public static final String TAG = ChannelUtil.class.getSimpleName();

    /**
     * 获取渠道信息
     *
     * @param context
     * @return json字符串
     */
    public static String getChannelInfo(Context context) {
        long start = System.currentTimeMillis();
        LogManager.getInstance().e(TAG, "ManifestMetaData getChannelInfo context = " + (context == null ? " null" : " not null"));
        if (null == context) {
            return null;
        }
        ApplicationInfo appInfo = context.getApplicationInfo();
        String srcDir = appInfo.sourceDir;

        ZipFile apk = null;
        StringBuilder sb = new StringBuilder();
        try {
            apk = new ZipFile(srcDir);
            Enumeration<?> entries = apk.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String name = entry.getName();
                //是channel_info文件，读取渠道信息
                if (name.startsWith("META-INF/channel_info")) {
                    InputStream in = apk.getInputStream(entry);
                    byte[] buf = new byte[1024];
                    int len;
                    //读文件内的内容，可以是属性键值对，或者是json，根据服务端产生格式去读取
                    while ((len = in.read(buf)) != -1) {
                        sb.append(new String(buf, 0, len, "UTF-8"));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.getInstance().dt(TAG, "read metadata error, use default website, Value=" + sb.toString() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getInstance().dt(TAG, "read metadata error, use default website, Value=" + sb.toString() + "\n" + e.getMessage());
        } finally {
            LogManager.getInstance().dt(TAG, "read metadata error, use default website, Value=" + sb.toString());
            if (null != apk) {
                try {
                    apk.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (TextUtils.isEmpty(sb.toString())) {
            LogManager.getInstance().dt(TAG, "没有找到渠道配置文件，或者文件读取错误，调用者需要使用manifest中默认渠道配置信息。");
            //默认Website渠道
            //sb.append("{\"umsKey\":\"e7ade454a2690fdf39bd881fc5813fbb\",\"channelName\":\"website\",\"channelType\":\"self\"}");
        }
        long end = System.currentTimeMillis();
        LogManager.getInstance().dt(TAG, "Read ManifestMetaData = " + sb.toString() + " time = " + (end - start));
        return sb.toString();
    }


    /**
     * 获取渠道ID
     *
     * @param context
     * @return
     */
    public static String getChannelName(Context context) {
        String channelName = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Object channelNameObj = appInfo.metaData.get("CHANNEL_NAME");
            channelName = channelNameObj == null ? "UNKNOWN" : channelNameObj.toString();
            Object channelTypeObj = appInfo.metaData.get("CHANNEL_TYPE");
            channelName += channelTypeObj == null ? "0" : String.valueOf(channelTypeObj);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }


}
