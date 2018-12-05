package com.pbtd.manager.vod.system.controller;

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
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.system.domain.Movieurl;
import com.pbtd.manager.vod.system.service.face.IMovieurlService;

@Controller
@RequestMapping("/vod/system/vodmovie")
public class MovieurlController {
	@Autowired
	private IMovieurlService movieurlService;
    
    /**
	 * 页面跳转	
	 * 
	 * @return
	 */
    @RequestMapping(value = {"/index", ""})
	public String recommandPicPage(Model model) {
		return "/vod/system/movieurl/index";
	}
	
	/**
	 * get 编辑
	 * id 标识
	 * @return 视图路径
	 */
    
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")int id,Model model) {
		return "/vod/system/movieurl/edit";
	}

	
	 /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    public Movieurl load(@PathVariable("id") int id) {
    	Movieurl m = null;
        if (id > 0) {
        	m = this.movieurlService.load(id);
        } 
        else {
        	m =new Movieurl(-1, null, null, 1, 1);
        }
        return m;
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
        return this.movieurlService.count(RequestUtil.asMap(request, false));
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
    	List<Movieurl> data = new ArrayList<Movieurl>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.movieurlService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.movieurlService.find(queryParams);
			return new PageResult(total, data);		
    }	
    
  
/**
 * 插入记录
 *
 * @param vodChannel VodChannel实例
 * @return 被插入的记录标识
 */
@LogAnnotation(operationInfo = "点播-返回播放地址管理-添加操作")
@ResponseBody
@RequestMapping(value ="/create", method = RequestMethod.POST)
public int create(Movieurl m, HttpServletRequest request) {
  this.movieurlService.insert(m);
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
@LogAnnotation(operationInfo = "点播-返回播放地址管理-更改操作")
@ResponseBody
@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
public int edit(@PathVariable("id") int id, Movieurl m) throws ServletException {
    return this.movieurlService.update(m);
}

/**
 * POST 删除多条
 *
 * @param ids 标识列表
 * @return 被删除的记录条数
 */
@LogAnnotation(operationInfo = "点播-返回播放地址管理-删除操作")
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
    return this.movieurlService.deletes(queryParams);
}

  
  //批量上线下线
	@LogAnnotation(operationInfo = "点播-返回播放地址管理-更改状态操作")
  @ResponseBody
  @RequestMapping("/updateStatus")
  public int updateStatus(HttpServletRequest request){
  	int result=0;
  	Map<String,Object> map=new HashMap<String,Object>();
      String id=request.getParameter("id");
      String[] ids=id.split(",");
      for(String idNew:ids){
      	int id1=Integer.parseInt(idNew);
      	int status=Integer.parseInt(request.getParameter("status"));
      	map.put("id", id1);
      	map.put("status", status);
      	result=movieurlService.updateStatus(map);       	
      }
		return result;    	
  }
	
}
