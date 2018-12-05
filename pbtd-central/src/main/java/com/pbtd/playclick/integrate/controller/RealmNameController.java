
package com.pbtd.playclick.integrate.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.integrate.domain.RealmName;
import com.pbtd.playclick.integrate.service.face.IRealmNameService;


@Controller 
@RequestMapping("/vod/realmname")
public class RealmNameController {

	@Autowired
	private IRealmNameService realmnameService;

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/index", ""})
	public String index(Model model) {
		return "/vod/realmname/index";
	} 
	/**
	 * GET 编辑
	 *
	 * @param id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id,Model model) {
		return "/vod/realmname/edit";
	}

	/**
	 *
	 *
	 * 
	 * @return 总记录
	 */
	@ResponseBody
	@RequestMapping("/page")
	public List<Map<String,Object>> page(HttpServletRequest request) {
		List<Map<String,Object>> list=null;
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total=realmnameService.count(queryParams);
		if (total==0) {
			list=Collections.emptyList();
		} 
		else {
			list =realmnameService.find(queryParams);
		}
		return list;
	}


	/**
	 * 根据标识获取记录
	 *
	 * @param id 标识
	 * @return 记录
	 */
	@ResponseBody
	@RequestMapping("/load/{id}")
	public RealmName load(@PathVariable("id") int id) {
		RealmName realm = null;
		if (id > 0) {
			realm = this.realmnameService.load(id);
		} 
		else {
			realm = new  RealmName(1, null, 1, null, null);
		}
		return realm;
	}

	/**
	 * 插入记录
	 *
	 * @param vodChannel VodChannel实例
	 * @return 被插入的记录标识
	 */
	@ResponseBody
	@RequestMapping(value ="/create", method = RequestMethod.POST)
	public int create(HttpServletRequest request) {
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		this.realmnameService.insert(queryParams);
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
	public int edit(@PathVariable("id") int id,HttpServletRequest request) throws ServletException {
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		return this.realmnameService.update(queryParams);
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
		return this.realmnameService.delete(queryParams);
	}


}
