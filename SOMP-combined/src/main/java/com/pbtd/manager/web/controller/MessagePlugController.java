package com.pbtd.manager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plug/message")
public class MessagePlugController {
	@RequestMapping("page")
	public String page(){
		return "/message";
	}
}
