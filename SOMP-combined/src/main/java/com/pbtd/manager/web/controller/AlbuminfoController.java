package com.pbtd.manager.web.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.query.AlbuminfoQueryObject;
import com.pbtd.manager.service.AlbuminfoService;
import com.pbtd.manager.util.PageResult;

@Controller
@RequestMapping("/albuminfo")
public class AlbuminfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlbuminfoController.class);
	@Autowired
	private AlbuminfoService albuminfoService;

	@RequestMapping("page")
	public String page(Long cpAlbuminfoId, String cpCode, Model model) {
		model.addAttribute("cpAlbuminfoId", cpAlbuminfoId);
		model.addAttribute("cpCode", cpCode);
		return "/albuminfo/albuminfo";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(AlbuminfoQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = albuminfoService.queryList(qo);
		} catch (Exception e) {
			LOGGER.error("己方专辑数据管理-列表查询失败！", e);
		}
		return queryList;
	}
}
