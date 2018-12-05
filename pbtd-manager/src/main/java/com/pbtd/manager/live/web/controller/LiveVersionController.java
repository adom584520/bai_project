package com.pbtd.manager.live.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.live.domain.LiveVersion;
import com.pbtd.manager.live.domain.LiveVideo;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveVersionService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;

/**
 * 直播版本管理
 * @author YFENG
 *
 */

@RequestMapping("/live/Version")
@Controller
public class LiveVersionController {
	@Autowired
	private ILiveVersionService liveVersionService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	
	/**
	 * /live/Version/list
	 * @param qo
	 * @return
	 * 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult Live() {
		PageResult pageResult = null;
		try {
			pageResult = liveVersionService.querylistallLiveVersion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageResult;
	}
	
    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/live/versionManage/index";
    }
    
    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/versionManage/edit";
    }
    
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "直播-直播版本管理 -删除操作")
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
        return this.liveVersionService.deletes(queryParams);
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "直播-直播版本管理 -添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(LiveVersion liveVersion, HttpServletRequest request) {
      this.liveVersionService.insert(liveVersion);
      return 1;
    }
	
	  /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/Version/page?page=1&rows=10
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, @RequestParam(value="versionname")String versionname) {
    	List<LiveVersion> data = new ArrayList<LiveVersion>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    	if(versionname != null && ! versionname.equals("")){
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("versionname", "%"+versionname+"%");
            int total = this.liveVersionService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveVersionService.find(queryParams);
    			return new PageResult(total, data);
    	}else{
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		
            int total = this.liveVersionService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveVersionService.find(queryParams);
    			return new PageResult(total, data);
    	}
    }
    
    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    public LiveVersion load(@PathVariable("id") int id) {
    	LiveVersion liveVersion = null;
        if (id > 0) {
        	liveVersion = this.liveVersionService.load(id);
        }
        else {
        	liveVersion = new LiveVersion(
        			0
        			,null
        			,null
        			,null
        			,null
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
        return liveVersion;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodChannel VodChannel实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-直播版本管理 -更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, LiveVersion liveVersion) throws ServletException {
        return this.liveVersionService.update(liveVersion);
    }
    
}