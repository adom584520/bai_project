package com.pbtd.manager.vod.phone.slideshow.web.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;
import com.pbtd.manager.util.SequenceUtil;
import com.pbtd.manager.vod.phone.slideshow.domain.Slideshow;
import com.pbtd.manager.vod.phone.slideshow.query.SlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.service.SlideshowService;

@Controller
@RequestMapping("/vod/phone/slideshow")
public class SlideshowController {
	private static final Logger logger = LoggerFactory.getLogger(SlideshowController.class);
	@Autowired
	private SlideshowService slideshowService;

	@Autowired
	private SequenceUtil  sequenceUtil;
	
	@RequestMapping("index")
	public String index() {
		return "/vod/slideshow/index";
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id) {
		return "/vod/slideshow/edit";
	}

	@RequestMapping("page")
	@ResponseBody
	public PageResult queryList(SlideshowQueryObject qo) {
		PageResult queryList = new PageResult(0L, Collections.EMPTY_LIST);
		try {
			queryList = slideshowService.queryList(qo);
		} catch (Exception e) {
			logger.error("手机推荐管理-轮播图管理-查询失败", e);
		}
		return queryList;
	}

	@RequestMapping("/queryById/{id}")
	@ResponseBody
	public Slideshow queryById(@PathVariable("id") Integer id) {
		Slideshow slideshow = null;
		if (id < 1) {
			slideshow = new Slideshow();
			slideshow.setId(-1);
			return slideshow;
		}
		try {
			slideshow = slideshowService.queryById(id);
		} catch (Exception e) {
			slideshow = new Slideshow();
			slideshow.setId(-1);
			logger.error("手机推荐管理-轮播图管理-查询单条记录操作-" + e.getMessage(), e);
		}
		return slideshow;
	}

	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机推荐管理-轮播图管理-新增操作")
	public ResultBean<Slideshow> insert(Slideshow slideshow) {
		ResultBean<Slideshow> json = new ResultBean<>();
		try {
			Map<String, Object> queryParams = new HashMap();
			Map<String, Object> maxmap = slideshowService.findmaxVSminsequence(queryParams);// 查询更改排序的最大最小原始值
			int curmax = 0;
			if (maxmap != null) {
				curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
			}
			slideshow.setSequence(curmax += 1);
			slideshowService.insert(slideshow);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机推荐管理-轮播图管理-新增操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机推荐管理-轮播图管理-新增操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("/update/{id}")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机推荐管理-轮播图管理-编辑操作")
	public ResultBean<Slideshow> update(@PathVariable("id") int id, Slideshow slideshow) {
		ResultBean<Slideshow> json = new ResultBean<>();
		try {
			int newmax = slideshow.getSequence();
			int newmin = 0;
			int max = newmax;// 排序最大值
			int min = newmin;// 排序最小值
			Map<String, Object> mapsequence = new HashMap<>();
			mapsequence.put("id", Integer.toString(id));
			Map<String, Object> maxmap = slideshowService.findmaxVSminsequence(mapsequence);// 查询更改排序的最大最小原始值
			int curmax = 0;
			int curmin = 0;
			if (maxmap != null) {
				curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
				curmin = Integer.parseInt(maxmap.get("min") == null ? "0" : maxmap.get("min").toString());// 原始数据排序最大值
			}
			if (newmax < curmax) {//// 如果原始数据最大值比更改排序的最大值大 复制为最大值
				max = curmax;
			}
			if (newmin > curmin) {// 如果原始数据最小值比更改排序的最小值小 复制为最小值
				min = curmin;
			}
			mapsequence.put("max", max);
			mapsequence.put("min", min);
			List<Map<String, Object>> list = slideshowService.findsequence(mapsequence);
			Map<String, Object> map = new HashMap<>();
			String[] newsequences = sequenceUtil.Sortball(Integer.toString(newmax));
			for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
				map = list.get(i);
				min += 1;
				int j = newsequences.length;
				for (int jj = 0; jj < j; jj++) {// 修改排序=原始排序+jj
												// jj为循环次数（修改改值与原始值的间隔值）
					if (min == Integer.parseInt(newsequences[jj])) {
						min += 1;
					}
				}
				// 修改排序=原始排序+jj jj为循环次数（修改改值与原始值的间隔值）
				String code = map.get("id").toString();
				Slideshow slideshow1 = new Slideshow();
				slideshow1.setId(Integer.parseInt(code));
				slideshow1.setSequence(min);
				slideshow1.setStatus(-1);
				slideshowService.update(slideshow1);
			}
			// 更改编辑的排序信息
			slideshow.setId(id);
			slideshowService.update(slideshow);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机推荐管理-轮播图管理-编辑操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机推荐管理-轮播图管理-编辑操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
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
	@LogAnnotation(operationInfo = "手机推荐管理-轮播图管理-批量删除操作")
	public int deletes(@RequestBody List<Integer> ids) {
		int row = 0;
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("id_", ids);
			// 查询更改排序的原始值
			List<Map<String, Object>> oldsequencelist = slideshowService.findsequencesum(queryParams);
			int min = Integer.parseInt(oldsequencelist.get(0).get("sequence") == null ? "1"
					: oldsequencelist.get(0).get("sequence").toString());
			queryParams.put("min", min);
			List<Map<String, Object>> list = slideshowService.findsequence(queryParams);
			Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
				map = list.get(i);
				int curmin = Integer.parseInt(map.get("sequence") == null ? "1" : map.get("sequence").toString());
				int j = oldsequencelist.size();
				int c = 1;
				for (int jj = 0; jj < j; jj++) {
					if (curmin > Integer.parseInt(oldsequencelist.get(jj).get("sequence") == null ? "1"
							: oldsequencelist.get(jj).get("sequence").toString())) {
						c = jj + 1;
					}
				}
				curmin -= c;
				Slideshow slideshow1 = new Slideshow();
				slideshow1.setId(Integer.parseInt(map.get("id").toString()));
				slideshow1.setSequence(curmin);
				slideshow1.setStatus(-1);
				slideshowService.update(slideshow1);
			}
			// 删除
			row = slideshowService.deleteBatch(ids);
		} catch (JsonMessageException e) {
			logger.warn("手机推荐管理-轮播图管理-批量删除操作-" + e.getMessage());
		} catch (Exception e) {
			logger.error("手机推荐管理-轮播图管理-批量删除操作-" + e.getMessage(), e);
		}
		return row;
	}

	@RequestMapping("update_status")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机推荐管理-轮播图管理-上下线操作")
	public ResultBean<Slideshow> uplineOrDownLine(String ids, Integer status) {
		ResultBean<Slideshow> json = new ResultBean<>();
		try {
			List<Integer> list = JSON.parseArray(ids, Integer.class);
			slideshowService.uplineOrDownLine(list, status);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机推荐管理-轮播图管理-上下线操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机推荐管理-轮播图管理-上下线操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping("update_iamge_url")
	@ResponseBody
	@LogAnnotation(operationInfo = "手机推荐管理-轮播图管理-修改图片操作")
	public ResultBean<Slideshow> updateImageUrl(HttpServletRequest request) {
		String imgUrl = request.getParameter("imgUrl");
		int id = Integer.parseInt(request.getParameter("id"));
		ResultBean<Slideshow> json = new ResultBean<>();
		try {
			slideshowService.updateImageUrl(id, imgUrl);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			logger.warn("手机推荐管理-轮播图管理-修改图片操作-" + e.getMessage());
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		} catch (Exception e) {
			logger.error("手机推荐管理-轮播图管理-修改图片操作-" + e.getMessage(), e);
			json.setMessage(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}
}
