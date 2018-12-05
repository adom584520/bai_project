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
import com.pbtd.manager.util.ResultBean;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.system.domain.BottomNavigation;
import com.pbtd.manager.vod.system.service.face.BottomNavigationService;

@Controller
@RequestMapping("/vod/system/bottomnavigation")
public class BottomNavigationController {

	@Autowired
	private BottomNavigationService navigationService;

	  @Autowired
	    private IDictionaryService dictionaryService;

		/**
		 * 角色页面跳转	
		 * 
		 * @return
		 */
		@RequestMapping("/index")
		public String bottomNavigationPage(Model model) {
			Map<String, Object> params=new HashMap<>();
			model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannelsystem"));	     
			return "/vod/system/bottomNavigation/index";
		}
		
		/**
		 * get 编辑
		 * id 标识
		 * @return 视图路径
		 */
		@RequestMapping("/edit/{id}")
		public String edit(@PathVariable("id")int id,Model model) {
			return "/vod/system/bottomNavigation/edit";
		}

		
		 /**
	     * 根据标识获取记录
	     *
	     * @param id 标识
	     * @return 记录
	     */
	    @ResponseBody
	    @RequestMapping("/load/{id}")	    
	    public BottomNavigation load(@PathVariable("id") int id) {
	    	BottomNavigation bottomNavigation = null;
	        if (id > 0) {
	        	bottomNavigation= this.navigationService.load(id);
	        } 
	        else {
	        	int c=this.navigationService.generatePosition(null);
	        	bottomNavigation =new BottomNavigation(-1);
	        }
	        return bottomNavigation;
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
	        return this.navigationService.count(RequestUtil.asMap(request, false));
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
	    	List<BottomNavigation> data = new ArrayList<BottomNavigation>();
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	        int total = this.navigationService.count(queryParams);
			if (total == 0) {
				return new PageResult(total, Collections.EMPTY_LIST);
			}
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
				data =this.navigationService.find(queryParams);
				return new PageResult(total, data);		
	    }	
	    
	    /**
	     * 更改imgNor
	     * @param request
	     * @return
	     */
	@LogAnnotation(operationInfo = "运营-phone底部导航管理-更改图片操作")
	@RequestMapping("/updateImgNor")
	@ResponseBody
	public ResultBean<BottomNavigation> updateImgNor(HttpServletRequest request){
		String imgNor=request.getParameter("imgUrl");
		int id=Integer.parseInt(request.getParameter("id"));		
		ResultBean resultBean=new ResultBean();
		try{
		BottomNavigation bottomNavigation=new BottomNavigation();
		bottomNavigation.setId(id);
		bottomNavigation.setImgNor(imgNor);
		navigationService.updateImg(bottomNavigation);
		resultBean.setMessage("更新成功");
		resultBean.setSuccess(true);
		}catch(Exception e){
			resultBean.setMessage("更新失败");
			resultBean.setSuccess(false);
			e.printStackTrace();
		}
		return resultBean;
		}
	
    /**
     * 更改imgSelect
     * @param request
     * @return
     */
	@LogAnnotation(operationInfo = "运营-phone底部导航管理-更改图片操作")
    @RequestMapping("/updateImgSelect")
    @ResponseBody
    public ResultBean<BottomNavigation> updateImgSelect(HttpServletRequest request){
	String imgSelect=request.getParameter("imgUrl");
	int id=Integer.parseInt(request.getParameter("id"));		
	ResultBean resultBean=new ResultBean();
	try{
	BottomNavigation bottomNavigation=new BottomNavigation();
	bottomNavigation.setId(id);
	bottomNavigation.setImgSelect(imgSelect);
	navigationService.updateImg(bottomNavigation);
	resultBean.setMessage("更新成功");
	resultBean.setSuccess(true);
	}catch(Exception e){
		resultBean.setMessage("更新失败");
		resultBean.setSuccess(false);
		e.printStackTrace();
	}
	return resultBean;
	}


	/**
	 * 精确判断是否存在记录
	 * 
	 * @param id 标识
	 * @param request 查询参数
	 * @return 记录条数
	 */
	@RequestMapping("/exist/{id}")
	@ResponseBody
	public int exist(@PathVariable("id")int id, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		List<BottomNavigation> bottomNavigation = this.navigationService.find(queryParams);
		if (bottomNavigation.size() == 1) {
			return bottomNavigation.get(0).getId() == id ? 0 : 1;
		} else {
			return bottomNavigation.size();
		}
	}
	/**
	 * 插入记录
	 *
	 * @param vodChannel VodChannel实例
	 * @return 被插入的记录标识
	 */
	@LogAnnotation(operationInfo = "运营-phone底部导航管理-添加操作")
	@ResponseBody
	@RequestMapping(value ="/create", method = RequestMethod.POST)
	public int create(BottomNavigation bottomNavigation, HttpServletRequest request) {
	  this.navigationService.add(bottomNavigation);
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
	@LogAnnotation(operationInfo = "运营-phone底部导航管理-更改操作")
	@ResponseBody
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public int edit(@PathVariable("id") int id, BottomNavigation bottomNavigation) throws ServletException {
	    return this.navigationService.modify(bottomNavigation);
	}


	/**
	 * POST 删除多条
	 *
	 * @param ids 标识列表
	 * @return 被删除的记录条数
	 */
	@LogAnnotation(operationInfo = "运营-phone底部导航管理-删除操作")
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
	    return this.navigationService.deletes(queryParams);
	}
}
