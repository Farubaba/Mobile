package com.silence.rootfeature.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.silence.rootfeature.logger.LogManager;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author suetming ( suetming.ma@gmail.com )
 * 
 *         2014-10-31 下午12:05:55
 * 
 */
final class RSACoder {

	public static final String TAG = RSACoder.class.getSimpleName();
	// 非对称加密密钥算法
	public static final String KEY_ALGORITMA = "RSA";

	// 公钥
	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String PRIVITE_KEY = "RSAPrivateKey";

	/**
	 * RSA密钥长度 默认1024位 密钥长度必须是64的倍数 范围在512-65536之间
	 */

	private static final int KEY_SIZE = 1024;

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key)
			throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITMA);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 * @param key
	 * @returnØ
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key)
			throws Exception {
		// 取得公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITMA);
		// 生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key)
			throws Exception {
		// 取得公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
		// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITMA);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITMA);
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		// 对数据加密
		// byte[] bytes = publicKey.getEncoded();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key)
			throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITMA);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVITE_KEY);
		return key.getEncoded();
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {

		// 实例化密钥对生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(KEY_ALGORITMA);

		// 初始化密钥对生成器
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 封装密钥
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVITE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 加密数据,如果加密失败，则返回空字符串""
	 * @param realPublicKey
	 * @param sourceString
	 * @return
	 */
	public synchronized static String encrypt(String realPublicKey, String sourceString) throws Exception {
		String cryptValue = "";
		if(TextUtils.isEmpty(sourceString) || TextUtils.isEmpty(sourceString.trim())){
			return cryptValue;
		}else{
			byte[] cryptPasswordByte = RSACoder.encryptByPublicKey(
					sourceString.getBytes(),
					Base64.decode(realPublicKey, Base64.NO_WRAP));
			cryptValue = Base64.encodeToString(cryptPasswordByte, Base64.NO_WRAP);
		}
		LogManager.getInstance().e(TAG, "RSACoder.encrypt sourceString = " + sourceString + " ---- cryptValue = " + cryptValue);
		return cryptValue;
	}



	/**********************************************RSA全站加密相关start******************************************/
	public static Cipher fengjrRSACipher;
	/**
	 * 获取全站唯一的RSA Cipher
	 * @return
	 */
	public static Cipher getFengjrRSACipher(){
		if(fengjrRSACipher == null){
			try {
				fengjrRSACipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
		}
		return fengjrRSACipher;
	}

	/**
	 * 从服务器获取到的publicKey是base64加密的，解密后生成PublicKey对象
	 * @param base64PublicKey
	 * @return
	 * @throws Exception
	 */
	public static final PublicKey getFengjrPublicKey(String base64PublicKey) throws Exception {
		//base64的publicKey字符串解密
		byte[] realPublicKey = Base64.decode(base64PublicKey, Base64.NO_WRAP);
		// 取得公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(realPublicKey);
		// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITMA);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITMA);
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		//Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		return publicKey;
	}

	/**
	 * 添加Block
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	private static byte[] append(byte[] prefix, byte[] suffix){
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i=0; i< prefix.length; i++){
			toReturn[i] = prefix[i];
		}
		for (int i=0; i< suffix.length; i++){
			toReturn[i+prefix.length] = suffix[i];
		}
		return toReturn;
	}

	/**
	 * 分块加密
	 * @param bytes
	 * @param mode
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static byte[] blockCipher(byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException {
		// string initialize 2 buffers.
		// scrambled will hold intermediate results
		byte[] scrambled = new byte[0];

		// toReturn will hold the total result
		byte[] toReturn = new byte[0];
		// if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
		int length = (mode == Cipher.ENCRYPT_MODE)? getFengjrRSACipher().getBlockSize() : 128;

		// another buffer. this one will hold the bytes that have to be modified in this step
		byte[] buffer = new byte[length];

		for (int i=0; i< bytes.length; i++){

			// if we filled our buffer array we have our block ready for de- or encryption
			if ((i > 0) && (i % length == 0)){
				//execute the operation
				scrambled = getFengjrRSACipher().doFinal(buffer);
				// add the result to our total result.
				toReturn = append(toReturn,scrambled);
				// here we calculate the length of the next buffer required
				int newlength = length;

				// if newlength would be longer than remaining bytes in the bytes array we shorten it.
				if (i + length > bytes.length) {
					newlength = bytes.length - i;
				}
				// clean the buffer array
				buffer = new byte[newlength];
			}
			// copy byte into our buffer.
			buffer[i%length] = bytes[i];
		}

		// this step is needed if we had a trailing buffer. should only happen when encrypting.
		// example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
		scrambled = getFengjrRSACipher().doFinal(buffer);

		// final step before we can return the modified data.
		toReturn = append(toReturn,scrambled);

		return toReturn;
	}

	/**
	 * 加密
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
	public static String encryptFengjrRSA(String base64PublicKey, String plaintext) throws Exception {
		getFengjrRSACipher().init(Cipher.ENCRYPT_MODE, getFengjrPublicKey(base64PublicKey));
		byte[] bytes = plaintext.getBytes("UTF-8");

		byte[] encrypted = blockCipher(bytes, Cipher.ENCRYPT_MODE);

		String encodedString = Base64.encodeToString(encrypted, Base64.NO_WRAP);
		LogManager.getInstance().dt(TAG,"encryptFengjrRSA加密后："+encodedString);
		return encodedString;
	}

	public static String encryptFengjrRSA2(String base64PublicKey, String plaintext) throws Exception {
		return SecurityUtil.encrypt(plaintext,base64PublicKey);
	}

	public static String encryptFengjrRSA3(String base64PublicKey, String plaintext) throws Exception {
		Cipher cipher = getFengjrRSACipher();
		cipher.init(Cipher.ENCRYPT_MODE, getFengjrPublicKey(base64PublicKey));
		byte[] data = plaintext.getBytes("UTF-8");
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
		int leavedSize = data.length % blockSize;
		int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length
				/ blockSize;
		byte[] raw = new byte[outputSize * blocksSize];
		int i = 0;
		while (data.length - i * blockSize > 0) {
			LogManager.getInstance().e(TAG,"--->len="+ data.length);
			if (data.length - i * blockSize > blockSize)
				cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
			else
				cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i
						* outputSize);
			// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
			i++;
		}
		String encodedString = Base64.encodeToString(raw, Base64.NO_WRAP);
		LogManager.getInstance().dt(TAG,"encryptFengjrRSA加密后："+encodedString);
		return encodedString;
	}

	/*************************************************RSA全站加密相关end*************************************************/


}
