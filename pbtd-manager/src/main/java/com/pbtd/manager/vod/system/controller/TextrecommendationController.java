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
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.system.domain.Textrecommendation;
import com.pbtd.manager.vod.system.service.face.ITextrecommendationService;

@Controller
@RequestMapping("/vod/system/textrecommendation")
public class TextrecommendationController {
	@Autowired
	private ITextrecommendationService textrecommendationService;
	
    @Autowired
    private IDictionaryService dictionaryService;

	/**
	 * 角色页面跳转	
	 * 
	 * @return
	 */
    @RequestMapping(value = {"/index", ""})
	public String recommandPicPage(Model model) {
		Map<String, Object> params=new HashMap<>();
		params.put("levels", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));	     
		return "/vod/system/textrecommendation/index";
	}
	
	/**
	 * get 编辑
	 * id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")int id,Model model) {
		Map<String, Object> params=new HashMap<>();
    	params.put("levels", 1);
    	model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
		return "/vod/system/textrecommendation/edit";
	}

	
	 /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    
    
    public Textrecommendation load(@PathVariable("id") int id) {
    	Textrecommendation m = null;
        if (id > 0) {
        	m = this.textrecommendationService.load(id);
        } 
        else {
        	m =new Textrecommendation(-1);
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
        return this.textrecommendationService.count(RequestUtil.asMap(request, false));
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
    	List<Textrecommendation> data = new ArrayList<Textrecommendation>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.textrecommendationService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.textrecommendationService.find(queryParams);
			return new PageResult(total, data);		
    }	
    
  
/**
 * 插入记录
 *
 * @param vodChannel VodChannel实例
 * @return 被插入的记录标识
 */
@LogAnnotation(operationInfo = "点播-文字消息管理-添加操作")
@ResponseBody
@RequestMapping(value ="/create", method = RequestMethod.POST)
public int create(Textrecommendation m, HttpServletRequest request) {
  this.textrecommendationService.insert(m);
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
@LogAnnotation(operationInfo = "点播-文字消息管理-更改操作")
@ResponseBody
@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
public int edit(@PathVariable("id") int id, Textrecommendation m) throws ServletException {
    return this.textrecommendationService.update(m);
}

/**
 * POST 删除多条
 *
 * @param ids 标识列表
 * @return 被删除的记录条数
 */
@LogAnnotation(operationInfo = "点播-文字消息管理-删除操作")
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
    return this.textrecommendationService.deletes(queryParams);
}

  
  //批量上线下线
	@LogAnnotation(operationInfo = "点播-文字消息管理-更改状态操作")
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
      	result=textrecommendationService.updateStatus(map);       	
      }
		return result;    	
  }
	
    @ResponseBody
    @RequestMapping(value = "/editimg", method = RequestMethod.POST)
    public int editimg(HttpServletRequest request) throws ServletException  {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.textrecommendationService.updateimg(queryParams);
    }
}
