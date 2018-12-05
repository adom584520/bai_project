package com.zckj.tool.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.zckj.service.VersionInfoBean;

/**
 * 常量类
 * 
 * @author liuxiaomeng
 * @datetime 2015-7-31_下午4:09:02
 */
public class ConstUtils {
	/** 所有版本数据缓存 */
	public static Map<String, Map<Integer, VersionInfoBean>> ch2IndexId2VerMap = new HashMap<String, Map<Integer, VersionInfoBean>>();

	/** 所有版本->序号ID缓存 */
	public static Map<String, Map<String, Integer>> ch2VerId2IndexIdMap = new HashMap<String, Map<String, Integer>>();

	/** 合成更新包进度数据缓存 */
	public static List<String> fileMergeInfoList = new ArrayList<String>();

	/** 服务器接收上传文件的根目录 */
	public static String uploadRootPath = FileUtils.getSetConfigPors().getProperty("uploadRootPath");//"D:/test/";
	static {
		ConstUtils.uploadRootPath = ServletActionContext.getServletContext().getRealPath("/").replace('\\', '/') + ConstUtils.uploadRootPath;
	}

	/** 最新版本ID */
	public static String versionId = null;

	/** 最新版本序号 */
	public static int maxId = 0;

	public static final String f_separator = "/";

	/** 更新版本删除日志文件名 */
	public static String deleteFileName = FileUtils.getSetConfigPors().getProperty("deleteFileName");//"delete.txt";

	/** 更新版本删除文件列表缓存 */
	public static List<String> deleteFileList = new ArrayList<String>();

	/** 更新包名后缀 */
	public static String updatePkgSuffix = FileUtils.getSetConfigPors().getProperty("updatePkgSuffix");//"_update.zip";

	/** 合并根文件夹 */
	public static String mergePath = FileUtils.getSetConfigPors().getProperty("mergePath");//"mergePath/";

	/** 合并版本说明根文件夹 */
	public static String manifestPath = FileUtils.getSetConfigPors().getProperty("manifestPath");//"manifest/";

	/** 合并版本信息根文件夹 */
	public static String vmanifestPath = FileUtils.getSetConfigPors().getProperty("vmanifestPath");//"vmanifest/";

	/** 更新版本包地址 */
	public static String packageUrl = FileUtils.getSetConfigPors().getProperty("packageUrl");
	
	/** 备用更新版本包地址 */
	public static String packageUrl2 = FileUtils.getSetConfigPors().getProperty("packageUrl2");

	/** 更新版本请求 */
	public static String lastVersionUrl = FileUtils.getSetConfigPors().getProperty("lastVersionUrl");

	/** 版本说明文件地址 */
	public static String remoteManifestUrl = FileUtils.getSetConfigPors().getProperty("remoteManifestUrl");

	/** 版本地址 */
	public static String remoteVersionUrl = FileUtils.getSetConfigPors().getProperty("remoteVersionUrl");

	/** 版本说明文件名称 */
	public static String pmfName = FileUtils.getSetConfigPors().getProperty("pmfName");//project.manifest

	/** 版本信息文件名称 */
	public static String vmfName = FileUtils.getSetConfigPors().getProperty("vmfName");

	/** 数据库连接名称 */
	public static String name = FileUtils.getSetConfigPors().getProperty("name");

	/** 数据库连接地址 */
	public static String url = FileUtils.getSetConfigPors().getProperty("url");

	/** 数据库登陆账户 */
	public static String user = FileUtils.getSetConfigPors().getProperty("user");

	/** 数据库登陆密码 */
	public static String password = FileUtils.getSetConfigPors().getProperty("password");
	
	public static String ZCTECH_TEST = "1215";
}
