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
import com.pbtd.manager.domain.Article;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.query.ArticleQueryObject;
import com.pbtd.manager.service.ArticleService;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("article")
public class ArticleController {
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	private ArticleService articleService;

	@RequestMapping("page")
	public String groupPage() {
		return "/pb/article";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(ArticleQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = articleService.queryList(qo);
		} catch (Exception e) {
			logger.error("文章查询列表查询失败！", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	public ResultBean<Article> insert(Article article) {
		ResultBean<Article> json = new ResultBean<>();
		try {
			articleService.insert(article);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-分组管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-新增操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	public ResultBean<Article> update(Article article) {
		ResultBean<Article> json = new ResultBean<>();
		try {
			articleService.update(article);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("文章编辑操作:" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("文章编辑操作:", e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResultBean<Article> delete(Long id) {
		ResultBean<Article> json = new ResultBean<>();
		try {
			articleService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("文章删除操作:" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("文章删除操作:", e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete_batch")
	@ResponseBody
	public ResultBean<Article> deleteBatch(String ids) {
		ResultBean<Article> json = new ResultBean<>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			articleService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("文章批量删除操作:" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统错误，请联系管理员！");
			logger.error("文章批量删除操作:", e);
		}
		return json;
	}
}
