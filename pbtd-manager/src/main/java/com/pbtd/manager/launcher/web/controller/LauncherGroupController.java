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
import com.pbtd.manager.launcher.domain.LauncherGroup;
import com.pbtd.manager.launcher.page.LauncherGroupQueryObject;
import com.pbtd.manager.launcher.service.LauncherGroupService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("launcher_group")
public class LauncherGroupController {
	private static final  Logger logger = LoggerFactory.getLogger(LauncherGroupController.class);
	@Autowired
	private LauncherGroupService launcherGroupService;

	@RequestMapping("page")
	public String groupPage() {
		return "/launcher/group";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryListGroup(LauncherGroupQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = launcherGroupService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-查询失败",e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-分组管理-新增操作")
	public ResultBean<LauncherGroup> launcherGroupInsert(LauncherGroup launcherGroup) {
		ResultBean<LauncherGroup> json = new ResultBean<>();
		try {
			launcherGroupService.insert(launcherGroup);
			json.setMessage("添加成功！");
			json.setData(launcherGroup);
		} catch (JsonMessageException e) {
			logger.warn("运营管理-分组管理-新增操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-新增操作-"+e.getMessage(),e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-分组管理-编辑操作")
	public ResultBean<LauncherGroup> launcherGroupUpdate(LauncherGroup launcherGroup) {
		ResultBean<LauncherGroup> json = new ResultBean<>();
		try {
			launcherGroupService.update(launcherGroup);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-分组管理-编辑操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-编辑操作-"+e.getMessage(),e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-分组管理-删除操作")
	public ResultBean<LauncherGroup> launcherGroupDelete(Long id) {
		ResultBean<LauncherGroup> json = new ResultBean<>();
		try {
			launcherGroupService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-分组管理-删除操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-删除操作-"+e.getMessage(),e);
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
	@LogAnnotation(operationInfo = "运营管理-分组管理-批量删除操作")
	public ResultBean<LauncherGroup> deleteBatch(String ids) {
		ResultBean<LauncherGroup> json = new ResultBean<LauncherGroup>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			launcherGroupService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("运营管理-分组管理-批量删除操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统维护中，请稍后重试！");
			logger.error("运营管理-分组管理-批量删除操作-"+e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 查询所有分组 开机信息下拉框使用
	 * 
	 * @return
	 */
	@RequestMapping("group_list_all")
	@ResponseBody
	public List<LauncherGroup> queryGroupListAll() {
		return launcherGroupService.queryGroupListAll();
	}

}
