package com.pbtd.manager.user.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.user.domain.Upgrade;
import com.pbtd.manager.user.page.UpgradeQueryObject;
import com.pbtd.manager.user.service.UpgradeService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("upgrade")
public class UpgradeController {
	private static final Logger logger = LoggerFactory.getLogger(UpgradeController.class);
	@Autowired
	private UpgradeService upgradeService;

	@RequestMapping("page")
	public String page() {
		return "/launcher/upgrade/upgrade";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(UpgradeQueryObject qo) {
		PageResult queryList = new PageResult();
		try {
			queryList = upgradeService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-apk版本升级管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-apk版本升级管理-新增操作")
	public ResultBean<Upgrade> insert(Upgrade upgrade) {
		ResultBean<Upgrade> json = new ResultBean<>();
		try {
			upgradeService.insert(upgrade);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-apk版本升级管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-apk版本升级管理-新增操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-apk版本升级管理-编辑操作")
	public ResultBean<Upgrade> update(Upgrade upgrade) {
		ResultBean<Upgrade> json = new ResultBean<>();
		try {
			upgradeService.update(upgrade);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-apk版本升级管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-apk版本升级管理-编辑操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-apk版本升级管理-删除操作")
	public ResultBean<Upgrade> delete(Long id) {
		ResultBean<Upgrade> json = new ResultBean<>();
		try {
			upgradeService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-apk版本升级管理-删除操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-apk版本升级管理-删除操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
}
