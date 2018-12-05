package com.zckj.tool.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 数据AES格式加密
 * 
 * @author liuxiaomeng
 * @datetime 2015-8-12_下午1:40:53
 */
public class EncrypAES {
	/**
	 * 在java中调用sun公司提供的3DES加密解密算法时，需要使用到$JAVA_HOME/jre/lib/目录下如下的4个jar包： jce.jar security/US_export_policy.jar 　　 security/local_policy.jar ext/sunjce_provider.jar
	 */
	/** 默认密钥，实际项目中可配置注入或数据库读取 */
	private static String defaultKey = "ZCTECHENCRYPTION";

	/** 加密工具 */
	private Cipher encryptCipher = null;

	/** 解密工具 */
	private Cipher decryptCipher = null;

	private static EncrypAES instance = new EncrypAES(EncrypAES.defaultKey);

	public static EncrypAES getInstance() {
		return EncrypAES.instance;
	}

	private EncrypAES(String keyvalue) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		if (keyvalue == null) {
			keyvalue = EncrypAES.defaultKey;
		}
		byte[] arrBTmp = null;
		try {
			arrBTmp = keyvalue.getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 创建一个空的length位字节数组（默认值为0）
		final byte[] arrB = new byte[16];
		// 将原始字节数组转换为8位
		for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		final Key key = new javax.crypto.spec.SecretKeySpec(arrB, "AES");
		// 生成Cipher对象,指定其支持的DES算法
		try {
			this.encryptCipher = Cipher.getInstance("AES");
			this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			this.decryptCipher = Cipher.getInstance("AES");
			this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (final NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (final InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] encryptor(final String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] src = null;
		try {
			src = str.getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.encryptCipher.doFinal(src);
	}

	/**
	 * 对字符串加密，返回加密后字符串
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String encryptorString(final String str) {
		try {
			final byte[] src = this.encryptor(str);
			return this.parseByte2HexStr(src);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decryptor(final byte[] buff) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return this.decryptCipher.doFinal(buff);
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public String decryptorString(final String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		final byte[] buff = this.parseHexStr2Byte(str);
		final byte[] rs = this.decryptCipher.doFinal(buff);
		return new String(rs, "UTF-8");
	}

	/**
	 * 将二进制转换成16进制，转字符串
	 * 
	 * @param buf 二进制
	 * @return
	 */
	public String parseByte2HexStr(final byte buf[]) {
		final StringBuffer sb = new StringBuffer();
		for (final byte element : buf) {
			String hex = Integer.toHexString(element & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 字符串 转 16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public byte[] parseHexStr2Byte(final String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		final byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < (hexStr.length() / 2); i++) {
			final int high = Integer.parseInt(hexStr.substring(i * 2, (i * 2) + 1), 16);
			final int low = Integer.parseInt(hexStr.substring((i * 2) + 1, (i * 2) + 2), 16);
			result[i] = (byte) ((high * 16) + low);
		}
		return result;
	}

	/**
	 * @param args
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 */
	public static void main(final String[] args) throws Exception {
		final EncrypAES de1 = EncrypAES.getInstance();
		//String msg = "郭德纲-搞笑相声全集";
		final String msg = "6bc1bee22e409f96e93d7e117393172a";
		final byte[] encontent = de1.encryptor(msg);
		final String st = de1.parseByte2HexStr(encontent);
		final byte[] decontent = de1.decryptor(de1.parseHexStr2Byte(st));
		//		OutputStream out = new FileOutputStream("D:\\AES\\2.txt");
		//	out.write(encontent);
		//out.close();
		//		InputStream in = new FileInputStream("D:\\AES\\2.txt");
		//		byte[] ib = new byte[in.available()];
		//		in.read(ib);
		//		byte[] deib = de1.Decryptor(ib);
		System.out.println("明文是:" + msg);
		System.out.println("加密后:" + de1.parseByte2HexStr(encontent));
		//System.out.println("加密后:" + new String(encontent));
		System.out.println("解密后:" + new String(decontent));
	}
}