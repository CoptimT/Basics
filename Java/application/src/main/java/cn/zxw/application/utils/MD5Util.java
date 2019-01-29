package cn.zxw.application.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 */
public final class MD5Util {
	
	/**
	 * MD5 加密
	 * @param content 需要加密的数据是{@link String}类型的字符串
	 * @return 返回{@link String}类型的字符串
	 * @throws Exception 抛出异常
	 */
	public static String md5AsString(String content) throws Exception {
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		return Hex.encodeHexString(mDigest.digest(content.getBytes("UTF-8"))); 
	}
	
	/**
	 * MD5 加密
	 * @param content 需要加密的数据是{@link byte}类型数组
	 * @return 返回{@link String}类型的字符串
	 * @throws Exception 
	 */
	public static String md5AsString(byte[] content) throws Exception {
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		return Hex.encodeHexString(mDigest.digest(content));
	}
	
	/**
	 * MD5 加密
	 * @param content 需要加密的数据是{@link String}类型的数据
	 * @return 返回{@link byte}类型的数组
	 * @throws Exception 抛出异常
	 */
	public static byte[] md5(String content) throws Exception {
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		return mDigest.digest(content.getBytes("UTF-8")); 
	}
	
	/**
	 * MD5 加密
	 * @param content 需要加密的数据是{@link byte}类型数组
	 * @return 返回{@link byte}类型的数组
	 */
	public static byte[] md5(byte[] content) throws Exception {
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		return mDigest.digest(content); 
	}
	
	/**
	 * MD5方法
	 * @param text 明文
	 * @param key 密钥
	 * @return 密文
	 * @throws Exception
	 */
	public static String md5(String text, String key) throws Exception {
		// 加密后的字符串
		return DigestUtils.md5Hex(text + key);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(md5("hello","world"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
