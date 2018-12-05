/**
 * 
 */
package com.zckj.service;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.zckj.files.compress.FlodCompress;
import com.zckj.tool.utils.ConstUtils;
import com.zckj.tool.utils.DataBasUtils;
import com.zckj.tool.utils.FileUtils;

/**
 * 更新包进行差异版本合并
 * 
 * @author liuxiaomeng
 * @datetime 2015-7-31_下午4:13:34
 */
public class UpdateFilesMerge extends ActionSupport {
	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午4:27:42
	 */
	private static final long serialVersionUID = 3734680727833798988L;

	public String filesMergeProc() {
		try {
			final JSONObject jobj = new JSONObject();
			if (ConstUtils.fileMergeInfoList.contains("fin") && (ConstUtils.fileMergeInfoList.size() == 1)) {
				jobj.put("fin", "fin---------");
				ConstUtils.fileMergeInfoList.remove(0);
			} else if (!ConstUtils.fileMergeInfoList.isEmpty()) {
				String tmp = "";
				for (final String s : ConstUtils.fileMergeInfoList) {
					tmp = tmp + "\n" + s;
				}
				jobj.put("info", tmp);
				if (ConstUtils.fileMergeInfoList.contains("fin")) {
					jobj.put("fin", "fin---------");
				}
				ConstUtils.fileMergeInfoList.clear();
				//ConstUtils.fileMergeInfoList.remove(0);
			}
			//System.out.println(System.currentTimeMillis() + "]  合成信息  ： " + jobj);
			if (!jobj.isEmpty()) {
				final HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jobj.toString().replace('\"', '\''));
				response.getWriter().flush();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	/**
	 * 更新包进行差异版本合并
	 * 
	 * @param newVersionZip
	 * @param versionId
	 */
	public String  filesMerge() {
		final JSONObject jobj = new JSONObject();
		final HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("json");
		response.setCharacterEncoding("UTF-8");
		boolean falg=false;
		try {
			ConstUtils.fileMergeInfoList.clear();
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String versionId = request.getParameter("versionId");
			final String id = request.getParameter("id");
			final String channel = request.getParameter("channel");
			final VersionInfoBean vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(Integer.parseInt(id));
			final String channelPath = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator;
			//最新差异版本解压路径
			final String newPath = channelPath + versionId + ConstUtils.f_separator;
			if ((null == vib) || (null == vib.getFilePath())) {
				falg= true ;
				return Action.SUCCESS;
			}
			final File newVersionZip = new File(vib.getFilePath());
			//最新差异版本解压，便于与前差异版本合并
			// FlodUnCompress.getInstance().unZipFiles(newVersionZip, newPath + "update" + ConstUtils.f_separator);
			//上传最新差异版本包名称
			final String zipName = "update";//newVersionZip.getName().replace(".zip", "");
			//强制更新版本
			VersionInfoBean forceUpdateInfo = null;
			//版本号拆分计算，稍后确定版本号格式需做调整
			final int indexIdInt = Integer.parseInt(id);
			if (indexIdInt > 1) {
				int perId = indexIdInt - 1;
				VersionInfoBean pre1Vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(perId);
				for (int i = perId; i > 0; i--) {
					pre1Vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(i);
					if(null != pre1Vib) {
						perId = i;
						break;
					}
				}
				for (int i = indexIdInt - 1; i > 0; i--) {
					final VersionInfoBean preVib = ConstUtils.ch2IndexId2VerMap.get(channel).get(i);
					if (null == preVib) {
						continue;
					}
					//如果是强制更新版本
					if(preVib.getForceUpdate()){
						forceUpdateInfo = preVib;
						break;
					}
					//判断是否有相应版本更新的数据包
					//if(!new File(ConstUtils.uploadRootPath + i + "_" + newVersionZip.getName()).exists())
					//	continue;
					final String margeDirName = versionId + "_" + preVib.getVersionId();
					final String margePath = channelPath + ConstUtils.mergePath + margeDirName;
					if (i == perId) {//只差一个更新包
						final String perVersionPath = channelPath + preVib.getVersionId() + ConstUtils.f_separator;
						// 复制前一版本文件到合并目录
						FileUtils.copyDirectiory(perVersionPath + zipName, margePath + ConstUtils.f_separator + zipName);
						//更新版本删除旧数据
						FileUtils.findFilesToDelete(margePath + ConstUtils.f_separator + zipName, ConstUtils.deleteFileList);
						// 复制当前版本到合并目录
						FileUtils.copyDirectiory(newPath + zipName + ConstUtils.f_separator, margePath + ConstUtils.f_separator + zipName);
						// 压缩合并后的更新包
						FlodCompress.getInstance().zip(margePath + ConstUtils.f_separator + zipName, channelPath + margeDirName + ConstUtils.updatePkgSuffix);
						ConstUtils.fileMergeInfoList.add(margeDirName + ConstUtils.updatePkgSuffix);
						//删除前一版本更新包文件夹
//						FileUtils.delFolder(perVersionPath + ConstUtils.f_separator + zipName);
						//文件MD5密码
						final String md5 = FileUtils.getMd5ByFile(channelPath + margeDirName + ConstUtils.updatePkgSuffix);
						//生成版本说明文件
						this.createVerManifest(vib, md5, margeDirName);
					} else {//差多个更新包，用当前更新包覆盖跟前一版本合并目录
						final String perMargeDirName = pre1Vib.getVersionId() + "_" + preVib.getVersionId();
						final String perMargePath = channelPath + ConstUtils.mergePath + perMargeDirName;
						final File perMargePathDir = new File(perMargePath);
						if (perMargePathDir.exists()) {//已存在前版本合并目录
							//更新版本删除旧数据
							FileUtils.findFilesToDelete(perMargePath, ConstUtils.deleteFileList);
							// 复制当前版本到合并目录
							FileUtils.copyDirectiory(newPath + zipName + ConstUtils.f_separator, perMargePath + ConstUtils.f_separator + zipName + ConstUtils.f_separator);
							// 重命名合并目录为当前版本号
							perMargePathDir.renameTo(new File(margePath));
							// 压缩合并后的更新包
							FlodCompress.getInstance().zip(margePath + ConstUtils.f_separator + zipName, channelPath + margeDirName + ConstUtils.updatePkgSuffix);
							ConstUtils.fileMergeInfoList.add(margeDirName + ConstUtils.updatePkgSuffix);
							//FileUtils.delFile(ConstUtils.uploadRootPath + perMargeDirName + ConstUtils.updatePkgSuffix);
							//文件MD5密码
							final String md5 = FileUtils.getMd5ByFile(channelPath + margeDirName + ConstUtils.updatePkgSuffix);
							//生成版本说明文件
							this.createVerManifest(vib, md5, margeDirName);
						} else {
							final File margePathDir = new File(margePath);
							if (margePathDir.exists()) {
								FileUtils.delFolder(margePath);
							}
							margePathDir.mkdir();
							for(int j = preVib.getId();j <= pre1Vib.getId();j++){
								VersionInfoBean versionInfo = ConstUtils.ch2IndexId2VerMap.get(channel).get(j);
								final String perVersionPath = channelPath + versionInfo.getVersionId() + ConstUtils.f_separator;
								
								FileUtils.findFilesToDelete(margePath+ ConstUtils.f_separator + zipName, ConstUtils.deleteFileList);
								FileUtils.copyDirectiory(perVersionPath + zipName, margePath+ ConstUtils.f_separator + zipName);
							}
							
							FileUtils.findFilesToDelete(margePath+ ConstUtils.f_separator + zipName, ConstUtils.deleteFileList);
							// 复制当前版本到合并目录
							FileUtils.copyDirectiory(newPath + zipName + ConstUtils.f_separator, margePath+ ConstUtils.f_separator + zipName);
							// 压缩合并后的更新包
							FlodCompress.getInstance().zip(margePath + ConstUtils.f_separator + zipName, channelPath + margeDirName + ConstUtils.updatePkgSuffix);
							ConstUtils.fileMergeInfoList.add(margeDirName + ConstUtils.updatePkgSuffix);
							//FileUtils.delFile(ConstUtils.uploadRootPath + perMargeDirName + ConstUtils.updatePkgSuffix);
							//文件MD5密码
							final String md5 = FileUtils.getMd5ByFile(channelPath + margeDirName + ConstUtils.updatePkgSuffix);
							//生成版本说明文件
							this.createVerManifest(vib, md5, margeDirName);
						}
					}
				}
				//newVersionZip.renameTo(new File(ConstUtils.uploadRootPath + versionId + ConstUtils.updatePkgSuffix));
			} //else {
				//FileUtils.copyFile(newVersionZip, new File(ConstUtils.uploadRootPath + versionId + ConstUtils.updatePkgSuffix));
			
			//如果有强制更新的版本包,比如说是 0.1,当前版本是1.0,强制更新版本是0.5
			if(forceUpdateInfo != null){
				//强制更新目录
				String forceUpdatePath = channelPath + "forceUpdate";
				
				try{
					delFile(forceUpdatePath);
				}catch(Exception ex){
					
				}
				
				final File updateDir = new File(forceUpdatePath);
				updateDir.mkdir();
				
				int version1Id = 0;
				for (int i = indexIdInt - 1; i > 0; i--) {
					final VersionInfoBean preVib = ConstUtils.ch2IndexId2VerMap.get(channel).get(i);
					if (preVib != null && preVib.getBuildState() == 1) {
						version1Id = i;
					}
				}
				
				VersionInfoBean version1Info = ConstUtils.ch2IndexId2VerMap.get(channel).get(version1Id);
				VersionInfoBean pre1Vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(indexIdInt - 1);
				//最终的文件名称
				final String finishFileName = versionId + "_" + version1Info.getVersionId(); 
				
				String versionString = pre1Vib.getVersionId()+ "_" + version1Info.getVersionId();
				
				String preForceUpdateZip = channelPath + versionString + ConstUtils.updatePkgSuffix;
				//首先看有没有0.9-0.1.zip,如果有,直接和1.0.zip合并就行了
				final File margePathDir = new File(preForceUpdateZip);
				if (margePathDir.exists()) {
					FlodCompress.getInstance().unzip(preForceUpdateZip, forceUpdatePath);
					// 复制当前版本到合并目录
					FileUtils.copyDirectiory(newPath + zipName + ConstUtils.f_separator, forceUpdatePath);
					// 压缩合并后的更新包
					FlodCompress.getInstance().zip(forceUpdatePath, channelPath + finishFileName + ConstUtils.updatePkgSuffix);
					
					ConstUtils.fileMergeInfoList.add(finishFileName + ConstUtils.updatePkgSuffix);
					//文件MD5密码
					final String md5 = FileUtils.getMd5ByFile(channelPath + finishFileName + ConstUtils.updatePkgSuffix);
					//生成版本说明文件
					this.createVerManifest(vib, md5, finishFileName);
				}else{
					//TODO 一般情况下,不会出现这样的情况,如果出现,将来这里需要补充逻辑
					//如果没有,找到最小版本 0.1,是否有0.5-0.1.zip包,如果有,直接解压;是否有1.0-0.6.zip,如果有,直接解压, 解压的2个目录 合并;
					//如果没有zip包,就需要循环版本列表, 同164行那样,一个一个版本合并
				}
			}
			
			final String nvzName = newVersionZip.getAbsolutePath().replace(newVersionZip.getName(), "").replace('\\', '/');
			// 压缩合并后的更新包
			FlodCompress.getInstance().zip(nvzName + ConstUtils.f_separator + zipName+ ConstUtils.f_separator + versionId, channelPath + versionId + ConstUtils.updatePkgSuffix);
			final String md5 = FileUtils.getMd5ByFile(channelPath + versionId + ConstUtils.updatePkgSuffix);
			//生成版本说明文件
			this.createVerManifest(vib, md5, versionId);
			//}
			//合并更新包进度信息
			ConstUtils.fileMergeInfoList.add(versionId + ConstUtils.updatePkgSuffix);
			ConstUtils.fileMergeInfoList.add("fin");
			//System.out.println("saveBuildState : " + ConstUtils.fileMergeInfoList);
			//最新差异版本号记录文件
			//FileUtils.writerTxt(ConstUtils.uploadRootPath + ConstUtils.verLogFileName, versionId);
			ConstUtils.maxId = indexIdInt;
			//System.out.println("更新内存版本ID： " + ConstUtils.versionId);
			this.saveBuildState(channel);
			falg= true ;
		
		} catch (final Exception e) {
			falg= false ;
			e.printStackTrace();
		}
		try {
			response.getWriter().print(falg);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	/**
	 * 创建版本更新包说明文件
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午1:13:43
	 * @param vib
	 * @param md5
	 */
	public void createVerManifest(final VersionInfoBean vib, final String md5, final String zipName) {
		final JSONObject jobj = new JSONObject();
		jobj.put("packageUrl", ConstUtils.packageUrl + vib.getChannel() + ConstUtils.f_separator);
		jobj.put("packageUrl2", ConstUtils.packageUrl2 + vib.getChannel() + ConstUtils.f_separator);
		jobj.put("lastVersionUrl", ConstUtils.lastVersionUrl);
		jobj.put("remoteManifestUrl", ConstUtils.remoteManifestUrl);
		jobj.put("remoteVersionUrl", ConstUtils.remoteVersionUrl);
		jobj.put("engineVersion", vib.getEngineId());
		jobj.put("update_channel", vib.getChannel());
		jobj.put("bundle", vib.getPackageDate());
		jobj.put("version", vib.getVersionId());
		//jobj.put("permission", vib.getState() + "");
		jobj.put("svn", vib.getSvnId());
		final String channelPath = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator;
		final String vfiledir = channelPath + ConstUtils.vmanifestPath + zipName + ConstUtils.f_separator;
		final File vfd = new File(vfiledir);
		if (!vfd.exists()) {
			vfd.mkdirs();
		}
		final String vfilePath = vfiledir + ConstUtils.f_separator + ConstUtils.vmfName;
		//生成文件version.manifest
		FileUtils.writerTxt(vfilePath, jobj.toString());
		final JSONObject assetsobj = new JSONObject();
		final JSONObject fileobj = new JSONObject();
		fileobj.put("md5", md5);
		fileobj.put("compressed", true);
		assetsobj.put(zipName + ConstUtils.updatePkgSuffix, fileobj);
		jobj.put("assets", assetsobj);
		final String filedir = channelPath + ConstUtils.manifestPath + zipName + ConstUtils.f_separator;
		final File fd = new File(filedir);
		if (!fd.exists()) {
			fd.mkdirs();
		}
		final String filePath = filedir + ConstUtils.f_separator + ConstUtils.pmfName;
		//生成文件projest.manifest
		FileUtils.writerTxt(filePath, jobj.toString());
	}

	/**
	 * 生成状态保存
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午1:26:40
	 * @return
	 */
	public String saveBuildState(String channel) {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String id = request.getParameter("id");
			final String sql = "UPDATE `versioninfo` SET `buildState` = '1' WHERE `id` = '" + id + "'; ";
			DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
			final VersionInfoBean vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(Integer.parseInt(id));
			vib.setBuildState(1);
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * 获得生成文件信息列表
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-7_上午11:33:46
	 * @return
	 */
	public String getMergeFileInfo() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String versionId = request.getParameter("versionId");
			final String channel = request.getParameter("channel");
			final Integer currIndex = ConstUtils.ch2VerId2IndexIdMap.get(channel).get(versionId);
			if (0 == ConstUtils.maxId) {
				final String sqlquery = "select max(id) from versioninfo where buildState=1 and channel='" + channel + "'";
				final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
				ret.next();
				ConstUtils.maxId = ret.getInt(1);
				if (ConstUtils.ch2IndexId2VerMap.get(channel).isEmpty()) {
					new VersionInfoBean().getAllVersionInfo(channel);
				}
			}
			final VersionInfoBean vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(currIndex);
			final String channelPath = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator;
			final StringBuffer allFileAddr = new StringBuffer();
			File file = new File(channelPath + vib.getVersionId() + ConstUtils.updatePkgSuffix);
			if(file.exists()) {
				long size = file.length();
				allFileAddr.append(ConstUtils.packageUrl).append(vib.getChannel()).append(ConstUtils.f_separator);
				allFileAddr.append(vib.getVersionId()).append(ConstUtils.updatePkgSuffix).append("\n文件大小(KB)：").append(size/1024 + 1).append("\n");
			}
			for (int i = currIndex - 1; i > 0; i--) {
				final VersionInfoBean vibPre = ConstUtils.ch2IndexId2VerMap.get(channel).get(i);
				if (null != vibPre && vibPre.getChannel().equals(channel)) {
					final String fileName = vib.getVersionId() + "_" + vibPre.getVersionId() + ConstUtils.updatePkgSuffix;
					final File fileTmp = new File(channelPath + fileName);
					if (fileTmp.exists()) {
						final long size = fileTmp.length();
						allFileAddr.append(ConstUtils.packageUrl).append(vib.getChannel()).append(ConstUtils.f_separator);
						allFileAddr.append(fileName).append("\n文件大小(KB)：").append((size / 1024) + 1).append("\n");
					}
				}
			}
			final JSONObject fileobj = new JSONObject();
			fileobj.put("info", allFileAddr.length() == 0 ? "无" : allFileAddr.toString());
			final HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(fileobj.toString().replace('\"', '\''));
			response.getWriter().flush();
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}
	
	private void delFile(String filepath) throws IOException{
		File f = new File(filepath);
		if(f.exists() && f.isDirectory()){
			if(f.listFiles().length==0){
				f.delete(); 
			}else{
				File delFile[]=f.listFiles();
				int i =f.listFiles().length;
				for(int j=0;j<i;j++){ 
					if(delFile[j].isDirectory()){
						delFile(delFile[j].getAbsolutePath());
					}
					delFile[j].delete();
				}
			}
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		// TODO Auto-generated method stub
		//		final File newVersionZip = new File("D:/test/doc.zip");
		//		final String versionId = "7";
		//		new UpdateFilesMerge(versionId, newVersionZip).filesMerge(newVersionZip, versionId);
		// File newVersionZip = new File("D:/test/doc2");
		// newVersionZip.renameTo(new File("D:/test/doc1"));
		// FileUtils.delFolder("D:/test/doc1");
		// FlodCompress.getInstance().zip("D:/test/3/doc" ,
		// "D:/test/3/doc.zip");
	}
}
