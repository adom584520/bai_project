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
import com.pbtd.manager.domain.Group;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.query.GroupQueryObject;
import com.pbtd.manager.service.GroupService;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("group")
public class GroupController {
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	@Autowired
	private GroupService groupService;

	@RequestMapping("page")
	public String groupPage() {
		return "/pb/group";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryListGroup(GroupQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = groupService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	public ResultBean<Group> launcherGroupInsert(Group launcherGroup) {
		ResultBean<Group> json = new ResultBean<>();
		try {
			groupService.insert(launcherGroup);
			json.setMessage("添加成功！");
			json.setData(launcherGroup);
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
	public ResultBean<Group> launcherGroupUpdate(Group launcherGroup) {
		ResultBean<Group> json = new ResultBean<>();
		try {
			groupService.update(launcherGroup);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-分组管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-编辑操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResultBean<Group> launcherGroupDelete(Long id) {
		ResultBean<Group> json = new ResultBean<>();
		try {
			groupService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-分组管理-删除操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-分组管理-删除操作-" + e.getMessage(), e);
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
	public ResultBean<Group> deleteBatch(String ids) {
		ResultBean<Group> json = new ResultBean<Group>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			groupService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("运营管理-分组管理-批量删除操作-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统错误，请联系管理员！");
			logger.error("运营管理-分组管理-批量删除操作-" + e.getMessage(), e);
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
	public List<Group> queryGroupListAll() {
		return groupService.queryGroupListAll();
	}

}
