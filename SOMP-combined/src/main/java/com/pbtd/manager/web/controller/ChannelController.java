package com.pbtd.manager.web.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.query.ChannelQueryObject;
import com.pbtd.manager.service.ChannelService;
import com.pbtd.manager.util.PageResult;

@Controller
@RequestMapping("/channel")
public class ChannelController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelController.class);
	@Autowired
	private ChannelService channelService;

	@RequestMapping("page")
	public String page(Integer id, String cpCode,Long cpChannelId, Model model) {
		model.addAttribute("cpChannelId", id);
		model.addAttribute("cpCode", cpCode);
		return "/albuminfo/channel";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(ChannelQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = channelService.queryList(qo);
		} catch (Exception e) {
			LOGGER.error("cp直播数据管理 -列表查询失败！", e);
		}
		return queryList;
	}
}
