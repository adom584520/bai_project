package com.pbtd.playclick.guoguang.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.guoguang.service.ICibnStorageService;
@Controller("cibn.CibnStorageController")
@RequestMapping("/guoguang/strategy")
public class CibnStorageController {
	
	@Autowired
	private ICibnStorageService cibnservice;
	//国广爬取数据结束 更新数据保存到临时表vod_albuminfo_strategy
	@RequestMapping(value = {"/albuminfo"})
	public void  albuminfo(){
		Map<String, Object> map=new HashMap<>();
		cibnservice.albumStorage(map);
	}
	
	
	/**
	 * 入库 根据id入库
	 */
	@ResponseBody
 	@RequestMapping("/albuminfoid" )
	public String albuminfo(ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] ids=queryParams.get("id").toString().split(",");
		 for (String id : ids) {
				queryParams.put("seriescode", id);
				cibnservice.albumStorageid(queryParams);
		 }
		return "";
	}

}
