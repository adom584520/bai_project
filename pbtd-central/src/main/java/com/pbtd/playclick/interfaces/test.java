package com.pbtd.playclick.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
@Controller("test")
@RequestMapping("/test")
public class test {
	
	 @RequestMapping(value = {"/index", ""})
	 public  String play_history(HttpServletRequest request,HttpServletResponse response) throws IOException  {
		 JSONObject json = new JSONObject();
			response.setContentType("text/html;charset=utf-8");
			String cht_id=request.getParameter("cht_id")==null?"":request.getParameter("cht_id").toString();//鉴权成功返回值（base64加密）
			String pro_id=request.getParameter("pro_id")==null?"":request.getParameter("pro_id").toString();//运营平台生成的项目ID
			String mac=request.getParameter("mac")==null?"":request.getParameter("mac").toString();//mac
			String use_id=request.getParameter("user_id")==null?"":request.getParameter("user_id").toString();//用户id
			String type=request.getParameter("type")==null?"1":request.getParameter("type").toString();//
			response.setContentType("text/html;charset=utf-8");
			int start = 0;
			int limit = 20;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("type_id",  1 );
	     	m.put("imgNor", "http://112.13.167.159/nav_home.png");
			m.put("imgSelect", "http://112.13.167.159/nav_home_after.png");
			Map<String,Object> m1=new HashMap<String,Object>();
			m1.put("type_id", 2 );
			m1.put("imgNor", "http://112.13.167.159/nav_live.png");
			m1.put("imgSelect", "http://112.13.167.159/nav_live_after.png");
			Map<String,Object> m4=new HashMap<String,Object>();
			m4.put("type_id",  5 );
			m4.put("imgNor", "http://112.13.167.159/nav_flow.png");
			
			m4.put("imgSelect", "http://112.13.167.159/nav_flow_after.png");
			Map<String,Object> m5=new HashMap<String,Object>();
			m5.put("type_id",  6);
			m5.put("imgNor", "http://112.13.167.159/nav_menber.png");
			m5.put("imgSelect", "http://112.13.167.159/nav_menber_after.png");
	    	Map<String,Object> m3=new HashMap<String,Object>();
			m3.put("type_id",  4 );
			m3.put("imgNor", "http://112.13.167.159/nav_my.png");
			m3.put("imgSelect", "http://112.13.167.159/nav_my_after.png");
	    	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			list.add(m);list.add(m1);list.add(m4);list.add(m5);list.add(m3);
			json.accumulate("body", JSON.toJSONString(list));
			response.getWriter().write(json.toString());
			return null;
}
}
