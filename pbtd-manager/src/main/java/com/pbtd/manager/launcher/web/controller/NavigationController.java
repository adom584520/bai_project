package com.pbtd.manager.launcher.web.controller;

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
import com.pbtd.manager.launcher.domain.Navigation;
import com.pbtd.manager.launcher.page.NavigationQueryObject;
import com.pbtd.manager.launcher.service.NavigationService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("navigation")
public class NavigationController {
	private static final Logger logger = LoggerFactory.getLogger(NavigationController.class);
	@Autowired
	private NavigationService navigationService;

	@RequestMapping("page")
	public String macPage() {
		return "/launcher/nav/nav";
	}

	/**
	 * 列表分页查询
	 * 
	 * @param qo
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(NavigationQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = navigationService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-导航管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-导航管理-新增操作")
	public ResultBean<Navigation> insert(Navigation nav) {
		ResultBean<Navigation> json = new ResultBean<>();
		try {
			navigationService.insert(nav);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-导航管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-导航管理-新增操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-导航管理-编辑操作")
	public ResultBean<Navigation> update(Navigation nav) {
		ResultBean<Navigation> json = new ResultBean<>();
		try {
			navigationService.update(nav);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-导航管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-导航管理-编辑操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-导航管理-删除操作")
	public ResultBean<Navigation> delete(Long id) {
		ResultBean<Navigation> json = new ResultBean<>();
		try {
			navigationService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-导航管理-删除操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-导航管理-删除操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
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
	@LogAnnotation(operationInfo = "运营管理-导航管理-批量删除操作")
	public ResultBean<Navigation> deleteBatch(String ids) {
		ResultBean<Navigation> json = new ResultBean<Navigation>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			navigationService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("运营管理-导航管理-批量删除操作-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统维护中，请稍后重试！");
			logger.error("运营管理-导航管理-批量删除操作-" + e.getMessage(), e);
		}
		return json;
	}

	@RequestMapping("upline_or_downline")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-导航管理-上下线操作")
	public ResultBean<Navigation> uplineOrDownLine(Navigation nav) {
		ResultBean<Navigation> json = new ResultBean<>();
		try {
			navigationService.uplineOrDownLine(nav);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-导航管理-上下线操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-导航管理-上下线操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("list_by_group_id")
	@ResponseBody
	public List<Navigation> queryListByGroupId(Long groupId) {
		List<Navigation> data = null;
		try {
			data = navigationService.queryListByGroupId(groupId);
		} catch (Exception e) {
			logger.error("运营管理-导航管理-查询失败", e);
		}
		return data;
	}
}
