package org.fast.crawler.core.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Coder {

	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	public static final String KEY_ALGORTHM = "RSA";
	protected final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String urlEncode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String urlDecode(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();
	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	public static RSACoder newRSACoder() {
		RSACoder coder = new RSACoder();
		return coder;
	}

	public static class RSACoder extends Coder {

		/**
		 * 用私钥加密
		 * 
		 * @param data
		 *            加密数据
		 * @param key
		 *            密钥
		 * @return
		 * @throws Exception
		 */
		public byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
			// 解密密钥
			RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(key, 16), new BigInteger("10001"));
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key privateKey = keyFactory.generatePublic(privateKeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		}

		/**
		 * 用私钥解密
		 * 
		 * @param data
		 *            加密数据
		 * @param key
		 *            密钥
		 * @return
		 * @throws Exception
		 */
		public byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
			// 对私钥解密
			RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(key, 16), new BigInteger("65537"));
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key privateKey = keyFactory.generatePublic(privateKeySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		}

		/**
		 * 用公钥加密
		 * 
		 * @param data
		 *            加密数据
		 * @param key
		 *            密钥
		 * @return
		 * @throws Exception
		 */
		public byte[] encryptByPublicKey(byte[] data, String key) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException,
		        IllegalBlockSizeException {
			// 对公钥解密
			RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(key, 16), new BigInteger("65537"));
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key publicKey = keyFactory.generatePublic(publicKeySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		}

		/**
		 * 用公钥解密
		 * 
		 * @param data
		 *            加密数据
		 * @param key
		 *            密钥
		 * @return
		 * @throws Exception
		 */
		public byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
			// 对私钥解密
			RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(key, 16), new BigInteger("65537"));
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key publicKey = keyFactory.generatePublic(publicKeySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		}
	}

}
