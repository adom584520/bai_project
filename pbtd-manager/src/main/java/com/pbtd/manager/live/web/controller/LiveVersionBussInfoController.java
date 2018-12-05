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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.live.domain.LiveVersionBussInfo;
import com.pbtd.manager.live.domain.LiveBussInfo;
import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveVersionBussInfoService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;


/**
 * 直播 商家版本控制
 * 
 * @author JOJO	
 *
 */

@RequestMapping("/live/VersionBussInfo")
@Controller
public class LiveVersionBussInfoController {
	@Autowired
	private ILiveVersionBussInfoService liveVersionBussInfoService;

    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/live/versionBussInfoManage/index";
    }
    
    /**
     * GET 编辑/新建
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/versionBussInfoManage/edit";
    }
    
	/**
	 * 频道列表查询
	 * /live/VersionBussInfo/queryVersionBussInfolist
	 * @param qo
	 * @return
	 */
	@RequestMapping("/queryVersionBussInfolist")
	@ResponseBody
	public PageResult LiveVersionBussInfoInfo() {
		PageResult pageResult = null;
		try {
			pageResult = liveVersionBussInfoService.querylistallLiveVersionBussInfo();
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
	@LogAnnotation(operationInfo = "直播-商家版本控制管理 -删除操作")
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
        return this.liveVersionBussInfoService.deletes(queryParams);
    }
    
    /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码LiveVersionBussInfo liveVersionBussInfo
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/VersionBussInfo/page/1?page=1&rows=10
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, @RequestParam(value="name")String name) {
    	List<LiveVersionBussInfo> data = new ArrayList<LiveVersionBussInfo>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    	if(name != null && ! name.equals("")){
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		//queryParams.put("id", id);
    		queryParams.put("name",  "%"+name+"%");
            int total = this.liveVersionBussInfoService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveVersionBussInfoService.find(queryParams);
    			return new PageResult(total, data);
    	}else{
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		//queryParams.put("bussid", id);
            int total = this.liveVersionBussInfoService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveVersionBussInfoService.find(queryParams);
    			return new PageResult(total, data);
    	}
    }
    
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "直播-商家版本控制管理 -添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(LiveVersionBussInfo liveBussInfo, HttpServletRequest request) {
      this.liveVersionBussInfoService.insert(liveBussInfo);
      return 1;
    }

    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    public LiveVersionBussInfo load(@PathVariable("id") int id) {
    	LiveVersionBussInfo liveVersionBussInfo = null;
        if (id > 0) {
        	liveVersionBussInfo = this.liveVersionBussInfoService.load(id);
        }
        else {
        	liveVersionBussInfo = new LiveVersionBussInfo(
        			0
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
            );
        }
        return liveVersionBussInfo;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodVersionBussInfo VodVersionBussInfo实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-商家版本控制管理 -更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id,LiveVersionBussInfo liveVersionBussInfo) throws ServletException {
        return this.liveVersionBussInfoService.update(liveVersionBussInfo);
    }

}