package com.pbtd.manager.inject.tv.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pbtd.manager.inject.tv.service.face.IInjectTvPriorityService;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;

/**
 * @Description: 频道优先级管理
 * @author shenjr
 * @date 2017年11月8日 下午4:19:44
 * @version V1.0
 */

@Controller
@RequestMapping("/inject/tv/priority")
public class InjectTvPriorityController {

	@Autowired
	private IInjectTvPriorityService injectTvPriorityService;
	
	/*@Autowired
	private VodChannelService vodChannelService;*/


	@RequestMapping(value = { "/index", "" })
	public String index(HttpServletRequest request) {
		Map<String,Object> queryParams=new HashMap<String,Object>();
		List<Map<String,Object>> listChannel=this.injectTvPriorityService.findChannels();
		request.setAttribute("listChannel", listChannel);
		return "/inject/tv/priority/index";
	}

	
	

	@ResponseBody
	@RequestMapping("/page")
	public PageResult page(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int limit,
			HttpServletRequest request) {
		List<Map<String,Object>> list =null;
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.injectTvPriorityService.count(queryParams);
		LoginInfo current = LoginInfoContext.getCurrent();
		String username = current.getUsername();
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo = new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		list = this.injectTvPriorityService.find(queryParams);
		
		return new PageResult(total, list);
	}
	
	
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model) {
    	Map<String, Object> params=new HashMap<>();
    	if(id>0){//编辑
        	model.addAttribute("channelList", injectTvPriorityService.findAllChannels());
    	}else{    	//新建0
    		model.addAttribute("channelList", injectTvPriorityService.findPartChannels());
    	}
    	return "/inject/tv/priority/edit";
    }
    
    
    
    
    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    public Map<String,Object> load(@PathVariable("id") int id) {
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("id",id);
    	return this.injectTvPriorityService.findById(map);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(HttpServletRequest request) {
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("channelCode",request.getParameter("channelCode"));
    	map.put("priority",request.getParameter("priority"));
    	map.put("updatetime", new Date());
    	LoginInfo loginInfo=LoginInfoContext.getCurrent();
		String username = loginInfo.getUsername();
    	map.put("modifier",username);
    	this.injectTvPriorityService.savePriority(map);
      return 1;
    }
    
    /**
     * 根据id修改记录
     * @author lzl
     * @version 创建时间 ：2017年11月14日 下午2:51:06
     */
    
    @ResponseBody
    @RequestMapping(value = "/edit/{id}" ,method = RequestMethod.POST)
    public int edit(HttpServletRequest request){
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	queryParams.put("updatetime", new Date());
    	LoginInfo loginInfo=LoginInfoContext.getCurrent();
    	String userName=loginInfo.getUsername();
    	queryParams.put("modifier",userName);
    	this.injectTvPriorityService.updatePriority(queryParams);
    	return 1;
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
    	if(ids!=null){
    		int len=ids.size();
    		Map<String,Object> map=new HashMap<String,Object>();
    		try{
	    		for(int i=0;i<len;i++){
	    			map.put("id", ids.get(i));
	    			this.injectTvPriorityService.deletePriority(map);
	    		}
    		}catch(Exception e){
    			return -1;
    		}
    	}
    	return 1;
    }
    
    
    
    
    
    
    
}
