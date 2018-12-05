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

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.domain.LivePackage;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILivePackageService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;

/**
 * 直播节目包管理
 * @author YFENG
 *
 */
@RequestMapping("/live/Package")
@Controller
public class LivePackageController {
	@Autowired
	private ILivePackageService LivePackageService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	
	
	/**
	 * /live/Package/list
	 * @param qo
	 * @return
	 * 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult Live() {
		PageResult pageResult = null;
		try {
			pageResult = LivePackageService.querylistallLivePackage();
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
    	return "/live/packageManage/index";
    }
    
    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/packageManage/edit";
    }
    
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "直播-节目包管理 -删除操作")
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
        return this.LivePackageService.deletes(queryParams);
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "直播-节目包管理 -添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(LivePackage LivePackage, HttpServletRequest request){
      this.LivePackageService.insert(LivePackage);
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
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,HttpServletRequest request) {
    	List<LivePackage> data = new ArrayList<LivePackage>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
            int total = this.LivePackageService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.LivePackageService.find(queryParams);
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
    public LivePackage load(@PathVariable("id") int id) {
    	LivePackage LivePackage = null;
        if (id > 0) {
        	LivePackage = this.LivePackageService.load(id);
        }
        else {
        	LivePackage = new LivePackage(
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
        return LivePackage;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodChannel VodChannel实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-节目包管理 -更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, LivePackage LivePackage) throws ServletException {
        return this.LivePackageService.update(LivePackage);
    }
    
    /**
     * 批量上线下线
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-节目包管理 -更改状态操作")
    @ResponseBody
    @RequestMapping(value = "/editzt")
    public int editzt(HttpServletRequest request) throws ServletException { 
    	String id=request.getParameter("id");
    	String[] ids=id.split(",");   
    	int result=0;
    	for(String idnew:ids){
    		int status=Integer.parseInt(request.getParameter("status"));
    		int id1=Integer.parseInt(idnew);
        	result=LivePackageService.updateupdown(id1,status);
          }
		 return result;
    }

    /**
     *
     * @param 置顶
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-节目包管理 -置顶操作")
    @ResponseBody
    @RequestMapping(value = "/albuminfo/{id}", method = RequestMethod.GET)
    public int albuminfo(@PathVariable("id") int id) throws ServletException {
        return this.LivePackageService.updateTop(id);
    }
    
    /**
     * 根据版本号获取节目包
     *
     * @param id 标识
     * @return 记录
     * /live/Package/packageinterface/SD_CMCC_OTT_HUAWEI
     */
    @ResponseBody
    @RequestMapping("/packageinterface/{pro_id}")
    public List<LivePackage> packageinterface(@PathVariable("pro_id") String str) {
    	List <LivePackage> list = new ArrayList<>();
        if (str != null && str.length() != 0) {
        	try {
				list = this.LivePackageService.getpackage(str);
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