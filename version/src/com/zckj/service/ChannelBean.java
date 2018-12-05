/**
 * @author liuxiaomeng
 * @datetime 2015-8-6_下午3:11:18
 */
package com.zckj.service;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.zckj.tool.utils.DataBasUtils;

/**
 * 渠道数据bean
 * 
 * @author liuxiaomeng
 * @datetime 2015-8-6_下午3:11:18
 */
public class ChannelBean extends ActionSupport {
	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:26:05
	 */
	private static final long serialVersionUID = -3832891849396091949L;

	/** ID */
	private int id;

	/** 渠道 */
	private String channel;

	/** 渠道名称 */
	private String channelName;

	/** 关键词 */
	private String keyWord;

	/** 链接 */
	private String shopLink;

	/** 打包数量 */
	private int pkgCount;

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @param id the id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @return the channel
	 */
	public String getChannel() {
		return this.channel;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @param channel the channel to set
	 */
	public void setChannel(final String channel) {
		this.channel = channel;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @return the channelName
	 */
	public String getChannelName() {
		return this.channelName;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @param channelName the channelName to set
	 */
	public void setChannelName(final String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return this.keyWord;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @return the shopLink
	 */
	public String getShopLink() {
		return this.shopLink;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @param shopLink the shopLink to set
	 */
	public void setShopLink(final String shopLink) {
		this.shopLink = shopLink;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @return the pkgCount
	 */
	public int getPkgCount() {
		return this.pkgCount;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:25:44
	 * @param pkgCount the pkgCount to set
	 */
	public void setPkgCount(final int pkgCount) {
		this.pkgCount = pkgCount;
	}
	
	/**
	 * 新建渠道数据
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-9-7_下午7:23:13
	 * @return
	 */
	public String createChannelInfo() {
		try {
			final String sqlquery = "select count(*) from channel where channel='" + this.channel + "'";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
			final JSONObject jobj = new JSONObject();
			ret.next();
			final int count = ret.getInt(1);
			if (count > 0) {
				jobj.put("success", false);
				jobj.put("info", "保存失败！输入渠道  "+this.channel+" 已经存在，请更改后再次提交！");
			} else {
				String sql = "INSERT INTO `channel` (`channel`, `channelName`, `keyWord`, `shopLink`) VALUES ('" + this.channel + "', '" + this.channelName + "', '" + this.keyWord + "', '" + this.shopLink + "');";
				DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
				jobj.put("success", true);
				jobj.put("info", "添加成功");
			}
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
	 * 编辑渠道数据
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-9-7_下午7:23:13
	 * @return
	 */
	public String editChannelInfo() {
		try {
//			final String sqlquery = "select count(*) from channel where channel='" + this.channel + "'";
//			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
			final JSONObject jobj = new JSONObject();
//			ret.next();
//			final int count = ret.getInt(1);
//			if (count > 0) {
//				jobj.put("success", false);
//				jobj.put("info", "保存失败！输入渠道  "+this.channel+" 已经存在，请更改后再次提交！");
//			} else {
				final String sql = "UPDATE `channel` SET `channelName` = '" + this.channelName +"', `keyWord`='" + 
						this.keyWord +"', `shopLink`='"+this.shopLink+"' where channel='" + this.channel + "'";
				DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
				jobj.put("success", true);
				jobj.put("info", "修改成功");
//			}
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
	 * 编辑渠道数据
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-9-7_下午7:23:13
	 * @return
	 */
	public String delChannelInfo() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String channel = request.getParameter("channel");
			final String sqlquery = "select count(*) from channel where channel='" + channel + "'";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
			final JSONObject jobj = new JSONObject();
			ret.next();
			final int count = ret.getInt(1);
			if (count == 0) {
				jobj.put("success", false);
				jobj.put("info", channel + " 删除失败！渠道不存在！");
			} else {
				final String sql = "DELETE FROM `channel` where `channel`='" + channel + "'";
				DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
				jobj.put("success", true);
				jobj.put("info", "删除成功");
			}
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
	 * 查询所有渠道信息
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-3_下午2:25:46
	 */
	public String getChannelNames() {
		try {
			//final HttpServletRequest request = ServletActionContext.getRequest();
			final HttpServletResponse response = ServletActionContext.getResponse();
			//final String gd = request.getParameter("gd");
			//if (gd.equals("1")) {
			final String sql = "select channel,channelName from channel ";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sql).executeQuery();// 执行语句，得到结果集
			final JSONObject jobjTitle = new JSONObject();
			final JSONArray ja = new JSONArray();
			while (ret.next()) {
				final JSONObject jobj = new JSONObject();
				jobj.put("channel", ret.getString(1));
				jobj.put("channelName", ret.getString(2));
				ja.add(jobj);
			}
			jobjTitle.put("items", ja);
			jobjTitle.put("totalProperty", ja.size());
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobjTitle.toString());
			response.getWriter().flush();
			//}
			return Action.NONE;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}
	
	/**
	 * 查询所有渠道信息
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-3_下午2:25:46
	 */
	public String getChannelInfo() {
		try {
			//final HttpServletRequest request = ServletActionContext.getRequest();
			final HttpServletResponse response = ServletActionContext.getResponse();
			//final String gd = request.getParameter("gd");
			//if (gd.equals("1")) {
			final String sql = "select * from channel ";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sql).executeQuery();// 执行语句，得到结果集
			final JSONObject jobjTitle = new JSONObject();
			final JSONArray ja = new JSONArray();
			while (ret.next()) {
				final JSONObject jobj = new JSONObject();
				int id = 0;
				String sqls=" or 1=1";
				try {
					id = ret.getInt(1);
				} catch (Exception e) {
					System.out.println("初始化加载。。。。。。。。。");
				}
				jobj.put("id", id);
				jobj.put("channel", ret.getString(2));
				jobj.put("channelName", ret.getString(3));
				jobj.put("keyWord", ret.getString(4));
				jobj.put("shopLink", ret.getString(5));
				  String sqlquery = "select count(*) from versioninfo where channel='" + ret.getString(3) + "'";
				if(id==0){
					sqlquery+=sqls;
				}
				final ResultSet retc = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
				retc.next();
				final int count = retc.getInt(1);
				jobj.put("pkgCount", count);
				ja.add(jobj);
			}
			jobjTitle.put("items", ja);
			jobjTitle.put("success", true);
			jobjTitle.put("totalProperty", ja.size());
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobjTitle.toString());
			response.getWriter().flush();
			//}
			return Action.NONE;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午3:11:18
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO 需要完善相关逻辑
	}
}
