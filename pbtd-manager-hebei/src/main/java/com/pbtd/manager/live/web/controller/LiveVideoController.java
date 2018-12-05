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

import com.pbtd.manager.live.domain.LiveBussChnCodePackage;
import com.pbtd.manager.live.domain.LiveVideo;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveVideoService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;

/**
 * 直播版本管理
 * @author YFENG
 *
 */
@RequestMapping("/live/Video")
@Controller
public class LiveVideoController {
	@Autowired
	private ILiveVideoService liveVideoService;

	
	/**
	 * /live/Video/list
	 * @param qo
	 * @return
	 * 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult Live() {
		PageResult pageResult = null;
		try {
			pageResult = liveVideoService.querylistallLiveVideo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageResult;
	}
	
    @RequestMapping("/show/{id}")
    public String edit(@PathVariable("id") String id,ModelMap model) {
    	model.addAttribute("id",id);
    	model.addAttribute("packagecode",id);
        return "/live/videoManage/show";
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
    @RequestMapping("/pages/{packagecode}")
    public PageResult pages(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,@PathVariable("packagecode") String packagecode, @RequestParam(value="chncode")String chncode) {
    	List<LiveVideo> data = new ArrayList<LiveVideo>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("packagecode", packagecode);
            int total = liveVideoService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveVideoService.find(queryParams);
    			return new PageResult(total, data);
    }
    
    
    
    
    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/live/VideoManage/index";
    }
    
    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/VideoManage/edit";
    }
    
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "直播-节目包剧集管理 -删除操作")
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
        return this.liveVideoService.deletes(queryParams);
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "直播-节目包剧集管理 -添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(LiveVideo liveVideo, HttpServletRequest request) {
      this.liveVideoService.insert(liveVideo);
      return 1;
    }
	
	  /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/Video/page?page=1&rows=10
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, @RequestParam(value="Videoname")String Videoname) {
    	List<LiveVideo> data = new ArrayList<LiveVideo>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    	if(Videoname != null && ! Videoname.equals("")){
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("Videoname", "%"+Videoname+"%");
            int total = this.liveVideoService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveVideoService.find(queryParams);
    			return new PageResult(total, data);
    	}else{
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		
            int total = this.liveVideoService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveVideoService.find(queryParams);
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
    public LiveVideo load(@PathVariable("id") int id) {
    	LiveVideo liveVideo = null;
        if (id > 0) {
        	liveVideo = this.liveVideoService.load(id);
        }
        else {
        	liveVideo = new LiveVideo(
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
            );
        }
        return liveVideo;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodChannel VodChannel实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-节目包剧集管理 -更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, LiveVideo liveVideo) throws ServletException {
        return this.liveVideoService.update(liveVideo);
    }

}