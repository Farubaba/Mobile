package com.silence.rootfeature.utils;

import android.database.Cursor;

import com.silence.rootfeature.logger.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 提供安全关闭各种数据源的功能
 *
 * @author violet
 * @modify
 * @see
 */
public final class CloseUtil {

	private static final String TAG = "StreamUtil";

	/**
	 * close cursor.
	 * 
	 * @param cursor
	 */
	public static void closeCursor(Cursor cursor) {
		if (cursor != null && !cursor.isClosed()) {
			try {
				cursor.close();
			}
			catch (Exception e) {
				LogManager.getInstance().e(TAG, "cursor close exception : " + e.getMessage());
			}
		}
	}

	/**
	 * close inputStream.
	 * 
	 * @param inStream
	 */
	public static void closeStream(InputStream inStream) {
		if (inStream != null) {
			try {
				inStream.close();
			}
			catch (Exception e) {
				LogManager.getInstance().e(TAG, "inputStream close exception : " + e.getMessage());
			}
		}
	}

	/**
	 * close outputStream.
	 * 
	 * @param outStream
	 */
	public static void closeStream(OutputStream outStream) {
		if (outStream != null) {
			try {
				outStream.close();
			}
			catch (Exception e) {
				LogManager.getInstance().e(TAG, "outputStream close exception : " + e.getMessage());
			}
		}
	}

	/**
	 * close reader.
	 * 
	 * @param reader
	 */
	public static void closeStream(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			}
			catch (IOException e) {
				LogManager.getInstance().e(TAG, "reader close exception : " + e.getMessage());
			}
		}
	}

	/**
	 * close writer.
	 * 
	 * @param writer
	 */
	public static void closeStream(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			}
			catch (IOException e) {
				LogManager.getInstance().e(TAG, "writer close exception : " + e.getMessage());
			}
		}
	}

	/**
	 * close randomAccessFile.
	 * 
	 * @param randomAccessFile
	 */
	public static void closeStream(RandomAccessFile randomAccessFile) {
		if (randomAccessFile != null) {
			try {
				randomAccessFile.close();
			}
			catch (IOException e) {
				LogManager.getInstance().e(TAG, "randomAccessFile close exception : " + e.getMessage());
			}
		}
	}

	public static void closeSocket(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			}
			catch (Exception e) {
				LogManager.getInstance().dt(TAG, "socket close exception : " + e.getMessage());
			}
		}
	}

	public static void closeSocket(ServerSocket serverSocket) {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			}
			catch (Exception e) {
				LogManager.getInstance().dt(TAG, "serverSocket close exception : " + e.getMessage());
			}
		}
	}

	public static void closeHttpURLConnection(HttpURLConnection conn) {
		if (conn != null) {
			try {
				conn.disconnect();
				LogManager.getInstance().dt(TAG, "HttpURLConnection disconnect");
			}
			catch (Exception e) {
				LogManager.getInstance().e(TAG, "randomAHttpURLConnectionccessFile close exception : " + e.getMessage());
			}
		}
	}

}
