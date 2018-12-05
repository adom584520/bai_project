package com.pbtd.playclick.integrate.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.base.common.mvc.MutilCustomDateEditor;
import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.integrate.domain.VodMapping;
import com.pbtd.playclick.integrate.service.face.IDictionaryService;
import com.pbtd.playclick.integrate.service.impl.VodMappingService;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;

/**
 * vod_mapping( ) 控制器
 *
 * @author admin
 */
@Controller("integrate.VodMppingController")
@RequestMapping("/integrate/mapping")
public class VodMppingController {

    @Autowired
    private VodMappingService vodmappingService;
    
    @Autowired
    private IDictionaryService dictionaryService;
    

	@Autowired
	private DictionaryController dictionary;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new MutilCustomDateEditor("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" ));
    }

    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index(ModelMap model,HttpServletRequest request) {
    	List<Map<String, Object>> cpsource=dictionary.findDictionaryname("choosecpsource", request);
		model.addAttribute("cpsource", cpsource);
    	return "/vod/mapping/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/mapping/create";
    }

    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,HttpServletRequest request,ModelMap model) {
    	List<Map<String, Object>> cpsource=dictionary.findDictionaryname("choosecpsource", request);
		model.addAttribute("cpsource", cpsource);
        return "/vod/mapping/edit";
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
        return this.vodmappingService.count(RequestUtil.asMap(request, false));
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
    	List<VodMapping> data = new ArrayList<VodMapping>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodmappingService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		VodMapping d=new VodMapping();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		
			data =this.vodmappingService.find(queryParams);
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
    public VodMapping load(@PathVariable("id") int id) {
    	VodMapping vodmapping= null;
        if (id > 0) {
        	vodmapping = this.vodmappingService.load(id);
        } 
        else {
        	vodmapping = new VodMapping(-1, null, null, null, null, null, null, 0,null,null);
        }
        return vodmapping;
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
    	List<VodMapping> vodmappings = this.vodmappingService.find(queryParams);
    	if (vodmappings.size() == 1) {
    		return vodmappings.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodmappings.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(VodMapping vodmapping, HttpServletRequest request) {
      this.vodmappingService.insert(vodmapping);
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
    public int edit(@PathVariable("id") int id, VodMapping vodmapping) throws ServletException {
        return this.vodmappingService.update(vodmapping);
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
        return this.vodmappingService.deletes(queryParams);
    }
     
    /**
     * 更改上线及下线状态
     * @param id 标识
     * @param request 查询参数
     */
    @RequestMapping("/savestatus")
    @ResponseBody
    public int savestatus(@RequestBody Map<String,Object> map) {
    	String ids =  map.get("ids").toString();
    	String type=map.get("type")==null?"-1":map.get("type").toString();
    	String[] id=ids.substring(1, ids.length()-1).trim().split(",");    			
    	VodMapping vodmapping =new VodMapping();
    	for (String i : id) {
    		vodmapping.setId(Integer.parseInt(i.trim()));
    		vodmapping.setStatus(Integer.parseInt(type));
        	 this.vodmappingService.update(vodmapping);
		}
    	return 1;
    }
    
}
