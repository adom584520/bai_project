package com.pbtd.playclick.pinyin.controller;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.playclick.pinyin.service.PinYinService;

@Controller
public class PinYinController {
	public static Logger log = Logger.getLogger("com.pbtd.playclick.pinyin.controller.PinYinController");
	
	@Autowired
	private PinYinService pinYinService;
	
	/**
	 * 增量汇聚解析出专辑和演员全拼.简拼
	 */
	@RequestMapping("/pinyin/add")
	public String AddPinYin(HttpServletResponse response)throws Exception {
		String flag = "1";
		try {
			pinYinService.getPinYin();;
		} catch (Exception e) {
			flag = "0";
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(flag);
		return null;
	}
}
