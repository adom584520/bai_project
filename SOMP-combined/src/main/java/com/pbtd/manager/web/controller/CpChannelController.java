package com.pbtd.manager.web.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.domain.CpChannel;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.query.CpChannelQueryObject;
import com.pbtd.manager.service.CpChannelService;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("/cpChannel")
public class CpChannelController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CpChannelController.class);
	@Autowired
	private CpChannelService cpChannelService;

	@RequestMapping("page")
	public String page() {
		return "/albuminfo/cpChannel";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(CpChannelQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = cpChannelService.queryList(qo);
		} catch (Exception e) {
			LOGGER.error("cp直播数据管理 -列表查询失败！", e);
		}
		return queryList;
	}

	@RequestMapping("bindingChannel")
	@ResponseBody
	public ResultBean<Object> bindingChannel(CpChannel cc) {
		ResultBean<Object> json = new ResultBean<>();
		try {
			cpChannelService.bindingChannel(cc);
			json.setMessage("绑定成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp方频道数据管理-绑定频道"+ e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp方频道数据管理-绑定频道" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("unpinless")
	@ResponseBody
	public ResultBean<Object> unpinless(String ids) {
		ResultBean<Object> json = new ResultBean<>();
		try {
			List<Integer> list = JSON.parseArray(ids, Integer.class);
			cpChannelService.unpinless(list);
			json.setMessage("解除绑定成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp频道数据管理-解除绑定操作！" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp频道数据管理-解除绑定操作！" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
}
