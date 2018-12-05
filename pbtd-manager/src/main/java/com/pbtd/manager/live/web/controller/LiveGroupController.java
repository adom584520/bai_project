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

import com.pbtd.manager.live.domain.LiveGroup;
import com.pbtd.manager.live.domain.LivePackage;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveGroupService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;


/**
 * 直播手机分组管理 
 * 
 * @author 
 *
 */
@RequestMapping("/live/Group")
@Controller
public class LiveGroupController {
	@Autowired
	private ILiveGroupService liveGroupService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/live/groupManage/index";
    }
    
    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/groupManage/edit";
    }
	/**
	 * 频道列表查询
	 * /live/Group/queryGrouplist
	 * @param qo
	 * @return
	 */
	@RequestMapping("/queryGrouplist")
	@ResponseBody
	public PageResult LiveGroupInfo() {
		PageResult pageResult = null;
		try {
			pageResult = liveGroupService.querylistallLiveGroup();
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
	@LogAnnotation(operationInfo = "直播-手机分组管理 -删除操作")
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
        return this.liveGroupService.deletes(queryParams);
    }
    /**
     * 插入记录
     *
     * @param vodGroup VodGroup实例
     * @return 被插入的记录标识
     */
	@LogAnnotation(operationInfo = "直播-手机分组管理 -添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(LiveGroup liveGroup, HttpServletRequest request) {
      this.liveGroupService.insert(liveGroup);
      return 1;
    }
	
	  /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码LiveGroup liveGroup
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/Version/page?page=1&rows=10
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, @RequestParam(value="groupname")String groupname) {
    	List<LiveGroup> data = new ArrayList<LiveGroup>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    	if(groupname != null && ! groupname.equals("")){
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("groupname", "%"+groupname+"%");
            int total = this.liveGroupService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveGroupService.find(queryParams);
    			return new PageResult(total, data);
    	}else{
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		
            int total = this.liveGroupService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveGroupService.find(queryParams);
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
    public LiveGroup load(@PathVariable("id") int id) {
    	LiveGroup liveGroup = null;
        if (id > 0) {
        	liveGroup = this.liveGroupService.load(id);
        }
        else {
        	liveGroup = new LiveGroup(
        			0
        			,null
        			,null
        			,null
        			,null
        			,null
            );
        }
        return liveGroup;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodGroup VodGroup实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-手机分组管理 -更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id,LiveGroup liveGroup) throws ServletException {
        return this.liveGroupService.update(liveGroup);
    }
    
    /**
     * 根据版本号获取手机分组
     *
     * @param id 标识
     * @return 记录
     * /live/Group/groupinterface/SD_CMCC_OTT_HUAWEI
     */
    @ResponseBody
    @RequestMapping("/groupinterface/{pro_id}")
    public List<LiveGroup> groupinterface(@PathVariable("pro_id") String str) {
    	List <LiveGroup> list = new ArrayList<>();
        if (str != null && str.length() != 0) {
        	try {
				list = this.liveGroupService.getgroup(str);
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
        }
        else{
        	logger.info("请输入参数 pro_id");  
        }
        return list;
    }

}