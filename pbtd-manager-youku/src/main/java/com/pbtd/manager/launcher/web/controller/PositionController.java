package com.pbtd.manager.launcher.web.controller;

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
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.Position;
import com.pbtd.manager.launcher.service.PositionService;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.page.QueryObject;

@Controller
@RequestMapping("/launcher/position/")
public class PositionController{
     
	@Autowired
	private PositionService positionService;
	
	/**
	 * 角色页面跳转	
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String positionPage(Model model) {
		Map<String, Object> params=new HashMap<>();
		model.addAttribute("positionlist", positionService.queryType(params));
		return "/launcher/position/index";
	}
	
	
	/**
	 * get 编辑
	 * id 标识
	 * @return 视图路径
	 */
     @RequestMapping("/edit/{id}")
     public String create(@PathVariable("id") int id,Model model){
    	 Map<String, Object> params=new HashMap<>();
 		 model.addAttribute("positionlist", positionService.queryType(params));
    	 return "/launcher/position/edit";
     }
	
	 /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    
    @RequestMapping("/load/{id}")
    @ResponseBody
    public Position load(@PathVariable("id")int id) {
    	Position position=null;
    	if(id>0){
    		position=positionService.load(id);   		
    	}else{
    		position=new Position(-1);
    	}
    	return position;   	
    }
    

    /**
     * 模糊统计符合查询条件的记录总数
     *
     * @param request 查询参数
     * @return 记录总数
     */
    @RequestMapping("/count")
    @ResponseBody
    public int count(HttpServletRequest request){   	
		return this.positionService.count(RequestUtil.asMap(request));   	
    }
 

    /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     */
  @RequestMapping("/page")
  @ResponseBody
  public PageResult page(@RequestParam(value="page")int page,@RequestParam(value="rows")int limit,HttpServletRequest request ){
	  List<Position> data=new ArrayList<Position>();
	  Map<String,Object> queryParams=RequestUtil.asMap(request, false);
	  int total=this.positionService.count(queryParams);
	  if(total==0){
		 return new PageResult(total,Collections.EMPTY_LIST);
	  }
	  try{
	  QueryObject qo=new QueryObject();
	  qo.setPage(page);
	  qo.setRows(limit);
	  queryParams.put("start", qo.getStart());
	  queryParams.put("pageSize",qo.getPageSize());	  
	  data=positionService.find(queryParams);	 
	  }catch(JsonMessageException e){
            e.printStackTrace();
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return new PageResult(total,data);	  
  }
  
  /**
   * 插入记录
   *
   * @param vodChannel VodChannel实例
   * @return 被插入的记录标识
   */
  @ResponseBody
  @RequestMapping(value ="/create", method = RequestMethod.POST)
  public int create(Position position, HttpServletRequest request) {
    this.positionService.add(position);
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
  public int edit(@PathVariable("id") int id,Position position) throws ServletException {
      return this.positionService.modify(position);
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
      return this.positionService.deletes(queryParams);
  }
}