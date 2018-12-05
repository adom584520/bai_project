package com.pbtd.manager.system.util;

/**
 * 用户信息常量
 * 
 * @author JOJO
 *
 */
public class LoginInfoConstant {
	private LoginInfoConstant() {
	}

	/**
	 * 超级管理员
	 */
	public static final Integer ADMIN_SYSTEM_MANAGER = 1;
	/**
	 * 普通管理员
	 */
	public static final Integer GENERAL_SYSTEM_MANAGER = 2;
	/**
	 * 正常状态
	 */
	public static final Integer NATURAL_STATUS = 1;
	/**
	 * 禁用状态
	 */
	public static final Integer DISABLED_STATUS = 2;

	/**
	 * 账号初始密码
	 */
	public static final String INITIAL_PASSWORD = "123456";

	/**
	 * MD5加密字符前缀
	 */
	public static final String MD5_CIPHERTEXT_PREFIX = "pbtd";
	
	/**
	 * MD5加密字符后缀
	 */
	public static final String MD5_CIPHERTEXT_SUFFIX = "pbtd";

	/**
	 * 默认管理员账号名称
	 */
	public static final String ADMIN_LOGININFO_NAME = "admin";
	
	/**
	 * 默认管理员账号密码
	 */
	public static final String ADMIN_LOGININFO_PASSWORD = "123456";
	
	public static String encryption(String password){
		return MD5.encode(new StringBuilder().append(MD5_CIPHERTEXT_PREFIX).append(password).append(MD5_CIPHERTEXT_SUFFIX).toString());
	}
}
