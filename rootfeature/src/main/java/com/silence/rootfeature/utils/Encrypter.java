package com.silence.rootfeature.utils;

import android.os.Build;
import android.support.annotation.StringDef;

import com.silence.rootfeature.app.C;
import com.silence.rootfeature.file.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * <pre>
 *  加密解密工具类
 *
 * 1.MD5获得大文件的消息摘要比较耗时，应该放到单独的线程去完成。
 *   实验数据如下：
 *   MacBook Pro (Retina, 15-inch, Mid 2014)
 *   处理器：2.2 GHz Intel Core i7
 *   内存：16 GB 1600 MHz DDR3
 *
 *  --文件名--------文件大小---------Java代码耗时-----md5 -t 命令行耗时----
 *  test.zip        3.39G           9823毫秒           约10秒
 *  test.apk        9.7M            52毫秒             约2秒
 *  test-do         76.1M           271毫秒            约3秒
 *  test2.zip       305.6           971毫秒            约3秒
 *  ------------------------------------------------------------------
 *  </pre>
 * @author violet
 * @date 2018/7/1 22:25
 */

public class Encrypter {

    public static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final byte[] CHUNK_SEPARATOR = {'\r', '\n'};

    /**
     * This array is a lookup table that translates 6-bit positive integer index values into their "Base64 Alphabet"
     * equivalents as specified in Table 1 of RFC 2045.
     *
     * Thanks to "commons" project in ws.apache.org for this code.
     * http://svn.apache.org/repos/asf/webservices/commons/trunk/modules/util/
     */
    private static final byte[] STANDARD_ENCODE_TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    /**
     * This is a copy of the STANDARD_ENCODE_TABLE above, but with + and /
     * changed to - and _ to make the encoded Base64 results more URL-SAFE.
     * This table is only used when the Base64's mode is set to URL-SAFE.
     */
    private static final byte[] URL_SAFE_ENCODE_TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };

    private static final int DEFAULT_FILE_BUFFER_SIZE = C.SizeUnits.FILE_DOWNLOAD_BUFFER_SIZE;

    @StringDef({MessageDigestAlgorithm.CODE_MD5, MessageDigestAlgorithm.CODE_SHA_1, MessageDigestAlgorithm.CODE_SHA_224, MessageDigestAlgorithm.CODE_SHA_256,
            MessageDigestAlgorithm.CODE_SHA_384, MessageDigestAlgorithm.CODE_SHA_512, MessageDigestAlgorithm.CODE_SHA_512224, MessageDigestAlgorithm.CODE_SHA_512256})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageDigestAlgorithm {
        String CODE_MD5 = "MD5";
        String CODE_SHA_1 = "SHA-1";//default
        String CODE_SHA_224 = "SHA-224";
        String CODE_SHA_256 = "SHA-256";
        String CODE_SHA_384 = "SHA-384";
        String CODE_SHA_512 = "SHA-512";
        String CODE_SHA_512224 = "SHA-512224";
        String CODE_SHA_512256 = "SHA-512256";
    }


    public static byte[] base64Encode(String source){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            return Base64.getEncoder().encode(source.getBytes());
//        }else{
//
//        }
        return null;
    }

    /**
     * 获取字节数组的消息摘要
     * @param bytes
     * @param messageDigestAlgorithm
     * @return
     */
    public static String getMessageDigest(byte[] bytes, @MessageDigestAlgorithm String messageDigestAlgorithm){
        byte[] digest = null;
        if(bytes != null && messageDigestAlgorithm != null){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(messageDigestAlgorithm);
                messageDigest.update(bytes);
                digest =  messageDigest.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return RadixConverter.byteArray2HexString(new StringBuilder(), digest).toString();
    }

    /**
     * 获取字符串的消息摘要
     * @param source
     * @param messageDigestAlgorithm
     * @return
     */
    public static String getMessageDigest(String source, @MessageDigestAlgorithm String messageDigestAlgorithm){
        if(source != null){
            return getMessageDigest(source.getBytes(), messageDigestAlgorithm);
        }
        return C.Strings.EMPTY;
    }

    /**
     * 根据给定的algorithm,获取文件的消息摘要
     * @param file
     * @param messageDigestAlgorithm
     * @return
     */
    private static byte[] getMessageDigest(File file, @MessageDigestAlgorithm String messageDigestAlgorithm, int bufferSize){
        FileInputStream fileInputStream = null;
        byte[] digest = null;
        if(file != null && messageDigestAlgorithm != null){
            bufferSize = IOUtil.checkMinBufferSize(bufferSize);
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(messageDigestAlgorithm);
                byte[] buffer = new byte[bufferSize];
                fileInputStream = new FileInputStream(file);
                int readLength = 0;
                while(IOUtil.hasMoreContent(readLength = fileInputStream.read(buffer))){
                    messageDigest.update(buffer, 0 , readLength);
                }
                digest = messageDigest.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                CloseUtil.closeIOQuietly(fileInputStream);
            }
        }
        return digest;
    }

    /**
     * 根据提供的消息摘要算法，计算出文件的消息摘要。
     * @param file
     * @param messageDigestAlgorithm
     * @param bufferSize
     * @return
     */
    public static String messageDigest(File file , @MessageDigestAlgorithm String messageDigestAlgorithm, int bufferSize){
        byte[] messageDigest = getMessageDigest(file, messageDigestAlgorithm, bufferSize);
        return RadixConverter.byteArray2HexString(new StringBuilder(), messageDigest).toString();
    }

    /**
     * 生成文件的MD5值
     * @param file
     * @return
     */
    public static String getMD5(File file ,int bufferSize){
        return messageDigest(file, MessageDigestAlgorithm.CODE_MD5, bufferSize);
    }

    /**
     * 生成文件的MD5值
     * @param file
     * @return
     */
    public static String getMD5(File file){
        return messageDigest(file, MessageDigestAlgorithm.CODE_MD5, DEFAULT_FILE_BUFFER_SIZE);
    }


    /**
     * 生成文件的SHA_1值
     * @param file
     * @return
     */
    public static String getSHA1(File file , int bufferSize){
        return messageDigest(file, MessageDigestAlgorithm.CODE_SHA_1, bufferSize);
    }

    /**
     * 生成文件的SHA_1值
     * @param file
     * @return
     */
    public static String getSHA1(File file){
        return messageDigest(file, MessageDigestAlgorithm.CODE_SHA_1, DEFAULT_FILE_BUFFER_SIZE);
    }

    /**
     * 生成文件的SHA_1值
     * @param file
     * @return
     */
    public static String getSHA256(File file , int bufferSize){
        return messageDigest(file, MessageDigestAlgorithm.CODE_SHA_256, bufferSize);
    }


    /**
     * 生成文件的SHA_1值
     * @param file
     * @return
     */
    public static String getSHA256(File file){
        return messageDigest(file, MessageDigestAlgorithm.CODE_SHA_256, DEFAULT_FILE_BUFFER_SIZE);
    }

}
