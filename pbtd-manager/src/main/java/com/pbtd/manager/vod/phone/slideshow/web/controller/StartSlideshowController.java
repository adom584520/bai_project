package com.pbtd.manager.vod.phone.slideshow.web.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;
import com.pbtd.manager.vod.phone.slideshow.domain.Slideshow;
import com.pbtd.manager.vod.phone.slideshow.domain.StartSlideshow;
import com.pbtd.manager.vod.phone.slideshow.query.StartSlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.service.StartSlideshowService;

@Controller
@RequestMapping("/vod/phone/startslideshow")
public class StartSlideshowController {
	private static final Logger logger = LoggerFactory.getLogger(StartSlideshowController.class);
	@Autowired
	private StartSlideshowService startSlideshowService;

	@RequestMapping("index")
	public String index() {
		return "/vod/startshow/index";
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id) {
		return "/vod/startshow/edit";
	}

	@RequestMapping("page")
	@ResponseBody
	public PageResult queryList(StartSlideshowQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = startSlideshowService.queryList(qo);
		} catch (Exception e) {
			logger.error("手机管理-开机轮播图管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("/queryById/{id}")
	@ResponseBody
	public StartSlideshow queryById(@PathVariable("id") Integer id) {
		StartSlideshow startShow = null;
		if (id < 1) {
			startShow = new StartSlideshow();
			startShow.setId(-1);
			return startShow;
		}
		try {
			startShow = startSlideshowService.queryById(id);
		} catch (Exception e) {
			startShow = new StartSlideshow();
			startShow.setId(-1);
			logger.error("手机管理-开机轮播图管理-查询单条记录操作-" + e.getMessage(), e);
		}
		return startShow;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机管理-开机轮播图管理-新增操作")
	public ResultBean<StartSlideshow> insert(StartSlideshow startShow) {
		ResultBean<StartSlideshow> json = new ResultBean<>();
		try {
			startSlideshowService.insert(startShow);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机管理-开机轮播图管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机管理-开机轮播图管理-新增操作-" + e.getMessage(), e);
			json.setMessage("系统出错，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("/update/{id}")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机管理-开机轮播图管理-编辑操作")
	public ResultBean<StartSlideshow> update(@PathVariable("id") int id, StartSlideshow startShow) {
		ResultBean<StartSlideshow> json = new ResultBean<>();
		try {
			startSlideshowService.update(startShow);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机管理-开机轮播图管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机管理-开机轮播图管理-编辑操作-" + e.getMessage(), e);
			json.setMessage("系统出错，请联系管理员！");
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
	@ResponseBody
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	@LogAnnotation(operationInfo = "手机管理-开机轮播图管理-批量删除操作")
	public int deletes(@RequestBody List<Integer> ids) {
		int row = 0;
		try {
			row = startSlideshowService.deleteBatch(ids);
		} catch (JsonMessageException e) {
			logger.warn("手机管理-开机轮播图管理-批量删除操作-" + e.getMessage());
		} catch (Exception e) {
			logger.error("手机管理-开机轮播图管理-批量删除操作-" + e.getMessage(), e);
		}
		return row;
	}

	@RequestMapping("update_status")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机管理-开机轮播图管理-上下线操作")
	public ResultBean<Slideshow> uplineOrDownLine(Integer id, Integer status) {
		ResultBean<Slideshow> json = new ResultBean<>();
		try {
			startSlideshowService.uplineOrDownLine(id, status);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机管理-开机轮播图管理-上下线操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机管理-开机轮播图管理-上下线操作-" + e.getMessage(), e);
			json.setMessage("系统出错，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update_iamge_url")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机管理-开机轮播图管理-修改图片操作")
	public ResultBean<Slideshow> updateImageUrl(HttpServletRequest request) {
		String imgUrl = request.getParameter("imgUrl");
		int id = Integer.parseInt(request.getParameter("id"));
		ResultBean<Slideshow> json = new ResultBean<>();
		try {
			startSlideshowService.updateImageUrl(id, imgUrl);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机管理-开机轮播图管理-修改图片操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机管理-开机轮播图管理-修改图片操作-" + e.getMessage(), e);
			json.setMessage("系统出错，请联系管理员！");
			json.setSuccess(false);
		}
		return json;
	}
}
