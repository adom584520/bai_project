package com.pbtd.manager.vod.tv.common.controller;

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
import com.pbtd.manager.vod.tv.common.domain.VodTvlabel;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvLabelService;

/**
 * @author zr
 */
@Controller
@RequestMapping("/vod/tv/Vodlabel")
public class VodTvLabelController {

    @Autowired()
    private IVodTvLabelService vodlabelService;
    
    
    @Autowired
    private IDictionaryService dictionaryService;
    
    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index(Model model) {
     	Map<String, Object> params=new HashMap<>();
    	params.put("levels", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechanneltv"));
     
    	return "/vod/tv/common/vodlabel/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/tv/common/vodlabel/create";
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
    	params.put("levels", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechanneltv"));
        return "/vod/tv/common/vodlabel/edit";
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
        return this.vodlabelService.count(RequestUtil.asMap(request, false));
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
    	List<VodTvlabel> data = new ArrayList<VodTvlabel>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodlabelService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodlabelService.find(queryParams);
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
    public VodTvlabel load(@PathVariable("id") int id) {
    	VodTvlabel vodlabel = null;
        if (id > 0) {
        	vodlabel = this.vodlabelService.load(id);
        } 
        else {
        	vodlabel = new VodTvlabel(-1, null, 0, 0, 0,
        			0, null, null, null, null, null,null);
        }
        return vodlabel;
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
    	List<VodTvlabel> vodlabel = this.vodlabelService.find(queryParams);
    	if (vodlabel.size() == 1) {
    		return vodlabel.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodlabel.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-tv-标签管理-添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(VodTvlabel vodlabel, HttpServletRequest request) {
      this.vodlabelService.insert(vodlabel);
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
    @LogAnnotation(operationInfo = "点播-tv-标签管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, VodTvlabel vodlabel) throws ServletException {
        return this.vodlabelService.update(vodlabel);
    }

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-tv-标签管理-删除操作")
    @ResponseBody
    @RequestMapping("/deletes")
    public int deletes(HttpServletRequest request) {
    	Map<String, Object> queryParams = new HashMap<String, Object>();
 	   String id=request.getParameter("id");
 	   String [] idarray=id.split(",");
 	  List<Integer> ids=new ArrayList<>();
 	   for(int i=0;i<idarray.length;i++){
 		  ids.add(Integer.parseInt(idarray[i]));
 	   }  
        return this.vodlabelService.deletes(queryParams,ids);
    }
    
    //批量上线下线
    @LogAnnotation(operationInfo = "点播-tv-标签管理-更改状态操作")
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
        	result=vodlabelService.updateStatus(map);       	
        }
		return result;    	
    }
    
}
