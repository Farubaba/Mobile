package com.silence.rootfeature.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.UserManager;

import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.app.C;
import com.silence.rootfeature.logger.LogManager;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 1. secondary external storage media.
 * 2. emulated storage
 * 3. SD card
 *
 * @author violet
 * date :  2018/6/29 12:11
 */

public class StorageUtilTest {

    private static final String TAG = StorageUtilTest.class.getSimpleName();


    public static String getFileAbsolutePath(File file){
        if(file != null){
            return file.getAbsolutePath();
        }
        return C.Strings.EMPTY;
    }


    /*********************Internal*****************************/

    public static void testPath(){
        LogManager.getInstance().dt(TAG, "getFilesDir = " + getFileAbsolutePath(AppUtil.getAppContext().getFilesDir()));

        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        int linenumber = 0;
        try {
            fileOutputStream = AppUtil.getAppContext().openFileOutput("test-open-file-output", Context.MODE_PRIVATE);
            fileOutputStream.write("this file is write by openFileOutput method.".getBytes());
//            System.lineSeparator()
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write("second line".getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.flush();

            fileInputStream = AppUtil.getAppContext().openFileInput("test-open-file-output");
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);




            linenumber = lineNumberReader.getLineNumber();
            LogManager.getInstance().dt(TAG, "linenumber = "+ linenumber);
            String line = lineNumberReader.readLine();
            LogManager.getInstance().dt(TAG, "readline = "+ line);
            linenumber = lineNumberReader.getLineNumber();
            LogManager.getInstance().dt(TAG, "linenumber = "+ linenumber);


            File cacheDir = AppUtil.getAppContext().getCacheDir();
            if(!cacheDir.exists()){
                cacheDir.mkdir();
            }

            File cachefile = new File(cacheDir,"test-cache-file");
            if(!cachefile.exists()){
                cachefile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(cachefile);
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos));
            br.write("cache start");
            br.newLine();
            br.write("cache content here.");
            br.newLine();
            br.write("cache end");

            CloseUtil.closeIOQuietly(br);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                UserManager.supportsMultipleUsers();
            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIOQuietly(fileOutputStream);
        }

        LogManager.getInstance().dt(TAG, "linenumber = "+ linenumber);
        LogManager.getInstance().dt(TAG, "getCacheDir = " + getFileAbsolutePath(AppUtil.getAppContext().getCacheDir()));
        LogManager.getInstance().dt(TAG, "getObbDir = " + getFileAbsolutePath(AppUtil.getAppContext().getObbDir()));

        LogManager.getInstance().dt(TAG, "getExternalCacheDir = " + getFileAbsolutePath(AppUtil.getAppContext().getExternalCacheDir()));
        LogManager.getInstance().dt(TAG, "getExternalFilesDir = " + getFileAbsolutePath(AppUtil.getAppContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogManager.getInstance().dt(TAG, "getCodeCacheDir = " + getFileAbsolutePath(AppUtil.getAppContext().getCodeCacheDir()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LogManager.getInstance().dt(TAG, "getDataDir = " + getFileAbsolutePath(AppUtil.getAppContext().getDataDir()));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File[] files = AppUtil.getAppContext().getExternalFilesDirs(Environment.DIRECTORY_PICTURES);
            if(files != null){
                for(File file : files){
                    LogManager.getInstance().dt(TAG, "getExternalFilesDirs = " + getFileAbsolutePath(file));
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File[] caches = AppUtil.getAppContext().getExternalCacheDirs();
            if(caches != null){
                for(File cache : caches){
                    LogManager.getInstance().dt(TAG, "getExternalCacheDirs = " + getFileAbsolutePath(cache));
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            File[] medias = AppUtil.getAppContext().getExternalMediaDirs();
            if(medias != null){
                for(File media : medias){
                    LogManager.getInstance().dt(TAG, "getExternalMediaDirs = " + getFileAbsolutePath(media));
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            File file = AppUtil.getAppContext().getNoBackupFilesDir();
            if(file != null){
                LogManager.getInstance().dt(TAG, "getNoBackupFilesDir = " + getFileAbsolutePath(file));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File[] obbs = AppUtil.getAppContext().getObbDirs();
            if(obbs != null){
                for(File obb : obbs){
                    LogManager.getInstance().dt(TAG, "getObbDirs = " + getFileAbsolutePath(obb));
                }
            }
        }


        File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if(pictureDir != null){
            LogManager.getInstance().dt(TAG, "Environment.getExternalStoragePublicDirectory = " + getFileAbsolutePath(pictureDir));
        }

        File externalStorageDir = Environment.getExternalStorageDirectory();
        if(externalStorageDir != null){
            LogManager.getInstance().dt(TAG, "Environment.getExternalStorageDirectory = " + getFileAbsolutePath(externalStorageDir));
        }

        File dataDirectory = Environment.getDataDirectory();
        if(dataDirectory != null){
            LogManager.getInstance().dt(TAG, "Environment.getDataDirectory = " + getFileAbsolutePath(dataDirectory));
        }

        File downloadCache = Environment.getDownloadCacheDirectory();
        if(downloadCache != null){
            LogManager.getInstance().dt(TAG, "Environment.getDownloadCacheDirectory = " + getFileAbsolutePath(downloadCache));
        }

        File root = Environment.getRootDirectory();
        if(root != null){
            LogManager.getInstance().dt(TAG, "Environment.getRootDirectory = " + getFileAbsolutePath(root));
        }

    }

    /**
     *
     * @return
     */
    public static File getInternalFilesDir(){
        File file = AppUtil.getAppContext().getFilesDir();
        LogManager.getInstance().dt(TAG, "getInternalFilesDir = " + getFileAbsolutePath(file));
        return file;
    }

    public static File getInternalCacheDir(){
        File file = AppUtil.getAppContext().getCacheDir();
        LogManager.getInstance().dt(TAG, "getInternalCacheDir = " + getFileAbsolutePath(file));
        return file;
    }

    public static OutputStream openInternalFileOutput(String name, int mode){
        try {
            return AppUtil.getAppContext().openFileOutput(name, mode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getCodeCacheDir(){
        File file = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            file = AppUtil.getAppContext().getCodeCacheDir();
        }
        return file;
    }

    /*********************External*****************************/

    /**
     *
     * @param type
     * @return
     */
    public static File getExternalFilesDir(String type){
        File file = AppUtil.getAppContext().getExternalFilesDir(type);
        LogManager.getInstance().dt(TAG, "getExternalFilesDir = " + getFileAbsolutePath(file));
        return file;
    }

    /**
     * API 19 at least.
     * @param type
     * @return
     */
    public static File getExternalFilesDirs(String type){
        //API 19
        //File[] files = AppUtil.getAppContext().getExternalFilesDirs(type);
        //LogManager.getInstance().dt(TAG, "getExternalFilesDir = " + getFileAbsolutePath(file[0]));
        return null;
    }

    public static File getExternalStoragePublicDir(String type){
        File file = Environment.getExternalStoragePublicDirectory(type);
        LogManager.getInstance().dt(TAG, "getExternalStoragePublicDir = " + getFileAbsolutePath(file));
        return file;
    }


}

