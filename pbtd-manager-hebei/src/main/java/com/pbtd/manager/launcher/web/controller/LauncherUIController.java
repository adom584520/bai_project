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
import com.pbtd.manager.launcher.domain.LauncherUI;
import com.pbtd.manager.launcher.page.LauncherUIQueryObject;
import com.pbtd.manager.launcher.service.LauncherUIService;
import com.pbtd.manager.system.util.PermissionAnnotation;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("/launcher_ui")
public class LauncherUIController {
	private static final  Logger logger = LoggerFactory.getLogger(LauncherUIController.class);
	@Autowired
	private LauncherUIService launcherUIService;

	@RequestMapping("page")
	@PermissionAnnotation(value = "/launcher_ui/page")
	public String launcherUIPage() {
		return "/launcher/ui/ui";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult launcherUIQueryList(LauncherUIQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = launcherUIService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-UI管理-查询失败",e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-UI管理-新增操作")
	public ResultBean<LauncherUI> insert(LauncherUI launcherUI) {
		ResultBean<LauncherUI> json = new ResultBean<>();
		try {
			launcherUIService.insert(launcherUI);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-UI管理-新增操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-UI管理-新增操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-UI管理-编辑操作")
	public ResultBean<LauncherUI> update(LauncherUI groupDetail) {
		ResultBean<LauncherUI> json = new ResultBean<>();
		try {
			launcherUIService.update(groupDetail);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-UI管理-编辑操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-UI管理-编辑操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-UI管理-删除操作")
	public ResultBean<LauncherUI> delete(Long id) {
		ResultBean<LauncherUI> json = new ResultBean<>();
		try {
			launcherUIService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-UI管理-删除操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-UI管理-删除操作-"+e.getMessage(),e);
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
	@LogAnnotation(operationInfo = "运营管理-UI管理-批量删除操作")
	public ResultBean<LauncherUI> deleteBatch(String ids) {
		ResultBean<LauncherUI> json = new ResultBean<LauncherUI>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			launcherUIService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("运营管理-UI管理-批量删除操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统错误，请联系管理员！");
			logger.error("运营管理-UI管理-批量删除操作-"+e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("upline_or_downline")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-UI管理-上下线操作")
	public ResultBean<LauncherUI> uplineOrDownline(Long id, Long groupId, Integer status) {
		ResultBean<LauncherUI> json = new ResultBean<>();
		try {
			launcherUIService.uplineOrDownline(id, groupId, status);
			json.setMessage("操作成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-UI管理-上下线操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-UI管理-上下线操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
}
