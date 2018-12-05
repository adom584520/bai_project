package com.pbtd.manager.system.web.controller;


import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.system.domain.SystemLogger;
import com.pbtd.manager.system.page.SystemLoggerQueryObject;
import com.pbtd.manager.system.service.SystemLoggerService;
import com.pbtd.manager.system.util.PermissionAnnotation;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

/**
 * 日志Controller
 * 
 * @author JOJO
 *
 */
@Controller()
@RequestMapping("/sys_logger")
public class SystemLoggerController {
	private static final Logger logger = LoggerFactory.getLogger(SystemLoggerController.class);
	@Autowired
	private SystemLoggerService systemLoggerService;

	/**
	 * 日志页面跳转
	 * 
	 * @return
	 */
	@PermissionAnnotation(value = "/sys_logger/page")
	@RequestMapping("/page")
	public String page() {
		return "/system/logger";
	}

	/**
	 * 查询日志列表
	 * 
	 * @param qo
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(SystemLoggerQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = systemLoggerService.queryList(qo);
		} catch (Exception e) {
			logger.error("系统管理-日志管理-日志查询失败！", e);
		}
		return queryList;
	}

	/**
	 * 删除日志
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-日志管理-删除日志")
	public ResultBean<SystemLogger> delete(Long id) {
		ResultBean<SystemLogger> json = new ResultBean<>();
		try {
			systemLoggerService.delete(id);
			json.setMessage("删除成功！");
		} catch (RuntimeException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-日志管理-删除日志-" + e.getMessage(), e);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-日志管理-删除日志", e);
		}
		return json;
	}

	/**
	 * 批量删除日志
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping("/delete_batch")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-日志管理-批量删除日志")
	public ResultBean<SystemLogger> deleteBatch(String data) {
		ResultBean<SystemLogger> json = new ResultBean<>();
		try {
			List<Long> parseArray = JSON.parseArray(data, Long.class);
			systemLoggerService.deleteBatch(parseArray);
			json.setMessage("批量删除成功！");
		} catch (RuntimeException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-日志管理-批量删除日志-" + e.getMessage(), e);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-日志管理-批量删除日志", e);
		}
		return json;
	}
	/**
	 * 根据条件删除日志
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping("/condition_delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-日志管理-条件删除日志")
	public ResultBean<SystemLogger> deleteCondition(SystemLoggerQueryObject qo) {
		ResultBean<SystemLogger> json = new ResultBean<>();
		try {
			systemLoggerService.deleteCondition(qo);
			json.setMessage("删除成功！");
		} catch (RuntimeException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-日志管理-条件删除日志-" + e.getMessage(), e);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-日志管理-条件删除日志", e);
		}
		return json;
	}
}