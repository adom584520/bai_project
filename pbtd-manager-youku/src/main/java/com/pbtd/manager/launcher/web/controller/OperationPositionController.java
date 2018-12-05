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
import com.pbtd.manager.launcher.domain.OperationPosition;
import com.pbtd.manager.launcher.page.OperationPositionQueryObject;
import com.pbtd.manager.launcher.service.OperationPositionService;
import com.pbtd.manager.system.util.PermissionAnnotation;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("/operation_position")
public class OperationPositionController {
	private static final Logger logger = LoggerFactory.getLogger(OperationPositionController.class);
	@Autowired
	private OperationPositionService operationPositionService;

	@RequestMapping("page")
	@PermissionAnnotation(value = "/operation_position/page")
	public String macPage() {
		return "/launcher/op/op";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryListMac(OperationPositionQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = operationPositionService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-运营位管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位管理-新增操作")
	public ResultBean<OperationPosition> insert(OperationPosition operationPosition) {
		ResultBean<OperationPosition> json = new ResultBean<>();
		try {
			operationPositionService.insert(operationPosition);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位管理-新增操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位管理-编辑操作")
	public ResultBean<OperationPosition> update(OperationPosition operationPosition) {
		ResultBean<OperationPosition> json = new ResultBean<>();
		try {
			operationPositionService.update(operationPosition);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位管理-编辑操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位管理-删除操作")
	public ResultBean<OperationPosition> delete(Long id) {
		ResultBean<OperationPosition> json = new ResultBean<>();
		try {
			operationPositionService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位管理-删除操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位管理-删除操作-" + e.getMessage(), e);
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
	@LogAnnotation(operationInfo = "运营管理-运营位管理-批量删除操作")
	public ResultBean<OperationPosition> deleteBatch(String ids) {
		ResultBean<OperationPosition> json = new ResultBean<OperationPosition>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			operationPositionService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("运营管理-运营位管理-批量删除操作-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统错误，请联系管理员！");
			logger.error("运营管理-运营位管理-批量删除操作-" + e.getMessage(), e);
		}
		return json;
	}

	@RequestMapping("upline_or_downline")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位管理-上下线操作")
	public ResultBean<OperationPosition> uplineOrDownLine(OperationPosition op) {
		ResultBean<OperationPosition> json = new ResultBean<>();
		try {
			operationPositionService.uplineOrDownLine(op);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位管理-上下线操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位管理-上下线操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("query_by_id")
	@ResponseBody
	public ResultBean<OperationPosition> queryById(Long id) {
		ResultBean<OperationPosition> json = new ResultBean<>();
		OperationPosition op = null;
		try {
			op = operationPositionService.queryById(id);
			json.setMessage("查询成功！");
			json.setData(op);
		} catch (Exception e) {
			logger.error("运营管理-运营位管理-查询操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("copy")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-运营位管理-拷贝操作")
	public ResultBean<OperationPosition> copy(OperationPosition operationPosition) {
		ResultBean<OperationPosition> json = new ResultBean<>();
		try {
			operationPositionService.copy(operationPosition);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-运营位管理-拷贝操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-运营位管理-拷贝操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
}
