package com.pbtd.manager.live.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.live.domain.LiveBussChnCodePackage;
import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveBussChnCodePackageService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;


/**
 * 用户操作
 * 
 * @author JOJO
 *
 */
@RequestMapping("/live/BussChnCodePackage")
@Controller
public class LiveBussChnCodePackageController {
	@Autowired
	private ILiveBussChnCodePackageService liveBussChnCodePackageService;

    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/live/bussChnCodePackageManage/index";
    }
    
    @RequestMapping("/show/{id}")
    public String edit(@PathVariable("id") String id,ModelMap model) {
    	model.addAttribute("id",id);
    	model.addAttribute("bussId",id);
        return "/live/bussChnCodePackageManage/show";
    }

    @RequestMapping(value = "/create/{id}")
    public String create(@PathVariable("id") int id,ModelMap model) {
    	model.addAttribute("id",id);
    	model.addAttribute("bussId",id);
      return "/live/bussChnCodePackageManage/edit";
    }
    
    /**
     * GET 编辑
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/bussChnCodePackageManage/edit";
    }
	/**
	 * 频道列表查询
	 * /live/BussChnCodePackage/queryBussChnCodePackagelist
	 * @param qo
	 * @return
	 */
	@RequestMapping("/queryBussChnCodePackagelist")
	@ResponseBody
	public PageResult LiveBussChnCodePackageInfo() {
		PageResult pageResult = null;
		try {
			pageResult = liveBussChnCodePackageService.querylistallLiveBussChnCodePackage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageResult;
	}
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
	@LogAnnotation(operationInfo = "直播-商家频道分组管理-删除操作")
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public int deletes( HttpServletRequest request) {
    	Object channelidlist = request.getParameter("id");
    	Object bussid = request.getParameter("bussid");
    	Map<String, Object> queryParams = new HashMap<String, Object>();
    	String[] id=channelidlist.toString().split(",");
    	queryParams.put("id_", id);
    	queryParams.put("bussid", Integer.parseInt(bussid.toString()));
        return this.liveBussChnCodePackageService.deletes(queryParams);
    }
    /**
     * POST 添加
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
	@LogAnnotation(operationInfo = "直播-商家频道分组管理-添加操作")
    @ResponseBody
    @RequestMapping(value = "/creates", method = RequestMethod.POST)
    public int creates( HttpServletRequest request) {
    	Object channelidlist = request.getParameter("id");
    	String bussid = request.getParameter("bussid");
    	Map<String, Object> queryParams = new HashMap<String, Object>();
    	String[] id=channelidlist.toString().split(",");
    	queryParams.put("id_", id);
    	queryParams.put("bussid", bussid);
    	
    	return this.liveBussChnCodePackageService.creates(queryParams);
    }

	  /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码LiveBussChnCodePackage liveBussChnCodePackage
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/BussChnCodePackage/page/1?page=1&rows=10
     */
    @ResponseBody
    @RequestMapping("/page/{id}")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,@PathVariable("id") int id, @RequestParam(value="chncode")String chncode) {
    	List<LiveBussChnCodePackage> data = new ArrayList<LiveBussChnCodePackage>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    	if(chncode != null && ! chncode.equals("")){
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("bussid", id);
    		queryParams.put("chncode",  "%"+chncode+"%");
            int total = this.liveBussChnCodePackageService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveBussChnCodePackageService.find(queryParams);
    			return new PageResult(total, data);
    	}else{
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("bussid", id);
            int total = this.liveBussChnCodePackageService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveBussChnCodePackageService.find(queryParams);
    			return new PageResult(total, data);
    	}
    }
    /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码LiveBussChnCodePackage liveBussChnCodePackage
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/BussChnCodePackage/pages/1?page=1&rows=10?id=1
     */
    @ResponseBody
    @RequestMapping("/pages/{id}")
    public PageResult pages(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit ,@PathVariable("id") int id) {
    	List<LiveChannel> data = new ArrayList<LiveChannel>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("bussid", id);
    		int total = this.liveBussChnCodePackageService.counts(queryParams);
    		data =this.liveBussChnCodePackageService.finds(queryParams); 
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
    public LiveBussChnCodePackage load(@PathVariable("id") int id) {
    	LiveBussChnCodePackage liveBussChnCodePackage = null;
        if (id > 0) {
        	liveBussChnCodePackage = this.liveBussChnCodePackageService.load(id);
        }
        else {
        	liveBussChnCodePackage = new LiveBussChnCodePackage(
        			0
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
            );
        }
        return liveBussChnCodePackage;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodBussChnCodePackage VodBussChnCodePackage实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-商家频道分组管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id,LiveBussChnCodePackage liveBussChnCodePackage) throws ServletException {
        return this.liveBussChnCodePackageService.update(liveBussChnCodePackage);
    }

}