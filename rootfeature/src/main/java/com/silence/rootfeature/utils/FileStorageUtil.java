/*
 * Copyright © 2012-2013 LiuZhongnan. All rights reserved.
 * 
 * Email:qq81595157@126.com
 * 
 * PROPRIETARY/CONFIDENTIAL.
 */

package com.silence.rootfeature.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.app.C;
import com.silence.rootfeature.logger.LogManager;

/**
 * FileStorageUtil.SD卡管理，空间数据的获取
 * 
 * @author
 * @version
 * @created
 */
public class FileStorageUtil {

	public static final String TAG  = FileStorageUtil.class.getSimpleName();

	private String sdPath;
	private long nSDTotalSize;
	private long nSDFreeSize;

	public FileStorageUtil(String sdPath) {
		this.sdPath = sdPath;
		init();
	}

	public static String getFileAbsolutePath(File file){
		if(file != null){
			return file.getAbsolutePath();
		}
		return C.Strings.EMPTY;
	}

	@SuppressWarnings("deprecation")
	private void init() {
		try {
			StatFs statFs = new StatFs(sdPath);
			long totalBlocks = statFs.getBlockCount();// 区域块数
			long availableBlocks = statFs.getAvailableBlocks();// 可利用区域块数
			long blockSize = statFs.getBlockSize();// 每个区域块大小
			nSDTotalSize = totalBlocks * blockSize;
			nSDFreeSize = availableBlocks * blockSize;
		} catch (Exception e) {

		}
	}

	/**
	 * 是否存在
	 *
	 * @return
	 */
	public boolean exist() {
		return nSDTotalSize == 0 ? false : true;
	}

	/**
	 * 总空间
	 *
	 * @return
	 */
	public long getTotalSize() {
		return nSDTotalSize;
	}

	/**
	 * 剩余空间
	 *
	 * @return
	 */
	public long getFreeSize() {
		return nSDFreeSize;
	}

	/**
	 * TODO 递归取得文件夹大小
	 */
	private static long getFileSize(File f) {
		long size = 0;
		if (f.isDirectory()) {
			File files[] = f.listFiles();
			if (files != null) {
				for (int i = 0, n = files.length; i < n; i++) {
					if (files[i].isDirectory()) {
						size = size + getFileSize(files[i]);
					} else {
						size = size + files[i].length();
					}
				}
			}
		} else {
			size = f.length();
		}
		return size;
	}

	/** Returns 是否有SD卡 */
	public static boolean hasSDCard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public static String getDefauleSDCardPath() {
		return hasSDCard() ? Environment.getExternalStorageDirectory()
				.getAbsolutePath() : "";
	}

	/**
	 * 获得外部存储路径
	 *
	 * @return /mnt/sdcard或者/storage/extSdCard等多种名称
	 */
	@SuppressLint("NewApi")
//	public static ArrayList<SDCardInfo> getExternalStorageDirectory() {
//		ArrayList<SDCardInfo> list = new ArrayList<SDCardInfo>();
//		if (UIUtils.hasKitKat()) {
//			// OS 4.4 以上读取外置 SD 卡
//			final File[] externalFiles = AppUtil.getAppContext()
//					.getExternalFilesDirs(null);
//			if (null != externalFiles) {
//				SDCardInfo info = new SDCardInfo();
//				info.path = getDefauleSDCardPath();
//				info.isExternal = false;
//				list.add(info);
//				if (externalFiles.length > 1 && (null != externalFiles[1])) {
//						SDCardInfo externalInfo = new SDCardInfo();
//						externalInfo.path = externalFiles[1].getAbsolutePath();
//						externalInfo.isExternal = true;
//						list.add(externalInfo);
//				}
//			}
//			return list;
//		} else {
//			Runtime runtime = Runtime.getRuntime();
//			Process proc;
//			try {
//				proc = runtime.exec("mount");
//				// InputStream is = Youku.context.getAssets().open("mount.txt");
//				InputStream is = proc.getInputStream();
//				InputStreamReader isr = new InputStreamReader(is);
//				String line;
//				// String mount = new String();
//				BufferedReader br = new BufferedReader(isr);
//				String defauleSDCardPath = getDefauleSDCardPath();
//				while ((line = br.readLine()) != null) {
//					// Logger.d("nathan", line);
//					// "fat"为不可卸载的；"fuse"为可卸载的；但是有的手机不适用，所以统一去处理;storage为一些三星手机的存储路径标识
//					if (line.contains("fat") || line.contains("fuse")
//							|| line.contains("storage")) {
//						if (line.contains("secure") || line.contains("asec")
//								|| line.contains("firmware")
//								|| line.contains("shell")
//								|| line.contains("obb")
//								|| line.contains("legacy")
//								|| line.contains("data")
//								|| line.contains("tmpfs")) {
//
//							continue;
//						}
//						String columns[] = line.split(" ");
//						for (int i = 0; i < columns.length; i++) {
//							String path = columns[i];
//							if (path.contains("/") && !path.contains("data")
//									&& !path.contains("Data")) {
//								try {
//									FileStorageUtil m = new FileStorageUtil(path);
//									if (m.getTotalSize() >= 1024 * 1024 * 1024) {
//										SDCardInfo info = new SDCardInfo();
//										info.path = columns[i];
//										if (info.path.equals(defauleSDCardPath)) {
//											info.isExternal = false;
//										} else {
//											info.isExternal = true;
//										}
//										list.add(info);
//									}
//								} catch (Exception e) {
//									continue;
//								}
//							}
//						}
//					}
//				}
//
//				if (list.size() == 1) {
//					if (!defauleSDCardPath.equals(list.get(0).path)) {
//						SDCardInfo info = new SDCardInfo();
//						info.path = defauleSDCardPath;
//						info.isExternal = false;
//						list.add(info);
//					} else {
//						Youku.savePreference("download_file_path",
//								defauleSDCardPath);
//					}
//				} else if (list.size() == 0) {
//					if (defauleSDCardPath != null
//							&& defauleSDCardPath.length() != 0) {
//						SDCardInfo info = new SDCardInfo();
//						info.path = defauleSDCardPath;
//						info.isExternal = false;
//						list.add(info);
//					}
//				}
//				return list;
//			} catch (IOException e) {
//
//			}
//			return null;
//
//		}
//	}

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

	public static class SDCardInfo {

		/** 路径/mnt/sdcard或者/storage/extSdCard等多种名称 */
		public String path;

		/** 是否是外部存储 */
		public boolean isExternal;
	}
}
