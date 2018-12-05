package com.pbtd.manager.vod.common.corner.controller;

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
import com.pbtd.manager.vod.common.corner.domain.VodCorner;
import com.pbtd.manager.vod.common.corner.service.face.IVodCornerService;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;

/**
 * @author zr
 */
@Controller 
@RequestMapping("/vod/vodCorner")
public class VodCornerController {

    @Autowired
    private IVodCornerService vodCornerService;
    
    @Autowired
    private IDictionaryService dictionaryService;
    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/vod/vodcorner/index";
    }

    @RequestMapping("/albuminfo/indexCorner/{ids}")
    public String indexCorner(@PathVariable("ids")String ids, Model model) {
    	model.addAttribute("ids", ids);
    	return "/vod/tv/album/vodtvalbuminfo/cornerindex";
    }
    
    /**
     * 模糊获取符合查询条件的分页记录
     * 角标
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     */
    @ResponseBody
    @RequestMapping("/albuminfo/page")
    public PageResult pageCorner(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
    	List<VodCorner> data = new ArrayList<VodCorner>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodCornerService.countCorner(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodCornerService.findCorner(queryParams);
			return new PageResult(total, data);
		
    }
    
    @RequestMapping("/albuminfo/phoneCorner/{ids}")
    public String phoneCorner(@PathVariable("ids")String ids, Model model) {
    	model.addAttribute("ids", ids);
    	return "/vod/phone/album/vodalbuminfo/cornerindex";
    }
    
    /**
     * 模糊获取符合查询条件的分页记录
     * 角标
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     */
    @ResponseBody
    @RequestMapping("/albuminfo/phonepage")
    public PageResult phonepageCorner(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
    	List<VodCorner> data = new ArrayList<VodCorner>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodCornerService.countCorner(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodCornerService.findCorner(queryParams);
			return new PageResult(total, data);
		
    }
    
    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/vodcorner/create";
    }

    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model) {
    	Map<String, Object> params=new HashMap<>();
        return "/vod/vodcorner/edit";
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
        return this.vodCornerService.count(RequestUtil.asMap(request, false));
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
    	List<VodCorner> data = new ArrayList<VodCorner>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodCornerService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodCornerService.find(queryParams);
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
    public VodCorner load(@PathVariable("id") int id) {
    	VodCorner vodCorner = null;
        if (id > 0) {
        	vodCorner = this.vodCornerService.load(id);
        } 
        else {
        //	int c=this.vodCornerService.generatePosition(null);
        	vodCorner = new VodCorner(-1, null, null, null, null, null, null, 1, null,0);
        }
        return vodCorner;
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
    	List<VodCorner> vodSpecial = this.vodCornerService.find(queryParams);
    	if (vodSpecial.size() == 1) {
    		return vodSpecial.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodSpecial.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-角标费包管理-新增操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(VodCorner vodCorner, HttpServletRequest request) {
      this.vodCornerService.insert(vodCorner);
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
    @LogAnnotation(operationInfo = "点播-角标费包管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, VodCorner vodCorner) throws ServletException {
        return this.vodCornerService.update(vodCorner);
    }

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-角标费包管理-删除操作")
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
        return this.vodCornerService.deletes(queryParams);
    }
    
    @LogAnnotation(operationInfo = "点播-角标费包管理-更改图片操作")
    @ResponseBody
    @RequestMapping(value = "/editimg/{id}", method = RequestMethod.POST)
    public int editimg(@PathVariable("id") int id ,HttpServletRequest request) throws ServletException  {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	String imgUrl=queryParams.get("imgUrl").toString();
    	String imgtype=queryParams.get("imgtype").toString();
    	VodCorner vodCorner=new VodCorner();
    	vodCorner.setImgurl(imgUrl);
    	vodCorner.setId(id);
		return this.vodCornerService.update(vodCorner);
    }
    
    //批量上线下线
    @LogAnnotation(operationInfo = "点播-角标费包管理-更改状态操作")
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
        	result=vodCornerService.updateStatus(map);       	
        }
		return result;    	
    }
}
