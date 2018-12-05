package com.pbtd.manager.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pbtd.manager.domain.PushUser;
import com.pbtd.manager.service.AppService;
import com.pbtd.manager.util.pushUtils;

import net.sf.json.JSONObject;

@SuppressWarnings("all")
@Controller
public class LiveAppController{
	public static Logger log = Logger.getLogger(LiveAppController.class);
	@Autowired
	private AppService appService;
	private pushUtils push = new pushUtils();

	public String appoint(HttpServletRequest request,HttpServletResponse response) {
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
			Obj = JSONObject.fromObject(reqBody);// 将json字符串转换为json对象
			PushUser pushUser = new PushUser();
			if ("1".equals(Obj.get("des_type")) || "2".equals(Obj.get("des_type"))) {
				pushUser = appService.getselect(Obj.get("des_token").toString(), Obj.get("src_token").toString());
			} else {
				pushUser = appService.getselect(Obj.get("src_token").toString(), Obj.get("des_token").toString());
			}
			if (pushUser != null) {
				status = pushUser.getStatus();
			}
			if ("1".equals(status)) {
				if ("1".equals(Obj.get("des_type"))) {
					bl = push.sendAndroidUnicast(Obj.get("des_token").toString(), "播放推屏影片: " + Obj.get("title").toString(),
							reqBody, type);
					log.info("安卓单播");
				} else if ("2".equals(Obj.get("des_type"))) {
					bl = push.sendIOSUnicast(Obj.get("des_token").toString(), "播放推屏影片: " + Obj.get("title").toString(),
							reqBody, type);
					log.info("IOS单播");
				} else if ("3".equals(Obj.get("des_type"))) {
					bl = push.sendTvUnicast(Obj.get("des_token").toString(), "播放推屏影片: " + Obj.get("title").toString(),
							reqBody, type);
					log.info("TV单播");
				}
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "未绑定");
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			}
		} catch (Exception e) {
			log.error("推屏参数有误" + e);
			json.accumulate("code", 0);
			json.accumulate("message", "推屏参数有误");
			try {
				response.getWriter().write(json.toString());// 传递给页面
				return null;
			} catch (IOException e1) {
				log.error("推屏参数有误" + e);
			}
		}
		try {
			if (bl) {
				json.accumulate("code", 1);
				json.accumulate("message", "成功");
				log.info("=====" + reqBody + "...");
			} else {
				json.accumulate("code", 0);
				json.accumulate("message", "访问超时");
			}
			response.getWriter().write(json.toString());// 传递给页面
		} catch (IOException e) {
			log.error("返回参数有误" + e);
		}
		return null;
	}

}