/**
 * @author zr
 * @datetime 2015-8-6_下午3:11:18
 */
package com.zckj.service;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.zckj.tool.utils.DataBasUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 通知数据bean
 * @author zr
 */
public class NoticeBean extends ActionSupport {
	/**
	 * @author zr
	 */
	private static final long serialVersionUID = -383289184939609194L;

	private int id;
	/** 名称 */
	private String name;
	/** 标题 */
	private String title;
	/** 图片 */
	private String imgurl;

	//描述
	private String bz;
	/** 状态 */
	private String status;

	public int getId() {
		return this.id;
	}
	public void setId(final int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 新建通知数据
	 * @author zr
	 * @return
	 */
	public String createNoticeInfo() {
		try {
			final String sqlquery = "select count(*) from notice where name='" + this.name + "'";
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
			final JSONObject jobj = new JSONObject();
			ret.next();
			final int count = ret.getInt(1);
			if (count > 0) {
				jobj.put("success", false);
				jobj.put("info", "保存失败！输入名称  "+this.name+" 已经存在，请更改后再次提交！");
			} else {
				String sql = "INSERT INTO `notice` (  name,title,imgurl,bz,status ) VALUES (  '" + this.name + "', '" + this.title + "', '" + this.imgurl + "', '"  + this.bz +"', '"+ this.status + "');";
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
	 * @author zr
	 * @return
	 */
	public String editNoticeInfo() {
		try {
			final JSONObject jobj = new JSONObject();
				final String sql = "UPDATE `notice` SET `name` = '" + this.name +"', `title`='" + 
						this.title +"', `bz`='"+this.bz+"', `imgurl`='"+this.imgurl+"', `status`="+this.status+"  where id='" + this.id + "'";
				DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
				jobj.put("success", true);
				jobj.put("info", "修改成功");
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
	 * @author zr
	 * @return
	 */
	public String delNoticeInfo() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final String id = request.getParameter("id");
			final String name = request.getParameter("name");
			final String sqlquery = "select count(*) from notice where id=" + id ;
			final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sqlquery).executeQuery();
			final JSONObject jobj = new JSONObject();
			ret.next();
			final int count = ret.getInt(1);
			if (count == 0) {
				jobj.put("success", false);
				jobj.put("info", name + " 删除失败！通知不存在！");
			} else {
				final String sql = "DELETE FROM `notice` where `id`='" + id + "'";
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
	 * @author zr
	 * @datetime 2015-8-3_下午2:25:46
	 */
	public String getNoticeInfo() {
		try {
			final HttpServletResponse response = ServletActionContext.getResponse();
			final String sql = "select  id,name,title,imgurl,bz,if(status=0,'false','true') status from notice ";
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
				jobj.put("name", ret.getObject("name"));
				jobj.put("imgurl", ret.getObject("imgurl"));
				jobj.put("bz", ret.getObject("bz"));
				jobj.put("status",ret.getObject("status"));
				jobj.put("title", ret.getObject("title"));
				String sqlquery = "select count(*) from notice ";
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
	 * 紧急通知公告
	 * @return
	 */
	public String getNoticeJson() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final HttpServletResponse response = ServletActionContext.getResponse();
			final String name = request.getParameter("name");
			final String sql = "select  id,name,title,imgurl,bz,if(status=0,'false','true') status from notice  where status=true and  name='"+name+"' limit 0,1";
			JSONObject jobjTitle;
			JSONArray ja = null;
			jobjTitle = new JSONObject();
			try {
				final ResultSet ret = DataBasUtils.getInstance().getPrepareStatement(sql).executeQuery();// 执行语句，得到结果集
				ja = new JSONArray();
				jobjTitle.put("success", false);
				while (ret.next()) {
					final JSONObject jobj = new JSONObject();
					jobj.put("name", ret.getObject("name"));
					jobj.put("imgurl", ret.getObject("imgurl"));
					jobj.put("bz", ret.getObject("bz"));
					jobj.put("status",ret.getObject("status"));
					jobj.put("title", ret.getObject("title"));
					ja.add(jobj);
					jobjTitle.put("success", true);
					jobjTitle.put("items", ja);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				jobjTitle.put("success", false);
			}
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jobjTitle.toString());
			response.getWriter().flush();
			return Action.NONE;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.NONE;
	}
	/**
	 * @author zr
	 */
	public static void main(final String[] args) {
	}
}
