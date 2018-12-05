package com.pbtd.playuser.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.playuser.service.impl.RedisService;
import com.pbtd.playuser.util.ResponseModal;


@RestController
public class ExampleController {

	
	@Autowired
	private RedisService redisService;
	
	
	@RequestMapping("/redis/set")
	public ResponseModal redisSet(@RequestParam("value")String value){
		boolean isOk = redisService.set("name", value);
		return new ResponseModal(isOk ? 200 : 500, isOk, isOk ? "success" : "error" , null);
	}
	
	@RequestMapping("/redis/get")
	public ResponseModal redisGet(){
		String name = redisService.get("name");
		return new ResponseModal(200, true,"success",name);
	}
	
}
