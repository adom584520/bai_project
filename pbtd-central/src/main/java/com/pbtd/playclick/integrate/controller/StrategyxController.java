package com.pbtd.playclick.integrate.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.base.common.mvc.MutilCustomDateEditor;
import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.integrate.domain.Strategy;
import com.pbtd.playclick.integrate.service.face.IStrategyService;
import com.pbtd.playclick.integrate.service.face.IVodactorsService;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;
import com.pbtd.playclick.pinyin.service.PinYinService;
@Controller("integrate.StrategyxController")
@RequestMapping("/integrate/strategy")
public class StrategyxController {

	@Autowired
	private IStrategyService strategyService;

	@Autowired
	private DictionaryController dictionary;

	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new MutilCustomDateEditor("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" ));
	}
	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/index", ""})
	public String index() {
		return "/vod/strategyx/index";
	}

	/**
	 * GET 创建
	 *
	 * @return 视图路径
	 */
	@RequestMapping("/create")
	public String create() {
		return "/vod/strategyx/create";
	}

	/**
	 * GET 编辑
	 *
	 * @param id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, HttpServletRequest request,ModelMap model) {
		List<Map<String, Object>> ablumsmap=dictionary.findDictionaryname("chooseAlbums", request);
		model.addAttribute("ablumsmap", ablumsmap);
		List<Map<String, Object>> cpsource=dictionary.findDictionaryname("choosecpsource", request);
		model.addAttribute("cpsource", cpsource);
		return "/vod/strategyx/edit";
	}

	/**
	 * 模糊统计符合查询条件的记录总数
	 *
	 * @param request 查询参数
	 * @return 记录总数
	 */
	@ResponseBody
	@RequestMapping("/count")
	public int count(HttpServletRequest request) {
		return this.strategyService.count(RequestUtil.asMap(request, false));
	}

	/**
	 * 模糊获取符合查询条件的分页记录
	 * 
	 * @param page 页码
	 * @param limit 页长
	 * @param request 查询参数
	 * @return 记录列表
	 */
	@ResponseBody
	@RequestMapping("/page")
	public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
		List<Strategy> data = new ArrayList<Strategy>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.strategyService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.strategyService.find(queryParams);
		return new PageResult(total, data);

	}

	/**
	 * 根据标识获取记录
	 *
	 * @param id 标识
	 * @return 记录
	 */
	@ResponseBody
	@RequestMapping("/load/{id}")
	public Strategy load(@PathVariable("id") int id) {
		Strategy vodstrategy = null;
		if (id > 0) {
			vodstrategy = this.strategyService.load(id);
		} 
		else {
			vodstrategy = new  Strategy(-1,
					null, 0, null,
					null, null, null, null,
					null, null, null, null,
					0);
		}
		return vodstrategy;
	}

	/**
	 * 精确判断是否存在记录
	 * 
	 * @param id 标识
	 * @param request 查询参数
	 * @return 记录条数
	 */
	@RequestMapping("/exist/{id}")
	@ResponseBody
	public int exist(@PathVariable("id")int id, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		List<Strategy> strategy = this.strategyService.find(queryParams);
		if (strategy.size() == 1) {
			return strategy.get(0).getId() == id ? 0 : 1;
		} else {
			return strategy.size();
		}
	}
	/**
	 * 插入记录
	 *
	 * @param vodChannel VodChannel实例
	 * @return 被插入的记录标识
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public int create(Strategy strategy, HttpServletRequest request) {
		this.strategyService.insert(strategy);
		return 1;
	}

	/**
	 * POST 编辑
	 *
	 * @param id 标识
	 * @param vodChannel VodChannel实例
	 * @return int 被修改的记录条数
	 * @throws ServletException
	 */
	@ResponseBody
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public int edit(@PathVariable("id") int id, Strategy strategy) throws ServletException {
		return this.strategyService.update(strategy);
	}

	/**
	 * POST 删除多条
	 *
	 * @param ids 标识列表
	 * @return 被删除的记录条数
	 */
	@ResponseBody
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	public int deletes(@RequestBody List<Integer> ids) {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if (ids == null || ids.size() == 0)
		{
			queryParams.put("id", ids);
		}else{
			queryParams.put("id_", ids);
		}
		return this.strategyService.deletes(queryParams);
	}
}
