package com.pbtd.util;

import java.io.File;

import org.apache.log4j.Logger;

public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 新建目录
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在
			logger.info("目录已存在，无需创建");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			logger.info("创建目录成功：" + destDirName);
			return true;
		} else {
			logger.info("创建目录失败");
			return false;
		}
	}

}
