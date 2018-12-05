package com.pbtd.manager.vod.page.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.vod.page.service.face.IJsonInterfaceService;

/**
 * 中心平台通知分平台接口 tv
 * @author zr
 *
 */
@Controller("pbtdController.TvJsonInterfaceController")
@RequestMapping("/integrate/outputjson/tv")
public class TvJsonInterfaceController {

	
	@Autowired
	private VodcentralController vodcentral;
	
	@Autowired
	private IJsonInterfaceService jsonInterfaceService;
	
 
	
	   
	//专辑
	 @RequestMapping(value = "/album")
	    public String phonealbum(Model model,HttpServletRequest request) {
		 Date d = new Date();  
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	       String dateNowStr = sdf.format(d);  
		 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		  Date date=new Date(queryParams.get("curtime")==null?"":queryParams.get("curtime").toString());
			String s1 = sdf.format(date);  //2015-02-09  format()才是格式化
		 final String curtime=s1==""?dateNowStr:s1;
		 	try {
				Thread hth = new Thread(){
					@Override
					public void run() {
						try {
							//根据日期获取专辑信息
							Map<String, Object> map=new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.tv_album);
							jsonInterfaceService.tvalbuminfo(map);
						} catch (IllegalArgumentException ec) {
							interrupted();
						}  
					}
				};
				hth.start();
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    	return "1";
	    }

	 
	 //频道
	 @RequestMapping(value = "/channel")
	    public String phonechannel(Model model,HttpServletRequest request) {
		 Date d = new Date();  
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	       String dateNowStr = sdf.format(d);  
		 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		  Date date=new Date(queryParams.get("curtime")==null?"":queryParams.get("curtime").toString());
			String s1 = sdf.format(date);  //2015-02-09  format()才是格式化
		     final String curtime=s1==""?dateNowStr:s1;
		     
		 		try {
				Thread hth = new Thread(){
					@Override
					public void run() {
						try {
							//根据日期获取频道信息
							Map<String, Object> map=new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.tv_channel);
							jsonInterfaceService.tvchannel(map);
						} catch (IllegalArgumentException ec) {
							interrupted();
						}  
					}
				};
				hth.start();
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	return "1";
	    }
	//标签
	 @RequestMapping(value = "/label")
	    public String phonelabel(Model model,HttpServletRequest request) {
		 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		 Date d = new Date();  
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	       String dateNowStr = sdf.format(d);  
		  Date date=new Date(queryParams.get("curtime")==null?"":queryParams.get("curtime").toString());
			String s1 = sdf.format(date);  //2015-02-09  format()才是格式化
		     final String curtime=s1==""?dateNowStr:s1;
		     try {
				Thread hth = new Thread(){
					@Override
					public void run() {
						try {
							//根据日期获取标签信息
							Map<String, Object> map=new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.tv_label);
							jsonInterfaceService.tvlabel(map);
						} catch (IllegalArgumentException ec) {
							interrupted();
						}  
					}
				};
				hth.start();
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	return "1";
	    }
	 
		 //vod_tv_special 专题
		 @RequestMapping(value = "/special")
		    public String special(Model model,HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 Date d = new Date();  
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		       String dateNowStr = sdf.format(d);  
			 Date date=new Date(queryParams.get("curtime")==null?"":queryParams.get("curtime").toString());
				String s1 = sdf.format(date);  //2015-02-09  format()才是格式化
			 final String curtime=s1==""?dateNowStr:s1;
			 	try {
					Thread hth = new Thread(){
						@Override
						public void run() {
							try {
								//根据日期获取专题信息
								Map<String, Object> map=new HashMap<>();
								map.put("curtime", curtime);
								map.put("url", vodcentral.tv_special);
								jsonInterfaceService.tvspecial(map);
							} catch (IllegalArgumentException ec) {
								interrupted();
							}  
						}
					};
					hth.start();
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	return "1";
		    }
		 
}
