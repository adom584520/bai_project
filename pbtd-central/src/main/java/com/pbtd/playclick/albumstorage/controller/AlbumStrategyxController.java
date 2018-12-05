package com.pbtd.playclick.albumstorage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.albumstorage.service.face.IAlbumStrategyService;
import com.pbtd.playclick.base.common.mvc.MutilCustomDateEditor;
import com.pbtd.playclick.guoguang.controller.CibnStorageController;
import com.pbtd.playclick.heyi.controller.GuttvStorageController;
import com.pbtd.playclick.yinhe.controller.YhStorageController;
@Controller("StrategyxController")
@RequestMapping("/central/strategy")
public class AlbumStrategyxController {

	@Autowired
	private IAlbumStrategyService strategyService;

	@Autowired
	private CibnStorageController guoguangcontroller;
	@Autowired
	private GuttvStorageController  guttvcontroller;
	@Autowired
	private YhStorageController  yinhecontroller;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new MutilCustomDateEditor("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" ));
	}
	/**
	 * 根据规则更新汇聚数据 国广银河  合一
	 * @throws InterruptedException 
	 */
	@RequestMapping("/updateStrategalbum")
	@ResponseBody
	public void updateStrategalbum() throws InterruptedException { 
		guoguangcontroller.albuminfo();//汇聚国广
		guttvcontroller.albuminfo();//汇聚合一
		yinhecontroller.albuminfo();//汇聚银河
		Map<String, Object> queryParams=new HashMap<>();
		strategyService.updateStrategalbum(queryParams);
		
	}
}