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
import com.pbtd.manager.launcher.domain.OpTemplate;
import com.pbtd.manager.launcher.page.OpTemplateQueryObject;
import com.pbtd.manager.launcher.service.OpTemplateService;
import com.pbtd.manager.system.util.PermissionAnnotation;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("/op_template")
public class OpTemplateController {
	private static final Logger logger = LoggerFactory.getLogger(OpTemplateController.class);
	@Autowired
	private OpTemplateService opTemplateService;

	@RequestMapping("page")
	@PermissionAnnotation(value = "/op_template/page")
	public String macPage() {
		return "/launcher/opTemplate/opTemplate";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryListMac(OpTemplateQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = opTemplateService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位模板管理-新增操作")
	public ResultBean<OpTemplate> insert(OpTemplate temp) {
		ResultBean<OpTemplate> json = new ResultBean<>();
		try {
			opTemplateService.insert(temp);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位模板管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-新增操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位模板管理-编辑操作")
	public ResultBean<OpTemplate> update(OpTemplate temp) {
		ResultBean<OpTemplate> json = new ResultBean<>();
		try {
			opTemplateService.update(temp);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位模板管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-编辑操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位模板管理-删除操作")
	public ResultBean<OpTemplate> delete(Long id) {
		ResultBean<OpTemplate> json = new ResultBean<>();
		try {
			opTemplateService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位模板管理-删除操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-删除操作-" + e.getMessage(), e);
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
	@LogAnnotation(operationInfo = "运营管理-运营位模板管理-批量删除操作")
	public ResultBean<OpTemplate> deleteBatch(String ids) {
		ResultBean<OpTemplate> json = new ResultBean<OpTemplate>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			opTemplateService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("运营管理-运营位模板管理-批量删除操作-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统错误，请联系管理员！");
			logger.error("运营管理-运营位模板管理-批量删除操作-" + e.getMessage(), e);
		}
		return json;
	}

	@RequestMapping("upline_or_downline")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位模板管理-上下线操作")
	public ResultBean<OpTemplate> uplineOrDownLine(Long id, Integer status) {
		ResultBean<OpTemplate> json = new ResultBean<>();
		try {
			opTemplateService.uplineOrDownLine(id, status);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位模板管理-上下线操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-上下线操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("list_by_navId")
	@ResponseBody
	public List<OpTemplate> queryListByNavId(Long navId) {
		List<OpTemplate> data = null;
		try {
			data = opTemplateService.queryListByNavId(navId);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-查询失败", e);
		}
		return data;
	}
	@RequestMapping("query_by_id")
	@ResponseBody
	public ResultBean<OpTemplate> queryById(Long id) {
		ResultBean<OpTemplate> json = new ResultBean<>();
		OpTemplate ot = null;
		try {
			ot = opTemplateService.queryById(id);
			json.setData(ot);
			json.setMessage("查询成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位模板管理-查询单条记录操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-查询单条记录操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("copy")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位模板管理-拷贝操作")
	public ResultBean<OpTemplate> copy(OpTemplate temp) {
		ResultBean<OpTemplate> json = new ResultBean<>();
		try {
			opTemplateService.copy(temp);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位模板管理-拷贝操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位模板管理-拷贝操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
}
