package com.pbtd.manager.vod.buss.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.vod.buss.domain.Vodbussinfo;
import com.pbtd.manager.vod.buss.service.face.IVodbussinfoService;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;

/**
 * @author zr
 */
@Controller 
@RequestMapping("/vod/vodbussinfo")
public class VodbussinfoController {
	
    @Autowired
    private IVodbussinfoService vodbussinfoservice;
    @Autowired
    private IDictionaryService dictionaryService;
    
	 /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/vod/buss/vodbussinfo/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/buss/vodbussinfo/create";
    }

    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model) {
    	Map<String, Object> params=new HashMap<>();
    	params.put("id", id);
        return "/vod/buss/vodbussinfo/edit";
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
        return this.vodbussinfoservice.count(RequestUtil.asMap(request, false));
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
    	List<Vodbussinfo> data = new ArrayList<Vodbussinfo>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodbussinfoservice.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodbussinfoservice.find(queryParams);
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
    public Vodbussinfo load(@PathVariable("id") int id) {
    	Vodbussinfo vodbussinfo = null;
        if (id > 0) {
        	vodbussinfo = this.vodbussinfoservice.load(id);
        } 
        else {
        	int c=this.vodbussinfoservice.generatePosition(null);
        	vodbussinfo = new Vodbussinfo(-1, 0, null, null, null, null, null, null, null,
        			null, null, null, null);
        }
        return vodbussinfo;
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
    	List<Vodbussinfo> vodbussinfo = this.vodbussinfoservice.find(queryParams);
    	if (vodbussinfo.size() == 1) {
    		return vodbussinfo.get(0).getBussId() == id ? 0 : 1;
    	} else {
    		return vodbussinfo.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-分组管理-新增操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(Vodbussinfo vodbussinfo, HttpServletRequest request) {
      Map<String, Object> queryParams = RequestUtil.asMap(request, false);
      this.vodbussinfoservice.insert(vodbussinfo,queryParams);
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
    @LogAnnotation(operationInfo = "点播-分组管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, Vodbussinfo vodbussinfo, HttpServletRequest request) throws ServletException {
    	 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        return this.vodbussinfoservice.update(vodbussinfo,queryParams);
    }

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-分组管理-删除操作")
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public int deletes( HttpServletRequest request) {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	String[] id=queryParams.get("id").toString().split(",");
    	queryParams.put("seriescode_", id);
    	 if (id == null || id.length == 1)
         {
    		 queryParams.put("bussId", id[0]);
         }else{
        	 queryParams.put("bussId_", id);
         }
        return this.vodbussinfoservice.deletes(queryParams);
    }
}
