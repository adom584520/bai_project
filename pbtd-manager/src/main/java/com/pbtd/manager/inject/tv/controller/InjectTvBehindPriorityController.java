package com.pbtd.manager.inject.tv.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.inject.tv.service.face.IInjectTvBehindPriorityService;


@Controller
@RequestMapping("/inject/InjectTvBehindPriority")
public class InjectTvBehindPriorityController {

	@Autowired
	private IInjectTvBehindPriorityService InjectTvBehindPriorityService;
	
	
	@RequestMapping("/dealInjection")
	public void dealInjection(){
		this.InjectTvBehindPriorityService.dealInjection();
	}
	
	
	
	
	
}
