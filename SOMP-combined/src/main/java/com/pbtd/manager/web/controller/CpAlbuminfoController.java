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
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.query.CpAlbuminfoQueryObject;
import com.pbtd.manager.service.CpAlbuminfoService;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("/cpAlbuminfo")
public class CpAlbuminfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CpAlbuminfoController.class);
	@Autowired
	private CpAlbuminfoService cpAlbuminfoService;

	@RequestMapping("page")
	public String page() {
		return "/albuminfo/cpAlbuminfo";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(CpAlbuminfoQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = cpAlbuminfoService.queryList(qo);
		} catch (Exception e) {
			LOGGER.error("cp方专辑管理-列表查询失败！", e);
		}
		return queryList;
	}

	@RequestMapping("bindingAlbum")
	@ResponseBody
	public ResultBean<Object> bindingAlbum(Long id, Long seriesCode, String seriesName, String cpCode) {
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			cpAlbuminfoService.bindingAlbum(id, seriesCode, seriesName, cpCode);
			resultBean.setMessage("绑定成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp方专辑管理-绑定专辑操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp方专辑管理-绑定专辑操作-", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}

	@RequestMapping("unpinless")
	@ResponseBody
	public ResultBean<Object> unpinless(String ids) {
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			cpAlbuminfoService.unpinless(list);
			resultBean.setMessage("解除绑定成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp方专辑管理-解除绑定专辑操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp方专辑管理-解除绑定专辑操作-", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}

	@RequestMapping("confirm")
	@ResponseBody
	public ResultBean<Object> confirm(String ids) {
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			cpAlbuminfoService.confirm(list);
			resultBean.setMessage("确认绑定关系成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp方专辑管理-确认绑定关系操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp方专辑管理-确认绑定关系操作-", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}

}
