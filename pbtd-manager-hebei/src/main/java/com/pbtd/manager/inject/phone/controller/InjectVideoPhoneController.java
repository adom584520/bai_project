package com.pbtd.manager.inject.phone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.inject.phone.service.face.InjectVideoService;


/**   
* @Description: TODO(手机端视频专辑/剧集注入controller) 
* @author lzl 
* @date 2017年11月16日 下午3:26:05 
* @version V1.0   
*/
//@Controller
//@RequestMapping("/inject/video/phone")
public class InjectVideoPhoneController {
	
	@Autowired
	private InjectVideoService injService;
	
	/**
	 * 手机视频注入逻辑视图
	 * @author lzl
	 * @version 创建时间 ：2017年11月16日 下午3:59:11 
	 * @return
	 */
	@RequestMapping(value = { "/index", "" })
	public String index() {
		return "/inject/priority/index";
	}
}
