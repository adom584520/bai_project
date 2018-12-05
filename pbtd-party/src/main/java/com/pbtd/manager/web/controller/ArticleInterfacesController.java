package com.pbtd.manager.web.controller;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.domain.Group;
import com.pbtd.manager.domain.JSONEmptyObject;
import com.pbtd.manager.dto.ArticleDTO;
import com.pbtd.manager.dto.ArticleListDTO;
import com.pbtd.manager.service.ArticleService;
import com.pbtd.manager.service.GroupService;
import com.pbtd.manager.util.DateUtil;

@RestController
@RequestMapping("/tv/party")
public class ArticleInterfacesController {
	private static final Logger logger = LoggerFactory.getLogger(ArticleInterfacesController.class);
	@Autowired
	private ArticleService articleService;
	@Autowired
	private GroupService groupService;

	@RequestMapping("getArticleList")
	public String getArticleList(Integer classId, Long groupId) {
		HashMap<String, Object> map = new HashMap<>();
		ArticleListDTO articleList = null;
		try {
			articleList = articleService.queryArticleList(classId, groupId);
			if (articleList != null) {
				map.put("code", 1);
				map.put("message", "查询成功！");
				map.put("timestamp", DateUtil.date2Num(new Date()));
				map.put("data", articleList);
			} else {
				map.put("code", 0);
				map.put("message", "查询失败！");
				map.put("timestamp", DateUtil.date2Num(new Date()));
				map.put("data", JSONEmptyObject.EMPTY);
			}
		} catch (Exception e) {
			map.put("code", 0);
			map.put("message", "查询失败！");
			map.put("timestamp", DateUtil.date2Num(new Date()));
			map.put("data", JSONEmptyObject.EMPTY);
			logger.error("文章查询列表接口查询失败！", e);
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping("getArticleById")
	public String getArticleById(Long articleId) {
		HashMap<String, Object> map = new HashMap<>();
		ArticleDTO dto = null;
		try {
			dto = articleService.queryArticleById(articleId);
			if (dto != null) {
				HashMap<Object, Object> params = new HashMap<>();
				params.put("bussId", dto.getBussId());
				params.put("title", dto.getTitle());
				params.put("classId", dto.getClassId());
				params.put("className", dto.getClassName());
				params.put("content", dto.getContent());
				params.put("pics", dto.getPics());
				map.put("code", 1);
				map.put("message", "查询成功！");
				map.put("timestamp", DateUtil.date2Num(new Date()));
				map.put("data", params);
			} else {
				map.put("code", 0);
				map.put("message", "该文章不存在或已删除！");
				map.put("timestamp", DateUtil.date2Num(new Date()));
				map.put("data", JSONEmptyObject.EMPTY);
			}
		} catch (Exception e) {
			map.put("code", 0);
			map.put("message", "查询失败！");
			map.put("timestamp", DateUtil.date2Num(new Date()));
			map.put("data", JSONEmptyObject.EMPTY);
			logger.error("文章查询单条记录接口查询失败！", e);
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping("validateMac")
	public String validateMac(String mac) {
		HashMap<String, Object> map = new HashMap<>();
		Group group = null;
		try {
			group = groupService.queryByMac(mac);
			if (group != null) {
				map.put("code", 1);
				map.put("message", "查询成功！");
				map.put("timestamp", DateUtil.date2Num(new Date()));
				HashMap<String, Object> params = new HashMap<>();
				params.put("groupId",group.getId());
				params.put("groupName",group.getGroupName());
				map.put("data", params);
			} else {
				map.put("code", 0);
				map.put("message", "MAC未绑定！");
				map.put("timestamp", DateUtil.date2Num(new Date()));
				map.put("data", JSONEmptyObject.EMPTY);
			}
		} catch (Exception e) {
			map.put("code", 0);
			map.put("message", "查询失败！");
			map.put("timestamp", DateUtil.date2Num(new Date()));
			map.put("data", JSONEmptyObject.EMPTY);
			logger.error("根据mac查询分组ID失败！", e);
		}
		return JSON.toJSONString(map);
	}

}
