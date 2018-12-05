package com.pbtd.manager.launcher.web.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.LauncherGroup;
import com.pbtd.manager.launcher.domain.Mac;
import com.pbtd.manager.launcher.page.MacQueryObject;
import com.pbtd.manager.launcher.service.LauncherGroupService;
import com.pbtd.manager.launcher.service.MacService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("mac")
public class MacController {
	private static final  Logger logger = LoggerFactory.getLogger(MacController.class);
	@Autowired
	private MacService macService;
	@Autowired
	private LauncherGroupService launcherGroupService;

	@RequestMapping("page")
	public String page(Long groupId, Model model) {
		LauncherGroup launcherGroup = null;
		try {
			// 如果是新增就不需要查询group
			if (groupId != null && groupId > 0) {
				launcherGroup = launcherGroupService.queryById(groupId);
			}else{
				launcherGroup = new LauncherGroup();
			}
			model.addAttribute("launcherGroup", launcherGroup);
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-查询失败",e);
		}
		return "/launcher/mac";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(MacQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = macService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-查询失败",e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-MAC管理-新增操作")
	public ResultBean<Mac> insert(Mac mac) {
		ResultBean<Mac> json = new ResultBean<>();
		try {
			macService.insert(mac);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-MAC管理-新增操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-新增操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-MAC管理-编辑操作")
	public ResultBean<Mac> update(Mac mac) {
		ResultBean<Mac> json = new ResultBean<>();
		try {
			macService.update(mac);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-MAC管理-编辑操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-编辑操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-MAC管理-删除操作")
	public ResultBean<Mac> delete(Long id) {
		ResultBean<Mac> json = new ResultBean<>();
		try {
			macService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-MAC管理-删除操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-删除操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("delete_batch")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-MAC管理-批量删除操作")
	public ResultBean<Mac> deleteBatch(String ids) {
		ResultBean<Mac> json = new ResultBean<>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			macService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-MAC管理-批量删除操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-批量删除操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@PostMapping("batch_import")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-MAC管理-批量导入操作")
	public ResultBean<Mac> batchImportUserKnowledge(Mac mac, MultipartFile file) {
		ResultBean<Mac> json = new ResultBean<>();
		try {
			String macBatchImport = macService.macBatchImport(mac, file);
			if (macBatchImport != null) {
				json.setMessage(macBatchImport + "已存在，未导入！");
			} else {
				json.setMessage("导入成功！");
			}
		} catch (JsonMessageException e) {
			logger.warn("运营管理-MAC管理-批量导入操作-"+e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-批量导入操作-"+e.getMessage(),e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("batch_export")
	@ResponseBody
	public ResultBean<Mac> export(Long groupId, HttpServletResponse response) {
		ResultBean<Mac> json = new ResultBean<>();
		response.setContentType("application/binary;charset=UTF-8");
		try {
			ServletOutputStream out = response.getOutputStream();
			String fileName = new String(("MAC" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(),
					"UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
			macService.export(groupId, out);
			json.setMessage("导出成功！");
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-批量导出操作-"+e.getMessage(),e);
			json.setMessage("导出失败！");
			json.setSuccess(false);
		}
		return json;
	}
}
