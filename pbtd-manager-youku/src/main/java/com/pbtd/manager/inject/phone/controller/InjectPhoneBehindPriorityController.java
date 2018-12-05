package com.pbtd.manager.inject.phone.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.inject.phone.service.face.IInjectPhoneBehindPriorityService;

@Controller
@RequestMapping("/inject/InjectPhoneBehindPriority")
public class InjectPhoneBehindPriorityController {

	@Autowired
	private IInjectPhoneBehindPriorityService InjectPhoneBehindPriorityService;
	
	
	@RequestMapping("/dealInjection")
	public void dealInjection(){
		this.InjectPhoneBehindPriorityService.dealInjection();
	}
	
	
	
	
	
}
