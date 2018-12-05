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
import com.pbtd.manager.launcher.domain.Advertisement;
import com.pbtd.manager.launcher.page.AdvertisementQueryObject;
import com.pbtd.manager.launcher.service.AdvertisementService;
import com.pbtd.manager.system.util.PermissionAnnotation;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Controller
@RequestMapping("advertisement")
public class AdvertisementController {
	private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);
	@Autowired
	private AdvertisementService advertisementService;

	@RequestMapping("page")
	@PermissionAnnotation(value = "/advertisement/page")
	public String page() {
		return "/launcher/adv/adv";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResult queryList(AdvertisementQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = advertisementService.queryList(qo);
		} catch (Exception e) {
			logger.error("运营管理-广告管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-广告管理-新增操作")
	public ResultBean<Advertisement> insert(Advertisement adv) {
		ResultBean<Advertisement> json = new ResultBean<>();
		try {
			advertisementService.insert(adv);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-广告管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-广告管理-新增操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-广告管理-编辑操作")
	public ResultBean<Advertisement> update(Advertisement adv) {
		ResultBean<Advertisement> json = new ResultBean<>();
		try {
			advertisementService.update(adv);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-广告管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-广告管理-编辑操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-广告管理-删除操作")
	public ResultBean<Advertisement> delete(Long id) {
		ResultBean<Advertisement> json = new ResultBean<>();
		try {
			advertisementService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-广告管理-删除操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-广告管理-删除操作-" + e.getMessage(), e);
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
	@LogAnnotation(operationInfo = "运营管理-广告管理-批量删除操作")
	public ResultBean<Advertisement> deleteBatch(String ids) {
		ResultBean<Advertisement> json = new ResultBean<Advertisement>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			advertisementService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("运营管理-广告管理-批量删除操作-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统错误，请联系管理员！");
			logger.error("运营管理-广告管理-批量删除操作-" + e.getMessage(), e);
		}
		return json;
	}

	@RequestMapping("upline_or_downline")
	@ResponseBody
	@LogAnnotation(operationInfo = "运营管理-广告管理-上下线操作")
	public ResultBean<Advertisement> uplineOrDownLine(Long id, Integer status) {
		ResultBean<Advertisement> json = new ResultBean<>();
		try {
			advertisementService.uplineOrDownLine(id, status);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("运营管理-广告管理-上下线操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("运营管理-广告管理-上下线操作-" + e.getMessage(), e);
			json.setMessage("系统错误，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
}
