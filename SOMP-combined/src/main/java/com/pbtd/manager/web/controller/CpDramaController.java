package com.pbtd.manager.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.domain.CpDrama;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.service.CpDramaService;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("/cpDrama")
public class CpDramaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CpDramaController.class);
	@Autowired
	private CpDramaService cpDramaService;

	@RequestMapping("page")
	public String page(Long cpSeriesCode, Long seriesCode, String cpCode, Model model) {
		model.addAttribute("cpSeriesCode", cpSeriesCode);
		model.addAttribute("seriesCode", seriesCode);
		model.addAttribute("cpCode", cpCode);
		return "/albuminfo/cpDrama";
	}

	@RequestMapping("queryByAlbumId")
	@ResponseBody
	public List<CpDrama> queryByAlbumId(Long cpSeriesCode, String cpCode) {
		List<CpDrama> cds = null;
		try {
			cds = cpDramaService.queryByAlbumId(cpSeriesCode, cpCode);
		} catch (Exception e) {
			LOGGER.error("cp方剧集管理列表查询失败！", e);
			cds = new ArrayList<CpDrama>(2);
		}
		return cds;
	}

	@RequestMapping("bindingDrama")
	@ResponseBody
	public ResultBean<Object> bindingDrama(Long cpDramaId, Long dramaId) {
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			//cpDramaId,dramaId全局唯一
			cpDramaService.bindingDrama(cpDramaId, dramaId);
			resultBean.setMessage("绑定成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp方剧集管理-绑定剧集操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp方剧集管理-绑定剧集操作-", e);
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
			cpDramaService.confirm(list);
			resultBean.setMessage("确认绑定关系成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp方剧集管理-确认绑定关系操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp方剧集管理-确认绑定关系操作-", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}
}
