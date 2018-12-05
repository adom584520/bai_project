/**
 * @author liuxiaomeng
 * @datetime 2015-8-5_上午11:15:18
 */
package com.zckj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.zckj.tool.utils.BASE64Tools;
import com.zckj.tool.utils.ConstUtils;
import com.zckj.tool.utils.DataBasUtils;
import com.zckj.tool.utils.DateUtils;

/**
 * @author liuxiaomeng
 * @datetime 2015-8-5_上午11:15:18
 */
public class VersionUpdate extends ActionSupport {
	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-5_上午11:50:01
	 */
	private static final long serialVersionUID = -1480586758755423230L;

	/**
	 * 使用Response实现文件下载:
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午5:39:16
	 * @return
	 */
	public String getProjectManifestFile() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			// 请求者当前版本
			final String versionId = request.getParameter("version");
			// 请求权限
			final String state = request.getParameter("testing");
			int userState = 1;
			if(null == state || (state.trim().length() == 0)){
				userState = 3;
			} else if(state.equals(ConstUtils.ZCTECH_TEST)){
				userState = 2;
			} else {
				userState = 3;
			}
			//final String bundle = request.getParameter("bundle");
			// 请求渠道
			final String channel = request.getParameter("update_channel");
			if ((null == versionId) || (null != state && state.equals("1") ) || (null == channel) || (channel.trim().length() == 0) || (versionId.trim().length() == 0)) {
				final JSONObject jobj = new JSONObject();
				jobj.put("message", "请求数据异常！！没有更新版本");
				jobj.put("status", 0);
				final HttpServletResponse response = ServletActionContext.getResponse();
				final HttpSession seesion = request.getSession();
				seesion.setAttribute("xxx", "test");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jobj.toString());
				response.getWriter().flush();
				return Action.NONE;
			} else {
				Map<Integer, VersionInfoBean> verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
				if (0 == ConstUtils.maxId) {
					final String sqlquery = "select max(id) from versioninfo where buildState=1 and channel='" + channel + "'";
					final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
					ret.next();
					ConstUtils.maxId = ret.getInt(1);
					if (null == verMap || verMap.isEmpty()) {
						new VersionInfoBean().getAllVersionInfo(channel);
						verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
					}
				}
				Map<String, Integer> verId2IndexIdMap = ConstUtils.ch2VerId2IndexIdMap.get(channel);
				if(null == verId2IndexIdMap || verId2IndexIdMap.isEmpty()) {
					final JSONObject jobj = new JSONObject();
					jobj.put("message", "没有可更新版本！！");
					jobj.put("status", 3);
					final HttpServletResponse response = ServletActionContext.getResponse();
					final HttpSession seesion = request.getSession();
					seesion.setAttribute("xxx", "test");
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(jobj.toString());
					response.getWriter().flush();
					return Action.NONE;
				}
				Integer userIndexId = verId2IndexIdMap.get(versionId);
				if(null == userIndexId) {
					userIndexId = 1;
					for (Entry<String, Integer> entry : verId2IndexIdMap.entrySet()) {
						int rs = versionId.compareToIgnoreCase(entry.getKey());
						if (rs > 0 && userIndexId < entry.getValue()) {
							userIndexId = entry.getValue();
						}
					}
				}
				if (null != userIndexId) {
					
					String version1 = "";
					String currEnableVerId = "";
					for (int i = ConstUtils.maxId; i > 0; i--) {
						final VersionInfoBean vib = verMap.get(i);
						if(null != vib && vib.getBuildState() == 1){
							currEnableVerId = vib.getVersionId();
							version1 = vib.getVersionId();
						}
					}
					
					int forceIndex = 0;
					int currIndex = userIndexId;
					//final int userState = Integer.parseInt(state);
					for (int i = ConstUtils.maxId; i > 0; i--) {
						final VersionInfoBean vib = verMap.get(i);
						if (null != vib && vib.getBuildState() == 1) {
							if(vib.getId() < userIndexId)
								break;
							if (vib.getState() >= userState) {
								if(currIndex < vib.getId()) {
									currIndex = vib.getId();
									currEnableVerId = vib.getVersionId();
								}
								if(vib.getForceUpdate()) {
									forceIndex = vib.getId();
									break;
								}
							}
						}
					}
					int startIndex = userIndexId + 1;
					for (int i = startIndex ; i <= ConstUtils.maxId; i++) {
						final VersionInfoBean vib = verMap.get(i);
						if (null != vib){
							startIndex = i;
							break;
						}
					}
					
//					if(userIndexId <= 24) {
//						userIndexId = 24;
//						currIndex = 25;
//					}
					
					if ((currIndex > userIndexId) && !currEnableVerId.equals(versionId.trim())) {
						final VersionInfoBean vib = verMap.get(currIndex);
						if(null != vib) {
							final String filedir = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator + ConstUtils.manifestPath;
							//final String zipName = currIndex == (userIndexId + 1) ? vib.getVersionId() : vib.getVersionId() + "_" + (verMap.get(startIndex).getVersionId());
							String zipName;
							if(forceIndex != 0){
								zipName = vib.getVersionId() + "_" + version1;;
							}else{
								if (currIndex != startIndex)
									zipName = (currIndex - userIndexId) == 1 ? currEnableVerId : currEnableVerId + "_" + (verMap.get(startIndex).getVersionId());
								else
									zipName = currEnableVerId;
							}
							
							final String filePath = filedir + zipName + ConstUtils.f_separator + ConstUtils.pmfName;
							//File fd = new File(filedir);
							//if (fd.exists()) {
							final HttpServletResponse response = ServletActionContext.getResponse();
							final HttpSession seesion = request.getSession();
							seesion.setAttribute("xxx", "test");
							response.setContentType("text/html");
							response.setCharacterEncoding("UTF-8");
							response.setHeader("Content-Type", "application/octet-stream");
							response.setHeader("Content-Disposition", "attachment;filename=" + ConstUtils.pmfName);
							//response.setHeader("content-disposition", "attachment;filename" + URLEncoder.encode("grid.js", "UTF-8"));
							//设置响应头，告诉浏览器，该响应是下载响应，如果文件名包含中文，必须使用URL编码
							//}
							//通过文件路径获得File对象(假如此路径中有一个download.pdf文件)   
							final File file = new File(filePath);
							try {
								final FileInputStream inputStream = new FileInputStream(file);
								//3.通过response获取ServletOutputStream对象(out)   
								final ServletOutputStream out = response.getOutputStream();
								int b = 0;
								final byte[] buffer = new byte[1024 * 5];
								while (b != -1) {
									b = inputStream.read(buffer);
									if (b != -1) {
										//4.写到输出流(out)中   
										out.write(buffer, 0, b);
									}
								}
								inputStream.close();
								out.close();
								out.flush();
							} catch (final IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * 使用Response实现文件下载:
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午5:39:16
	 * @return
	 */
	public String getVersionManifestFile() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			// 请求者当前版本
			final String versionId = request.getParameter("version");
			// 请求权限
			final String state = request.getParameter("testing");
			int userState = 1;
			if(null == state || (state.trim().length() == 0)){
				userState = 3;
			} else if(state.equals(ConstUtils.ZCTECH_TEST)){
				userState = 2;
			} else {
				userState = 3;
			}
			// 请求渠道
			final String channel = request.getParameter("update_channel");
			if ((null == versionId) || (null != state && state.equals("1") )|| (null == channel) || (channel.trim().length() == 0) || (versionId.trim().length() == 0)) {
				final JSONObject jobj = new JSONObject();
				jobj.put("message", "请求数据异常！！没有更新版本");
				jobj.put("status", 0);
				final HttpServletResponse response = ServletActionContext.getResponse();
				final HttpSession seesion = request.getSession();
				seesion.setAttribute("xxx", "test");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jobj.toString());
				response.getWriter().flush();
				return Action.NONE;
			} else {
				Map<Integer, VersionInfoBean> verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
				if (0 == ConstUtils.maxId) {
					final String sqlquery = "select max(id) from versioninfo where buildState=1 and channel='" + channel + "'";
					final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
					ret.next();
					ConstUtils.maxId = ret.getInt(1);
					if (null == verMap || verMap.isEmpty()) {
						new VersionInfoBean().getAllVersionInfo(channel);
						verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
					}
				}

				Map<String, Integer> verId2IndexIdMap = ConstUtils.ch2VerId2IndexIdMap.get(channel);
				if(null == verId2IndexIdMap || verId2IndexIdMap.isEmpty()) {
					final JSONObject jobj = new JSONObject();
					jobj.put("message", "没有可更新版本！！");
					jobj.put("status", 3);
					final HttpServletResponse response = ServletActionContext.getResponse();
					final HttpSession seesion = request.getSession();
					seesion.setAttribute("xxx", "test");
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(jobj.toString());
					response.getWriter().flush();
					return Action.NONE;
				}
				
				Integer userIndexId = verId2IndexIdMap.get(versionId);
				if(null == userIndexId) {
					userIndexId = 1;
					for (Entry<String, Integer> entry : verId2IndexIdMap.entrySet()) {
						int rs = versionId.compareToIgnoreCase(entry.getKey());
						if (rs > 0 && userIndexId < entry.getValue()) {
							userIndexId = entry.getValue();
						}
					}
				}
				if (null != userIndexId) {
					
					String version1 = "";
					String currEnableVerId = "";
					for (int i = ConstUtils.maxId; i > 0; i--) {
						final VersionInfoBean vib = verMap.get(i);
						if(null != vib && vib.getBuildState() == 1){
							currEnableVerId = vib.getVersionId();
							version1 = vib.getVersionId();
						}
					}
					
					int forceIndex = 0;
					int currIndex = userIndexId;
					//final int userState = Integer.parseInt(state);
					for (int i = ConstUtils.maxId; i > 0; i--) {
						final VersionInfoBean vib = verMap.get(i);
						if (null != vib && vib.getBuildState() == 1) {
							if(vib.getId() < userIndexId)
								break;
							if (vib.getState() >= userState) {
								if(currIndex < vib.getId()) {
									currIndex = vib.getId();
									currEnableVerId = vib.getVersionId();
								}
								if(vib.getForceUpdate()) {
									forceIndex = vib.getId();
									break;
								}
							}
						}
					}
					int startIndex = userIndexId + 1;
					for (int i = startIndex ; i <= ConstUtils.maxId; i++) {
						final VersionInfoBean vib = verMap.get(i);
						if (null != vib){
							startIndex = i;
							break;
						}
					}
					
//					if(userIndexId <= 24) {
//						userIndexId = 24;
//						currIndex = 25;
//					}
					
					if ((currIndex > userIndexId) && !currEnableVerId.equals(versionId.trim())) {
						final VersionInfoBean vib = verMap.get(currIndex);
						if(null != vib) {
							final String filedir = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator + ConstUtils.vmanifestPath;
							//final String zipName = currIndex == (userIndexId + 1) ? vib.getVersionId() : vib.getVersionId() + "_" + (verMap.get(startIndex).getVersionId());
							String zipName;
							if(forceIndex != 0){
								zipName = vib.getVersionId() + "_" + version1;;
							}else{
								if (currIndex != startIndex)
									zipName = (currIndex - userIndexId) == 1 ? currEnableVerId : currEnableVerId + "_" + (verMap.get(startIndex).getVersionId());
								else
									zipName = currEnableVerId;
							}
							
							final String filePath = filedir + zipName + ConstUtils.f_separator + ConstUtils.vmfName;
							//File fd = new File(filedir);
							//if (fd.exists()) {
							final HttpServletResponse response = ServletActionContext.getResponse();
							final HttpSession seesion = request.getSession();
							seesion.setAttribute("xxx", "test");
							response.setContentType("text/html");
							response.setCharacterEncoding("UTF-8");
							response.setHeader("Content-Type", "application/octet-stream");
							response.setHeader("Content-Disposition", "attachment;filename=" + ConstUtils.vmfName);
							//response.setHeader("content-disposition", "attachment;filename" + URLEncoder.encode("grid.js", "UTF-8"));
							//设置响应头，告诉浏览器，该响应是下载响应，如果文件名包含中文，必须使用URL编码
							//}
							//通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
							try {
								final File file = new File(filePath);
								final FileInputStream inputStream = new FileInputStream(file);
								//3.通过response获取ServletOutputStream对象(out)   
								final ServletOutputStream out = response.getOutputStream();
								int b = 0;
								final byte[] buffer = new byte[1024 * 5];
								while (b != -1) {
									b = inputStream.read(buffer);
									if (b != -1) {
										//4.写到输出流(out)中   
										out.write(buffer, 0, b);
									}
								}
								inputStream.close();
								out.close();
								out.flush();
							} catch (final IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * 获取更新版本名称
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-5_上午11:26:58
	 * @return
	 */
	public String getVersionUpdatePkgName() {
		//		    "status": 2,
		//		    "message": "可以异步更新",
		//		    "announcement": "异步更新测试使用",
		//		    "version": "1.2",
		//		    "patchsize": 2975
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			
		// 请求者当前版本
			String versionId = request.getParameter("version");
			// 请求权限,1:development;2:testing;3:public 这3中模式
			String state = request.getParameter("testing");
			// 请求渠道
			String channel = request.getParameter("update_channel");
			
			//			if(request.getParameter("isEncrypt") != null){
//				Map<String,String[]> map = request.getParameterMap();
//				String content = "";
//				for(String key : map.keySet()){
//					if(!key.equals("isEncrypt") && !key.equals("encryptTitle")){
//						content = key;
//						break;
//					}
//				}
//				content = BASE64Tools.getDASE64Decoder(content);
//				String[] strSplit = content.split("&");
//				Map<String,String> splitMap = new HashMap<String,String>(0);
//				for(String string : strSplit){
//					if(string != null && !string.equals("")){
//						splitMap.put(string.split("=")[0], string.split("=")[1]);
//					}
//				}
//				
//				versionId = splitMap.get("version");
//				state = splitMap.get("testing");
//				channel = splitMap.get("update_channel");
//			}
		
			final HttpServletResponse response = ServletActionContext.getResponse();
			final HttpSession seesion = request.getSession();
			final JSONObject jobj = new JSONObject();
			if(channel!= null){//验证是否启用紧急通知
				final String sql = "select  id,name,title,imgurl,bz,if(status=0,'false','true') status from notice  where status=true and  name='"+channel+"' limit 0,1";
				JSONObject jobjTitle;
				JSONArray ja = null;
				jobjTitle = new JSONObject();
				try {
					final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sql).executeQuery();// 执行语句，得到结果集
					ja = new JSONArray();
					while (ret.next()) {
						final JSONObject jobj2 = new JSONObject();
						jobj2.put("name", ret.getObject("name"));
						jobj2.put("imgurl", ret.getObject("imgurl"));
						jobj2.put("bz", ret.getObject("bz"));
						jobj2.put("status",ret.getObject("status"));
						jobj2.put("title", ret.getObject("title"));
						ja.add(jobj2);
						jobjTitle.put("server_status", true);
						jobjTitle.put("message", ja);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(ja.size()>0){//存在紧急通知时返回数据
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(jobjTitle.toString());
					response.getWriter().flush();
					return Action.SUCCESS;
				}
		   }
			
			
			int userState = 1;
			if(null == state || (state.trim().length() == 0)){
				userState = 3;
			} else if(state.equals(ConstUtils.ZCTECH_TEST)){
				userState = 2;
			} else {
				userState = 3;
			}
			
			if (null == versionId || versionId.trim().length() == 0) {
				jobj.put("message", "versionId请求数据异常！");
				jobj.put("status", 0);
			} else if( null != state && state.equals("1")   )
			{
				jobj.put("message", "state请求数据异常！");
				jobj.put("status", 0);
			}else if(null == channel || channel.trim().length() == 0 ){
				jobj.put("message", "channel请求数据异常！");
				jobj.put("status", 0);
			}else {
				Map<Integer, VersionInfoBean> verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
				if (0 == ConstUtils.maxId) {
					final String sqlquery = "select max(id) from versioninfo where buildState=1 and channel='" + channel + "'";
					final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
					ret.next();
					ConstUtils.maxId = ret.getInt(1);
					if (null == verMap || verMap.isEmpty()) {
						new VersionInfoBean().getAllVersionInfo(channel);
						verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
					}
				}
				Map<String, Integer> verId2IndexIdMap = ConstUtils.ch2VerId2IndexIdMap.get(channel);
				if(null == verId2IndexIdMap || verId2IndexIdMap.isEmpty()) {
					jobj.put("message", "没有可更新版本！！");
					jobj.put("status", 3);
				} else {
					Integer userIndexId = verId2IndexIdMap.get(versionId);
					if(null == userIndexId) {
						//最新版本
						Long maxverid=(long) 0;
						Long minverid= (long) 999999999;//最低版本
						Long curminverid= (long) 0;//就近版本
						int curminid=0;
						Double curuserIndexId=Double.parseDouble(versionId.toString());//当前传入版本格式化
						int falg=0;//标识是否取到当前传入版本的就近版本
						 //一般不会出现这个问题,一旦出现此问题,查找距离此版本最近的版本
						userIndexId = 1;
						for (Entry<String, Integer> entry : verId2IndexIdMap.entrySet()) {
							Long getkey=Long.parseLong(entry.getKey().toString());
							int rs = versionId.compareToIgnoreCase(entry.getKey());
							if(maxverid<getkey){
								maxverid=getkey;//得到最新版本
							}
							if(minverid>getkey){
								minverid=getkey;//得到最低版本
							}
							if(curuserIndexId>getkey && falg==0){
								falg=1;
								curminverid=getkey;//得到最近的版本
								curminid=entry.getValue();
							}
							/*if (rs > 0 && userIndexId < entry.getValue()) {
								userIndexId = entry.getValue();
							}*/
						}
						
						//当前传入版本和最新版本比对  如果大于最新版本 提示无更新 否则提示就近版本更新
						if(curuserIndexId>maxverid){
							jobj.put("message", "没有可更新版本！！");
							jobj.put("status", 3);
							long now = System.currentTimeMillis();
							String date = DateUtils.longToString(now, DateUtils.FORMAT_TYPE_1);
							System.out.println(date +"] Vupdate client : addr " + request.getRemoteAddr() + ", host " + request.getRemoteHost() + "; version : " + versionId + "; testing : " + state + "; channel : " + channel);
							System.out.println(date +"] response : " + jobj);
							seesion.setAttribute("xxx", "test");
							response.setContentType("text/html");
							response.setCharacterEncoding("UTF-8");
							response.getWriter().print(jobj.toString());
							response.getWriter().flush();
							return Action.SUCCESS;
						}else{
							final VersionInfoBean vib = verMap.get(curminid);
							final String vName = minverid+"_"+vib.getVersionId();
							final String pkgName = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator + vName + ConstUtils.updatePkgSuffix;
							final File tmpFile = new File(pkgName);
							jobj.put("message", "可以异步更新");
							jobj.put("status", 1);
							jobj.put("patchsize", tmpFile.length());
							jobj.put("announcement", vib.getVersionNotice());
							jobj.put("version",vName);
							long now = System.currentTimeMillis();
							String date = DateUtils.longToString(now, DateUtils.FORMAT_TYPE_1);
							System.out.println(date +"] Vupdate client : addr " + request.getRemoteAddr() + ", host " + request.getRemoteHost() + "; version : " + versionId + "; testing : " + state + "; channel : " + channel);
							System.out.println(date +"] response : " + jobj);
							seesion.setAttribute("xxx", "test");
							response.setContentType("text/html");
							response.setCharacterEncoding("UTF-8");
							response.getWriter().print(jobj.toString());
							response.getWriter().flush();
							return Action.SUCCESS;
						}
					/*	if(userIndexId == 1) {
							for (Entry<String, Integer> entry : verId2IndexIdMap.entrySet()) {
								int rs = versionId.compareToIgnoreCase(entry.getKey());
								if (rs < 0 && userIndexId > entry.getValue()) {
									userIndexId = entry.getValue();
								}
							}
						} */
					/*	*/
					}
					{
						//最基础的版本
						String version1 = "";
						String currEnableVerId = "";
							for (int i = ConstUtils.maxId; i > 0; i--) {
							final VersionInfoBean vib = verMap.get(i);
							if(null != vib && vib.getBuildState() == 1){
								currEnableVerId = vib.getVersionId();
								version1 = vib.getVersionId();
							}
						}
						int currIndex = userIndexId;
						int forceIndex = 0;
						//final int userState = Integer.parseInt(state);
						for (int i = userIndexId;i<=ConstUtils.maxId; i++) {
							final VersionInfoBean vib = verMap.get(i);
							if (null != vib && vib.getBuildState() == 1) {
								if(vib.getId() < userIndexId){ break; }
								if (vib.getState() >= userState) {
									if(currIndex < vib.getId()) {
										currIndex = vib.getId();
										currEnableVerId = vib.getVersionId();
									}
									if(vib.getForceUpdate()) {
										forceIndex = vib.getId();
										break;
									}
								}
							}
						}
						int startIndex = userIndexId + 1;
						for (int i = startIndex ; i <= ConstUtils.maxId; i++) {
							final VersionInfoBean vib = verMap.get(i);
							if (null != vib){
								startIndex = i;
								break;
							}
						}
						
	//					if(userIndexId <= 24) {
	//						userIndexId = 24;
	//						currIndex = 25;
	//					}
						
						
						if ((currIndex <= userIndexId) || currEnableVerId.equals(versionId.trim())) {
							jobj.put("message", "已经是最新版本，没有可更新版本！！");
							jobj.put("status", 3);
						} else {
							if(forceIndex >= userIndexId) {//存在强制更新处理
								final VersionInfoBean vib = verMap.get(currIndex);
								final String vName = vib.getVersionId() + "_" + version1;
								final String pkgName = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator + vName + ConstUtils.updatePkgSuffix;
								final File tmpFile = new File(pkgName);
								if (tmpFile.exists()) {
									jobj.put("message", "存在更新");
									jobj.put("status", 1);
									jobj.put("patchsize", tmpFile.length());
									jobj.put("announcement", vib.getVersionNotice());
									jobj.put("version", vib.getVersionId());
//									jobj.put("addr", vib.getChannelAddr());//渠道客户端下载地址
								}else {
									jobj.put("message", "版本库异常！请稍后再试.ch2IndexId2VerMap..");
									jobj.put("status", 4);
								}
							} else {
								final VersionInfoBean vib = verMap.get(currIndex);
								if(null == vib) {
									jobj.put("message", "版本库异常！请稍后再试.ch2IndexId2VerMap..");
									jobj.put("status", 4);
								} else {
									String vName;
									if (currIndex != startIndex)
										vName = (currIndex - userIndexId) == 1 ? currEnableVerId : currEnableVerId + "_" + (verMap.get(startIndex).getVersionId());
									else
										vName = currEnableVerId;
									final String pkgName = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator + vName + ConstUtils.updatePkgSuffix;
									final File tmpFile = new File(pkgName);
									if (tmpFile.exists()) {
										jobj.put("message", "可以异步更新");
										jobj.put("status", 1);
										jobj.put("patchsize", tmpFile.length());
										jobj.put("announcement", vib.getVersionNotice());
										jobj.put("version",versionId+"_"+vib.getVersionId());
									} else {
										jobj.put("message", "版本库异常！请稍后再试...");
										jobj.put("status", 4);
									}
								}
							}
						}
					}
			}
			}
			long now = System.currentTimeMillis();
			String date = DateUtils.longToString(now, DateUtils.FORMAT_TYPE_1);
			System.out.println(date +"] Vupdate client : addr " + request.getRemoteAddr() + ", host " + request.getRemoteHost() + "; version : " + versionId + "; testing : " + state + "; channel : " + channel);
			System.out.println(date +"] response : " + jobj);
			seesion.setAttribute("xxx", "test");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			if(request.getParameter("isEncrypt") != null){
				response.getWriter().print(BASE64Tools.getDASE64Encoder(jobj.toString()));
			}else{
				response.getWriter().print(jobj.toString());
			}
			response.getWriter().flush();
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-5_上午11:15:18
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO 需要完善相关逻辑
	}
}
