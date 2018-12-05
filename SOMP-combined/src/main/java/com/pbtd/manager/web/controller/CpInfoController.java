package com.pbtd.manager.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.domain.CpInfo;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.query.CpInfoQueryObject;
import com.pbtd.manager.service.CpInfoService;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("/cpInfo")
public class CpInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CpInfoController.class);
	@Autowired
	private CpInfoService cpInfoService;

	@RequestMapping("page")
	public String page() {
		return "/albuminfo/cpInfo";
	}
	
	@RequestMapping("list_all")
	@ResponseBody
	public List<CpInfo> all() {
		List<CpInfo> list = null;
		try {
			list = cpInfoService.queryAll();
			if(list==null){
				list = new ArrayList<>(1);
			}
		} catch (Exception e) {
			LOGGER.error("cp源管理查询所有数据失败！", e);
			list = new ArrayList<>(1);
		}
		return list;
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(CpInfoQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = cpInfoService.queryList(qo);
		} catch (Exception e) {
			LOGGER.error("cp源管理列表查询失败！", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	public ResultBean<Object> insert(CpInfo cpInfo) {
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			cpInfoService.insert(cpInfo);
			resultBean.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp源管理-添加操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp源管理-添加操作-", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}

	@RequestMapping("update")
	@ResponseBody
	public ResultBean<Object> update(CpInfo cpInfo) {
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			cpInfoService.update(cpInfo);
			resultBean.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp源管理-编辑操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp源管理-编辑操作-", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResultBean<Object> delete(Integer id) {
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			cpInfoService.delete(id);
			resultBean.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			LOGGER.warn("cp源管理-删除操作-" + e.getMessage());
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			LOGGER.error("cp源管理-删除操作-", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}
	
}
