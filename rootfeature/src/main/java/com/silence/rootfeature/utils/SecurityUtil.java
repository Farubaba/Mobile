package com.silence.rootfeature.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import com.silence.rootfeature.logger.LogManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 加解密工具类
 */
public final class SecurityUtil {

	public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVCgIQCuhESwOO+seJdo2xUd/f/bk5a5qUsB4ZxarSXB6vMJpJr1P2PaVRI/gLfzYewY7Zfoh1K3HCbdHF1QKdTywtY7Tak+WKcCZ/27ICjBypPBZNGWVZY8P6i6exy6VaHa2iV082Mbe2q8+MVfjtB32i4yucJqRVUPmRvLqcqwIDAQAB";

	public static String encrypt(String expressly, String publicKey) throws Exception {
		PublicKey publicKeyObj = getKeyPairPublicKey(publicKey);
		return encryptRSA2Base64String(expressly, publicKeyObj).trim();
	}

	/**
	 * @param expressly
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String expressly) throws Exception {
		PublicKey publicKeyObj = getKeyPairPublicKey(PUBLICKEY);
		return encryptRSA2Base64String(expressly, publicKeyObj).trim();
	}

	private static PublicKey getKeyPairPublicKey(String key) throws Exception {
		byte[] bytes = Base64.decode(key, Base64.NO_WRAP);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	@SuppressLint("TrulyRandom")
	private static String encryptRSA2Base64String(String encryptStr, PublicKey publicKey) throws Exception {
		byte[] encrypt = encryptStr.getBytes("UTF-8");
//		Cipher cipher = Cipher.getInstance("RSA", "BC");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		SecureRandom random = new SecureRandom();
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] enBytes = getEncryptStream(encrypt, cipher);
		return Base64.encodeToString(enBytes, Base64.NO_WRAP);
	}

	private static byte[] getEncryptStream(byte[] encrypt, Cipher cipher) throws Exception {
		byte[] encryptStream = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			InputStream is = new ByteArrayInputStream(encrypt);
			LogManager.getInstance().e("getBlockSize", "getBlockSize = " + cipher.getBlockSize());
			//原来长度117只支持1024,为了动态支持不同长度，修改成cipher.getBlockSize()
			byte[] data = new byte[cipher.getBlockSize()];
			int num = is.read(data);
			while (num != (-1)) {
				baos.write(cipher.doFinal(data, 0, num));
				baos.flush();
				num = is.read(data);
			}
			encryptStream = baos.toByteArray();
			baos.close();
			baos = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != baos) {
				baos.close();
			}
			baos = null;
		}

		return encryptStream;
	}
}
