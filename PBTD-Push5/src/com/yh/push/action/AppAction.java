package com.yh.push.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.yh.push.bean.PushUser;
import com.yh.push.service.AppService;
import com.yh.push.utils.pushUtils;

@SuppressWarnings("all")
@Controller("appAction")
@Scope("prototype")
public class AppAction extends ActionSupport {
	public static Logger log = Logger.getLogger(AppAction.class);
	@Autowired
	private AppService appService;
	private pushUtils push = new pushUtils();

	// 状态0:已绑定,1:绑定,2:推送,3:解绑
	public String receivePost() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		String type = "2";
		boolean bl = false;
		String status = "0";
		try {
			reqBody = push.getrequest(request);
			log.info("推屏参数: " + reqBody);
			// 将json字符串转换为json对象
			Obj = JSONObject.parseObject(reqBody);
			// 查询绑定参数
			PushUser pushUser = appService.getselect(Obj.get("pUserId").toString(), Obj.get("tvUserId").toString());
			if (pushUser != null) {
				// xxxx用户推送xxx影片成功
				String userPhone = pushUser.getPNum();
				String tvName = pushUser.getTvName();
				if ("1".equals(Obj.get("deviceType")) || "2".equals(Obj.get("deviceType"))) {
					bl = push.sendTvUnicast(pushUser.getTvToken(), "收到" + userPhone + "推送的影片", reqBody, type);
					log.info("TV单播");
				} else if ("3".equals(Obj.get("deviceType"))) {
					if ("1".equals(pushUser.getPSystem())) {
						bl = push.sendAndroidUnicast(pushUser.getPToken(), "收到" + tvName + "推送的影片", reqBody, type);
						log.info("安卓单播");
					} else if ("2".equals(pushUser.getPSystem())) {
						bl = push.sendIOSUnicast(pushUser.getPToken(), "收到" + tvName + "推送的影片", reqBody, type);
						log.info("IOS单播");
					}
				}
			} else {
				json.put("code", 0);
				json.put("message", "未绑定");
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			}
		} catch (Exception e) {
			log.error("推屏参数有误" + e);
			json.put("code", 0);
			json.put("message", "推屏参数有误");
			response.getWriter().write(json.toString());// 传递给页面
			return null;
		}
		// 判断友盟推送服务是否正常
		if (bl) {
			json.put("code", 1);
			if ("1".equals(Obj.get("deviceType")) || "2".equals(Obj.get("deviceType"))) {
				json.put("message", "推送成功，可在电视端上观看推送内容");
			} else if ("3".equals(Obj.get("deviceType"))) {
				json.put("message", "推送成功，可在手机端上观看推送内容");
			}
			log.info("=====" + reqBody + "...");
		} else {
			json.put("code", 0);
			json.put("message", "推送失败，请检查网络情况后再次推送");
		}
		response.getWriter().write(json.toString());// 传递给页面
		return null;
	}

	// 手机绑定TV
	public String getTv() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		String type = "1";
		try {
			reqBody = push.getrequest(request);
			log.info("绑定TV参数: " + reqBody);
			// 将json字符串转换为json对象
			Obj = JSONObject.parseObject(reqBody);
			try {
				String pUserId = Obj.get("pUserId") == null ? "" : Obj.get("pUserId").toString();
				String tvUserId = Obj.get("tvUserId") == null ? "" : Obj.get("tvUserId").toString();
				String pToken = Obj.get("pToken") == null ? "" : Obj.get("pToken").toString();
				String tvToken = Obj.get("tvToken") == null ? "" : Obj.get("tvToken").toString();
				if (!"".equals(pUserId) && !"".equals(tvUserId) && !"".equals(pToken) && !"".equals(tvToken)) {
					// 查询绑定参数
					PushUser pushUser = appService.getselect(pUserId, tvUserId);
					if (pushUser != null) {
						log.info(pushUser.getPNum() + "已绑定该设备,无需重复绑定!");
						push.sendTvUnicast(tvToken, "该设备已绑定您,无需绑定", "该设备已绑定您,无需绑定", "0");
						json.put("code", 0);
						json.put("message", "已绑定该设备,无需重复绑定!");
						response.getWriter().write(json.toString());// 传递给页面
						return null;
					}
				} else {
					log.error("绑定参数有误" + Obj);
					json.put("code", 0);
					json.put("message", "绑定参数有误");
					response.getWriter().write(json.toString());// 传递给页面
					return null;
				}
			} catch (Exception ee) {
				log.error("绑定参数有误" + ee);
				json.put("code", 0);
				json.put("message", "绑定参数有误");
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			}
			// 解除TV所有绑定
			PushUser pushUserDel = appService.getDeleteBy(Obj.get("tvUserId").toString());
			if (pushUserDel != null) {
				if ("1".equals(pushUserDel.getPSystem())) {
					push.sendAndroidUnicast(pushUserDel.getPToken(), "您的绑定关系已经被" + Obj.get("pNum").toString() + "用户解除",
							"您的绑定关系已经被" + Obj.get("pNum").toString() + "用户解除", "3");
					log.info("安卓单播TV端与你解绑");
				} else if ("2".equals(pushUserDel.getPSystem())) {
					push.sendIOSUnicast(pushUserDel.getPToken(), "您的绑定关系已经被" + Obj.get("pNum").toString() + "用户解除",
							"您的绑定关系已经被" + Obj.get("pNum").toString() + "用户解除", "3");
					log.info("IOS单播TV端与你解绑");
				}
			}
			// 将建json对象转换为PushUser对象
			PushUser jb = JSONObject.toJavaObject(Obj, PushUser.class);
			// 手机和TV绑定参数
			appService.getAddTv(jb);
			if ("1".equals(Obj.get("pSystem"))) {
				push.sendAndroidUnicast(Obj.get("pToken").toString(), "绑定成功", "绑定成功", type);
				log.info("安卓单播绑定TV成功");
				push.sendTvUnicast(Obj.get("tvToken").toString(), "绑定成功", reqBody, type);
				log.info("发送到TV单播绑定");
			} else if ("2".equals(Obj.get("pSystem"))) {
				push.sendIOSUnicast(Obj.get("pToken").toString(), "绑定成功", "绑定成功", type);
				log.info("IOS单播绑定TV成功");
				push.sendTvUnicast(Obj.get("tvToken").toString(), "绑定成功", reqBody, type);
				log.info("发送到TV单播绑定");
			}
		} catch (Exception e) {
			log.error("绑定参数有误" + e);
			json.put("code", 0);
			json.put("message", "绑定失败");
			response.getWriter().write(json.toString());// 传递给页面
			return null;
		}
		log.info("手机和TV绑定成功");
		json.put("code", 1);
		json.put("message", "绑定成功");
		response.getWriter().write(json.toString());// 传递给页面
		return null;
	}

	// 手机和TV解绑
	public String getDeleteToken() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		String type = "3";
		try {
			reqBody = push.getrequest(request);
			log.info("解绑参数: " + reqBody);
			// 将json字符串转换为json对象
			Obj = JSONObject.parseObject(reqBody);
			if (Obj.get("deviceType") != null && Obj.get("pUserId") != null && Obj.get("tvUserId") != null) {
				String pUserId = Obj.get("pUserId").toString();
				String tvUserId = Obj.get("tvUserId").toString();
				String deviceType = Obj.get("deviceType").toString();
				// 解绑绑定关系
				PushUser pushUser = appService.getSelectDel(pUserId, tvUserId);
				if (pushUser != null) {
					if ("1".equals(deviceType) || "2".equals(deviceType)) {
						push.sendTvUnicast(pushUser.getTvToken(), "您和手机端的绑定关系已经解除", pushUser.getTvUserId(), type);
						log.info("手机解绑TV");
					} else if ("3".equals(deviceType)) {
						if ("1".equals(pushUser.getPSystem())) {
							push.sendAndroidUnicast(pushUser.getPToken(), "您和TV端的绑定关系已经解除", pushUser.getPUserId(), type);
							log.info("TV解绑手机A");
						} else if ("2".equals(pushUser.getPSystem())) {
							push.sendIOSUnicast(pushUser.getPToken(), "您和TV端的绑定关系已经解除", pushUser.getPUserId(), type);
							log.info("TV解绑手机I");
						}
					}
				}

			} else {
				json.put("code", 0);
				json.put("message", "解绑参数有误");
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			}
		} catch (Exception e) {
			log.error("解绑参数有误" + e);
			json.put("code", 1);
			json.put("message", "解绑失败，请检查网络情况后再次尝试");
			log.info("===== 解绑失败 ...");
			response.getWriter().write(json.toString());// 传递给页面
			return null;
		}
		json.put("code", 1);
		json.put("message", "解绑成功");
		log.info("===== 推送解绑 ...");
		response.getWriter().write(json.toString());// 传递给页面
		return null;
	}

	// 更新token
	public String updateDevieToken() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		try {
			reqBody = push.getrequest(request);
			log.info("更新Token参数: " + reqBody);
			// 将json字符串转换为json对象
			Obj = JSONObject.parseObject(reqBody);
		} catch (Exception e) {
			log.error("更新token参数有误" + e);
			json.put("code", 0);
			json.put("message", "更新token参数有误");
			response.getWriter().write(json.toString());// 传递给页面
			return null;
		}
		String deviceType = Obj.get("deviceType") == null ? "" : Obj.get("deviceType").toString();
		String pUserId = Obj.get("pUserId") == null ? "" : Obj.get("pUserId").toString();
		String tvUserId = Obj.get("tvUserId") == null ? "" : Obj.get("tvUserId").toString();
		String pToken = Obj.get("pToken") == null ? "" : Obj.get("pToken").toString();
		String tvToken = Obj.get("tvToken") == null ? "" : Obj.get("tvToken").toString();
		int getdelete = 0;
			if ("1".equals(deviceType)) {
				if (!"".equals(pToken)) {
				getdelete = appService.getTokenPhone(pUserId, pToken, deviceType);
				log.info(getdelete + "更新");
				}else {
					log.error("===== 安卓更新的token为空 =====");
				}
			} else if ("2".equals(deviceType)) {
				if (!"".equals(pToken)) {
				getdelete = appService.getTokenPhone(pUserId, pToken, deviceType);
				log.info(getdelete + "更新");
				}else {
					log.error("===== IOS更新的token为空 =====");
				}
			} else if ("3".equals(deviceType)) {
				if (!"".equals(tvToken)) {
				getdelete = appService.getTokenTv(tvUserId, tvToken);
				log.info(getdelete + "更新");
				}else {
					log.error("===== TV更新的token为空 =====");
				}
			}
		
		if (getdelete != 0) {
			json.put("code", 1);
			json.put("message", "更新token成功");
			log.info("===== 更新token ...");
		} else {
			json.put("code", 0);
			json.put("message", "无token更新");
		}
		response.getWriter().write(json.toString());// 传递给页面
		return null;
	}

	// 查看绑定关系
	public String getSelectToken() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		// 读取请求内容
		String reqBody = null;
		JSONObject Obj = null;
		List<PushUser> list = new ArrayList<PushUser>();
		try {
			reqBody = push.getrequest(request);
			log.info("查看绑定参数: " + reqBody);
			// 将json字符串转换为json对象
			Obj = JSONObject.parseObject(reqBody);
			String deviceType = Obj.get("deviceType") == null ? "" : Obj.get("deviceType").toString();
			String pUserId = Obj.get("pUserId") == null ? "" : Obj.get("pUserId").toString();
			String tvUserId = Obj.get("tvUserId") == null ? "" : Obj.get("tvUserId").toString();
			if ("1".equals(Obj.get("deviceType")) || "2".equals(Obj.get("deviceType"))) {
				list = appService.getselectPhone(pUserId);
			} else if ("3".equals(Obj.get("deviceType"))) {
				list = appService.getselectTv(tvUserId);
			}
		} catch (Exception e) {
			log.error("查看绑定参数有误" + e);
			json.put("code", 0);
			json.put("message", "查看绑定参数有误");
			response.getWriter().write(json.toString());// 传递给页面
			return null;
		}

		if (list.size() > 0) {
			json.put("code", 1);
			json.put("message", "成功");
			json.put("body", list);
		} else {
			json.put("code", 1);
			json.put("message", "无数据");
			json.put("body", list);
		}
		response.getWriter().write(json.toString());// 传递给页面
		return null;
	}
}