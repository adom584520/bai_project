package com.pbtd.manager.vod.system.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.RequestUtil;

import com.pbtd.manager.vod.system.domain.Logo;
import com.pbtd.manager.vod.system.service.face.ILogoService;

@Controller 
@RequestMapping("/vod/system/vodlogo")
public class LogoController {
	
	@Autowired
	private ILogoService logoService;
	
	/**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index(Model model) {
    	return "/vod/system/logo/index";
    }
    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model) {
        return "/vod/system/logo/edit";
    }
    
    /**
     *
     *
     * 
     * @return 总记录
     */
    @ResponseBody
    @RequestMapping("/page")
    public List<Map<String,Object>> page(HttpServletRequest request) {
    	List<Map<String,Object>> list=null;
    	Map<String,Object> queryParams=RequestUtil.asMap(request);
    	int total=logoService.count(queryParams);
        if (total==0) {
          list=Collections.emptyList();
        } 
        else {
        	list =logoService.find(queryParams);
        }
        return list;
    }
    
    
    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    public Logo load(@PathVariable("id") int id) {
    	Logo logo = null;
        if (id > 0) {
        	logo = this.logoService.load(id);
        } 
        else {
        	logo = new  Logo(-1);
        }
        return logo;
    }
    
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-logo管理-添加操作")
    @ResponseBody
    @RequestMapping(value ="/create", method = RequestMethod.POST)
    public int create(Logo logo,HttpServletRequest request) {
     String create_user=request.getSession().getAttribute("username").toString();
     Map<String,Object> queryParams=RequestUtil.asMap(request);
     queryParams.put("create_user",create_user);
      this.logoService.add(queryParams);
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
    @LogAnnotation(operationInfo = "点播-logo管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id,HttpServletRequest request) throws ServletException {
    	String modify_user=request.getSession().getAttribute("username").toString();
    	Map<String,Object> queryParams=RequestUtil.asMap(request);
    	queryParams.put("modify_user", modify_user);
        return this.logoService.modify(queryParams);
    }
    /**
     * id 标识
     * 上传LOGO
     */
    @LogAnnotation(operationInfo = "点播-logo管理-更改操作")
    @ResponseBody
    @RequestMapping("/updateLogo")
    public int updateLogo(HttpServletRequest request) throws ServletException {
    	String pic=request.getParameter("imgUrl");
    	int id=Integer.parseInt(request.getParameter("id"));
    	Map<String,Object> queryParams=new HashMap<String,Object>();
    	queryParams.put("logo", pic);
    	queryParams.put("id", id);
        return this.logoService.modify(queryParams);
    }
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-logo管理-删除操作")
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
        return this.logoService.delete(queryParams);
    }
    
	 
}
