package com.pbtd.manager.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 需要使用配置文件注入的值都在这里注入
 * 
 * @author JOJO
 *
 */
@Component
public class ValueInjectConstant {
	@Autowired
	private Environment environment;
	/**
	 * session的过期时间
	 */
	public static Integer sessionActiveTime = 7200;
	public static String uploadImageUrl = "47.93.10.26/uploadfile";
	public static String uploadImageLocalhostUrl = "D:/upload/image/";

	public void setSessionActiveTime() {
		String mySessionActiveTime = environment.getProperty("mySessionActiveTime");
		if (mySessionActiveTime != null && mySessionActiveTime.length() > 0) {
			sessionActiveTime = Integer.parseInt(mySessionActiveTime);
		}
		String myUploadImageUrl = environment.getProperty("my_upload_image_url");
		if (myUploadImageUrl != null && myUploadImageUrl.length() > 0) {
			uploadImageUrl = myUploadImageUrl;
		}
		String myUploadImageLocalhostUrl = environment.getProperty("my_upload_image_localhost_url");
		if (myUploadImageLocalhostUrl != null && myUploadImageLocalhostUrl.length() > 0) {
			uploadImageLocalhostUrl = myUploadImageLocalhostUrl;
		}
	}

	public void init() {
		setSessionActiveTime();
	}
}
