package com.silence.rootfeature.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.silence.rootfeature.app.C;
import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.FormatUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author violet
 * date :  2018/7/1 12:58
 */

public class IOUtil {

    private static final int MIN_FREE_SDCARD_SPACE = 100;//100M
    private static final String TAG = "FileUtil";
    private static final String DOT = ".";
    private static final String FILE_PATH_SEPERATOR = File.separator;
    private static File internalFileDir;
    private static File internalCacheDir;
    private static File externalCustomFileDir;
    private static File appCustomExternalDir;

    /** Internal Storage **/

    /**
     * internalFileDir = context.getFilesDir()<br>
     * when the user uninstalls the app, system delete files on internalStorage.
     *
     * @param context
     * @param fileName
     * @return
     */
    public static File getInternalFileDir(Context context, String fileName) {
        if (internalFileDir == null) {
            internalFileDir = new File(context.getFilesDir(), fileName);
        }
        return internalFileDir;
    }

    /**
     * internalFileOutputStream = context.openFileOutput(fileName); <br>
     * when the user uninstalls the app, system delete files on internalStorage.
     *
     * @param context
     * @param fileName
     * @param mode
     * @return
     * @throws FileNotFoundException
     */
    public static FileOutputStream getInternalFileOutputStream(Context context,
                                                               String fileName, int mode) throws FileNotFoundException {
        return context.openFileOutput(fileName, mode);
    }

    /**
     * internalFileInputStream = context.openFileInput(fileName); <br>
     * when the user uninstalls the app, system delete files on internalStorage.
     *
     * @param context
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static FileInputStream getInternalFileInputStream(Context context,
                                                             String fileName) throws FileNotFoundException {
        return context.openFileInput(fileName);
    }

    /**
     * internalCacheDir = context.getCacheDir() <br>
     * when the user uninstalls the app, you should manually delete all cached
     * files created with getCacheDir()
     *
     * @param context
     * @return
     */
    public static File getInternalCacheDir(Context context) {
        if (internalCacheDir == null) {
            internalCacheDir = context.getCacheDir();
        }
        return internalCacheDir;
    }

    /**
     * internalCacheDir = context.getCacheDir() <br>
     * when the user uninstalls the app, you should manually delete all cached
     * files created with getCacheDir()
     *
     * @param context
     * @param fileName
     * @param suffix
     * @return
     * @throws IOException
     */
    public static File createInternalCacheFile(Context context,
                                               String fileName, String suffix) throws IOException {
        return File.createTempFile(fileName, suffix, context.getCacheDir());
    }

    /**
     * external Storage is modifiable by the user and other apps, there are two
     * categories of files you might save here:<br>
     *
     * Public files <br>
     * Files that should be freely available to other apps and to the user. When
     * the user uninstalls your app, these files should remain available to the
     * user. For example, photos captured by your app or other downloaded files.
     *
     * Private files <br>
     * Files that rightfully belong to your app and should be deleted when the
     * user uninstalls your app. Although these files are technically accessible
     * by the user and other apps because they are on the external storage, they
     * are files that realistically don't provide value to the user outside your
     * app. When the user uninstalls your app, the system deletes all files in
     * your app's external private directory. For example, additional resources
     * downloaded by your app or temporary media files.
     *
     * If you want to save public files on the external storage, use the
     * getExternalStoragePublicDirectory() method to get a File representing the
     * appropriate directory on the external storage. The method takes an
     * argument specifying the type of file you want to save so that they can be
     * logically organized with other public files, such as DIRECTORY_MUSIC or
     * DIRECTORY_PICTURES. For example
     **/

    /**
     * isExternalStorageWritable
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 判断SDCard是否可用写入，同时判断了SDCard是否存在以及可用空间。（目前最小可用空间设置为100M）
     *
     * @return <b>boolean</b>
     * @author liangzhenggen@youku.com
     * date :  2014年6月19日 下午5:03:26
     * @modify
     * @see
     */
    public static boolean isSDCardWriteable(){
        if(isExternalStorageWritable()){
            if(getSDFreeSize() > MIN_FREE_SDCARD_SPACE ){
                return true;
            }
        }
        return false;
    }
    /**
     * isExternalStorageReadable
     *
     * @return
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * PulbicExternalFileDir =
     * Environment.getExternalStoragePublicDirectory(dirType)
     *
     * @param dirType
     *            for example : Environment.DIRECTORY_PICTURES
     * @param dirName
     * @return
     */
    public static File getPulbicExternalFileDir(String dirType, String dirName) {
        File dir = new File(
                Environment.getExternalStoragePublicDirectory(dirType), dirName);
        if (!dir.mkdirs()) {
            LogManager.getInstance().e(TAG, "Public ExternalFile Directory not created");
        }
        return dir;
    }

    /**
     * PrivateExternalFileDir = context.getExternalFilesDir(dirType)
     *
     * when the user uninstalls the app, system delete files saved by
     * getExternalFilesDir()
     *
     * @param context
     * @param dirType
     *            for example : Environment.DIRECTORY_PICTURES
     * @param dirName
     * @return
     */
    public static File getPrivateExternalFileDir(Context context,
                                                 String dirType, String dirName) {
        File dir = new File(context.getExternalFilesDir(dirType), dirName);
        if (!dir.mkdirs()) {
            LogManager.getInstance().e(TAG, "Private externalFile Directory not created");
        }
        return dir;
    }

    /**
     * create a directory in ExternalStorage,set the last word of package name
     * to the file name.
     *
     * @param context
     * @return
     */
    public static File getAppExternalCustomFileDir(Context context) {
        if (appCustomExternalDir == null) {
            String pkgname = context.getPackageName();
            // here get the dirName from PackageName.
            String dirName = pkgname.substring(pkgname.lastIndexOf(DOT) + 1);
            if (isExternalStorageWritable()) {
                if (externalCustomFileDir == null) {
                    externalCustomFileDir = new File(
                            Environment.getExternalStorageDirectory()
                                    + FILE_PATH_SEPERATOR + dirName);
                }
            }
        }
        return appCustomExternalDir;
    }

    /**
     * delete file and child files
     *
     * @param file
     */
    public static void deleteFiles(File file) {
        try {
            grant(file.getAbsolutePath());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if(files != null){
                    for (int i = 0; i < files.length; i++) {
                        deleteFiles(files[i]);
                    }
                }
            } else {
                boolean hasDelete = file.delete();
                LogManager.getInstance().d(TAG, "hasDeleted = "+ hasDelete);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all files in AppExternalCustomFileDir.
     *
     * @param context
     */
    public static void deleteAppExternalCustomFileDir(Context context) {
        deleteFiles(getAppExternalCustomFileDir(context));
    }

    /**
     * delete file of internal storage.
     *
     * @param context
     * @param fileName
     */
    public static void deleteInternalFile(Context context, String fileName) {
        context.deleteFile(fileName);
    }

    /**
     * whether SDCard exist
     *
     * @return <b>boolean</b>
     * @author liangzhenggen@youku.com
     * date :  2014年6月6日 下午2:22:39
     * @modify
     * @see
     */
    public static boolean hasSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * SDCard total size
     *
     * @return <b>long</b> MB
     * @author liangzhenggen@youku.com
     * date :  2014年6月6日 下午2:26:24
     * @modify
     * @see
     */
    public static long getSDAllSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCount();
        LogManager.getInstance().d(TAG, blockSize + " " + allBlocks);
        // 返回SD卡大小
        // return allBlocks * blockSize; //单位Byte
        // return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * SDCard free size
     *
     * @return <b>long</b> MB
     * @author liangzhenggen@youku.com
     * date :  2014年6月6日 下午2:25:19
     * @modify
     * @see
     */
    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        LogManager.getInstance().d(TAG, blockSize + " " + freeBlocks);
        // 返回SD卡空闲大小
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    public static void grant(String filePath) throws IOException{
        String command = "chmod 777 " + filePath;
        Runtime runtime = Runtime.getRuntime();
        runtime.exec(command);
    }

    public static String getFileNameFromUrl(String url){
        if(TextUtils.isEmpty(url) || !url.contains("/")){
            return "";
        }else{
            return url.substring(url.lastIndexOf("/"));
        }
    }

    public static boolean isExists(String filePath){
        File file = new File(filePath);
        return isExists(file);
    }

    public static boolean isExists(File file){
        if(file != null && file.exists()){
            return true;
        }
        return false;
    }

    public static boolean deleteFilesByName(final File directory, final String prefix){
        if(isExists(directory) && directory.isDirectory()){
            File[] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if(directory.equals(dir) && filename.startsWith(prefix)){
                        return true;
                    }
                    return false;
                }
            });
            if(files != null){
                for(File file : files){
                    return file.delete();
                }

            }
        }
        return true;
    }

    public static void createExternalStoragePublicDirectory(String type){
        File folder = Environment.getExternalStoragePublicDirectory(type);
        if(folder.exists() && folder.isDirectory()){

        }else{
            folder.mkdirs();
        }
    }

    /**
     * 使用大小为{@link C.SizeUnits#FILE_DOWNLOAD_BUFFER_SIZE}的buffer来写入数据，适用于大文件写入。
     * 默认不使用追加模式append = false；
     *
     * @param inputStream
     * @param targetFile
     * @return
     * @throws IOException
     */
    public static File writeToBigSizeFile(InputStream inputStream, File targetFile) throws IOException {
        return writeToFile(inputStream, targetFile, false, C.SizeUnits.FILE_DOWNLOAD_BUFFER_SIZE);
    }

    /**
     * 使用大小为{@link C.SizeUnits#DEFAULT_BUFFER_SIZE}的buffer来写入数据，适用于图片或者小文件写入。
     * 默认不使用追加模式append = false；
     * @param inputStream
     * @param targetFile
     * @return
     * @throws IOException
     */
    public static File writeToImageSizeFile(InputStream inputStream, File targetFile) throws IOException {
        return writeToFile(inputStream, targetFile, false, C.SizeUnits.DEFAULT_BUFFER_SIZE);
    }


    /**
     * 使用大小为{@link C.SizeUnits#MIN_BUFFER_SIZE}的buffer来写入数据，适用于JSON数据等小文本写入。
     * 默认不使用追加模式append = false；
     * @param inputStream
     * @param targetFile
     * @return
     * @throws IOException
     */
    public static File writeToJsonSizeFile(InputStream inputStream, File targetFile) throws IOException {
        return writeToFile(inputStream, targetFile, false, C.SizeUnits.MIN_BUFFER_SIZE);
    }

    /**
     * 文件写入工具方法
     * @param inputStream
     * @param targetFile
     * @param append
     * @param bufferSize
     * @return 返回更改后的文件。{@link File#length()}可以获得更改后的文件字节（bytes）长度。
     * @throws IOException
     */
    public static File writeToFile(InputStream inputStream,  File targetFile, boolean append, int bufferSize) throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        boolean fileExists;
        FileOutputStream fileOutputStream = null;
        int perReadLen = 0;//每次读取字节数
        long totalReadLen = 0L;//总共读取字节数,long类型，对应file.length()方法返回值。

        if(inputStream != null && targetFile != null){
            if(!targetFile.exists()){
                fileExists = targetFile.createNewFile();
            }else{
                fileExists = true;
            }

            if(fileExists){
                fileOutputStream = new FileOutputStream(targetFile, append);
                checkMinBufferSize(bufferSize);
                byte[] buffer = new byte[bufferSize];

                while(hasMoreContent(perReadLen = inputStream.read(buffer))){
                    LogManager.getInstance().d(counter.incrementAndGet());
                    fileOutputStream.write(buffer, 0 , perReadLen);
                    totalReadLen += perReadLen;
                    //fileOutputStream.flush(); //
                }
                counter.set(0);
            }else{
                LogManager.getInstance().d("file not exists and createNewFile failed without exception. do nothing!!!");
            }
        }
        return targetFile;
    }


    public static File writeToFileWithStatus(InputStream inputStream, long totalFileLength, File targetFile, boolean append, int bufferSize) throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        boolean fileExists;
        FileOutputStream fileOutputStream = null;
        int perReadLen = 0;//每次读取字节数
        long totalReadLen = 0;//总共读取字节数,long类型，对应file.length()方法返回值。


        if(inputStream != null && targetFile != null){
            if(!targetFile.exists()){
                fileExists = targetFile.createNewFile();
            }else{
                fileExists = true;
            }

            if(fileExists){
                fileOutputStream = new FileOutputStream(targetFile, append);
                checkMinBufferSize(bufferSize);
                byte[] buffer = new byte[bufferSize];

                while(hasMoreContent(perReadLen = inputStream.read(buffer))){
                    LogManager.getInstance().d(counter.incrementAndGet());
                    fileOutputStream.write(buffer, 0 , perReadLen);
                    totalReadLen += perReadLen;
                    //fileOutputStream.flush(); //
                }
                counter.set(0);
            }else{
                LogManager.getInstance().d("file not exists and createNewFile failed without exception. do nothing!!!");
            }
        }
        return targetFile;
    }

    /**
     * config buffer size, buffer size should not less than 8KB. default is 16KB.
     * @param bufferSize
     * @return
     */
    public static int checkMinBufferSize(int bufferSize){
        if(bufferSize < C.SizeUnits.MIN_BUFFER_SIZE ){
            bufferSize = C.SizeUnits.MIN_BUFFER_SIZE;
        }
        return bufferSize;
    }

    /**
     * 判断文件是否已经读取完成。
     * @param readLength
     * @return
     */
    public static boolean hasMoreContent(int readLength){
        if(readLength != -1){
            return true;
        }
        return false;
    }

    /**
     * 获取进度 数值，没有百分号，例如： 90
     * @param readOrWriteLength
     * @param totalFileLength
     * @return
     */
    public static String getProgressNumber(double readOrWriteLength, double totalFileLength){
        String progress = "0";
        if(totalFileLength > 0){
            double rawProgress = readOrWriteLength / totalFileLength * 100;
            progress = FormatUtil.getFormatInteger().format(Math.floor(rawProgress));
        }
        return progress;
    }

    /**
     * 获取进度，包含百分号，例如： 90%
     * @param readOrWriteLength
     * @param totalFileLength
     * @return
     */
    public static String getProgressWithPercent(double readOrWriteLength, double totalFileLength){
        return getProgressNumber(readOrWriteLength, totalFileLength) + "%";
    }
}
