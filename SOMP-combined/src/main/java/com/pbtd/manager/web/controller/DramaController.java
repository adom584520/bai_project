package com.pbtd.manager.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.domain.Drama;
import com.pbtd.manager.service.DramaService;

@Controller
@RequestMapping("/drama")
public class DramaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DramaController.class);
	@Autowired
	private DramaService dramaService;

	@RequestMapping("page")
	public String page(Long seriesCode,Long cpDramaId,String cpCode,Model model) {
		model.addAttribute("seriesCode", seriesCode);
		model.addAttribute("cpDramaId", cpDramaId);
		return "/albuminfo/drama";
	}

	@RequestMapping("queryBySeriesCode")
	@ResponseBody
	public List<Drama> queryBySeriesCode(Long seriesCode) {
		List<Drama> cds = null;
		try {
			cds = dramaService.queryBySeriesCode(seriesCode);
		} catch (Exception e) {
			LOGGER.error("己方方剧集管理列表查询失败！", e);
			cds = new ArrayList<Drama>(2);
		}
		return cds;
	}
}
