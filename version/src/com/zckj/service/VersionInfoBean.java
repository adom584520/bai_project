/**
 * @author liuxiaomeng
 * @datetime 2015-8-3_下午2:16:38
 */
package com.zckj.service;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.zckj.tool.utils.ConstUtils;
import com.zckj.tool.utils.DataBasUtils;
import com.zckj.tool.utils.DateUtils;

/**
 * @author liuxiaomeng
 * @datetime 2015-8-3_下午2:16:38
 */
public class VersionInfoBean extends ActionSupport {
	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-3_下午2:34:53
	 */
	private static final long serialVersionUID = 134243234L;
	
	/** 序列号ID */
	private int id;

	/** 打包日期 */
	private String packageDate;

	/** 版本ID */
	private String versionId;

	/** SVN号 */
	private String svnId;

	/** 引擎版本ID */
	private String engineId;

	/** 发布日期时间 */
	private long releaseDateTime;

	/** 编辑日期时间 */
	private long editDateTime;

	/** 功能改变描述 */
	private String moduleChange;

	/** 版本变更公告 */
	private String versionNotice;

	/** 发布状态 */
	private int state;

	/** 上传状态 */
	private int uploadState;

	/** 合成状态 */
	private int buildState;

	/** 上传文件路径 */
	private String filePath;

	/** 渠道 */
	private String channel;
	
	/** 操作员ID */
	private String operatorId;
	
	/** 渠道安装 包下载地址 */
	private String channelAddr;

	/** 是否强制更新状态 */
	private boolean forceUpdate;
	
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getPackageDate() {
		return this.packageDate;
	}

	public void setPackageDate(final String packageDate) {
		this.packageDate = packageDate;
	}

	public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(final String versionId) {
		this.versionId = versionId;
	}

	public String getSvnId() {
		return this.svnId;
	}

	public void setSvnId(final String svnId) {
		this.svnId = svnId;
	}

	public String getEngineId() {
		return this.engineId;
	}

	public void setEngineId(final String engineId) {
		this.engineId = engineId;
	}

	public long getReleaseDateTime() {
		return this.releaseDateTime;
	}

	public void setReleaseDateTime(final long releaseDateTime) {
		this.releaseDateTime = releaseDateTime;
	}

	public long getEditDateTime() {
		return this.editDateTime;
	}

	public void setEditDateTime(final long editDateTime) {
		this.editDateTime = editDateTime;
	}

	public String getModuleChange() {
		return this.moduleChange;
	}

	public void setModuleChange(final String moduleChange) {
		this.moduleChange = moduleChange;
	}

	public String getVersionNotice() {
		return this.versionNotice;
	}

	public void setVersionNotice(final String versionNotice) {
		this.versionNotice = versionNotice;
	}

	public int getState() {
		return this.state;
	}

	public void setState(final int state) {
		this.state = state;
	}

	public int getUploadState() {
		return this.uploadState;
	}

	public void setUploadState(final int uploadState) {
		this.uploadState = uploadState;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午4:37:25
	 * @return the buildState
	 */
	public int getBuildState() {
		return this.buildState;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午4:37:25
	 * @param buildState the buildState to set
	 */
	public void setBuildState(final int buildState) {
		this.buildState = buildState;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午4:37:25
	 * @return the filePath
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午4:37:25
	 * @param filePath the filePath to set
	 */
	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午12:58:16
	 * @return the channel
	 */
	public String getChannel() {
		return this.channel;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午12:58:16
	 * @param channel the channel to set
	 */
	public void setChannel(final String channel) {
		this.channel = channel;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-9-6_下午1:56:07
	 * @return the operatorId
	 */
	public String getOperatorId() {
		return this.operatorId;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-9-6_下午1:56:07
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-9-7_下午3:58:34
	 * @return the channelAddr
	 */
	public String getChannelAddr() {
		return this.channelAddr;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-9-7_下午3:58:34
	 * @param channelAddr the channelAddr to set
	 */
	public void setChannelAddr(String channelAddr) {
		this.channelAddr = channelAddr;
	}

	public boolean getForceUpdate() {
		return this.forceUpdate;
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	/**
	 * 添加数据保存
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午1:26:40
	 * @return
	 */
	public String saveAddVersionInfo() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String channel = request.getParameter("channel");
			Map<Integer, VersionInfoBean> verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
			if(null == verMap) {
				final JSONObject jobj = new JSONObject();
				jobj.put("success", false);
				jobj.put("info", "保存失败！请刷新页面后重新提交！");
				final HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jobj.toString());
				response.getWriter().flush();
				return Action.SUCCESS;
			}
			for(VersionInfoBean vib : verMap.values()) {
				if(vib.getVersionId().trim().equals(this.versionId.trim())) {
					final JSONObject jobj = new JSONObject();
					jobj.put("success", false);
					jobj.put("info", "保存失败！输入版本号已经存在，请更改后再次提交！");
					final HttpServletResponse response = ServletActionContext.getResponse();
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(jobj.toString());
					response.getWriter().flush();
					return Action.SUCCESS;
				}
				int rs = vib.getVersionId().compareToIgnoreCase(this.versionId);
				if(rs == 1){
					final JSONObject jobj = new JSONObject();
					jobj.put("success", false);
					jobj.put("info", "保存失败！输入版本号低于当前存在版本号，请更改后再次提交！");
					final HttpServletResponse response = ServletActionContext.getResponse();
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(jobj.toString());
					response.getWriter().flush();
					return Action.SUCCESS;
				}
			}
			final String sqlquery = "select max(id) from versioninfo ";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
			ret.next();
			final int count = ret.getInt(1) + 1;
			if(this.svnId.length() == 0)
				this.svnId = "0";
			final String packageDate = DateUtils.dateToString(new Date(), DateUtils.FORMAT_TYPE_3);
			int fu = this.forceUpdate ? 1 : 0;
			final String sql = "INSERT INTO `versioninfo` (`id`, `packageDate`, `versionId`, `svnId`, `state`, `engineId`, `releaseDateTime`, " + "`moduleChange`, `versionNotice`, `uploadState`, `buildState`, `operatorId`, `channel`,`channelAddr`,`forceUpdate`) VALUES ('"
					+ count + "', '" + packageDate + "', '" + this.versionId + "', '" + this.svnId + "', '1', '" + this.engineId + "', '" + System.currentTimeMillis() + "', '" + this.moduleChange + "', '" + this.versionNotice
					+ "', '0', '0', '001', '"+this.channel + "','"+this.channelAddr+"',"+ fu +"); ";
			
			DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
			final JSONObject jobj = new JSONObject();
			jobj.put("success", true);
			jobj.put("info", "添加成功");
			final HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobj.toString());
			response.getWriter().flush();
			
//			if(this.forceUpdate) {
//				final String sqlForceUpdate = "UPDATE `versioninfo` SET `distory` = 1 WHERE `id` < " + count + " and channel='"+ channel + "'; ";
//				DataBasUtils.getInstance().getPrepareStatement(sqlForceUpdate).executeUpdate();
//				jobj.put("success", true);
//				for(int i=1;i<=count;i++) {
//					ConstUtils.ch2IndexId2VerMap.get(channel).remove(i);
//				}
//			}
			
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * 编辑数据保存
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午1:26:40
	 * @return
	 */
	public String saveEditVersionInfo() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String channel = request.getParameter("channel");
			Map<Integer, VersionInfoBean> verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
			for(VersionInfoBean vib : verMap.values()) {
				if(vib.getVersionId().trim().equals(this.versionId.trim()) && this.id != vib.getId()) {
					final JSONObject jobj = new JSONObject();
					jobj.put("success", false);
					jobj.put("info", "保存失败！输入版本ID已经存在，请更改后再次提交！");
					final HttpServletResponse response = ServletActionContext.getResponse();
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(jobj.toString());
					response.getWriter().flush();
					return Action.SUCCESS;
				}
			}
			
			//把原来的强制更新设置还原
			if(this.forceUpdate){
				String sql = "UPDATE `versioninfo` SET `editDateTime` = '"+ System.currentTimeMillis()+ "' , `forceUpdate` = 0 where `forceUpdate` = 1;";
				DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
			}
			
			
			if(this.svnId.length() == 0)
				this.svnId = "0";
			int fu = this.forceUpdate ? 1 : 0;
			final String sql = "UPDATE `versioninfo` SET `editDateTime` = '" + System.currentTimeMillis() + "' , `moduleChange` = '" + this.moduleChange + "' , `versionNotice` = '" + this.versionNotice + "' , `versionId` = '" + this.versionId
					+ "' , `engineId` = '" + this.engineId+ "' , `forceUpdate` = " + fu+ " , `channelAddr` = '" + this.channelAddr + "' , `svnId` = '" + this.svnId + "' WHERE `id` = '" + this.id + "'; ";
			DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
			final JSONObject jobj = new JSONObject();
			jobj.put("success", true);
			jobj.put("info", "保存成功");
			final HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobj.toString());
			response.getWriter().flush();
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * 发布状态保存
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午1:26:40
	 * @return
	 */
	public String saveState() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String state = request.getParameter("state");
			final String id = request.getParameter("id");
			final String channel = request.getParameter("channel");
			final String sql = "UPDATE `versioninfo` SET `state` = '" + state + "' WHERE `id` = '" + id + "'; ";
			DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
			final int s = Integer.parseInt(state);
			final JSONObject jobj = new JSONObject();
			jobj.put("success", true);
			jobj.put("info", s);
			final HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobj.toString().replace('\"', '\''));
			response.getWriter().flush();
			VersionInfoBean vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(Integer.parseInt(id));
			if(null != vib)
			vib.setState(Integer.parseInt(state));
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}
	
	/**
	 * 作废版本设置
	 * @author liuxiaomeng
	 * @datetime 2015-9-7_上午11:12:15
	 * @return
	 */
	public String distoryVersion() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String id = request.getParameter("id");
			final String channel = request.getParameter("channel");
			final String sql = "UPDATE `versioninfo` SET `distory` = 1 WHERE `id` <= " + id + " and channel='"+ channel + "'; ";
			DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
			final JSONObject jobj = new JSONObject();
			jobj.put("success", true);
			final HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobj.toString().replace('\"', '\''));
			response.getWriter().flush();
			int idInt = Integer.parseInt(id);
			for(int i=1;i<=idInt;i++) {
				ConstUtils.ch2IndexId2VerMap.get(channel).remove(i);
			}
			return Action.SUCCESS;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}
	
	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-3_下午2:25:46
	 */
	public String getVersionIds() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final HttpServletResponse response = ServletActionContext.getResponse();
			final String channel = request.getParameter("channel");
			final String sql = "select id,versionId from versioninfo where distory=0 and channel='"+channel+"' order by id desc";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sql).executeQuery();// 执行语句，得到结果集
			final JSONObject jobjTitle = new JSONObject();
			final JSONArray ja = new JSONArray();
			while (ret.next()) {
				final JSONObject jobj = new JSONObject();
				jobj.put("id", ret.getInt(1));
				jobj.put("versionId", ret.getString(2));
				ja.add(jobj);
			}
			jobjTitle.put("items", ja);
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobjTitle.toString());
			response.getWriter().flush();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}
	
	
	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-3_下午2:25:46
	 */
	public String getVersionInfo() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final HttpServletResponse response = ServletActionContext.getResponse();
			final String gd = request.getParameter("gd");
			final String channel = request.getParameter("channel");
			if (gd.equals("1") && null != channel && channel.length() > 0) {
				Map<Integer, VersionInfoBean> verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
				if(null == verMap){
					verMap = new HashMap<Integer, VersionInfoBean>();
					ConstUtils.ch2IndexId2VerMap.put(channel, verMap);
				}
				verMap.clear();
				Map<String, Integer> idMap = ConstUtils.ch2VerId2IndexIdMap.get(channel);
				if(null == idMap){
					idMap = new HashMap<String, Integer>();
					ConstUtils.ch2VerId2IndexIdMap.put(channel, idMap);
				}
				idMap.clear();
				final String sql = "select * from versioninfo where distory=0 and channel='"+channel+"' order by id desc";
				final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sql).executeQuery();// 执行语句，得到结果集
				final JSONObject jobjTitle = new JSONObject();
				final JSONArray ja = new JSONArray();
				while (ret.next()) {
					final JSONObject jobj = new JSONObject();
					final int id = ret.getInt(1);
					jobj.put("id", ret.getInt(1));
					jobj.put("packageDate", ret.getString(2));
					jobj.put("versionId", ret.getString(3));
					jobj.put("svnId", ret.getInt(4));
					jobj.put("state", ret.getInt(5));
					jobj.put("engineId", ret.getString(6));
					final long rdt = ret.getLong(7);
					jobj.put("releaseDateTime", rdt != 0 ? DateUtils.longToString(rdt, DateUtils.FORMAT_TYPE_1) : "");
					final long edt = ret.getLong(8);
					jobj.put("editDateTime", edt != 0 ? DateUtils.longToString(edt, DateUtils.FORMAT_TYPE_1) : "");
					jobj.put("moduleChange", ret.getString(9));
					jobj.put("versionNotice", ret.getString(10));
					jobj.put("uploadState", ret.getInt(11));
					jobj.put("buildState", ret.getInt(12));
					jobj.put("operatorId", ret.getString(13));
					jobj.put("channel", ret.getString(14));
					jobj.put("channelAddr", ret.getString(17));
					jobj.put("forceUpdate", ret.getInt(18) == 1);
					ja.add(jobj);
					final VersionInfoBean vib = (VersionInfoBean) JSONObject.toBean(jobj, VersionInfoBean.class);
					vib.setFilePath(ret.getString(15));
					verMap.put(id, vib);
					idMap.put(vib.getVersionId(), vib.getId());
				}
				jobjTitle.put("items", ja);
				jobjTitle.put("success", true);
				jobjTitle.put("totalProperty", ja.size());
				//System.out.println(jobjTitle);
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jobjTitle.toString());
				response.getWriter().flush();
			}
			return Action.NONE;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-3_下午2:25:46
	 */
	public String getAllVersionInfo(String channel) {
		try {
			Map<Integer, VersionInfoBean> verMap = ConstUtils.ch2IndexId2VerMap.get(channel);
			if(null == verMap){
				verMap = new HashMap<Integer, VersionInfoBean>();
				ConstUtils.ch2IndexId2VerMap.put(channel, verMap);
			}
			verMap.clear();
			Map<String, Integer> idMap = ConstUtils.ch2VerId2IndexIdMap.get(channel);
			if(null == idMap){
				idMap = new HashMap<String, Integer>();
				ConstUtils.ch2VerId2IndexIdMap.put(channel, idMap);
			}
			idMap.clear();
			final String sql = "select * from versioninfo where distory=0 and channel='"+channel+"' order by id desc";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sql).executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				final JSONObject jobj = new JSONObject();
				final int id = ret.getInt(1);
				jobj.put("id", ret.getInt(1));
				jobj.put("packageDate", ret.getString(2));
				jobj.put("versionId", ret.getString(3));
				jobj.put("svnId", ret.getInt(4));
				jobj.put("state", ret.getInt(5));
				jobj.put("engineId", ret.getString(6));
				final long rdt = ret.getLong(7);
				jobj.put("releaseDateTime", rdt != 0 ? DateUtils.longToString(rdt, DateUtils.FORMAT_TYPE_1) : "");
				final long edt = ret.getLong(8);
				jobj.put("editDateTime", edt != 0 ? DateUtils.longToString(edt, DateUtils.FORMAT_TYPE_1) : "");
				jobj.put("moduleChange", ret.getString(9));
				jobj.put("versionNotice", ret.getString(10));
				jobj.put("uploadState", ret.getInt(11));
				jobj.put("buildState", ret.getInt(12));
				jobj.put("operatorId", ret.getString(13));
				//String channel = ret.getString(14);
				jobj.put("channel", channel);
				jobj.put("channelAddr", ret.getString(17));
				jobj.put("forceUpdate", ret.getInt(18) == 1);
				final VersionInfoBean vib = (VersionInfoBean) JSONObject.toBean(jobj, VersionInfoBean.class);
				vib.setFilePath(ret.getString(15));
				verMap.put(id, vib);
				idMap.put(vib.getVersionId(), vib.getId());
			}
			return Action.NONE;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-3_下午2:16:38
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO 需要完善相关逻辑
	}
}
